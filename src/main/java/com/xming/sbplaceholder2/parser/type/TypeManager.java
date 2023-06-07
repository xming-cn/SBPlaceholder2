package com.xming.sbplaceholder2.parser.type;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.InstMethod;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import com.xming.sbplaceholder2.parser.type.inst.VoidInst;
import com.xming.sbplaceholder2.parser.type.type.*;
import org.apache.commons.lang.ArrayUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public class TypeManager {
    private static TypeManager instance = null;
    public static TypeManager getInstance() {
        if (instance == null) {
            instance = new TypeManager();
        }
        return instance;
    }
    private TypeManager() {}
    final HashMap<String, SBType<?>> types = new HashMap<>();
    final HashMap<String, HashMap<String, SBMethod>> method = new HashMap<>();
    public void register(String key, SBType<?> type) {
        types.put(key, type);
    }
    public SBMethod getMethod(SBInst<?> type, String name) {
        method.computeIfAbsent(type.getName(), k -> new HashMap<>());
        if (!method.get(type.getName()).containsKey(name)) {
            for (Method m : type.getClass().getMethods()) {
                InstMethod annotation = m.getAnnotation(InstMethod.class);
                if (annotation == null) continue;
                if (annotation.name().equalsIgnoreCase(name) ||
                        ArrayUtils.contains(annotation.alias(), name)) {
                    method.get(type.getName()).put(name, new SBMethod(m, annotation.args()));
                    break;
                }
            }
        }
        return method.get(type.getName()).get(name);
    }
    public static class SBMethod {
        private final Method method;
        private final String[] argsHint;
        public SBMethod(Method method, String... args) {
            this.method = method; this.argsHint = args;
        }
        public SBInst<?> trigger(Parser parser, SBInst<?> object, EntrustInst... args) {
            // if args hint end with "..." then it means the method can accept any number of args
            // if args hint end with "?" then it means when the args is null, autofill the void args
            for (int i = 0; i < argsHint.length; i++) {
                String hint = argsHint[i];
                int length = args.length;
                if (hint.endsWith("?") || hint.endsWith("...")) {
                    if (length <= i) {
                        args = Arrays.copyOf(args, i + 1);
                        args[i] = new EntrustInst(VoidInst.instance);
                    }
                } else {
                    if (length <= i) {
                        SBPlaceholder2.logger.warning("error args!");
                        SBPlaceholder2.logger.warning("object " + object.toDebug() + " call method " + method.getName());
                        SBPlaceholder2.logger.warning("hint is " + hint + " but args length is " + length);
                        return null;
                    }
                }
            }
            try {
                return (SBInst<?>) method.invoke(object, parser, args);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
    public void unregister(String key) {
        types.remove(key);
    }
    public void unregisterAll() {
        types.clear();
    }
    public void loadBuiltInTypes() {
        BoolType.inst.register("Bool");
        DictType.inst.register("Dict");
        ExpressionType.inst.register("Expression");
        FunctionType.inst.register("Function");
        IntType.inst.register("Int");
        ListType.inst.register("List");
        NumberType.inst.register("Number");
        PlayerType.inst.register("Player");
        StringType.inst.register("String");
        VoidType.inst.register("Void");
        TypeType.inst.register("Type");
    }
    public SBType<?> getType(String name) {
        for (String type : types.keySet()) {
            if (type.equals(name)) {
                return types.get(type);
            }
        }
        return null;
    }
    public Set<String> getTypes() {
        return types.keySet();
    }
    public String getInfo(String type) {
//        SBType<?> sbType = types.get(type);
        return "";
    }
}
