package com.xming.sbplaceholder2.parser.type.type;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBType;
import com.xming.sbplaceholder2.parser.type.element.PlayerElement;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
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
    public String getDescription() {
        return "玩家, 用于获取玩家的信息。";
    }

    @Override
    public PlayerElement newInst(Parser parser, EntrustInst... insts) {
        String name = insts[0].execute(parser).asString().value;
        Player player = Bukkit.getPlayer(name);
        return player == null ? PlayerElement.voidPlayer : new PlayerElement(player);
    }
}
