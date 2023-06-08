package ToT.PartyManagment.Commands;

import ToT.Utils.PartyManagment;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PartyChatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {

        if (!(sender instanceof Player player)) return true;

        if (args.length < 1) {
            player.sendMessage("You need specific a message to send!");
            return true;
        }

        if(!PartyManagment.inParty(PartyManagment.getParty(player), player)) {
            player.sendMessage(ChatColor.RED + "You are not in Party");
            return true;
        }

        Player[] party = PartyManagment.getParty(player);
        Player[] members = PartyManagment.getMembers(party);

        for (Player member : members) {
            if(PartyManagment.isOwner(party, player)) {
                member.sendMessage(ChatColor.BLUE + "[" + ChatColor.AQUA + ChatColor.BOLD + "PARTY" + ChatColor.BLUE + "] " + ChatColor.GOLD + player.getName() + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + String.join(" ", args));
            } else {
                member.sendMessage(ChatColor.BLUE + "[" + ChatColor.AQUA + ChatColor.BOLD + "PARTY" + ChatColor.BLUE + "] " + ChatColor.YELLOW + player.getName() + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + String.join(" ", args));
            }
        }

        return true;
    }

}
