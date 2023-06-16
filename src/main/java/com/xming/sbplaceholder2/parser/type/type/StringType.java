package com.xming.sbplaceholder2.parser.type.type;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBType;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import com.xming.sbplaceholder2.parser.type.inst.StringElement;
import org.bukkit.plugin.Plugin;

public class StringType extends SBType<StringElement> {
    public static StringType inst = new StringType();
    private StringType() {}
    @Override
    public Plugin getPlugin() {
        return SBPlaceholder2.plugin;
    }
    @Override
    public String getName() {
        return "String";
    }

    @Override
    public String getDescription() {
        return "字符串, 用于显示。";
    }

    @Override
    public StringElement newInst(Parser parser, EntrustInst... insts) {
        return insts[0].execute(parser).asString();
    }
}
