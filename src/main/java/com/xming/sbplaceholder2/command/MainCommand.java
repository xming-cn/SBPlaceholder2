package com.xming.sbplaceholder2.command;

import com.google.common.base.Joiner;
import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.TypeManager;
import com.xming.sbplaceholder2.parser.type.type.ExpressionType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.logging.Level;

public class MainCommand implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        switch (args[0]) {
            case "cache":
                for (String str : ExpressionType.cache.keySet()) {
                    sender.sendMessage(str);
                    sender.sendMessage("| " + ExpressionType.cache.get(str));
                }
                break;
            case "test":
                String join = Joiner.on(' ').join(args).substring(args[0].length() + 1);
                String result;
                try {
                    Parser parser = new Parser(join, null, -1);
                    result = parser.parse(sender instanceof Player ? (Player) sender : null).asString().value;
                    sender.sendMessage("§a解析结果 §f" + result);
                    sender.sendMessage("§f解析用时 §e" + parser.time() + "ms");
                } catch (Exception e) {
                    sender.sendMessage("§c解析时发生错误 " + e.getMessage());
                    sender.sendMessage("§c详细错误报告已发送至控制台");
                    SBPlaceholder2.logger.log(Level.SEVERE, "报错表达式 " + join);
                    SBPlaceholder2.logger.log(Level.SEVERE, e.getMessage(), e);
                }
                break;
            case "process":
                String exp = Joiner.on(' ').join(args).substring(args[0].length() + 1);
                String exp_result;
                try {
                    Parser parser = new Parser(exp, null, 100);
                    exp_result = parser.parse(sender instanceof Player ? (Player) sender : null).asString().value;
                    sender.sendMessage("§a解析结果 §f" + exp_result);
                    sender.sendMessage("§f解析用时 §e" + parser.time() + "ms");
                } catch (Exception e) {
                    sender.sendMessage("§c解析时发生错误 " + e.getMessage());
                    sender.sendMessage("§c详细错误报告已发送至控制台");
                    SBPlaceholder2.logger.log(Level.SEVERE, "报错表达式 " + exp);
                    SBPlaceholder2.logger.log(Level.SEVERE, e.getMessage(), e);
                }
                break;
            case "type":
                String info = TypeManager.getInstance().getInfo(args[1]);
                for (String s : info.split("\n")) {
                    sender.sendMessage(s);
                }
                break;
            case "types":
                Set<String> types = TypeManager.getInstance().getTypes();
                sender.sendMessage("§fTypes(§9" + types.size() + "§f): §a" + Joiner.on("§7, §a").join(types));
                break;
            case "global":
                if (Parser.getGlobal_variables() == null) {
                    Parser.loadGlobalVariables();
                }
                sender.sendMessage("§fGlobal Variables(§9" + Parser.getGlobal_variables().size() + "§f): §a"
                        + Joiner.on("§7, §a").join(Parser.getGlobal_variables().keySet()));
                break;
            case "reload":
                Parser.getGlobal_variables().clear();
                Parser.loadGlobalVariables();
                sender.sendMessage("§aReload Success!");
                break;
        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        switch (args.length) {
            case 1:
                return Arrays.asList("cache", "test", "process", "type", "global", "types", "reload");
            case 2 :
                switch (args[0]) {
                    case "type":
                        return new ArrayList<>(TypeManager.getInstance().getTypes());
                    case "test": case "process":
                        return Collections.singletonList("<表达式>");
                }
        }
        return Collections.emptyList();
    }
}
