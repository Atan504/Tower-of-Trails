package ToT.PartyManagment.Listener;

import ToT.Objects.TPlayer;
import ToT.Utils.PartyManagment;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class PlayerLeft implements Listener {

    @EventHandler
    public void onPlayerLeft(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        List<UUID> party = PartyManagment.getParty(player.getUniqueId());

        if(!PartyManagment.inParty(party, player.getUniqueId())) {
            player.sendMessage(ChatColor.RED + "You are not in Party");
            return;
        }

        List<UUID> members = PartyManagment.getMembers(party);
        TPlayer data = PartyManagment.getData(player.getUniqueId());

        if(members.size() == 1) {

            player.sendMessage(ChatColor.RED + "You are the only one who left in the party");
            player.sendMessage(ChatColor.RED + "Your Previous Party has been Disbanded");
            party.remove(player.getUniqueId());
            data.setParty(party);
            return;
        }

        if(PartyManagment.isOwner(party, player.getUniqueId())) {
            Player p = Bukkit.getServer().getPlayer(party.get(1));
            if(p != null) p.sendMessage(ChatColor.YELLOW + "Previous Party Owner left the Party you become the new Party Owner");
        }

        party.remove(player.getUniqueId());
        party = party.stream().filter(Objects::nonNull).toList();

        UUID owner = PartyManagment.getOwner(party);

        TPlayer data2 = PartyManagment.getData(owner);

        data.setParty(new ArrayList<>());
        data2.setParty(party);

        player.sendMessage(ChatColor.RED + "You Left the party!");

        members.stream().filter(p -> p != player.getUniqueId()).forEach(p -> {
            Player p2 = Bukkit.getServer().getPlayer(p);
            if(p2 != null) p2.sendMessage(ChatColor.RED + player.getName() + " Left the party!");
        });
    }
}
