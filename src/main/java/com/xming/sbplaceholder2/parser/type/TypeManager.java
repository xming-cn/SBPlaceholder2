package com.xming.sbplaceholder2.parser.type;

import com.google.common.base.Joiner;
import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.event.TypeLoadEvent;
import com.xming.sbplaceholder2.parser.ElementMethod;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import com.xming.sbplaceholder2.parser.type.inst.VoidElement;
import com.xming.sbplaceholder2.parser.type.type.*;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;

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
    final HashMap<String, ArrayList<SBMethod>> method = new HashMap<>();
    public void register(String key, SBType<?> type) {
        method.computeIfAbsent(type.getName(), k -> new ArrayList<>());
        ParameterizedType genericSuperclass = (ParameterizedType) type.getClass().getGenericSuperclass();
        for (Type elementType : genericSuperclass.getActualTypeArguments()) {
            Class<?> element;
            if (elementType instanceof ParameterizedType) {
                element = (Class<?>) ((ParameterizedType) elementType).getRawType();
            } else {
                element = (Class<?>) elementType;
            }
            for (Method m : element.getMethods()) {
                ElementMethod annotation = m.getAnnotation(ElementMethod.class);
                if (annotation == null) continue;
                method.get(type.getName()).add(new SBMethod(annotation.name(), annotation.alias(), m, annotation.args()));
            }
        }
        TypeLoadEvent event = new TypeLoadEvent(type);
        Bukkit.getPluginManager().callEvent(event);
        if (!event.isCancelled()) types.put(key, type);
    }
    public SBMethod getMethod(SBElement<?> type, String name) {
        ArrayList<SBMethod> methods = method.get(type.getName());
        for (SBMethod method : methods) {
            if (method.name.equals(name)) {
                return method;
            } else if (ArrayUtils.contains(method.alias, name)) {
                return method;
            }
        }
        return null;
    }

    public void expand(String type, String name, String[] alias, ExpandMethod method, String[] args) {
        this.method.computeIfAbsent(type, k -> new ArrayList<>());
        this.method.get(type).add(new SBMethod(name, alias, method, args));
    }

    public static class SBMethod {
        private final String name;
        private final String[] alias;
        private final Method method;
        private final ExpandMethod expandMethod;
        private final String[] argsHint;
        public SBMethod(String name, String[] alias, Method method, String... args) {
            this.name = name;
            this.alias = alias;
            this.method = method;
            this.expandMethod = null;
            this.argsHint = args;
        }

        public SBMethod(String name, String[] alias, ExpandMethod method, String... args) {
            this.name = name;
            this.alias = alias;
            this.method = null;
            this.expandMethod = method;
            this.argsHint = args;
        }
        public SBElement<?> run(Parser parser, SBElement<?> object, EntrustInst... args) {
            if (method != null) {
                try {
                    return (SBElement<?>) method.invoke(object, parser, args);
                } catch (Exception e) {
                    SBPlaceholder2.logger.log(Level.SEVERE, "Invoke failed", e);
                    return null;
                }
            } else {
                assert expandMethod != null;
                return expandMethod.run(parser, object, args);
            }
        }

        public SBElement<?> trigger(Parser parser, SBElement<?> object, EntrustInst... args) {
            // if args hint end with "..." then it means the method can accept any number of args
            // if args hint end with "?" then it means when the args is null, autofill the void
            for (int i = 0; i < argsHint.length; i++) {
                String hint = argsHint[i];
                int length = args.length;
                if (hint.endsWith("?") || hint.endsWith("...")) {
                    if (length <= i) {
                        args = Arrays.copyOf(args, i + 1);
                        args[i] = new EntrustInst(new VoidElement("可空参数为空"));
                    }
                } else {
                    if (length <= i) {
                        SBPlaceholder2.logger.warning("error args!");
                        SBPlaceholder2.logger.warning("object " + object.toDebug() + " call method " + name);
                        SBPlaceholder2.logger.warning("hint is " + hint + " but args length is " + length);
                        return null;
                    }
                }
            }
            return run(parser, object, args);
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
        TimeType.inst.register("Time");
        DurationType.inst.register("Duration");
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
        SBType<?> sbType = types.get(type);
        StringBuilder result = new StringBuilder("§aType: " + type + " §7from " + sbType.getPlugin().getName() + "\n");
        result.append("§7").append(sbType.getDescription()).append("\n");
        result.append("§fMethods: §7\n");
        ArrayList<SBMethod> methods = this.method.get(type);
        for (SBMethod method : methods) {
            result.append("§7• §f").append(type).append(".").append(method.name).append("§f(")
                    .append(Joiner.on(", ").join(method.argsHint)).append(")\n");
            if (method.alias == null) continue;
            if (method.alias.length > 0) {
                result.append("§7  §fAlias: §7").append(Joiner.on(", ").join(method.alias)).append("\n");
            }

        }
        return result.toString();
    }
}
