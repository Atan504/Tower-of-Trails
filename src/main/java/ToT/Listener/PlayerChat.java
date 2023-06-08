package ToT.Listener;

import ToT.Utils.CustomMenu;
import ToT.Data.SpigotData;
import ToT.Objects.TPlayer;
import ToT.Utils.PartyManagment;
import ToT.Utils.Utils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.util.io.BukkitObjectOutputStream;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Logger;

public class PlayerChat implements Listener, Plugin {

    private String itemToBase64(ItemStack itemStack) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeObject(itemStack);
            dataOutput.close();
            return Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private ItemStack getItemInMainHand(Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() != Material.AIR) {
            return item;
        }
        return null;
    }

    private String getItemDisplayName(ItemStack item) {
        if (item != null) {
            ItemMeta meta = item.getItemMeta();
            assert meta != null;
            if (meta.hasDisplayName()) {
                return meta.getDisplayName();
            } else {
                return item.getType().toString();
            }
        }
        return "Unknown Item";
    }

    private void sendHoverText(Player player, String playerName, String message, ItemStack item) {
        TextComponent textComponent = new TextComponent(playerName + ": " + message);

        // Create a new TextComponent for the item with HoverEvent and SHOW_ITEM action
        TextComponent itemComponent = new TextComponent("[" + getItemDisplayName(item) + "]");
        itemComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM,
                new BaseComponent[]{new TextComponent(item.serialize().toString())}));

        // Append the item component to the text component
        textComponent.addExtra(itemComponent);

        // Send the message to the player
        player.spigot().sendMessage(textComponent);
    }

    public static String toProperCase(String str) {
        StringBuilder properCase = new StringBuilder();

        boolean capitalizeNext = true;
        for (char c : str.toCharArray()) {
            if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            } else if (capitalizeNext) {
                c = Character.toTitleCase(c);
                capitalizeNext = false;
            }
            properCase.append(c);
        }

        return properCase.toString();
    }

    public static String intToRoman(int num) {
        String[] romanSymbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

        StringBuilder romanNumeral = new StringBuilder();
        int index = 0;

        while (num > 0) {
            if (num >= values[index]) {
                romanNumeral.append(romanSymbols[index]);
                num -= values[index];
            } else {
                index++;
            }
        }

        return romanNumeral.toString();
    }

    private BaseComponent[] getItemHoverTextComponents(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        int number = 2;

        assert itemMeta != null;
        if(itemMeta.hasEnchants()) number++;
        if(itemMeta.hasLore()) number++;

        BaseComponent[] hoverTextComponents = new BaseComponent[number];

        ArrayList<BaseComponent> texts = new ArrayList<>();

        // First line: Display name
        if(itemMeta.hasDisplayName()) texts.add(new TextComponent(itemMeta.getDisplayName()));
        else texts.add(new TextComponent(String.valueOf(itemStack.getType())));

        if(itemMeta.hasEnchants()) {
            ArrayList<String> enchantmentsText = new ArrayList<>();
            for (Enchantment enchantment : itemMeta.getEnchants().keySet()) {
                enchantmentsText.add("\n" + toProperCase(enchantment.getKey().getKey()) + " " + intToRoman(itemMeta.getEnchantLevel(enchantment)));
            }
            texts.add(new TextComponent(ChatColor.GRAY + String.join("", enchantmentsText)));
        }

        if (itemMeta.hasLore()) texts.add(new TextComponent("\n" + ChatColor.GRAY + String.join("\n", Objects.requireNonNull(itemMeta.getLore()))));

        texts.add(new TextComponent("\n " + ChatColor.DARK_GRAY + itemStack.getType().getKey()));

        for(int i = 0; i < hoverTextComponents.length; i++) {
            hoverTextComponents[i] = texts.get(i);
        }

        return hoverTextComponents;
    }


    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();
        String message = event.getMessage();

        SpigotData.getInstance().enterEntity(player.getUniqueId());
        TPlayer TP = ((TPlayer) (SpigotData.getInstance().getEntity(player.getUniqueId())));

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
        } else if(message.equals("stats")) {
            event.setCancelled(true);
            int[] stats = ((TPlayer) SpigotData.getInstance().getEntity(player.getUniqueId())).getStats();

            player.sendMessage("=-=-=-=-=-=-=-=-=-=-=-=");
            player.sendMessage("Mana: " + stats[0]);
            player.sendMessage("Max Mana: " + stats[1]);
            player.sendMessage("Strength: " + stats[2]);
            player.sendMessage("Health: " + stats[3]);
            player.sendMessage("Max Health: " + stats[4]);
            player.sendMessage("Defense: " + stats[5]);
            player.sendMessage("Speed: " + stats[6]);
            player.sendMessage("Magic: " + stats[7]);

        } else if(message.equals("reset")) {
            event.setCancelled(true);
            int[] stats = ((TPlayer) SpigotData.getInstance().getEntity(player.getUniqueId())).getStats();

            Arrays.fill(stats, 0);

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

            TP.getPoints()[0] = 500;

            player.sendMessage("YOU ARE THE TRUE FABULUS GOD!!!!!");
        } else if(message.equals("FABULOUS")) {
            event.setCancelled(true);

            TP.getPoints()[0] = 2;

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

            itemMeta.setUnbreakable(true); // Optional: Make the item unbreakable

            diamondSword.setItemMeta(itemMeta);

// You can then use the ItemStack as needed, for example, give it to a player:
            player.getInventory().addItem(diamondSword);
        } else {
            List<MetadataValue> active = player.getMetadata("party.chat.toggle");

            if(!active.isEmpty()) {
                if(active.get(0).asBoolean()) {
                    event.setCancelled(true);
                    Player[] party = PartyManagment.getParty(player);
                    Player[] members = PartyManagment.getMembers(party);

                    for (Player member : members) {
                        if(PartyManagment.isOwner(party, player)) {
                            member.sendMessage(ChatColor.BLUE + "[" + ChatColor.AQUA + ChatColor.BOLD + "PARTY" + ChatColor.BLUE + "] " + ChatColor.GOLD + player.getName() + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + event.getMessage());
                        } else {
                            member.sendMessage(ChatColor.BLUE + "[" + ChatColor.AQUA + ChatColor.BOLD + "PARTY" + ChatColor.BLUE + "] " + ChatColor.YELLOW + player.getName() + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + event.getMessage());
                        }
                    }
                }
                }
            }

    }

    @NotNull
    @Override
    public File getDataFolder() {
        return null;
    }

    @NotNull
    @Override
    public PluginDescriptionFile getDescription() {
        return null;
    }

    @NotNull
    @Override
    public FileConfiguration getConfig() {
        return null;
    }

    @Nullable
    @Override
    public InputStream getResource(@NotNull String s) {
        return null;
    }

    @Override
    public void saveConfig() {

    }

    @Override
    public void saveDefaultConfig() {

    }

    @Override
    public void saveResource(@NotNull String s, boolean b) {

    }

    @Override
    public void reloadConfig() {

    }

    @NotNull
    @Override
    public PluginLoader getPluginLoader() {
        return null;
    }

    @NotNull
    @Override
    public Server getServer() {
        return null;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {

    }

    @Override
    public boolean isNaggable() {
        return false;
    }

    @Override
    public void setNaggable(boolean b) {

    }

    @Nullable
    @Override
    public ChunkGenerator getDefaultWorldGenerator(@NotNull String s, @Nullable String s1) {
        return null;
    }

    @Nullable
    @Override
    public BiomeProvider getDefaultBiomeProvider(@NotNull String s, @Nullable String s1) {
        return null;
    }

    @NotNull
    @Override
    public Logger getLogger() {
        return null;
    }

    @NotNull
    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}
