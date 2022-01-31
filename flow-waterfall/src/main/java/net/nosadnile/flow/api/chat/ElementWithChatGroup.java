package net.nosadnile.flow.api.chat;

public class ElementWithChatGroup {
    public ChatGroup currentGroup = ChatGroup.GLOBAL;

    public ChatGroup getCurrentGroup() {
        return currentGroup;
    }

    public void setCurrentGroup(ChatGroup currentGroup) {
        this.currentGroup = currentGroup;
    }

    public boolean isInGroup() {
        return !(currentGroup == null);
    }
}
