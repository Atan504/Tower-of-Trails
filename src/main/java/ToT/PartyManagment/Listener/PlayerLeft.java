package ToT.PartyManagment.Listener;

import ToT.Utils.PartyManagment;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static ToT.Main.plugin;

public class PlayerLeft implements Listener {

    @EventHandler
    public void onPlayerLeft(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ArrayList<UUID> party = PartyManagment.getParty(player.getUniqueId());

        if(!PartyManagment.inParty(party, player.getUniqueId())) {
            return;
        }

        ArrayList<UUID> members = PartyManagment.getMembers(party);

        Bukkit.getScheduler().runTaskLater(plugin, () -> members.forEach(uuid -> {
            Player p = Bukkit.getServer().getPlayer(uuid);
            if (p != null) PartyManagment.updatePartyScoreboard(p);
        }), 40);
    }
}
