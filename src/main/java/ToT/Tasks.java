package ToT;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.List;

import static ToT.Main.plugin;

public class Tasks {

    static final Gson gson = new GsonBuilder().create();

    public static void saveItemStackAsMetadata(Player player, String key, ItemStack itemStack) {
        String itemStackJson = gson.toJson(itemStack);
        player.setMetadata(key, new FixedMetadataValue(plugin, itemStackJson));
    }

    public static ItemStack retrieveItemStackFromMetadata(Player player, String key) {
        if (player.hasMetadata(key)) {
            String itemStackJson = player.getMetadata(key).get(0).asString();
            return gson.fromJson(itemStackJson, ItemStack.class);
        }
        return null;
    }

    public static void updateStats() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (Player p : plugin.getServer().getOnlinePlayers()) {

                // Item Change
                ItemStack item = p.getInventory().getItemInMainHand();

                List<ItemStack> items = Utils.list("items/weapon", item);

                ItemStack weapon_value = retrieveItemStackFromMetadata(p, "playersWithModifiedStats.weapon." + p.getUniqueId());
                ItemStack helmet_value = retrieveItemStackFromMetadata(p, "playersWithModifiedStats.helmet." + p.getUniqueId());
                ItemStack chestplate_value = retrieveItemStackFromMetadata(p, "playersWithModifiedStats.chestplate." + p.getUniqueId());
                ItemStack leggings_value = retrieveItemStackFromMetadata(p, "playersWithModifiedStats.leggings." + p.getUniqueId());
                ItemStack boots_value = retrieveItemStackFromMetadata(p, "playersWithModifiedStats.boots." + p.getUniqueId());

                if (items.contains(item)) {
                    if (weapon_value == null) {
                        Utils.addStats(p, item);
                        saveItemStackAsMetadata(p, "playersWithModifiedStats.weapon." + p.getUniqueId(), item);
                    } else {
                        if(!weapon_value.equals(item)) {
                            Utils.removeStats(p, weapon_value);
                            p.removeMetadata("playersWithModifiedStats.weapon." + p.getUniqueId(), plugin);
                        }
                    }
                } else {
                    if (weapon_value != null) {
                        Utils.removeStats(p, weapon_value);
                        p.removeMetadata("playersWithModifiedStats.weapon." + p.getUniqueId(), plugin);
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
                        saveItemStackAsMetadata(p, "playersWithModifiedStats.helmet." + p.getUniqueId(), helmet);
                    } else {
                        if(!helmet_value.equals(helmet)) {
                            Utils.removeStats(p, helmet_value);
                            p.removeMetadata("playersWithModifiedStats.helmet." + p.getUniqueId(), plugin);
                        }
                    }
                } else {
                    if (helmet_value != null) {
                        Utils.removeStats(p, helmet_value);
                        p.removeMetadata("playersWithModifiedStats.helmet." + p.getUniqueId(), plugin);
                    }
                }




                if (items_chestplate.contains(chestplate)) {
                    if (chestplate_value == null) {
                        assert chestplate != null;
                        Utils.addStats(p, chestplate);
                        saveItemStackAsMetadata(p, "playersWithModifiedStats.chestplate." + p.getUniqueId(), chestplate);
                    } else {
                        if(!chestplate_value.equals(chestplate)) {
                            Utils.removeStats(p, chestplate_value);
                            p.removeMetadata("playersWithModifiedStats.chestplate." + p.getUniqueId(), plugin);
                        }
                    }
                } else {
                    if (chestplate_value != null) {
                        Utils.removeStats(p, chestplate_value);
                        p.removeMetadata("playersWithModifiedStats.chestplate." + p.getUniqueId(), plugin);
                    }
                }




                if (items_leggings.contains(leggings)) {
                    if (leggings_value == null) {
                        assert leggings != null;
                        Utils.addStats(p, leggings);
                        saveItemStackAsMetadata(p, "playersWithModifiedStats.leggings." + p.getUniqueId(), leggings);
                    } else {
                        if(!leggings_value.equals(leggings)) {
                            Utils.removeStats(p, leggings_value);
                            p.removeMetadata("playersWithModifiedStats.leggings." + p.getUniqueId(), plugin);
                        }
                    }
                } else {
                    if (leggings_value != null) {
                        Utils.removeStats(p, leggings_value);
                        p.removeMetadata("playersWithModifiedStats.leggings." + p.getUniqueId(), plugin);
                    }
                }




                if (items_boots.contains(boots)) {
                    if (boots_value == null) {
                        assert boots != null;
                        Utils.addStats(p, boots);
                        saveItemStackAsMetadata(p, "playersWithModifiedStats.boots." + p.getUniqueId(), boots);
                    } else {
                        if(!boots_value.equals(boots)) {
                            Utils.removeStats(p, boots_value);
                            p.removeMetadata("playersWithModifiedStats.boots." + p.getUniqueId(), plugin);
                        }
                    }
                } else {
                    if (boots_value != null) {
                        Utils.removeStats(p, boots_value);
                        p.removeMetadata("playersWithModifiedStats.boots." + p.getUniqueId(), plugin);
                    }
                }

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
