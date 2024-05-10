package com.xming.sbplaceholder2.parser.type.type;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBType;
import com.xming.sbplaceholder2.parser.type.element.TypeElement;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import org.bukkit.plugin.Plugin;

public class TypeType extends SBType<TypeElement> {
    public static TypeType inst = new TypeType();
    private TypeType() {}

    @Override
    public Plugin getPlugin() {
        return SBPlaceholder2.plugin;
    }
    @Override
    public String getName() {
        return "Type";
    }

    @Override
    public String getDescription() {
        return "类型, 用于构造其他类型。";
    }

    public TypeElement newInst(String name) {
        return new TypeElement(name);
    }
    @Override
    public TypeElement newInst(Parser parser, EntrustInst... insts) {
        return newInst(insts[0].execute(parser).asString().value);
    }
}
