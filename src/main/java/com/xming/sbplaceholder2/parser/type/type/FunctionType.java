package com.xming.sbplaceholder2.parser.type.type;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBElement;
import com.xming.sbplaceholder2.parser.type.SBType;
import com.xming.sbplaceholder2.parser.type.element.FunctionElement;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class FunctionType extends SBType<FunctionElement> {
    public static FunctionType inst = new FunctionType();
    private FunctionType() {}
    @Override
    public Plugin getPlugin() {
        return SBPlaceholder2.plugin;
    }
    @Override
    public String getName() {
        return "Function";
    }

    @Override
    public String getDescription() {
        return "函数, 用于存储一段代码。";
    }

    @Override
    public FunctionElement newInst(Parser parser, EntrustInst... insts) {
        ArrayList<String> args = new ArrayList<>();
        for (int i = 0; i < insts.length - 1; i++) {
            args.add(insts[i].execute(parser).asString().value);
        }
        return new FunctionElement(
                (Parser p, SBElement<?>... inst) -> {
                    for (int i = 0; i < args.size(); i++) {
                        p.getVariables().put(args.get(i), inst[i]);
                    }
                    return insts[insts.length-1].execute(p);
                }
        );
    }
}
