package ToT.Config.Files;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

import static ToT.Main.plugin;

public class Main {
    private static File file;
    private static FileConfiguration customFile;

    public Main() {
    }

    public static void setup() {
        file = new File(plugin.getDataFolder(), "MainConfig.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ignored) {
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
