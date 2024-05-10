package com.xming.sbplaceholder2;

import com.xming.sbplaceholder2.command.MainCommand;
import com.xming.sbplaceholder2.event.SBPlaceholderLoadEvent;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.TypeManager;
import com.xming.sbplaceholder2.parser.type.element.StringElement;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public final class SBPlaceholder2 extends JavaPlugin implements Listener {
    public static SBPlaceholder2 plugin;
    public static Logger logger;
    public static FileConfiguration config;
    public static SBPlaceholderExpansion placeholderExpansion;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        logger = getLogger();

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            placeholderExpansion = new SBPlaceholderExpansion();
            placeholderExpansion.register();
        }

        TypeManager.getInstance().loadBuiltInTypes();
        reload();

        Objects.requireNonNull(Bukkit.getPluginCommand("sbplaceholder")).setExecutor(new MainCommand());
        Bukkit.getPluginManager().callEvent(new SBPlaceholderLoadEvent());
        logger.info("plugin loaded.");
        logger.info("by xming (qq:1360197420)");
    }

    public void reload() {
        Parser.getGlobal_variables().clear();
        Parser.loadGlobalVariables();
        saveDefaultConfig();
        reloadConfig();
        config = getConfig();
        StringElement.false_strings.clear();
        StringElement.false_strings = config.getStringList("false_strings");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        placeholderExpansion.unregister();
    }
}
