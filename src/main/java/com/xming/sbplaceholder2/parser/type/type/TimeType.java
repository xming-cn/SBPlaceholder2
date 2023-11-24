package com.xming.sbplaceholder2.parser.type.type;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBElement;
import com.xming.sbplaceholder2.parser.type.SBType;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import com.xming.sbplaceholder2.parser.type.inst.TimeElement;
import org.apache.commons.lang.StringUtils;
import org.bukkit.plugin.Plugin;

import java.text.SimpleDateFormat;

public class TimeType extends SBType<TimeElement> {
    public static TimeType inst = new TimeType();
    private TimeType() {}
    @Override
    public Plugin getPlugin() {
        return SBPlaceholder2.plugin;
    }
    @Override
    public String getName() {
        return "Time";
    }

    @Override
    public String getDescription() {
        return "毫秒级时间戳";
    }

    @Override
    public TimeElement newInst(Parser parser, EntrustInst... insts) {
        if (insts.length == 1) {
            SBElement<?> execute = insts[0].execute(parser);
            if (StringUtils.isBlank(execute.asString().value)) return new TimeElement();
            return new TimeElement(execute.asInt().value);
        } else if (insts.length == 2) {
            // insts[0] time  insts[1] fotmat
            // parse time object from insts
            String time = insts[0].execute(parser).asString().value;
            String format = insts[1].execute(parser).asString().value;
            try {
                return new TimeElement(new SimpleDateFormat(format).parse(time).getTime());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("Time parse failed");
    }
}
