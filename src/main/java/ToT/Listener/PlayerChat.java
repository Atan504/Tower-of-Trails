package ToT.Listener;

import ToT.System.PartyManagment.Utils;
import ToT.Utils.CustomMenu;
import ToT.Objects.TPlayer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

import static ToT.Main.plugin;
import static ToT.Utils.Utils.getPlayerData;

public class PlayerChat implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();
        String message = event.getMessage();

        if (message.toLowerCase().startsWith("class")) {
            event.setCancelled(true);

            String[] args = event.getMessage().toLowerCase().split(" ");

            List<ItemStack> listItems = ToT.Utils.Utils.getItems("items/weapon/" + args[1]);

            if(listItems.size() == 0) {
                player.sendMessage("Class " + args[1] + " not found!");
                return;
            }

            for(ItemStack item : listItems) {
                player.getInventory().addItem(item);
            }
            player.sendMessage(args[1] + "  equipments Loaded!");
        } else if (message.toLowerCase().startsWith("armor")) {
            event.setCancelled(true);

            String[] args = event.getMessage().toLowerCase().split(" ");

            List<ItemStack> listItems = ToT.Utils.Utils.getItems("items/armor/" + args[1]);

            if(listItems.size() == 0) {
                player.sendMessage("Armor " + args[1] + " not found!");
                return;
            }

            for(ItemStack item : listItems) {
                player.getInventory().addItem(item);
            }
            player.sendMessage("You got the " + args[1] + " armor!");
        } else if (message.equals("menu")) {
            event.setCancelled(true);

            CustomMenu.openInventory(player, CustomMenu.getInventory(player, "skills_selector", 0));
        } else if (message.equals("menu2")) {
            event.setCancelled(true);

            CustomMenu.openInventory(player, CustomMenu.getInventory(player, "profile_menu", 0));
        } else if(message.startsWith("stats")) {
            event.setCancelled(true);

            TPlayer data = getPlayerData(player.getUniqueId());
            int[] statPoints = data.getStatPoints();

            String[] args = message.split(" ");

            if(args.length >= 3) {
                if(ToT.Utils.Utils.isInteger(args[2])) {
                    System.out.println(1);
                    if (Objects.equals(args[1], "mana")) statPoints[0] = Integer.parseInt(args[2]);
                    if (Objects.equals(args[1], "max_mana")) statPoints[1] = Integer.parseInt(args[2]);
                    if (Objects.equals(args[1], "str")) statPoints[2] = Integer.parseInt(args[2]);
                    if (Objects.equals(args[1], "hp")) statPoints[3] = Integer.parseInt(args[2]);
                    if (Objects.equals(args[1], "max_hp")) statPoints[4] = Integer.parseInt(args[2]);
                    if (Objects.equals(args[1], "def")) statPoints[5] = Integer.parseInt(args[2]);
                    if (Objects.equals(args[1], "speed")) statPoints[6] = Integer.parseInt(args[2]);
                    if (Objects.equals(args[1], "magic")) statPoints[7] = Integer.parseInt(args[2]);
                }
            }

            int[] stats = data.getStats();

            player.sendMessage("=-=-=-=-=-=-=-=-=-=-=-=");
            player.sendMessage(ChatColor.AQUA + "♦ Mana: " + stats[0]);
            player.sendMessage(ChatColor.AQUA + "♦ Max Mana: " + stats[1]);
            player.sendMessage(ChatColor.DARK_RED + "🗡 Strength: " + stats[2]);
            player.sendMessage(ChatColor.RED + "❤ Health: " + stats[3]);
            player.sendMessage(ChatColor.RED + "❤ Max Health: " + stats[4]);
            player.sendMessage(ChatColor.GRAY + "🛡 Defense: " + stats[5]);
            player.sendMessage(ChatColor.WHITE + "→ Speed: " + stats[6]);
            player.sendMessage(ChatColor.DARK_PURPLE + "🧪 Magic: " + stats[7]);

        } else if(message.equals("FABULUS")) {
            event.setCancelled(true);

            TPlayer data = getPlayerData(player.getUniqueId());

            data.getPoints()[0] = 500;

            player.sendMessage("YOU ARE THE TRUE FABULUS GOD!!!!!");
        } else if(message.equals("FABULOUS")) {
            event.setCancelled(true);

            TPlayer data = getPlayerData(player.getUniqueId());

            data.getPoints()[0] = 2;

            player.sendMessage("YOU ARE NOT REAL FABULUS!!!!");
        } else if(message.equals("wynncraft")) {

            event.setCancelled(true);

            ItemStack diamondSword = new ItemStack(Material.DIAMOND_SWORD);
            ItemMeta itemMeta = diamondSword.getItemMeta();

            assert itemMeta != null;
            itemMeta.setDisplayName(ChatColor.GREEN + "Continuum");

            itemMeta.setLore(Arrays.asList(
                    "",
                    ChatColor.GRAY + "Very Slow Attack Speed",
                    "",
                    ChatColor.GOLD + "✤ Neutral Damage: 0-100",
                    ChatColor.DARK_GREEN + "✤ Earth " + ChatColor.GRAY + "Damage: 100-700",
                    ChatColor.DARK_GRAY + "     Average DPS: " + ChatColor.GRAY + "374",
                    "",
                    ChatColor.GREEN + "✔ " + ChatColor.GRAY + "Class Req: Mage/Dark Wizard",
                    ChatColor.GREEN + "✔ " + ChatColor.GRAY + "Combat Lv. Min: 105",
                    ChatColor.RED + "❌ " + ChatColor.GRAY + "Strength Min: 70",
                    "",
                    ChatColor.GREEN + "+15" + ChatColor.GRAY + " Strength",
                    "",
                    ChatColor.GREEN + "+" + ToT.Utils.Utils.generateRandomInteger(7, 30) + "%" + ChatColor.GRAY + " Main Attack Damage",
                    ChatColor.GREEN + "+" + ToT.Utils.Utils.generateRandomInteger(7, 30) + "%" + ChatColor.GRAY + " Spell Damage",
                    ChatColor.RED + String.valueOf(ToT.Utils.Utils.generateRandomInteger(-18, -10)) + "/5s" + ChatColor.GRAY + " Mana Regen",
                    ChatColor.RED + String.valueOf(ToT.Utils.Utils.generateRandomInteger(-18, -10)) + "%" + ChatColor.GRAY + " Walk Speed",
                    ChatColor.GREEN + "+" + ToT.Utils.Utils.generateRandomInteger(9, 40) + "%" + ChatColor.GRAY + " Fire Defence",
                    "",
                    ChatColor.GRAY + "[0/2] Powder slots",
                    ChatColor.GREEN + "Set Item"
            ));

            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

            itemMeta.setUnbreakable(true);

            diamondSword.setItemMeta(itemMeta);

            player.getInventory().addItem(diamondSword);
        }

        if(message.equals("mobs")) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    Location centerLocation = new Location(player.getWorld(), -977, 67, -998); // Set your desired center location here
                    int distanceBetweenMobs = 3; // Set the desired distance between each mob

