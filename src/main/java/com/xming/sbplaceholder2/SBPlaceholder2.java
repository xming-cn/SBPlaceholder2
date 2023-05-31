package com.xming.sbplaceholder2;

import com.xming.sbplaceholder2.parser.type.TypeManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class SBPlaceholder2 extends JavaPlugin {
    public static Plugin plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new SBPlaceholderExpansion().register();
        }

        TypeManager.getInstance().loadBuiltInTypes();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
