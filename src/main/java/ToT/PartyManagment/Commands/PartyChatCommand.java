package ToT.PartyManagment.Commands;

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

        if(!PartyCommand.inParty(PartyCommand.getParty(player), player)) {
            player.sendMessage(ChatColor.RED + "You are not in Party");
            return true;
        }

        Player[] party = PartyCommand.getParty(player);
        Player[] members = PartyCommand.getMembers(party);

        for (Player member : members) {
            if(PartyCommand.isOwner(party, player)) {
                member.sendMessage(ChatColor.BLUE + "[" + ChatColor.AQUA + ChatColor.BOLD + "PARTY" + ChatColor.BLUE + "] " + ChatColor.GOLD + player.getName() + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + String.join(" ", args));
            } else {
                member.sendMessage(ChatColor.BLUE + "[" + ChatColor.AQUA + ChatColor.BOLD + "PARTY" + ChatColor.BLUE + "] " + ChatColor.YELLOW + player.getName() + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + String.join(" ", args));
            }
        }

        return true;
    }

}
