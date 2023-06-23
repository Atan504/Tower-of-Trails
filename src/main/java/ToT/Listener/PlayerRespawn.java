package ToT.Listener;

import ToT.Objects.TPlayer;
import ToT.Utils.PartyManagment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawn implements Listener {
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        player.setFireTicks(0);

        TPlayer data = PartyManagment.getData(player.getUniqueId());
        int[] stats = data.getStats();
        int[] statPoints = data.getStatPoints();

        statPoints[3] = stats[4];
    }


}
