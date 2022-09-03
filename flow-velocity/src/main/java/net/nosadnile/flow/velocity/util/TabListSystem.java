package net.nosadnile.flow.velocity.util;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.player.TabList;
import com.velocitypowered.api.proxy.player.TabListEntry;
import com.velocitypowered.api.util.GameProfile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.nosadnile.flow.velocity.FlowVelocity;

import java.util.ArrayList;
import java.util.UUID;

public class TabListSystem {
    public static int colorIndex = 0;

    public static NamedTextColor[] colors = {
            NamedTextColor.RED,
            NamedTextColor.GOLD,
            NamedTextColor.YELLOW,
            NamedTextColor.GREEN,
            NamedTextColor.AQUA,
            NamedTextColor.BLUE,
            NamedTextColor.LIGHT_PURPLE,
            NamedTextColor.DARK_PURPLE
    };

    public static void setTabList(Player player) {
        TabList tabList = player.getTabList();

        if (tabList == null) return;

        tabList.clearHeaderAndFooter();
        tabList.setHeaderAndFooter(
                getHeaderMessage(),
                ColorUtil.translateColorCodes('&', " \n&6play.nosadnile.net\n ")
        );

        for (TabListEntry entry : tabList.getEntries()) {
            tabList.removeEntry(entry.getProfile().getId());
        }

        for (Player target : FlowVelocity.server.getAllPlayers()) {
            TabListEntry.Builder builder = TabListEntry.builder();
            builder.profile(target.getGameProfile());
            builder.displayName(ColorUtil.translateColorCodes('&', "&7[&fDEFAULT&7]&f " + target.getUsername()));
            builder.tabList(tabList);

            tabList.addEntry(builder.build());
        }
    }

    public static TabListEntry createEmptyEntry(TabList tabList) {
        GameProfile profile = new GameProfile(UUID.randomUUID(), "", new ArrayList<>());

        TabListEntry.Builder emptyBuilder = TabListEntry.builder();
        emptyBuilder.profile(profile);
        emptyBuilder.displayName(Component.text(""));
        emptyBuilder.tabList(tabList);

        return emptyBuilder.build();
    }

    public static Component getHeaderMessage() {
        int currentIndex = colorIndex;
        Component text = Component.text(" \n");

        char[] chars = "NoSadNile Network".toCharArray();

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == ' ') {
                text = text.append(Component.text(" "));
                continue;
            }

            text = text.append(Component.text(chars[i]).color(colors[currentIndex]));

            currentIndex = getNextColorIndex(currentIndex);
        }

        colorIndex++;

        if (colorIndex >= (colors.length - 1)) {
            colorIndex = 0;
        }

        return text.append(Component.text("\n "));
    }

    public static int getNextColorIndex(int currentIndex) {
        if (currentIndex == colors.length - 1) {
            return 0;
        } else {
            return currentIndex + 1;
        }
    }
}
