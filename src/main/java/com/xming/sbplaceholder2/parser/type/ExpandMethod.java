package com.xming.sbplaceholder2.parser.type;

import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;

public abstract class ExpandMethod {
    public abstract SBElement<?> run(Parser parser, SBElement<?> element, EntrustInst... args);
    public void register(SBType<?> type, String name, String[] alias, String... args) {
        TypeManager.getInstance().expand(type.getName(), name, alias, this, args);
    }
}
