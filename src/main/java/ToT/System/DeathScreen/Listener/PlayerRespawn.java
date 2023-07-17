package ToT.System.DeathScreen.Listener;

import ToT.Objects.TPlayer;
import ToT.System.PartyManagment.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import static ToT.Utils.Utils.getPlayerData;

public class PlayerRespawn implements Listener {
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        player.setFireTicks(0);

        TPlayer data = getPlayerData(player.getUniqueId());
        int[] stats = data.getStats();
        int[] statPoints = data.getStatPoints();

        statPoints[3] = stats[4];
    }


}
