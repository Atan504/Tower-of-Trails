package ToT.System.DeathScreen;

import ToT.Objects.TPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;

import static ToT.Main.plugin;
import static ToT.Utils.Utils.getPlayerData;

public class Utils {

    public static void startCountdown(Player player) {

        new BukkitRunnable() {
            int count = 3;

            @Override
            public void run() {
                if (count > 0) {

                    TPlayer data = getPlayerData(player.getUniqueId());

                    ArrayList<UUID> party = ToT.System.PartyManagment.Utils.getParty(player.getUniqueId());

                    data.setDeath(true);

                    if(ToT.System.PartyManagment.Utils.inParty(party, player.getUniqueId())) {
                        ArrayList<UUID> members = ToT.System.PartyManagment.Utils.getMembers(party);

                        members.forEach(uuid -> {
                            Player p = Bukkit.getServer().getPlayer(uuid);
                            if(p != null) ToT.System.PartyManagment.Utils.updatePartyScoreboard(p);
                        });
                    }

                    player.sendTitle(ChatColor.RED + "☠ YOU DIED ☠", ChatColor.RED + String.valueOf(count), 0, 20, 0);
                    count--;
                } else {

                    TPlayer data = getPlayerData(player.getUniqueId());

                    data.setDeath(false);

                    ArrayList<UUID> party = ToT.System.PartyManagment.Utils.getParty(player.getUniqueId());

                    if(ToT.System.PartyManagment.Utils.inParty(party, player.getUniqueId())) {
                        ArrayList<UUID> members = ToT.System.PartyManagment.Utils.getMembers(party);

                        members.forEach(uuid -> {
                            Player p = Bukkit.getServer().getPlayer(uuid);
                            if(p != null) ToT.System.PartyManagment.Utils.updatePartyScoreboard(p);
                        });
                    }

                    World world = Bukkit.getWorld("Void");

                    player.sendMessage(ChatColor.GREEN + "You respawned!");
                    player.setInvisible(false);
                    player.setInvulnerable(false);
                    player.teleport(world.getSpawnLocation());
                    player.setFreezeTicks(0);
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    public static void teleportPlayerToWorld(Player player, String worldName) {
        World targetWorld = Bukkit.getWorld(worldName);
        if (targetWorld != null) {
            Bukkit.getScheduler().runTask(plugin, () -> {
                Location spawnLocation = targetWorld.getSpawnLocation();
                player.teleport(spawnLocation);
            });
        } else {
            player.sendMessage("The target world does not exist.");
        }
    }

}
