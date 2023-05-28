package Atan;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class YamlConfigLoader {

    public static void createAllConfig() {
        YamlConfigLoader.createCustomConfig("data/items/weapon/swordsman/Crude Iron Blade.yml");
        YamlConfigLoader.createCustomConfig("data/items/weapon/swordsman/Slime Sword.yml");

        YamlConfigLoader.createCustomConfig("data/items/armor/goblin/Goblin Helmet.yml");
        YamlConfigLoader.createCustomConfig("data/items/armor/goblin/Goblin Chestplate.yml");
        YamlConfigLoader.createCustomConfig("data/items/armor/goblin/Goblin Leggings.yml");
        YamlConfigLoader.createCustomConfig("data/items/armor/goblin/Goblin Boots.yml");
        YamlConfigLoader.createCustomConfig("data/items/armor/fur/Fur Helmet.yml");
        YamlConfigLoader.createCustomConfig("data/items/armor/fur/Fur Chestplate.yml");
        YamlConfigLoader.createCustomConfig("data/items/armor/fur/Fur Leggings.yml");
        YamlConfigLoader.createCustomConfig("data/items/armor/fur/Fur Boots.yml");

        YamlConfigLoader.createCustomConfig("data/sets/Goblin.yml");
        YamlConfigLoader.createCustomConfig("data/sets/Fur.yml");
    }

    static void createCustomConfig(String path) {
        File customConfigFile = new File(Main.plugin.getDataFolder(), path);
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            Main.plugin.saveResource(path, false);
        }

        YamlConfiguration.loadConfiguration(customConfigFile);
    }
}
