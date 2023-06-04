package ToT;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ToT.Main.plugin;
import static org.bukkit.Bukkit.getServer;

public class Utils {

    public static String ArmorType(ItemStack item) {
        if(item.getType().equals(Material.LEATHER_HELMET) ||
                item.getType().equals(Material.IRON_HELMET) ||
                item.getType().equals(Material.CHAINMAIL_HELMET) ||
                item.getType().equals(Material.GOLDEN_HELMET) ||
                item.getType().equals(Material.DIAMOND_HELMET) ||
                item.getType().equals(Material.NETHERITE_HELMET) ||
                item.getType().equals(Material.TURTLE_HELMET)) {
            return "HELMET";
        }

        if(item.getType().equals(Material.LEATHER_CHESTPLATE) ||
                item.getType().equals(Material.IRON_CHESTPLATE) ||
                item.getType().equals(Material.CHAINMAIL_CHESTPLATE) ||
                item.getType().equals(Material.GOLDEN_CHESTPLATE) ||
                item.getType().equals(Material.DIAMOND_CHESTPLATE) ||
                item.getType().equals(Material.NETHERITE_CHESTPLATE) ||
                item.getType().equals(Material.ELYTRA)) {
            return "CHESTPLATE";
        }

        if(item.getType().equals(Material.LEATHER_LEGGINGS) ||
                item.getType().equals(Material.IRON_LEGGINGS) ||
                item.getType().equals(Material.CHAINMAIL_LEGGINGS) ||
                item.getType().equals(Material.GOLDEN_LEGGINGS) ||
                item.getType().equals(Material.DIAMOND_LEGGINGS) ||
                item.getType().equals(Material.NETHERITE_LEGGINGS)) {
            return "LEGGINGS";
        }

        if(item.getType().equals(Material.LEATHER_BOOTS) ||
                item.getType().equals(Material.IRON_BOOTS) ||
                item.getType().equals(Material.CHAINMAIL_BOOTS) ||
                item.getType().equals(Material.GOLDEN_BOOTS) ||
                item.getType().equals(Material.DIAMOND_BOOTS) ||
                item.getType().equals(Material.NETHERITE_BOOTS)) {
            return "BOOTS";
        }

        return "NONE";
    }

    public static void addToArray(int[] arr,int[] value){
        for (int i = 0; i < value.length; i++) {
            arr[i]+=value[i];
        }
    }

