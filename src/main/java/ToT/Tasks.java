package ToT;

import ToT.Data.SpigotData;
import ToT.Objects.TPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

import static ToT.Main.plugin;

public class Tasks {

    public static String getSet(ItemStack item) {
        if(item == null) return "";
        ConfigurationSection config = Utils.getConfig("items", ChatColor.stripColor(Objects.requireNonNull(item.getItemMeta()).getDisplayName()), "");

        return config.getString("set");
    }

    public static int[] getItemStats(ItemStack item) {

        if(item == null) return new int[]{ 0, 0, 0, 0, 0, 0, 0, 0 };
        if(!item.hasItemMeta()) return new int[]{ 0, 0, 0, 0, 0, 0, 0, 0 };
        if(!Objects.requireNonNull(item.getItemMeta()).hasDisplayName()) return new int[]{ 0, 0, 0, 0, 0, 0, 0, 0 };

        ConfigurationSection config = Utils.getConfig("items", ChatColor.stripColor(item.getItemMeta().getDisplayName()), "stats");

        if(config == null) return new int[]{ 0, 0, 0, 0, 0, 0, 0, 0 };

        int mana,str,hp,def,speed,magic;

        mana = config.getInt("Mana");
        str = config.getInt("Strength");
        hp = config.getInt("Health");
        def = config.getInt("Defense");
        speed = config.getInt("Speed");
        magic = config.getInt("Magic");

        return new int[]{ 0, mana, str, 0, hp, def, speed, magic };
    }

    public static void updateStats() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (Player p : plugin.getServer().getOnlinePlayers()) {

                SpigotData.getInstance().enterEntity(p.getUniqueId());

                TPlayer TP = ((TPlayer) SpigotData.getInstance().getEntity(p.getUniqueId()));

                ItemStack weapon = p.getInventory().getItemInMainHand();
                ItemStack helmet = p.getInventory().getHelmet();
                ItemStack chestplate = p.getInventory().getChestplate();
                ItemStack leggings = p.getInventory().getLeggings();
                ItemStack boots = p.getInventory().getBoots();

                if(!Utils.ArmorType(weapon).equals("NONE")) weapon = null;

                int[] helmetStats = getItemStats(helmet);
                int[] chestplateStats = getItemStats(chestplate);
                int[] leggingsStats = getItemStats(leggings);
                int[] bootsStats = getItemStats(boots);
                int[] weaponStats = getItemStats(weapon);

                int[] setBonus = new int[]{ 0, 0, 0, 0, 0, 0, 0, 0 };

                if (getSet(helmet).equals(getSet(chestplate)) && getSet(chestplate).equals(getSet(leggings)) && getSet(leggings).equals(getSet(boots))) {
                            ConfigurationSection stats = Utils.getConfig("sets", getSet(helmet), "stats");

                            setBonus[1] = stats.getInt("Mana");
                            setBonus[2] = stats.getInt("Strength");
                            setBonus[4] = stats.getInt("Health");
                            setBonus[5] = stats.getInt("Defense");
                            setBonus[6] = stats.getInt("Speed");
                            setBonus[7] = stats.getInt("Magic");
                }

                 int[][] totalStats = new int[][]{helmetStats,chestplateStats,leggingsStats,bootsStats,weaponStats,setBonus};

                        TP.setStats(Utils.sumArrays(totalStats));

                /*
                // Item Change
                ItemStack item = p.getInventory().getItemInMainHand();

                List<ItemStack> items = Utils.list("items/weapon", item);

                ItemStack weapon_value = TP.getWeapon();
                ItemStack helmet_value = TP.getArmors()[0];
                ItemStack chestplate_value = TP.getArmors()[1];
                ItemStack leggings_value = TP.getArmors()[2];
                ItemStack boots_value = TP.getArmors()[3];

                int[] stats = new int[6];
                ItemStack[] armors = TP.getArmors();

                if (items.contains(item)) {
                    if (weapon_value == null) {

                        Utils.addStats(p, item);
                        TP.setWeapon(item);
                    } else {
                        if(!weapon_value.equals(item)) {
                            Utils.removeStats(p, weapon_value);
                            TP.setWeapon(null);
                        }
                    }
                } else {
                    if (weapon_value != null) {
                        Utils.removeStats(p, weapon_value);
                        TP.setWeapon(null);
                    }
                }

                // Armor Change
                ItemStack helmet = p.getInventory().getHelmet();
                ItemStack chestplate = p.getInventory().getChestplate();
                ItemStack leggings = p.getInventory().getLeggings();
                ItemStack boots = p.getInventory().getBoots();

                List<ItemStack> items_helmet = Utils.list("items/armor", helmet);
                List<ItemStack> items_chestplate = Utils.list("items/armor", chestplate);
                List<ItemStack> items_leggings = Utils.list("items/armor", leggings);
                List<ItemStack> items_boots = Utils.list("items/armor", boots);

                if (items_helmet.contains(helmet)) {
                    if (helmet_value == null) {
                        assert helmet != null;
                        Utils.addStats(p, helmet);
                        TP.getArmors()[0] = helmet;
                    } else {
                        if(!helmet_value.equals(helmet)) {
                            Utils.removeStats(p, helmet_value);
                            TP.getArmors()[0] = null;
                        }
                    }
                } else {
                    if (helmet_value != null) {
                        Utils.removeStats(p, helmet_value);
                        TP.getArmors()[0] = null;
                    }
                }




                if (items_chestplate.contains(chestplate)) {
                    if (chestplate_value == null) {
                        assert chestplate != null;
                        Utils.addStats(p, chestplate);
                        TP.getArmors()[1] = chestplate;
                    } else {
                        if(!chestplate_value.equals(chestplate)) {
                            Utils.removeStats(p, chestplate_value);
                            TP.getArmors()[1] = null;
                        }
                    }
                } else {
                    if (chestplate_value != null) {
                        Utils.removeStats(p, chestplate_value);
                        TP.getArmors()[1] = null;
                    }
                }




                if (items_leggings.contains(leggings)) {
                    if (leggings_value == null) {
                        assert leggings != null;
                        Utils.addStats(p, leggings);
                        TP.getArmors()[2] = leggings;
                    } else {
                        if(!leggings_value.equals(leggings)) {
                            Utils.removeStats(p, leggings_value);
                            TP.getArmors()[2] = null;
                        }
                    }
                } else {
                    if (leggings_value != null) {
                        Utils.removeStats(p, leggings_value);
                        TP.getArmors()[2] = null;
                    }
                }




                if (items_boots.contains(boots)) {
                    if (boots_value == null) {
                        assert boots != null;
                        Utils.addStats(p, boots);
                        TP.getArmors()[3] = boots;
                    } else {
                        if(!boots_value.equals(boots)) {
                            Utils.removeStats(p, boots_value);
                            TP.getArmors()[3] = null;
                        }
                    }
                } else {
                    if (boots_value != null) {
                        Utils.removeStats(p, boots_value);
                        TP.getArmors()[3] = null;
                    }
                }

                 */

                // Time Change
                /*World world = p.getWorld();
                Long time = world.getTime();
                PlayerData pd = new PlayerData(p.getUniqueId());
                if (time >= 0 && time < 12300) {
                    Utils.updateSet(p, pd, "added");
                } else {
                    Utils.updateSet(p, pd, "added");
                }*/

            }
        }, 0L, 0L);
    }
}
