package com.xming.sbplaceholder2.parser.type.type;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.type.SBInst;
import com.xming.sbplaceholder2.parser.type.SBType;
import com.xming.sbplaceholder2.parser.type.inst.TypeInst;
import org.bukkit.plugin.Plugin;

public class TypeType extends SBType<TypeInst> {
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
    public TypeInst newInst(SBInst<?>... insts) {
        return new TypeInst(insts[0].asString().value);
    }
}
