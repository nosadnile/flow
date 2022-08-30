package net.nosadnile.flow.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import org.slf4j.Logger;

@Plugin(
        id = "flow-velocity",
        name = "Flow",
        version = "1.2.0",
        description = "Powering the NoSadNile Network since 2022.",
        url = "https://www.nosadnile.net/",
        authors = {"RedstoneWizard08", "NoSadBeHappy", "KingGoldGolem", "EverestPlayer", "CloudWolf818"}
)
public class FlowVelocity {

    @Inject
    private Logger logger;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
    }
}
