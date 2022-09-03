package net.nosadnile.flow.velocity.tasks;

import com.velocitypowered.api.proxy.Player;
import net.nosadnile.flow.velocity.FlowVelocity;
import net.nosadnile.flow.velocity.util.TabListSystem;

public class ServerTickTask {
    public static void run() {
        for (Player player : FlowVelocity.server.getAllPlayers()) {
            TabListSystem.setTabList(player);
        }
    }
}
