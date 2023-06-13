package com.xming.sbplaceholder2.parser.type.type;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBType;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import com.xming.sbplaceholder2.parser.type.inst.PlayerElement;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PlayerType extends SBType<PlayerElement> {
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
    public PlayerElement newInst(Parser parser, EntrustInst... insts) {
        String name = insts[0].execute(parser).asString().value;
        Player player = Bukkit.getPlayer(name);
        return player == null ? PlayerElement.voidPlayer : new PlayerElement(player);
    }
}
