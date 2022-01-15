package net.nosadnile.flow.api.messaging;

public abstract class MessageHandler {
    public abstract void onRecieved(MessageData data);
}
