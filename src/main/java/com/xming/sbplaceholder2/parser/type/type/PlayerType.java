package com.xming.sbplaceholder2.parser.type.type;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.type.SBInst;
import com.xming.sbplaceholder2.parser.type.SBType;
import com.xming.sbplaceholder2.parser.type.inst.PlayerInst;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PlayerType extends SBType<PlayerInst> {
    public static PlayerType inst = new PlayerType();
    private PlayerType() {}

    @Override
    public Plugin getPlugin() {
        return SBPlaceholder2.plugin;
    }

    @Override
    public String getName() {
        return "Player";
    }

    @Override
    public PlayerInst newInst(SBInst<?>... insts) {
        String name = insts[0].asString().value;
        Player player = Bukkit.getPlayer(name);
        return player == null ? PlayerInst.voidPlayer : new PlayerInst(player);
    }
}
