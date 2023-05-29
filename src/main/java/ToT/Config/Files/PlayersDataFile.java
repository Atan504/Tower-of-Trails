package ToT.Config.Files;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

import static ToT.Main.plugin;

public class PlayersDataFile {
    private static File file;
    private static FileConfiguration customFile;

    public PlayersDataFile() {
    }

    public static void setup() {
        file = new File(plugin.getDataFolder(), "PlayersData.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return customFile;
    }

    public static void save() {
        try {
            customFile.save(file);
        } catch (IOException var1) {
            System.out.println("Couldn't save file");
        }

    }

    public static void reload() {
        customFile = YamlConfiguration.loadConfiguration(file);
    }
}
