package ToT.PartyManagment.Listener;

import ToT.Objects.TPlayer;
import ToT.Utils.PartyManagment;
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

        ArrayList<UUID> party = PartyManagment.getParty(player.getUniqueId());

        if(!PartyManagment.inParty(party, player.getUniqueId())) {
            return;
        }

        ArrayList<UUID> members = PartyManagment.getMembers(party);

        members.forEach(uuid -> {
            Player p = Bukkit.getServer().getPlayer(uuid);
            if(p != null) PartyManagment.updatePartyScoreboard(p);
        });
    }
}
