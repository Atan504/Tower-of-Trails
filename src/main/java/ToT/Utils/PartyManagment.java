package ToT.Utils;

import ToT.Data.SpigotData;
import ToT.Objects.TPlayer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Stream;

public class PartyManagment implements Serializable {

    static ScoreboardManager scoreboardManager;
    static Scoreboard scoreboard;
    static Objective objective;

    public static void updatePartyScoreboard(Player player) {
        scoreboardManager = Bukkit.getScoreboardManager();

        ArrayList<UUID> party = PartyManagment.getParty(player.getUniqueId());

        if(!PartyManagment.inParty(party, player.getUniqueId())) {
            if(scoreboard == null) return;
            Objective objective = scoreboard.getObjective(DisplaySlot.SIDEBAR);
            if (objective != null) {
                scoreboard.clearSlot(DisplaySlot.SIDEBAR);
                objective.unregister();
                player.setScoreboard(scoreboard);
            }
            return;
        }


        assert scoreboardManager != null;
        scoreboard = scoreboardManager.getNewScoreboard();
        objective = scoreboard.registerNewObjective("party", "dummy", ChatColor.GOLD + "Tower of Trails");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        // Clear the existing entries on the scoreboard
        scoreboard.getEntries().forEach(scoreboard::resetScores);

        ArrayList<UUID> members = getMembers(party);

        ArrayList<String> list = new ArrayList<>();

        ArrayList<Integer> lvls = new ArrayList<>();

        members.forEach(p -> {
            TPlayer data = getData(p);
            int lvl = data.getLvl();
            lvls.add(lvl);
        });

        list.add(ChatColor.RESET + " ");
        list.add(ChatColor.YELLOW + String.valueOf(ChatColor.BOLD) + "Party: " + ChatColor.GOLD + "[Lv. " + lvls.stream().mapToInt(Integer::intValue).sum() + "]");
        members.forEach(uuid -> {
            Player p = Bukkit.getServer().getPlayer(uuid);
            OfflinePlayer p2 = Bukkit.getServer().getOfflinePlayer(uuid);
            TPlayer data = getData(uuid);
            int lvl = data.getLvl();

            String hp = HealthBar(p);
            if(ChatColor.stripColor(hp).equals("[||0||]")) hp = ChatColor.DARK_RED + "[" + ChatColor.RED + "☠" + ChatColor.DARK_RED + "]";

            if (p != null) list.add(ChatColor.YELLOW + "- " + hp + " " + ChatColor.RESET + p.getName().substring(0, 8) + ChatColor.GRAY + " [" + lvl + "]");
            else list.add(ChatColor.YELLOW + "- " + ChatColor.GRAY + p2.getName());
        });
        list.add("");

        for (int i = 0; i < list.size(); i++) {
            String entry = list.get(i);
            Team team = scoreboard.getTeam("party.line." + i);
            if (team == null) {
                team = scoreboard.registerNewTeam("party.line." + i);
            } else {
                team.unregister();
            }
            team.addEntry(entry);

            // Display an empty score for each entry
            objective.getScore(entry).setScore(-i);
        }

        player.setScoreboard(scoreboard);
    }


    public static String HealthBar(Entity entity) {
        // Construct the health bar string
        Data stats = new Data(entity.getUniqueId());
        double health = stats.hp;
        StringBuilder healthBar = new StringBuilder(ChatColor.DARK_RED + "[");
        int barSize = 4;
        double maxHealth = stats.max_hp;
        double percentage = health / maxHealth;
        int filledBars = (int) (percentage * barSize);

        DecimalFormat decimalFormat = new DecimalFormat("0");
        String formattedHealth = decimalFormat.format(health);

        for(var i = 0; i < 4; i++) {
            if(i == 2) healthBar.append(ChatColor.RED).append(formattedHealth);
            if(i <= filledBars) healthBar.append(ChatColor.RED).append("|");
            else healthBar.append(ChatColor.GRAY).append("|");
        }
        healthBar.append(ChatColor.DARK_RED).append("]");

        return healthBar.toString();
    }


    public static TPlayer getData(UUID uuid) {
        SpigotData.getInstance().enterEntity(uuid);

        return ((TPlayer) SpigotData.getInstance().getEntity(uuid));
    }

    public static ArrayList<UUID> getParty(UUID uuid) {

        Stream<UUID> stream = SpigotData.getInstance().getAll().stream().filter(id -> {
            TPlayer data = getData(id);
            ArrayList<UUID> party = data.getParty();

            return party.contains(uuid);
        });

        ArrayList<UUID> list = new ArrayList<>(stream.toList());

        if(list.size() == 0) return new ArrayList<>();

        UUID owner = list.get(0);
        TPlayer data = getData(owner);

        return new ArrayList<>(data.getParty());
    }

    public static Boolean inParty(ArrayList<UUID> party, UUID uuid) {
        return party.contains(uuid);
    }

    public static Boolean isOwner(ArrayList<UUID> party, UUID uuid) {
        return getOwner(party).equals(uuid);
    }

    public static UUID getOwner(ArrayList<UUID> party) {
        Player player = Bukkit.getServer().getPlayer(party.get(0));
        if(player == null) {
            OfflinePlayer player2 = Bukkit.getServer().getOfflinePlayer(party.get(0));
            return player2.getUniqueId();
        } else {
            return player.getUniqueId();
        }
    }

    public static ArrayList<UUID> getMembers(ArrayList<UUID> party) {
        return new ArrayList<>(party.stream().filter(Objects::nonNull).toList());
    }

    public static UUID getPlayer(ArrayList<UUID> members, String username) {

        Stream<UUID> stream = members.stream().filter(p -> {
            Player p2 = Bukkit.getServer().getPlayer(p);
            OfflinePlayer p3 = Bukkit.getServer().getOfflinePlayer(p);

            if(p2 != null) return p2.getName().equals(username);
            else return Objects.equals(p3.getName(), username);
        });

        ArrayList<UUID> list = new ArrayList<>(stream.toList());

        if(list.size() == 0) return null;
        return list.get(0);
    }

}
