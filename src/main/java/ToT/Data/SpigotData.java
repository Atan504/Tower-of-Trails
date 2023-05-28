package ToT.Data;

import java.io.*;
import java.util.*;


public class SpigotData implements Serializable {
    //(TODO) every object that is inserted into the Data needs to implement Serializable.
    private static SpigotData s = null;
    HashMap<UUID, LinkedList<Object>> map;

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
        map.putIfAbsent(e, new LinkedList<>());
    }

    public LinkedList getEntity(UUID e) {
        return map.get(e);
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
