package ToT;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

public class Tasks {

    public static void updateStats() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.plugin, () -> {
            for (Player p : Main.plugin.getServer().getOnlinePlayers()) {

                // Item Change
                ItemStack item = p.getInventory().getItemInMainHand();

                List<ItemStack> items = Utils.list("items/weapon", item);

                List<MetadataValue> weapon_values = p.getMetadata("playersWithModifiedStats.weapon." + p.getUniqueId());
                List<MetadataValue> helmet_values = p.getMetadata("playersWithModifiedStats.helmet." + p.getUniqueId());
                List<MetadataValue> chestplate_values = p.getMetadata("playersWithModifiedStats.chestplate." + p.getUniqueId());
                List<MetadataValue> leggings_values = p.getMetadata("playersWithModifiedStats.leggings." + p.getUniqueId());
                List<MetadataValue> boots_values = p.getMetadata("playersWithModifiedStats.boots." + p.getUniqueId());

                if (items.contains(item)) {
                    if (weapon_values.isEmpty()) {
                        Utils.addStats(p, item);
                        p.setMetadata("playersWithModifiedStats.weapon." + p.getUniqueId(), new FixedMetadataValue(Main.plugin, item));
                    } else {
                        if(!weapon_values.get(0).equals(item)) {
                            Utils.removeStats(p, (ItemStack) weapon_values.get(0));
                            p.removeMetadata("playersWithModifiedStats.weapon." + p.getUniqueId(), Main.plugin);
                        }
                    }
                } else {
                    if (!weapon_values.isEmpty()) {
                        Utils.removeStats(p, (ItemStack) weapon_values.get(0));
                        p.removeMetadata("playersWithModifiedStats.weapon." + p.getUniqueId(), Main.plugin);
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
                    if (helmet_values.isEmpty()) {
                        assert helmet != null;
                        Utils.addStats(p, helmet);
                        p.setMetadata("playersWithModifiedStats.helmet." + p.getUniqueId(), new FixedMetadataValue(Main.plugin, helmet));
                    } else {
                        if(!helmet_values.get(0).equals(helmet)) {
                            Utils.removeStats(p, (ItemStack) helmet_values.get(0));
                            p.removeMetadata("playersWithModifiedStats.helmet." + p.getUniqueId(), Main.plugin);
                        }
                    }
                } else {
                    if (!helmet_values.isEmpty()) {
                        Utils.removeStats(p, (ItemStack) helmet_values.get(0));
                        p.removeMetadata("playersWithModifiedStats.helmet." + p.getUniqueId(), Main.plugin);
                    }
                }




                if (items_chestplate.contains(chestplate)) {
                    if (chestplate_values.isEmpty()) {
                        assert chestplate != null;
                        Utils.addStats(p, chestplate);
                        p.setMetadata("playersWithModifiedStats.chestplate." + p.getUniqueId(), new FixedMetadataValue(Main.plugin, chestplate));
                    } else {
                        if(!chestplate_values.get(0).equals(chestplate)) {
                            Utils.removeStats(p, (ItemStack) chestplate_values.get(0));
                            p.removeMetadata("playersWithModifiedStats.chestplate." + p.getUniqueId(), Main.plugin);
                        }
                    }
                } else {
                    if (!chestplate_values.isEmpty()) {
                        Utils.removeStats(p, (ItemStack) chestplate_values.get(0));
                        p.removeMetadata("playersWithModifiedStats.chestplate." + p.getUniqueId(), Main.plugin);
                    }
                }




                if (items_leggings.contains(leggings)) {
                    if (leggings_values.isEmpty()) {
                        assert leggings != null;
                        Utils.addStats(p, leggings);
                        p.setMetadata("playersWithModifiedStats.leggings." + p.getUniqueId(), new FixedMetadataValue(Main.plugin, leggings));
                    } else {
                        if(!leggings_values.get(0).equals(leggings)) {
                            Utils.removeStats(p, (ItemStack) leggings_values.get(0));
                            p.removeMetadata("playersWithModifiedStats.leggings." + p.getUniqueId(), Main.plugin);
                        }
                    }
                } else {
                    if (!leggings_values.isEmpty()) {
                        Utils.removeStats(p, (ItemStack) leggings_values.get(0));
                        p.removeMetadata("playersWithModifiedStats.leggings." + p.getUniqueId(), Main.plugin);
                    }
                }




                if (items_boots.contains(boots)) {
                    if (boots_values.isEmpty()) {
                        assert boots != null;
                        Utils.addStats(p, boots);
                        p.setMetadata("playersWithModifiedStats.boots." + p.getUniqueId(), new FixedMetadataValue(Main.plugin, boots));
                    } else {
                        if(!boots_values.get(0).equals(boots)) {
                            Utils.removeStats(p, (ItemStack) boots_values.get(0));
                            p.removeMetadata("playersWithModifiedStats.boots." + p.getUniqueId(), Main.plugin);
                        }
                    }
                } else {
                    if (!boots_values.isEmpty()) {
                        Utils.removeStats(p, (ItemStack) boots_values.get(0));
                        p.removeMetadata("playersWithModifiedStats.boots." + p.getUniqueId(), Main.plugin);
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
