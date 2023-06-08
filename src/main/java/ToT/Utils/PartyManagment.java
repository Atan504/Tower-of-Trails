package ToT.Utils;

import ToT.Data.SpigotData;
import ToT.Objects.TPlayer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PartyManagment {

    public static TPlayer getData(Player player) {
        SpigotData.getInstance().enterEntity(player.getUniqueId());

        return ((TPlayer) SpigotData.getInstance().getEntity(player.getUniqueId()));
    }

    public static List<Player> getParty(Player player) {

        if(Bukkit.getServer().getOnlinePlayers().stream().filter(p -> getData(p).getParty().contains(player)).toList().size() == 0) return new ArrayList<>();

        Player owner = Bukkit.getServer().getOnlinePlayers().stream().filter(p -> getData(p).getParty().contains(player)).toList().get(0);
        TPlayer data = getData(owner);

        return new ArrayList<>(data.getParty());
    }

    public static Boolean inParty(List<Player> party, Player member) {
        return party.contains(member);
    }

    public static Boolean isOwner(List<Player> party, Player member) {
        return getOwner(party) == member;
    }

    public static Player getOwner(List<Player> party) {
        return party.get(0);
    }

    public static List<Player> getMembers(List<Player> party) {
        return party.stream().filter(Objects::nonNull).toList();
    }

    public static Player getPlayer(List<Player> members, String username) {
        if(members.stream().filter(p -> p.getName().equals(username)).toList().size() == 0) return null;
        return members.stream().filter(p -> p.getName().equals(username)).toList().get(0);
    }

}
