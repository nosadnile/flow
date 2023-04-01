package net.nosadnile.flow.velocity.bans;

import net.nosadnile.flow.api.time.TimeUnit;
import net.nosadnile.flow.velocity.config.BanConfig;
import org.javatuples.Pair;

public class BanManager {
    public BanList banList;

    public void ban(String player) {
        this.banList.add(player);

        this.save();
    }

    public void tempBan(String player, TimeUnit unit, int time) {
        this.banList.addTemp(player, new Pair<>(unit, time));
    }

    public void load() throws Exception {
        this.banList = BanConfig.getBanList();
    }

    public void save() {
        BanConfig.updateBanList(this.banList);
    }
}
