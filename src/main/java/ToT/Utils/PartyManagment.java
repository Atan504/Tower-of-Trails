package ToT.Utils;

import ToT.Data.SpigotData;
import ToT.Objects.TPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Stream;

public class PartyManagment implements Serializable {

    public static TPlayer getData(UUID uuid) {
        SpigotData.getInstance().enterEntity(uuid);

        return ((TPlayer) SpigotData.getInstance().getEntity(uuid));
    }

    public static List<UUID> getParty(UUID uuid) {

        Stream<? extends Player> stream = Bukkit.getServer().getOnlinePlayers().stream().filter(p -> {
            TPlayer data = getData(p.getUniqueId());
            List<UUID> party = data.getParty();

            return party.contains(uuid);
        });

        List<? extends Player> list = stream.toList();

        if(list.size() == 0) return new ArrayList<>();

        Player owner = list.get(0);
        TPlayer data = getData(owner.getUniqueId());

        return new ArrayList<>(data.getParty());
    }

    public static Boolean inParty(List<UUID> party, UUID uuid) {
        return party.contains(uuid);
    }

    public static Boolean isOwner(List<UUID> party, UUID uuid) {
        return getOwner(party).equals(uuid);
    }

    public static UUID getOwner(List<UUID> party) {
        Player player = Bukkit.getServer().getPlayer(party.get(0));
        if(player == null) {
            OfflinePlayer player2 = Bukkit.getServer().getOfflinePlayer(party.get(0));
            return player2.getUniqueId();
        } else {
            return player.getUniqueId();
        }
    }

    public static List<UUID> getMembers(List<UUID> party) {
        return party.stream().filter(Objects::nonNull).toList();
    }

    public static UUID getPlayer(List<UUID> members, String username) {

        Stream<UUID> stream = members.stream().filter(p -> {
            Player p2 = Bukkit.getServer().getPlayer(p);
            OfflinePlayer p3 = Bukkit.getServer().getOfflinePlayer(p);

            if(p2 != null) return p2.getName().equals(username);
            else return Objects.equals(p3.getName(), username);
        });

        List<UUID> list = stream.toList();

        if(list.size() == 0) return null;
        return list.get(0);
    }

}
