package com.xming.sbplaceholder2.parser.type;

import com.xming.sbplaceholder2.parser.type.type.*;

import java.util.HashMap;

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
    public void register(String key, SBType<?> type) {
        types.put(key, type);
    }
    public void unregister(String key) {
        types.remove(key);
    }
    public void unregisterAll() {
        types.clear();
    }
    public void loadBuiltInTypes() {
        types.put("Bool", new BoolType());
        types.put("Int", new IntType());
        types.put("Number", new NumberType());
        types.put("String", new StringType());
        types.put("Expression", new ExpressionType());
        types.put("Player", new PlayerType());
    }
    public SBType<?> getType(String name) {
        for (String type : types.keySet()) {
            if (type.equals(name)) {
                return types.get(type);
            }
        }
        return null;
    }
}
