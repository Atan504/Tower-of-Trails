package ToT.System.PartyManagment.Listener;

import ToT.System.PartyManagment.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.metadata.MetadataValue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerChat implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        List<MetadataValue> active = player.getMetadata("party.chat.toggle");

        if(!active.isEmpty()) {
            if(active.get(0).asBoolean()) {
                event.setCancelled(true);
                ArrayList<UUID> party = Utils.getParty(player.getUniqueId());
                ArrayList<UUID> members = Utils.getMembers(party);

                for (UUID uuid : members) {
                    Player p = Bukkit.getServer().getPlayer(uuid);
                    if(Utils.isOwner(party, player.getUniqueId())) {
                        if(p != null) p.sendMessage(ChatColor.BLUE + "[" + ChatColor.AQUA + ChatColor.BOLD + "PARTY" + ChatColor.BLUE + "] " + ChatColor.GOLD + player.getName() + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + event.getMessage());
                    } else {
                        if(p != null) p.sendMessage(ChatColor.BLUE + "[" + ChatColor.AQUA + ChatColor.BOLD + "PARTY" + ChatColor.BLUE + "] " + ChatColor.YELLOW + player.getName() + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + event.getMessage());
                    }
                }
            }
        }
    }
}
