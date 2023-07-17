package ToT.System.PartyManagment.Listener;

import ToT.System.PartyManagment.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.UUID;

import static ToT.Main.plugin;

public class PlayerLeft implements Listener {

    @EventHandler
    public void onPlayerLeft(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ArrayList<UUID> party = Utils.getParty(player.getUniqueId());

        if(!Utils.inParty(party, player.getUniqueId())) {
            return;
        }

        ArrayList<UUID> members = Utils.getMembers(party);

        Bukkit.getScheduler().runTaskLater(plugin, () -> members.forEach(uuid -> {
            Player p = Bukkit.getServer().getPlayer(uuid);
            if (p != null) Utils.updatePartyScoreboard(p);
        }), 40);
    }
}
