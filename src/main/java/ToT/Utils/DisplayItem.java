package ToT.Utils;

import com.google.gson.Gson;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Objects;

public class DisplayItem {
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

    public static String fix_enchantments(String enchant) {
        if(enchant.contains("curse")) return ChatColor.RED + enchant + ChatColor.GRAY;
        else return enchant;
    }

    public static BaseComponent[] getItemHoverTextComponents(ItemStack itemStack) {
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
                enchantmentsText.add("\n" + Utils.toProperCase(fix_enchantments(enchantment.getKey().getKey().replace("_", " "))) + " " + Utils.intToRoman(itemMeta.getEnchantLevel(enchantment)));
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
