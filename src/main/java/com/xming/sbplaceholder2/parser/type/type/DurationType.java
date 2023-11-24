package com.xming.sbplaceholder2.parser.type.type;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBType;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import com.xming.sbplaceholder2.parser.type.inst.DurationElement;
import org.bukkit.plugin.Plugin;

public class DurationType extends SBType<DurationElement> {
    public static DurationType inst = new DurationType();
    private DurationType() {}

    @Override
    public Plugin getPlugin() {
        return SBPlaceholder2.plugin;
    }

    @Override
    public String getName() {
        return "Duration";
    }

    @Override
    public String getDescription() {
        return "持续时间。";
    }

    @Override
    public DurationElement newInst(Parser parser, EntrustInst... insts) {
        // 将 "1h 1m 1s" 转换为 3,661,000
        // 每 1ms 转换为 1
        // 1h 1m 1s 转换为 3,661,000
        // 1h 1m 转换为 3,600,000
        String time = insts[0].execute(parser).asString().value;
        String[] split = time.split(" ");
        Long ans = 0L;
        for (String s: split) {
            long l = Long.parseLong(s.substring(0, s.length() - 1));
            switch (s.charAt(s.length()-1)) {
                case 's':
                    ans += l * 1000;
                    break;
                case 'm':
                    ans += l * 60 * 1000;
                    break;
                case 'h':
                    ans += l * 60 * 60 * 1000;
                    break;
                case 'd':
                    ans += l * 24 * 60 * 60 * 1000;
                    break;
                case 'w':
                    ans += l * 7 * 24 * 60 * 60 * 1000;
                    break;
                case 'y':
                    ans += l * 365 * 24 * 60 * 60 * 1000;
                    break;
                case 'M':
                    ans += l * 30 * 24 * 60 * 60 * 1000;
                    break;
                case 'Q':
                    ans += l * 3 * 30 * 24 * 60 * 60 * 1000;
                    break;
                default:
                    ans += Long.parseLong(s);
                    break;
            }
        }
        return new DurationElement(ans);
    }
}
