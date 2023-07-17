package ToT.System.DeathScreen.Listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import ToT.System.DeathScreen.Utils;
import static ToT.Main.plugin;

public class PlayerDeath implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            Utils.teleportPlayerToWorld(player, "Death");
            player.spigot().respawn();
            player.setInvisible(true);
            player.setInvulnerable(true);
            Utils.startCountdown(player);
        }, 1L);
    }


}
