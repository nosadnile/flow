package net.nosadnile.flow.purpur.sidebar;

import me.catcoder.sidebar.ProtocolSidebar;
import me.catcoder.sidebar.Sidebar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.nosadnile.flow.purpur.FlowPurpur;
import net.nosadnile.flow.purpur.team.Team;

import java.util.Map;

public class SidebarManager {
    public static Sidebar<Component> SIDEBAR;

    public static void createSidebar(Map<Team, PlayerCount> teams) {
        TextComponent name = Component.text("==== ", NamedTextColor.GRAY)
                .append(Component.text("NoSadNile Network", NamedTextColor.GOLD))
                .append(Component.text(" ====", NamedTextColor.GRAY));

        SIDEBAR = ProtocolSidebar.newAdventureSidebar(name, FlowPurpur.plugin);

        updateSidebar(teams);

        SIDEBAR.updateLinesPeriodically(0, 10);
    }

    public static void createSidebar() {
        createSidebar(Map.of(
                new Team(NamedTextColor.RED), new PlayerCount(),
                new Team(NamedTextColor.BLUE), new PlayerCount(),
                new Team(NamedTextColor.GREEN), new PlayerCount(),
                new Team(NamedTextColor.YELLOW), new PlayerCount()
        ));
    }

    public static void updateSidebar(Map<Team, PlayerCount> teams) {
        SIDEBAR.addBlankLine();
        SIDEBAR.addLine(Component.text("       play.nosadnile.net", NamedTextColor.AQUA));
        SIDEBAR.addBlankLine();

        for (Map.Entry<Team, PlayerCount> entry : teams.entrySet()) {
            NamedTextColor color = entry.getValue().value > 0 ? NamedTextColor.DARK_GREEN : NamedTextColor.DARK_RED;
            TextComponent check = entry.getValue().value > 0 ? Component.text("\u2714", NamedTextColor.GREEN) : Component.text("\u2716", NamedTextColor.RED);

            TextComponent comp = Component.text("[", color)
                    .append(check)
                    .append(Component.text("] ", color))
                    .append(Component.text(entry.getKey().getNameCapitalized() + " Team", entry.getKey().color))
                    .append(Component.text(" [", NamedTextColor.DARK_AQUA))
                    .append(Component.text(entry.getValue().value, NamedTextColor.AQUA))
                    .append(Component.text(" / ", NamedTextColor.DARK_AQUA))
                    .append(Component.text(entry.getValue().max, NamedTextColor.AQUA))
                    .append(Component.text("]", NamedTextColor.DARK_AQUA));


            SIDEBAR.addLine(comp);
        }

        SIDEBAR.addBlankLine();
    }
}
