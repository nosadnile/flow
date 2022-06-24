package net.nosadnile.flow.command;

import com.github.juliarn.npc.NPC;
import com.github.juliarn.npc.profile.Profile;
import net.nosadnile.flow.FlowSpigot;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class SpawnNPC implements CommandExecutor {

    public Profile createProfile(String name) {
        Profile profile = new Profile(UUID.fromString("111dff66-087b-4948-bbd4-f47ed46af6ac"));
        profile.complete();

        profile.setName(name);
        profile.setUniqueId(new UUID(FlowSpigot.random.nextLong(), 0));

        return profile;
    }

    public void appendNPC(Location location, String name) {
        NPC npc = NPC.builder()
                .profile(this.createProfile(name))
                .location(location)
                .imitatePlayer(false)
                .lookAtPlayer(true)
                .build(FlowSpigot.npcPool);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 0) {
                sender.sendMessage("/npc <name|kill>");
            } else {
                if (Objects.equals(args[0], "kill")) {
                    FlowSpigot.npcPool.getNPCs().forEach((npc) -> {
                        FlowSpigot.npcPool.removeNPC(npc.getEntityId());
                    });
                } else {
                    Player player = (Player) sender;
                    this.appendNPC(player.getLocation(), args[0]);
                }
            }
        }

        return true;
    }
}
