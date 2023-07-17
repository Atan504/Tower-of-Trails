package ToT.System.PartyManagment.Listener;

import ToT.System.PartyManagment.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.*;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        ArrayList<UUID> party = Utils.getParty(player.getUniqueId());

        if(!Utils.inParty(party, player.getUniqueId())) {
            return;
        }

        ArrayList<UUID> members = Utils.getMembers(party);

        members.forEach(uuid -> {
            Player p = Bukkit.getServer().getPlayer(uuid);
            if(p != null) Utils.updatePartyScoreboard(p);
        });
    }
}
