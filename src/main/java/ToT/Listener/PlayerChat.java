package ToT.Listener;

import ToT.Utils.CustomMenu;
import ToT.Data.SpigotData;
import ToT.Objects.TPlayer;
import ToT.Utils.Data;
import ToT.Utils.PartyManagment;
import ToT.Utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.MetadataValue;

import org.bukkit.entity.Player;

import java.util.*;

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
        } else if (message.toLowerCase().startsWith("armor")) {
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
        } else if (message.equals("menu")) {
            event.setCancelled(true);

            CustomMenu.openInventory(player, CustomMenu.getInventory(player, "skills_selector", 0));
        } else if (message.equals("menu2")) {
            event.setCancelled(true);

            CustomMenu.openInventory(player, CustomMenu.getInventory(player, "profile_menu", 0));
        } else if(message.startsWith("stats")) {
            event.setCancelled(true);

            TPlayer data = PartyManagment.getData(player.getUniqueId());
            int[] statPoints = data.getStatPoints();

            String[] args = message.split(" ");

            if(args.length >= 3) {
                if(Utils.isInteger(args[2])) {
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
            player.sendMessage("Mana: " + stats[0]);
            player.sendMessage("Max Mana: " + stats[1]);
            player.sendMessage("Strength: " + stats[2]);
            player.sendMessage("Health: " + stats[3]);
            player.sendMessage("Max Health: " + stats[4]);
            player.sendMessage("Defense: " + stats[5]);
            player.sendMessage("Speed: " + stats[6]);
            player.sendMessage("Magic: " + stats[7]);

        } else if(message.equals("FABULUS")) {
            event.setCancelled(true);

            TPlayer data = PartyManagment.getData(player.getUniqueId());

            data.getPoints()[0] = 500;

            player.sendMessage("YOU ARE THE TRUE FABULUS GOD!!!!!");
        } else if(message.equals("FABULOUS")) {
            event.setCancelled(true);

            TPlayer data = PartyManagment.getData(player.getUniqueId());

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
                    ChatColor.GREEN + "+" + Utils.generateRandomInteger(7, 30) + "%" + ChatColor.GRAY + " Main Attack Damage",
                    ChatColor.GREEN + "+" + Utils.generateRandomInteger(7, 30) + "%" + ChatColor.GRAY + " Spell Damage",
                    ChatColor.RED + String.valueOf(Utils.generateRandomInteger(-18, -10)) + "/5s" + ChatColor.GRAY + " Mana Regen",
                    ChatColor.RED + String.valueOf(Utils.generateRandomInteger(-18, -10)) + "%" + ChatColor.GRAY + " Walk Speed",
                    ChatColor.GREEN + "+" + Utils.generateRandomInteger(9, 40) + "%" + ChatColor.GRAY + " Fire Defence",
                    "",
                    ChatColor.GRAY + "[0/2] Powder slots",
                    ChatColor.GREEN + "Set Item"
            ));

            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

            itemMeta.setUnbreakable(true);

            diamondSword.setItemMeta(itemMeta);

            player.getInventory().addItem(diamondSword);
        } else {
            List<MetadataValue> active = player.getMetadata("party.chat.toggle");

            if(!active.isEmpty()) {
                if(active.get(0).asBoolean()) {
                    event.setCancelled(true);
                    ArrayList<UUID> party = PartyManagment.getParty(player.getUniqueId());
                    ArrayList<UUID> members = PartyManagment.getMembers(party);

                    for (UUID uuid : members) {
                        Player p = Bukkit.getServer().getPlayer(uuid);
                        if(PartyManagment.isOwner(party, player.getUniqueId())) {
                            if(p != null) p.sendMessage(ChatColor.BLUE + "[" + ChatColor.AQUA + ChatColor.BOLD + "PARTY" + ChatColor.BLUE + "] " + ChatColor.GOLD + player.getName() + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + event.getMessage());
                        } else {
                            if(p != null) p.sendMessage(ChatColor.BLUE + "[" + ChatColor.AQUA + ChatColor.BOLD + "PARTY" + ChatColor.BLUE + "] " + ChatColor.YELLOW + player.getName() + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + event.getMessage());
                        }
                    }
                }
                }
            }

    }
}
