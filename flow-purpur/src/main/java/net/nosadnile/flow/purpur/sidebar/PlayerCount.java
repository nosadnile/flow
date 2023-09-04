package net.nosadnile.flow.purpur.sidebar;

public class PlayerCount {
    public int value;
    public int max;

    public PlayerCount(int value, int max) {
        this.value = value;
        this.max = max;
    }

    public PlayerCount(int value) {
        this(value, value);
    }

    public PlayerCount() {
        this(4);
    }
}
