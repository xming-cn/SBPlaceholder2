package com.xming.sbplaceholder2;

import com.xming.sbplaceholder2.command.MainCommand;
import com.xming.sbplaceholder2.event.SBPlaceholderLoadEvent;
import com.xming.sbplaceholder2.parser.type.TypeManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public final class SBPlaceholder2 extends JavaPlugin implements Listener {
    public static Plugin plugin;
    public static Logger logger;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        logger = getLogger();
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new SBPlaceholderExpansion().register();
        }

        Objects.requireNonNull(Bukkit.getPluginCommand("sbplaceholder")).setExecutor(new MainCommand());
        TypeManager.getInstance().loadBuiltInTypes();
        Bukkit.getPluginManager().callEvent(new SBPlaceholderLoadEvent());
        logger.info("plugin loaded.");
        logger.info("by xming (qq:1360197420)");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
