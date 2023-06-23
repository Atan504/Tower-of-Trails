package ToT.Listener;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import static ToT.Main.plugin;

public class PlayerDeath implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.setDeathMessage(null); // Clear the death message

        Player player = event.getEntity();
        Location deathLocation = player.getLocation();

        // Teleport the player to the specific location upon death
        teleportPlayer(player, deathLocation);

        // Start the countdown title on the player's screen

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            player.spigot().respawn();
            player.teleport(new Location(player.getWorld(), -999, 75, -973));
            player.setInvisible(true);
            player.setInvulnerable(true);
            player.setFreezeTicks(20);
            startCountdown(player);
                }, 1L);
    }

    private void teleportPlayer(Player player, Location location) {
        player.teleport(location);
    }

    private void startCountdown(Player player) {
        new BukkitRunnable() {
            int count = 3;

            @Override
            public void run() {
                if (count > 0) {
                    // Send the countdown title to the player's screen
                    sendCountdownTitle(player, count);
                    count--;
                } else {
                    player.sendMessage(ChatColor.GREEN + "You respawned!");
                    player.setInvisible(false);
                    player.setInvulnerable(false);
                    player.teleport(player.getWorld().getSpawnLocation());
                    player.setFreezeTicks(0);
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    private void sendCountdownTitle(Player player, int count) {
        player.sendTitle(ChatColor.RED + "☠ YOU DIED ☠", ChatColor.RED + String.valueOf(count), 0, 20, 0);
    }


}
