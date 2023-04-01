package net.nosadnile.flow.purpur.arena.manager;

import com.google.gson.JsonObject;
import net.nosadnile.flow.purpur.api.bedwars.CachedArena;
import net.nosadnile.flow.purpur.socket.ArenaSocketTask;

import java.util.LinkedList;
import java.util.UUID;

public class TpRequest {

    private static final LinkedList<TpRequest> requests = new LinkedList<>();
    private final UUID requester;
    private final String target;
    private final long millis;
    private CachedArena arena = null;

    public TpRequest(UUID requester, String target) {
        this.requester = requester;
        this.target = target;
        requests.add(this);

        JsonObject jo = new JsonObject();
        jo.addProperty("type", "Q");
        jo.addProperty("name", target);
        jo.addProperty("requester", requester.toString());

        for (ArenaSocketTask ast : ArenaManager.getInstance().getSocketByServer().values()) {
            ast.getOut().println(jo);
        }
        this.millis = System.currentTimeMillis() + 3000;
    }

    public static TpRequest getTpRequest(UUID requester) {
        for (TpRequest tr : requests) {
            if (tr.requester.equals(requester)) {
                if (tr.millis > System.currentTimeMillis()) {
                    return tr;
                } else {
                    requests.remove(tr);
                    return null;
                }
            }
        }
        return null;
    }

    public CachedArena getArena() {
        return arena;
    }

    public void setArena(CachedArena arena) {
        this.arena = arena;
    }

    public String getTarget() {
        return target;
    }
}
