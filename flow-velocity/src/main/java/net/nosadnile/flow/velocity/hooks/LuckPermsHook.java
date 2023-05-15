package net.nosadnile.flow.velocity.hooks;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;

public class LuckPermsHook {
    public static LuckPerms getLuckPerms() {
        return LuckPermsProvider.get();
    }
}