    public static int[] sumArrays(int[][] arr){
        int[] res = new int[arr[0].length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                res[j]+=arr[i][j];
            }
        }
        return res;
    }

    public static int[] getItemStats(ItemStack item) {

        if(item == null) return new int[]{ 0, 0, 0, 0, 0, 0 };
        if(!item.hasItemMeta()) return new int[]{ 0, 0, 0, 0, 0, 0 };
        if(!item.getItemMeta().hasDisplayName()) return new int[]{ 0, 0, 0, 0, 0, 0 };

        ConfigurationSection config = Utils.getConfig("items", ChatColor.stripColor(item.getItemMeta().getDisplayName()), "stats");

        if(config == null) return new int[]{ 0, 0, 0, 0, 0, 0};

        int mana,str,hp,def,speed,magic;

        mana = config.getInt("Mana");
        str = config.getInt("Strength");
        hp = config.getInt("Health");
        def = config.getInt("Defense");
        speed = config.getInt("Speed");
        magic = config.getInt("Magic");

        return new int[]{ mana, str, hp, def, speed, magic };
    }

    public static ConfigurationSection getConfig(String filePath, String name, String target) {
        String dirPath = plugin.getDataFolder() + "/data/" + filePath;
        String fileName = ChatColor.stripColor(name) + ".yml";

        try (Stream<Path> pathStream = Files.walk(Paths.get(dirPath))) {
            Path configFile = pathStream
                    .filter(path -> path.toFile().getName().equals(fileName))
                    .findFirst()
                    .orElse(null);

            if (configFile != null) {
                File yamlFile = configFile.toFile();

                // Load the YAML file into a ConfigurationSection
                YamlConfiguration yamlConfig = new YamlConfiguration();
                yamlConfig.load(yamlFile);

                return yamlConfig.getConfigurationSection(target);
            }
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }

        return new YamlConfiguration();
    }

    static boolean alreadySentRemovedMsg = false;

    public static String getArmorSetType(ItemStack armor) {

        String name = ChatColor.stripColor(Objects.requireNonNull(armor.getItemMeta()).getDisplayName());

        ConfigurationSection configSection = Utils.getConfig("items", name, "");

        return configSection.getString("set");
    }

    public static Integer getData(PlayerData pd, String stat_name) {
        int value = 0;
        if(stat_name.equals("Mana")) value = pd.maxmana;
        if(stat_name.equals("Damage")) value = pd.str;
        if(stat_name.equals("Strength")) value = pd.str;
        if(stat_name.equals("Health")) value = pd.maxhp;
        if(stat_name.equals("Defense")) value = pd.def;
        if(stat_name.equals("Speed")) value = pd.spe;
        if(stat_name.equals("Magic")) value = pd.magic;

        return value;
    }

    private static void updateStat(PlayerData pd, ConfigurationSection stat_config, List<String> list, String stat_name, String type) {

        Object value = stat_config.get(stat_name);

        Integer stat_value = getData(pd, stat_name);

        String data_name = null;

        if (stat_name.equals("Mana")) data_name = "maxmana";
        if (stat_name.equals("Strength") || stat_name.equals("Damage")) data_name = "str";
        if (stat_name.equals("Health")) data_name = "maxhp";
        if (stat_name.equals("Defense")) data_name = "def";
        if (stat_name.equals("Speed")) data_name = "spe";
        if (stat_name.equals("Magic")) data_name = "magic";

        if (Objects.equals(type, "add")) {

            assert value != null;
            String percentageString = value.toString();
            if (percentageString.contains("%")) {
                String valueString = percentageString.replace("%", "");
                value = Double.parseDouble(valueString) / 100.0;
            }

            if (value instanceof Double) {
                value = (int) (stat_value * ((double) value + 1));
                stat_name = "Strength";
                assert data_name != null;
                pd.set(data_name, value);
            } else {
                assert data_name != null;
                pd.set(data_name, stat_value + (int) value);
            }

            if ((int) value > 0)
                list.add(ChatColor.GREEN + "+" + value + ChatColor.GRAY + " " + stat_name + ChatColor.AQUA + " (" + getData(pd, stat_name) + ")");
            else
                list.add(ChatColor.RED + "-" + value + ChatColor.GRAY + " " + stat_name + ChatColor.AQUA + " (" + getData(pd, stat_name) + ")");
        }

        if (Objects.equals(type, "remove")) {

            assert value != null;
            String percentageString = value.toString();
            if (percentageString.contains("%")) {
                String valueString = percentageString.replace("%", "");
                value = Double.parseDouble(valueString) / 100.0;
            }

            if (value instanceof Double) {
                value = (int) (stat_value * ((double) value + 1));
                stat_name = "Strength";
                assert data_name != null;
                pd.set(data_name, value);
            } else {
                assert data_name != null;
                pd.set(data_name, stat_value - (int) value);
            }

            if ((int) value > 0)
                list.add(ChatColor.RED + "-" + value + ChatColor.GRAY + " " + stat_name + ChatColor.AQUA + " (" + getData(pd, stat_name) + ")");
            else
                list.add(ChatColor.GREEN + "+" + value + ChatColor.GRAY + " " + stat_name + ChatColor.AQUA + " (" + getData(pd, stat_name) + ")");
        }
    }

    public static void updateSet(Player player, PlayerData pd, String type) {

        ItemStack helmet = player.getInventory().getHelmet();
        ItemStack chestplate = player.getInventory().getChestplate();
        ItemStack leggings = player.getInventory().getLeggings();
        ItemStack boots = player.getInventory().getBoots();

        int count = 0; // Counter for the number of pieces of armor equipped

        // Check each armor slot and increment the counter if the slot is not null
        if (helmet != null) {
            count++;
        }
        if (chestplate != null) {
            count++;
        }
        if (leggings != null) {
            count++;
        }
        if (boots != null) {
            count++;
        }

        if(helmet == null) helmet = new ItemStack(Material.STONE);
        if(chestplate == null) chestplate = new ItemStack(Material.STONE);
        if(leggings == null) leggings = new ItemStack(Material.STONE);
        if(boots == null) boots = new ItemStack(Material.STONE);

        String helmetName = ChatColor.stripColor(Objects.requireNonNull(helmet.getItemMeta()).getDisplayName());
        String chestplateName = ChatColor.stripColor(Objects.requireNonNull(chestplate.getItemMeta()).getDisplayName());
        String leggingsName = ChatColor.stripColor(Objects.requireNonNull(leggings.getItemMeta()).getDisplayName());
        String bootsName = ChatColor.stripColor(Objects.requireNonNull(boots.getItemMeta()).getDisplayName());

        ConfigurationSection helmetConfigSection = Utils.getConfig("items", helmetName, "");
        ConfigurationSection chestplateConfigSection = Utils.getConfig("items", chestplateName, "");
        ConfigurationSection leggingsConfigSection = Utils.getConfig("items", leggingsName, "");
        ConfigurationSection bootsConfigSection = Utils.getConfig("items", bootsName, "");

        String helmetSet = (String) helmetConfigSection.get("set");
        String chestplateSet = (String) chestplateConfigSection.get("set");
        String leggingsSet = (String) leggingsConfigSection.get("set");
        String bootsSet = (String) bootsConfigSection.get("set");

        // If the player has a full set of armor equipped, get the set bonus stats
        if (count == 4) {

            // If the player has a full set of the same set bonus, apply the set bonus stats
            assert helmetSet != null;
            if (helmetSet.equals(chestplateSet) && chestplateSet.equals(leggingsSet) && leggingsSet.equals(bootsSet)) {

                alreadySentRemovedMsg = false;

                player.sendMessage(ChatColor.YELLOW + player.getName() + ChatColor.GREEN + " has equipped a full " + ChatColor.AQUA + helmetSet + " Set");

                ConfigurationSection statsSection = Utils.getConfig("sets", helmetSet, "stats");

                List<String> list = new ArrayList<>();

                if (statsSection.contains("Mana")) {
                    updateStat(pd, statsSection, list, "Mana", "add");
                }
                if (statsSection.contains("Strength")) {
                    updateStat(pd, statsSection, list, "Strength", "add");
                }
                if (statsSection.contains("Health")) {
                    updateStat(pd, statsSection, list, "Health", "add");
                }
                if (statsSection.contains("Defense")) {
                    updateStat(pd, statsSection, list, "Defense", "add");
                }
                if (statsSection.contains("Speed")) {
                    updateStat(pd, statsSection, list, "Speed", "add");
                }
                if (statsSection.contains("Magic")) {
                    updateStat(pd, statsSection, list, "Magic", "add");
                }

                ConfigurationSection ifSection = Utils.getConfig("sets", helmetSet, "if");

                if(ifSection != null) {
                    ConfigurationSection timeSection = ifSection.getConfigurationSection("time");
                    if(timeSection != null) {
                        ConfigurationSection daySection = timeSection.getConfigurationSection("day");
                        if(daySection != null) {
                            long time = player.getWorld().getTime();
                            if (time >= 0 && time < 12300) {
                                if (daySection.contains("Damage")) {
                                    updateStat(pd, daySection, list, "Damage", "add");
                                }
                                if (daySection.contains("Mana")) {
                                    updateStat(pd, daySection, list, "Mana", "add");
                                }
                                if (daySection.contains("Strength")) {
                                    updateStat(pd, daySection, list, "Strength", "add");
                                }
                                if (daySection.contains("Health")) {
                                    updateStat(pd, daySection, list, "Health", "add");
                                }
                                if (daySection.contains("Defense")) {
                                    updateStat(pd, daySection, list, "Defense", "add");
                                }
                                if (daySection.contains("Speed")) {
                                    updateStat(pd, daySection, list, "Speed", "add");
                                }
                                if (daySection.contains("Magic")) {
                                    updateStat(pd, daySection, list, "Magic", "add");
                                }
                            }
                        }
                        ConfigurationSection nightSection = timeSection.getConfigurationSection("night");
                        if(nightSection != null) {
                            long time = player.getWorld().getTime();
                            if (!(time >= 0 && time < 12300)) {
                                if (nightSection.contains("Damage")) {
                                    updateStat(pd, nightSection, list, "Damage", "add");
                                }
                                if (nightSection.contains("Mana")) {
                                    updateStat(pd, nightSection, list, "Mana", "add");
                                }
                                if (nightSection.contains("Strength")) {
                                    updateStat(pd, nightSection, list, "Strength", "add");
                                }
                                if (nightSection.contains("Health")) {
                                    updateStat(pd, nightSection, list, "Health", "add");
                                }
                                if (nightSection.contains("Defense")) {
                                    updateStat(pd, nightSection, list, "Defense", "add");
                                }
                                if (nightSection.contains("Speed")) {
                                    updateStat(pd, nightSection, list, "Speed", "add");
                                }
                                if (nightSection.contains("Magic")) {
                                    updateStat(pd, nightSection, list, "Magic", "add");
                                }
                            }
                        }
                    }
                }

                if (!list.isEmpty()) {
                    player.sendMessage(ChatColor.YELLOW + player.getName() + ChatColor.GREEN + " Got the list stats bonus:");
                    player.sendMessage(String.join("\n", list));
                    getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + player.getName() + ChatColor.GREEN + " Got the list stats bonus:");
                    getServer().getConsoleSender().sendMessage(String.join("\n", list));
                }
            }
        } else {
            if (type.equals("removed")) {
                if (!alreadySentRemovedMsg) {

                    String set = helmetSet;

                    if(set == null) set = chestplateSet;
                    if(set == null) set = leggingsSet;
                    if(set == null) set = bootsSet;
                    if(set == null) set = getArmorSetType((ItemStack) player.getMetadata("playersWithModifiedStats.helmet." + player.getUniqueId()).get(0));
                    if(set == null) set = getArmorSetType((ItemStack) player.getMetadata("playersWithModifiedStats.chestplate." + player.getUniqueId()).get(0));
                    if(set == null) set = getArmorSetType((ItemStack) player.getMetadata("playersWithModifiedStats.leggings." + player.getUniqueId()).get(0));
                    if(set == null) set = getArmorSetType((ItemStack) player.getMetadata("playersWithModifiedStats.boots." + player.getUniqueId()).get(0));
                    if(set == null) set = "Unknown";

                    player.sendMessage(ChatColor.YELLOW + player.getName() + ChatColor.RED + " has unequipped a full " + ChatColor.AQUA + set + " Set");

                    ConfigurationSection statsSection = Utils.getConfig("sets", set, "stats");

                    List<String> list = new ArrayList<>();

                    if (statsSection.contains("Mana")) {
                        updateStat(pd, statsSection, list, "Mana", "remove");
                    }
                    if (statsSection.contains("Strength")) {
                        updateStat(pd, statsSection, list, "Strength", "remove");
                    }
                    if (statsSection.contains("Health")) {
                        updateStat(pd, statsSection, list, "Health", "remove");
                    }
                    if (statsSection.contains("Defense")) {
                        updateStat(pd, statsSection, list, "Defense", "remove");
                    }
                    if (statsSection.contains("Speed")) {
                        updateStat(pd, statsSection, list, "Speed", "remove");
                    }
                    if (statsSection.contains("Magic")) {
                        updateStat(pd, statsSection, list, "Magic", "remove");
                    }

                    ConfigurationSection ifSection = Utils.getConfig("sets", set, "if");

                    if(ifSection != null) {
                        ConfigurationSection timeSection = ifSection.getConfigurationSection("time");
                        if(timeSection != null) {
                            ConfigurationSection daySection = timeSection.getConfigurationSection("day");
                            if(daySection != null) {
                                long time = player.getWorld().getTime();
                                if (time >= 0 && time < 12300) {
                                    if (daySection.contains("Damage")) {
                                        updateStat(pd, daySection, list, "Damage", "remove");
                                    }
                                    if (daySection.contains("Mana")) {
                                        updateStat(pd, daySection, list, "Mana", "remove");
                                    }
                                    if (daySection.contains("Strength")) {
                                        updateStat(pd, daySection, list, "Strength", "remove");
                                    }
                                    if (daySection.contains("Health")) {
                                        updateStat(pd, daySection, list, "Health", "remove");
                                    }
                                    if (daySection.contains("Defense")) {
                                        updateStat(pd, daySection, list, "Defense", "remove");
                                    }
                                    if (daySection.contains("Speed")) {
                                        updateStat(pd, daySection, list, "Speed", "remove");
                                    }
                                    if (daySection.contains("Magic")) {
                                        updateStat(pd, daySection, list, "Magic", "remove");
                                    }
                                }
                            }
                            ConfigurationSection nightSection = timeSection.getConfigurationSection("night");
                            if(nightSection != null) {
                                long time = player.getWorld().getTime();
                                if (!(time >= 0 && time < 12300)) {
                                    if (nightSection.contains("Damage")) {
                                        updateStat(pd, nightSection, list, "Damage", "remove");
                                    }
                                    if (nightSection.contains("Mana")) {
                                        updateStat(pd, nightSection, list, "Mana", "remove");
                                    }
                                    if (nightSection.contains("Strength")) {
                                        updateStat(pd, nightSection, list, "Strength", "remove");
                                    }
                                    if (nightSection.contains("Health")) {
                                        updateStat(pd, nightSection, list, "Health", "remove");
                                    }
                                    if (nightSection.contains("Defense")) {
                                        updateStat(pd, nightSection, list, "Defense", "remove");
                                    }
                                    if (nightSection.contains("Speed")) {
                                        updateStat(pd, nightSection, list, "Speed", "remove");
                                    }
                                    if (nightSection.contains("Magic")) {
                                        updateStat(pd, nightSection, list, "Magic", "remove");
                                    }
                                }
                            }
                        }
                    }

                    if (!list.isEmpty()) {
                        player.sendMessage(ChatColor.YELLOW + player.getName() + ChatColor.RED + " Got the list stats bonus:");
                        player.sendMessage(String.join("\n", list));
                        getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + player.getName() + ChatColor.RED + " Got the list stats bonus:");
                        getServer().getConsoleSender().sendMessage(String.join("\n", list));
                    }

                    alreadySentRemovedMsg = true;
                }
            }
        }
    }

    public static void addStats(Player player, ItemStack item) {

        PlayerData pd = new PlayerData(player.getUniqueId());

        String name = ChatColor.stripColor(Objects.requireNonNull(item.getItemMeta()).getDisplayName());

        player.sendMessage(ChatColor.YELLOW + player.getName() + ChatColor.GREEN + " has equipped a " + Objects.requireNonNull(item.getItemMeta()).getDisplayName());
        player.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + player.getName() + ChatColor.GREEN + " has equipped a " + Objects.requireNonNull(item.getItemMeta()).getDisplayName());

        ConfigurationSection configSection = Utils.getConfig("items", name, "");
        ConfigurationSection statsSection = Utils.getConfig("items", name, "stats");

        List<String> list = new ArrayList<>();

        if (statsSection != null) {
            if (statsSection.contains("Mana")) {
                updateStat(pd, statsSection, list, "Mana", "add");
            }
            if (statsSection.contains("Strength")) {
                updateStat(pd, statsSection, list, "Strength", "add");
            }
            if (statsSection.contains("Health")) {
                updateStat(pd, statsSection, list, "Health", "add");
            }
            if (statsSection.contains("Defense")) {
                updateStat(pd, statsSection, list, "Defense", "add");
            }
            if (statsSection.contains("Speed")) {
                updateStat(pd, statsSection, list, "Speed", "add");
            }
            if (statsSection.contains("Magic")) {
                updateStat(pd, statsSection, list, "Magic", "add");
            }

            if (!list.isEmpty()) {
                player.sendMessage(ChatColor.YELLOW + player.getName() + ChatColor.GREEN + " Got the list stats:");
                player.sendMessage(String.join("\n", list));
                getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + player.getName() + ChatColor.GREEN + " Got the list stats:");
                getServer().getConsoleSender().sendMessage(String.join("\n", list));
            }

            if(configSection.contains("set")) {
                updateSet(player, pd, "added");
            }

        }
    }

    public static void removeStats(Player player, ItemStack item) {

        PlayerData pd = new PlayerData(player.getUniqueId());

        String name = ChatColor.stripColor(Objects.requireNonNull(item.getItemMeta()).getDisplayName());

        player.sendMessage(ChatColor.YELLOW + player.getName() + ChatColor.RED + " has unequipped a " + Objects.requireNonNull(item.getItemMeta()).getDisplayName());
        player.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + player.getName() + ChatColor.RED + " has unequipped a " + Objects.requireNonNull(item.getItemMeta()).getDisplayName());

        String dirPath = plugin.getDataFolder() + "/data/items";
        String fileName = name + ".yml";

        try (var paths = Files.walk(Paths.get(dirPath))) {
            Path configFile = paths
                    .filter(path -> path.toFile().getName().equals(fileName))
                    .findFirst()
                    .orElse(null);

            if (configFile != null) {
                File yamlFile = configFile.toFile();

                // Load the YAML file into a ConfigurationSection
                YamlConfiguration yamlConfig = new YamlConfiguration();
                yamlConfig.load(yamlFile);

                ConfigurationSection configSection = yamlConfig.getConfigurationSection("");
                ConfigurationSection statsSection = yamlConfig.getConfigurationSection("stats");

                List<String> list = new ArrayList<>();

                if (statsSection != null) {
                    if (statsSection.contains("Mana")) {
                        updateStat(pd, statsSection, list, "Mana", "remove");
                    }
                    if (statsSection.contains("Strength")) {
                        updateStat(pd, statsSection, list, "Strength", "remove");
                    }
                    if (statsSection.contains("Health")) {
                        updateStat(pd, statsSection, list, "Health", "remove");
                    }
                    if (statsSection.contains("Defense")) {
                        updateStat(pd, statsSection, list, "Defense", "remove");
                    }
                    if (statsSection.contains("Speed")) {
                        updateStat(pd, statsSection, list, "Speed", "remove");
                    }
                    if (statsSection.contains("Magic")) {
                        updateStat(pd, statsSection, list, "Magic", "remove");
                    }

                    if (!list.isEmpty()) {
                        player.sendMessage(ChatColor.YELLOW + player.getName() + ChatColor.RED + " Got the list stats:");
                        player.sendMessage(String.join("\n", list));
                        player.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + player.getName() + ChatColor.RED + " Got the list stats:");
                        player.getServer().getConsoleSender().sendMessage(String.join("\n", list));
                    }

                    assert configSection != null;
                    if (configSection.contains("set")) {
                        updateSet(player, pd, "removed");
                    }
                }

            } else {
                player.getServer().getConsoleSender().sendMessage("File not Found");
            }
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static List<ItemStack> list(String path, ItemStack item) {

        List<ItemStack> list = getItems("items");
        if (path != null) list = getItems(path);

        if (item != null) {
            list = list.stream().peek(i -> i.setDurability(item.getDurability())).collect(Collectors.toList());
        }

        return list;
    }

    public static List<ItemStack> getItems(String location) {
        ArrayList<ItemStack> list = new ArrayList<>();

        Path path = Paths.get(plugin.getDataFolder() + "/data/" + location);
        try (Stream<Path> walk = Files.walk(path)) {
            List<Path> ymlFiles = walk
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".yml"))
                    .toList();

            ymlFiles.forEach(file -> {
                try {
                    list.add(getItem(String.valueOf(file)));
                } catch (IOException | InvalidConfigurationException e) {
                    throw new RuntimeException(e);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static ItemStack getItem(String path) throws IOException, InvalidConfigurationException {

        File yamlFile = new File(path);

        // Load the YAML file into a ConfigurationSection
        YamlConfiguration yamlConfig = new YamlConfiguration();

        yamlConfig.load(yamlFile);

        ConfigurationSection itemData = yamlConfig.getConfigurationSection("");

        assert itemData != null;
        ItemStack item = new ItemStack(Material.valueOf(itemData.getString("type")), 1);
        ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(itemData.getString("displayName"));

        List<String> lore = new ArrayList<>();
        lore.add("");
        if(itemData.get("attackSpeed") != null) {
            lore.add(ChatColor.GRAY + itemData.getString("attackSpeed") + " Attack Speed");
            lore.add("");
        }
        ConfigurationSection itemStats = itemData.getConfigurationSection("stats");
        if (itemStats != null) {
            for (String stat : itemStats.getKeys(false)) {
                String value = itemStats.getString(stat);
                String cond = "-";
                assert value != null;
                if (Integer.parseInt(value.replace("%","")) > 0) {
                    cond = "+";
                }
                lore.add(ChatColor.GREEN + cond + value + " " + ChatColor.GRAY + stat);
            }
        }

        String set = itemData.getString("set");

        if(set != null) {

            File yamlFile2 = new File(plugin.getDataFolder() + "/data/sets/" + set + ".yml");

            // Load the YAML file into a ConfigurationSection
            YamlConfiguration yamlConfig2 = new YamlConfiguration();

            yamlConfig2.load(yamlFile2);

            ConfigurationSection configSection2 = yamlConfig2.getConfigurationSection("");

            assert configSection2 != null;
            ConfigurationSection statsSection2 = configSection2.getConfigurationSection("stats");
            if (statsSection2 != null) {
                lore.add("");
                lore.add(ChatColor.YELLOW + "Set Bonus:");
                for (String stat : statsSection2.getKeys(false)) {
                    String value = statsSection2.getString(stat);
                    String cond = "-";
                    if (value != null && Integer.parseInt(value.replace("%", "")) > 0) {
                        cond = "+";
                    }
                    lore.add(ChatColor.GREEN + cond + value + " " + ChatColor.GRAY + stat);
                }
                ConfigurationSection ifSection = configSection2.getConfigurationSection("if");
                if (ifSection != null) {
                    ConfigurationSection timeSection = ifSection.getConfigurationSection("time");
                    if (timeSection != null) {
                        ConfigurationSection daySection = timeSection.getConfigurationSection("day");
                        if (daySection != null) {
                            for (String stat2 : daySection.getKeys(false)) {
                                String value2 = daySection.getString(stat2);
                                if(value2 != null) {
                                    String cond2 = "-";
                                    if (Integer.parseInt(value2.replace("%", "")) > 0) {
                                        cond2 = "+";
                                    }
                                    lore.add(ChatColor.GREEN + cond2 + value2 + " " + ChatColor.GRAY + stat2 + ChatColor.YELLOW + " (at Day)");
                                }
                            }
                        }
                        ConfigurationSection nightSection = timeSection.getConfigurationSection("night");
                        if (nightSection != null) {
                            for (String stat2 : nightSection.getKeys(false)) {
                                String value2 = nightSection.getString(stat2);
                                if(value2 != null) {
                                    String cond2 = "-";
                                    if (Integer.parseInt(value2.replace("%", "")) > 0) {
                                        cond2 = "+";
                                    }
                                    lore.add(ChatColor.GREEN + cond2 + value2 + " " + ChatColor.GRAY + stat2 + ChatColor.YELLOW + " (at Night)");
                                }
                            }
                        }
                    }
                }
            }
        }
        lore.add("");
        if (Objects.equals(itemData.get("rarity"), "COMMON")) {
            lore.add("§a§l" + itemData.getString("rarity"));
        }

        itemMeta.setLore(lore);
        if (itemData.getBoolean("unbreakable")) {
            itemMeta.setUnbreakable(true);
        }
        List<String> flags = itemData.getStringList("flags");
        for (String flag : flags) {
            itemMeta.addItemFlags(ItemFlag.valueOf(flag));
        }
        item.setItemMeta(itemMeta);

        if(itemData.contains("color")) {
            LeatherArmorMeta leather_armor_meta = (LeatherArmorMeta) item.getItemMeta();
            String colorData = itemData.getString("color");
            assert colorData != null;
            String[] colorList = colorData.split(",");

            int red = Integer.parseInt(colorList[0]);
            int green = Integer.parseInt(colorList[1]);
            int blue = Integer.parseInt(colorList[2]);

            leather_armor_meta.setColor(Color.fromRGB(red, green, blue));
            item.setItemMeta(leather_armor_meta);
        }

        return item;
    }

}
