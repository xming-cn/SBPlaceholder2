package com.xming.sbplaceholder2.parser.type.type;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.type.SBType;
import com.xming.sbplaceholder2.parser.type.inst.PlayerInst;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PlayerType extends SBType<PlayerInst> {

    @Override
    protected Plugin getPlugin() {
        return SBPlaceholder2.plugin;
    }

    @Override
    protected String getName() {
        return "Player";
    }

    @Override
    public PlayerInst newInst(String str) {
        Player player = Bukkit.getPlayer(str);
        return new PlayerInst(player == null ? Bukkit.getOfflinePlayer(str) : player);
    }
}
