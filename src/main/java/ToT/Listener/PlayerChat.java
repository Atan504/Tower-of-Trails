package ToT.Listener;

import ToT.CustomMenu;
import ToT.Main;
import ToT.PlayerData;
import ToT.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
public class PlayerChat implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();
        String message = event.getMessage();

        if (message.toLowerCase().startsWith("class")) {
            event.setCancelled(true);

            String[] args = event.getMessage().toLowerCase().split(" ");

            List<ItemStack> listItems = Utils.getItems("items/weapon/" + args[1]);

            if(listItems.size() == 0) {
                player.sendMessage("Class " + args[1] + " not found!");
                return;
            }

            for(ItemStack item : listItems) {
                player.getInventory().addItem(item);
            }
            player.sendMessage(args[1] + "  equipments Loaded!");
        }

        if (message.toLowerCase().startsWith("armor")) {
            event.setCancelled(true);

            String[] args = event.getMessage().toLowerCase().split(" ");

            List<ItemStack> listItems = Utils.getItems("items/armor/" + args[1]);

            if(listItems.size() == 0) {
                player.sendMessage("Armor " + args[1] + " not found!");
                return;
            }

            for(ItemStack item : listItems) {
                player.getInventory().addItem(item);
            }
            player.sendMessage("You got the " + args[1] + " armor!");
        }

        if (message.equals("menu")) {
            event.setCancelled(true);

            CustomMenu.openInventory(player, CustomMenu.getInventory(player, "skills_selector", 0));
        }

        if (message.equals("menu2")) {
            event.setCancelled(true);

            CustomMenu.openInventory(player, CustomMenu.getInventory(player, "profile_menu", 0));
        }

        if (message.startsWith("stats")) {
            event.setCancelled(true);

            PlayerData pd = new PlayerData(player.getUniqueId());

            String[] args = event.getMessage().toLowerCase().split(" ");

            if (args[2].equals("set")) {
                if(args[1].equalsIgnoreCase("mana")) {
                    pd.set("maxmana", Integer.parseInt(args[3]));
                }

                if(args[1].equalsIgnoreCase("strength")) {
                    pd.set("str", Integer.parseInt(args[3]));
                }

                if(args[1].equalsIgnoreCase("health")) {
                    pd.set("maxhp", Integer.parseInt(args[3]));
                }

                if(args[1].equalsIgnoreCase("defense")) {
                    pd.set("def", Integer.parseInt(args[3]));
                }

                if(args[1].equalsIgnoreCase("speed")) {
                    pd.set("spe", Integer.parseInt(args[3]));
                }

                if(args[1].equalsIgnoreCase("magic")) {
                    pd.set("magic", Integer.parseInt(args[3]));
                }
            }
        }

        if(message.equals("FABULUS")) {
            event.setCancelled(true);

            player.setMetadata("skills.point.count", new FixedMetadataValue(Main.plugin, 500));

            player.sendMessage("YOU ARE THE TRUE FABULUS GOD!!!!!");
        }

        if(message.equals("FABULOUS")) {
            event.setCancelled(true);

            player.setMetadata("skills.point.count", new FixedMetadataValue(Main.plugin, 2));

            player.sendMessage("YOU ARE NOT REAL FABULUS!!!!");
        }

        if(message.equals("wynncraft")) {

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
                    ChatColor.GREEN + "+" + generateRandomInteger(7, 30) + "%" + ChatColor.GRAY + " Main Attack Damage",
                    ChatColor.GREEN + "+" + generateRandomInteger(7, 30) + "%" + ChatColor.GRAY + " Spell Damage",
                    ChatColor.RED + String.valueOf(generateRandomInteger(-18, -10)) + "/5s" + ChatColor.GRAY + " Mana Regen",
                    ChatColor.RED + String.valueOf(generateRandomInteger(-18, -10)) + "%" + ChatColor.GRAY + " Walk Speed",
                    ChatColor.GREEN + "+" + generateRandomInteger(9, 40) + "%" + ChatColor.GRAY + " Fire Defence",
                    "",
                    ChatColor.GRAY + "[0/2] Powder slots",
                    ChatColor.GREEN + "Set Item"
            ));

            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

            itemMeta.setUnbreakable(true); // Optional: Make the item unbreakable

            diamondSword.setItemMeta(itemMeta);

// You can then use the ItemStack as needed, for example, give it to a player:
            player.getInventory().addItem(diamondSword);
        }

    }

    public static int generateRandomInteger(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

}
