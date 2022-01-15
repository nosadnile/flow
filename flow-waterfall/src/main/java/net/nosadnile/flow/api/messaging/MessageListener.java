package net.nosadnile.flow.api.messaging;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.nosadnile.flow.FlowWaterfall;

public class MessageListener implements Listener {
    @EventHandler
    public void onPluginMessage(PluginMessageEvent e) {
        if(!e.getTag().equalsIgnoreCase("nsn:proxy")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(e.getData());
        String subChannel = in.readUTF();
        MessageManager manager = FlowWaterfall.messageManager;
        if(manager.isRegistered(subChannel)) {
            manager.getHandler(subChannel).onRecieved(new MessageData(subChannel, e.getData(), e.getReceiver(), e.getSender(), in));
        }
    }
}
