package Atan.BaseDataBase;

import org.bukkit.entity.Entity;

import java.io.*;
import java.util.*;


public class SpigotPlayerData implements Serializable{
    private static SpigotPlayerData s = null;
    HashMap<UUID, LinkedList<Object>> PDict;
    private SpigotPlayerData()
    {
        PDict =new HashMap<>();
    }

    public static SpigotPlayerData getInstance()
    {
        if (s== null) {
            try {
                // Reads data using the ObjectInputStream
                FileInputStream fileStream = new FileInputStream("file.txt");
                ObjectInputStream objStream = new ObjectInputStream(fileStream);
                s= (SpigotPlayerData) objStream.readObject();
                objStream.close();
            }catch (Exception e){
                s = new SpigotPlayerData();
            }

        }
        return s;
    }

    public void enterEntity(UUID e){
        PDict.put(e,new LinkedList<>());
    }
    public LinkedList getEntity(UUID e){
        return PDict.get(e);
    }

    public void save(){
        try {

            FileOutputStream file = new FileOutputStream("file.txt");

            // Creates an ObjectOutputStream
            ObjectOutputStream output = new ObjectOutputStream(file);

            // writes objects to output stream
            output.writeObject(this);

            output.close();

        }

        catch (Exception e) {
            e.getStackTrace();
        }
    }

}
