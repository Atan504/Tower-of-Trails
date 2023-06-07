package ToT.Listener;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

import static ToT.Main.plugin;

class TextComponentUtil {
    public static BaseComponent[] append(BaseComponent[] baseComponents, BaseComponent... components) {
        BaseComponent[] newComponents = new BaseComponent[baseComponents.length + components.length];
        System.arraycopy(baseComponents, 0, newComponents, 0, baseComponents.length);
        System.arraycopy(components, 0, newComponents, baseComponents.length, components.length);
        return newComponents;
    }
}

public class DisplayItem implements Listener {

    Gson gson = new Gson();

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (event.getMessage().contains("[item]")) {

            String new_message = "<" + player.getDisplayName() + "> " + message;

            String[] parts = new_message.split("\\[item\\]", -1);

            BaseComponent[] messageComponents = new BaseComponent[0];

            for (String part : parts) {
                TextComponent textComponent = new TextComponent(part);

                ItemStack item = player.getInventory().getItemInMainHand();

                if (item.getType() != Material.AIR) {
                    ItemMeta itemMeta = item.getItemMeta();
                    if (itemMeta != null && itemMeta.hasDisplayName()) {
                        TextComponent itemComponent = new TextComponent(ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + item.getAmount() + "x " + ChatColor.RESET + itemMeta.getDisplayName() + ChatColor.DARK_GRAY + "]" + ChatColor.RESET);
                        BaseComponent[] hoverTextComponents = getItemHoverTextComponents(item);
                        itemComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverTextComponents));

                        if (messageComponents.length != 0)
                            messageComponents = TextComponentUtil.append(messageComponents, itemComponent);
                    } else {
                        TextComponent itemComponent = new TextComponent(ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + item.getAmount() + "x " + ChatColor.RESET + item.getType() + ChatColor.DARK_GRAY + "]" + ChatColor.RESET);
                        BaseComponent[] hoverTextComponents = getItemHoverTextComponents(item);
                        itemComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverTextComponents));

                        if (messageComponents.length != 0)
                            messageComponents = TextComponentUtil.append(messageComponents, itemComponent);
                    }
                }

                messageComponents = TextComponentUtil.append(messageComponents, textComponent);
            }

            for (Player p : plugin.getServer().getOnlinePlayers()) {
                p.spigot().sendMessage(ChatMessageType.CHAT, messageComponents);
            }

            event.setCancelled(true);

        }
    }

    public void sendChatMessage(Player player, ItemStack item) {
        // Create the hover event
        BaseComponent[] hoverText = new ComponentBuilder(getItemDisplayName(item)).create();
        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverText);

        // Create the chat component with the hover event
        BaseComponent[] message = new ComponentBuilder("This is a test message!")
                .event(hoverEvent)
                .create();

        // Send the chat component to the player
        player.spigot().sendMessage(message);
    }

    public String serializeItemStack(ItemStack itemStack) {
        Gson gson = new Gson();
        return gson.toJson(itemStack);
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

    public static String fix_enchantments(String enchant) {
        if(enchant.contains("curse")) return ChatColor.RED + enchant + ChatColor.GRAY;
        else return enchant;
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
                enchantmentsText.add("\n" + toProperCase(fix_enchantments(enchantment.getKey().getKey().replace("_", " "))) + " " + intToRoman(itemMeta.getEnchantLevel(enchantment)));
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
}
