package net.nosadnile.flow.api.player;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.nosadnile.flow.api.rank.PlayerRank;

public class NSNPlayer {
    private ProxiedPlayer player;
    private PlayerRank rank;

    public NSNPlayer(ProxiedPlayer player) {
        this.player = player;
    }

    public ProxiedPlayer get() {
        return player;
    }


}
