package com.xming.sbplaceholder2.command;

import com.google.common.base.Joiner;
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

import java.util.List;
import java.util.Set;

public class MainCommand implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        switch (args[0]) {
            case "cache" -> {
                for (String str : ExpressionType.cache.keySet()) {
                    sender.sendMessage(str);
                    sender.sendMessage("| " + ExpressionType.cache.get(str));
                }
            }
            case "test" -> {
                String join = Joiner.on(' ').join(args).substring(args[0].length() + 1);
                Parser parser = new Parser(join, null, -1);
                sender.sendMessage(
                        parser.parse(sender instanceof Player p ? p : null).asString().value
                );
            }
            case "type" -> {
                sender.sendMessage(TypeManager.getInstance().getInfo(args[1]));
            }
            case "types" -> {
                Set<String> types = TypeManager.getInstance().getTypes();
                sender.sendMessage("§fTypes(§9" + types.size() + "§f): §a" + Joiner.on("§7, §a").join(types));
            }
            case "global" -> {
                if (Parser.global_variables == null) {
                    Parser.loadGlobalVariables();
                }
                sender.sendMessage("§fGlobal Variables(§9" + Parser.global_variables.size() + "§f): §a"
                        + Joiner.on("§7, §a").join(Parser.global_variables.keySet()));
            }
            case "reload" -> {
                Parser.global_variables.clear();
                Parser.loadGlobalVariables();
                sender.sendMessage("§aReload Success!");
            }
        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        switch (args.length) {
            case 1 -> {
                return List.of("cache", "test", "type", "global", "types", "reload");
            }
            case 2 -> {
                switch (args[0]) {
                    case "type" -> {
                        return (List<String>) TypeManager.getInstance().getTypes();
                    }
                    case "test" -> {
                        return List.of("expression");
                    }
                }
            }
        }
        return List.of();
    }
}
