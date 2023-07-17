package ToT.System.PartyManagment.Commands;

import ToT.System.PartyManagment.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.UUID;

public class PartyChatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {

        if (!(sender instanceof Player player)) return true;

        if (args.length < 1) {
            player.sendMessage("You need specific a message to send!");
            return true;
        }

        ArrayList<UUID> party = Utils.getParty(player.getUniqueId());

        if(!Utils.inParty(party, player.getUniqueId())) {
            player.sendMessage(ChatColor.RED + "You are not in Party");
            return true;
        }

        ArrayList<UUID> members = Utils.getMembers(party);

        members.forEach(p -> {
            Player p2 = Bukkit.getServer().getPlayer(p);
            if(Utils.isOwner(party, player.getUniqueId())) {
                if(p2 != null) p2.sendMessage(ChatColor.BLUE + "[" + ChatColor.AQUA + ChatColor.BOLD + "PARTY" + ChatColor.BLUE + "] " + ChatColor.GOLD + player.getName() + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + String.join(" ", args));
            } else {
                if(p2 != null) p2.sendMessage(ChatColor.BLUE + "[" + ChatColor.AQUA + ChatColor.BOLD + "PARTY" + ChatColor.BLUE + "] " + ChatColor.YELLOW + player.getName() + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + String.join(" ", args));
            }
        });

        return true;
    }

}
