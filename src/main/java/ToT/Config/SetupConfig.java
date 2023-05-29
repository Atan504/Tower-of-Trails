package ToT.Config;

import ToT.Config.Files.Main;
import ToT.Config.Files.PlayersDataFile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;

public class SetupConfig implements Listener {
    public SetupConfig() {
    }

    public static void Setup() {
        Main.setup();
        Main.get().options().copyDefaults(true);
        Main.save();
        PlayersDataFile.setup();
        PlayersDataFile.get().options().copyDefaults(true);
        PlayersDataFile.save();
    }

    @EventHandler
    public void OnJoinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (!p.hasPlayedBefore()) {
            ConfigUtills.PlayersDataSet(p, "Info", "Status", "Normal");
            ConfigUtills.PlayersDataSet(p, "Info", "IsMoving", false);
            ConfigUtills.PlayersDataSet(p, "Info", "SubStatus", "Normal");
            ConfigUtills.PlayersDataSet(p, "Info", "Setuped", false);
            ConfigUtills.PlayersDataSet(p, "Info", "Class", "None");
            ConfigUtills.PlayersDataSet(p, "Info", "Selected Move Number", 1);
            ConfigUtills.PlayersDataSet(p, "Info", "Floor", 0);

            List<String> list = new ArrayList<>();
            list.add("null");
            ConfigUtills.PlayersDataSetList(p, "Skills", "Status", list);

            List<List<String>> list2 = new ArrayList<>();
            List<String> list22 = new ArrayList<>();
            list22.add("null");
            list2.add(list22);
            ConfigUtills.PlayersDataSetList(p, "Skills", "SubStatusList", list2);

            List<Integer> list3 = new ArrayList<>();
            list3.add(14211212);
            ConfigUtills.PlayersDataSetList(p, "Skills", "Cooldown", list3);

            List<Integer> list4 = new ArrayList<>();
            list4.add(14211212);
            ConfigUtills.PlayersDataSetList(p, "Skills", "Mana Cost", list4);

            ConfigUtills.PlayersDataSet(p, "Points", "Stat", 5);
            ConfigUtills.PlayersDataSet(p, "Stats", "Mana", 10);
            ConfigUtills.PlayersDataSet(p, "Stats", "Max Mana", 10);
            ConfigUtills.PlayersDataSet(p, "Stats", "Level", 1);
            ConfigUtills.PlayersDataSet(p, "Stats", "Xp", 1.0D);
            ConfigUtills.PlayersDataSet(p, "Stats", "MaxXp", 100.0D);
            ConfigUtills.PlayersDataSet(p, "Stats", "Strength", 1);
            ConfigUtills.PlayersDataSet(p, "Stats", "Health", 40);
            ConfigUtills.PlayersDataSet(p, "Stats", "Max Health", 40);
            ConfigUtills.PlayersDataSet(p, "Stats", "Defence", 0);
            ConfigUtills.PlayersDataSet(p, "Stats", "Speed", 0);
            ConfigUtills.PlayersDataSet(p, "Stats", "Magic", 0);
        }

    }
}