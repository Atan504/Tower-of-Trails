package ToT.Data;

import ToT.Objects.TEntity;
import ToT.Objects.TPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.*;


public class SpigotData implements Serializable {
    //(TODO) every object that is inserted into the Data needs to implement Serializable.
    private static SpigotData s = null;
    HashMap<UUID, TEntity> map;

    private SpigotData() {
        map = new HashMap<>();
    }

    public static SpigotData getInstance() {
        if (s == null) {
            try {
                // Reads data using the ObjectInputStream
                FileInputStream fileStream = new FileInputStream("Data.txt");
                ObjectInputStream objStream = new ObjectInputStream(fileStream);
                s = (SpigotData) objStream.readObject();
                objStream.close();
            } catch (Exception e) {
                s = new SpigotData();
            }

        }
        return s;
    }

    public void enterEntity(UUID e) {

        if (Bukkit.getPlayer(e) != null)
            map.putIfAbsent(e, new TPlayer(e));
        else
            map.putIfAbsent(e, new TEntity(e));
    }

    public TEntity getEntity(UUID e) {
        return map.get(e);
    }

    public List<UUID> getAll() {
        return map.keySet().stream().toList().stream().filter(uuid -> {
            Player p = Bukkit.getServer().getPlayer(uuid);
            OfflinePlayer p2 = Bukkit.getServer().getOfflinePlayer(uuid);

            if(p == null) {
                return p2 != null;
            } else return true;
        }).toList();
    }

    public void save() {
        try {

            FileOutputStream file = new FileOutputStream("Data.txt");

            // Creates an ObjectOutputStream
            ObjectOutputStream output = new ObjectOutputStream(file);

            // writes objects to output stream
            output.writeObject(s);

            output.close();

        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    @Override
    public String toString() {
        return "SpigotPlayerData{" +
                "PDict=" + map +
                '}';
    }
}
