package com.xming.sbplaceholder2.command;

import com.xming.sbplaceholder2.parser.type.TypeManager;
import com.xming.sbplaceholder2.parser.type.type.ExpressionType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MainCommand implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        switch (args[0]) {
            case "cache" -> {
                for (String str : ExpressionType.cache.keySet()) {
                    sender.sendMessage(str);
                    sender.sendMessage("| " + ExpressionType.cache.get(str));
                }
                return true;
            }
            case "test" -> {
                return true;
            }
            case "type" -> {
                sender.sendMessage(TypeManager.getInstance().getInfo(args[1]));
                return true;
            }
        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        switch (args.length) {
            case 1 -> {
                return List.of("cache", "test", "type");
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
