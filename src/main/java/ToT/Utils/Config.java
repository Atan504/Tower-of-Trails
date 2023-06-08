package ToT.Utils;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

import static ToT.Main.plugin;

public class Config {

    public static void createAllConfig() {
        createCustomConfig("data/items/weapon/swordsman/Crude Iron Blade.yml");
        createCustomConfig("data/items/weapon/swordsman/Slime Sword.yml");

        createCustomConfig("data/items/armor/goblin/Goblin Helmet.yml");
        createCustomConfig("data/items/armor/goblin/Goblin Chestplate.yml");
        createCustomConfig("data/items/armor/goblin/Goblin Leggings.yml");
        createCustomConfig("data/items/armor/goblin/Goblin Boots.yml");
        createCustomConfig("data/items/armor/fur/Fur Helmet.yml");
        createCustomConfig("data/items/armor/fur/Fur Chestplate.yml");
        createCustomConfig("data/items/armor/fur/Fur Leggings.yml");
        createCustomConfig("data/items/armor/fur/Fur Boots.yml");

        createCustomConfig("data/sets/Goblin.yml");
        createCustomConfig("data/sets/Fur.yml");
    }

    static void createCustomConfig(String path) {
        File customConfigFile = new File(plugin.getDataFolder(), path);
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            plugin.saveResource(path, false);
        }

        YamlConfiguration.loadConfiguration(customConfigFile);
    }

}
