package ToT.Utils;

import ToT.Data.SpigotData;
import ToT.Objects.TPlayer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class PartyManagment {

    public static TPlayer getData(Player player) {
        SpigotData.getInstance().enterEntity(player.getUniqueId());

        return ((TPlayer) SpigotData.getInstance().getEntity(player.getUniqueId()));
    }

    public static Player[] getParty(Player player) {
        Player[] party = new Player[4];

        for(Player p : Bukkit.getServer().getOnlinePlayers()) {
            TPlayer data = getData(p);

            if(Arrays.stream(data.getParty()).toList().contains(player)) {
                party = data.getParty();
                break;
            }
        }

        return party;
    }

    public static Boolean inParty(Player[] party, Player member) {

        for(Player p : party) {
            if (p != null && p.equals(member)) {
                return true;
            }
        }
        return false;
    }

    public static Boolean isOwner(Player[] party, Player member) {
        return getOwner(party) == member;
    }

    public static Player getOwner(Player[] party) {
        return party[0];
    }

    public static Player[] getMembers(Player[] party) {

        int count = 0;
        for (Player player : party) {
            if (player != null) {
                count++;
            }
        }

        Player[] members = new Player[count];

        int i = 0;
        for (Player player : party) {
            if (player != null) {
                members[i] = player;
                i++;
            }
        }

        return members;
    }

    public static Player getPlayer(Player player, String username) {

        Player member = Bukkit.getServer().getPlayer(username);

        if(member == null) {
            player.sendMessage(ChatColor.RED + username + " Player not found");
            return null;
        }

        return member;

    }

}