// Create a list of the mob types
                    List<EntityType> mobTypes = Arrays.asList(
                            EntityType.ELDER_GUARDIAN,
                            EntityType.WITHER_SKELETON,
                            EntityType.STRAY,
                            EntityType.HUSK,
                            EntityType.ZOMBIE_VILLAGER,
                            EntityType.SKELETON_HORSE,
                            EntityType.ZOMBIE_HORSE,
                            EntityType.DONKEY,
                            EntityType.MULE,
                            EntityType.EVOKER,
                            EntityType.VEX,
                            EntityType.VINDICATOR,
                            EntityType.ILLUSIONER,
                            EntityType.CREEPER,
                            EntityType.SKELETON,
                            EntityType.SPIDER,
                            EntityType.GIANT,
                            EntityType.ZOMBIE,
                            EntityType.SLIME,
                            EntityType.GHAST,
                            EntityType.ZOMBIFIED_PIGLIN,
                            EntityType.ENDERMAN,
                            EntityType.CAVE_SPIDER,
                            EntityType.SILVERFISH,
                            EntityType.BLAZE,
                            EntityType.MAGMA_CUBE,
                            // EntityType.ENDER_DRAGON,
                            // EntityType.WITHER,
                            EntityType.BAT,
                            EntityType.WITCH,
                            EntityType.ENDERMITE,
                            EntityType.GUARDIAN,
                            EntityType.SHULKER,
                            EntityType.PIG,
                            EntityType.SHEEP,
                            EntityType.COW,
                            EntityType.CHICKEN,
                            EntityType.SQUID,
                            EntityType.WOLF,
                            EntityType.MUSHROOM_COW,
                            EntityType.SNOWMAN,
                            EntityType.OCELOT,
                            EntityType.IRON_GOLEM,
                            EntityType.HORSE,
                            EntityType.RABBIT,
                            EntityType.POLAR_BEAR,
                            EntityType.LLAMA,
                            EntityType.PARROT,
                            EntityType.VILLAGER,
                            EntityType.TURTLE,
                            EntityType.PHANTOM,
                            EntityType.COD,
                            EntityType.SALMON,
                            EntityType.PUFFERFISH,
                            EntityType.TROPICAL_FISH,
                            EntityType.DROWNED,
                            EntityType.DOLPHIN,
                            EntityType.CAT,
                            EntityType.PANDA,
                            EntityType.PILLAGER,
                            EntityType.RAVAGER,
                            EntityType.TRADER_LLAMA,
                            EntityType.WANDERING_TRADER,
                            EntityType.FOX,
                            EntityType.BEE,
                            EntityType.HOGLIN,
                            EntityType.PIGLIN,
                            EntityType.STRIDER,
                            EntityType.ZOGLIN,
                            EntityType.PIGLIN_BRUTE,
                            EntityType.AXOLOTL,
                            EntityType.GLOW_SQUID,
                            EntityType.GOAT,
                            EntityType.ALLAY,
                            EntityType.FROG,
                            EntityType.TADPOLE,
                            EntityType.WARDEN
                    );

                    for (int i = 0; i < mobTypes.size(); i++) {
                        EntityType mobType = mobTypes.get(i);

                        // Calculate the offset for the current mob
                        double offsetX = (i % 3) * distanceBetweenMobs;
                        double offsetZ = (i / 3) * distanceBetweenMobs;

                        // Calculate the spawn location for the current mob
                        Location spawnLocation = centerLocation.clone().add(offsetX, 0, offsetZ);

                        // Spawn the mob at the calculated location
                        LivingEntity mob = (LivingEntity) centerLocation.getWorld().spawnEntity(spawnLocation, mobType);

                        // You can customize the mob further if needed
                        if (mob instanceof Mob) {
                            Mob mobEntity = (Mob) mob;
                            mobEntity.setAI(false);
                            mobEntity.setInvulnerable(true);
                        }
                    }
                }
            }.runTaskLater(plugin, 5L);
        }

    }
}
