package net.nosadnile.flow.api.messaging;

import com.google.common.io.ByteArrayDataInput;
import net.md_5.bungee.api.connection.Connection;

public class MessageData {
    private String channel;
    private byte[] data;
    private Connection reciever;
    private Connection sender;
    private ByteArrayDataInput dataInput;

    public MessageData(String channel, byte[] data, Connection reciever, Connection sender, ByteArrayDataInput in) {
        this.channel = channel;
        this.data = data;
        this.reciever = reciever;
        this.sender = sender;
        this.dataInput = in;
    }

    public String getChannel() {
        return channel;
    }

    public String getTag() {
        return channel;
    }

    public byte[] getData() {
        return data;
    }

    public Connection getReciever() {
        return reciever;
    }

    public Connection getSender() {
        return sender;
    }

    public ByteArrayDataInput getDataInput() {
        return dataInput;
    }
}
