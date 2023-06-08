package ToT.Listener;

import ToT.Utils.TextComponentUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ToT.Utils.DisplayItem;

import static ToT.Main.plugin;

public class DisplayItemChat implements Listener {

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
                        BaseComponent[] hoverTextComponents = DisplayItem.getItemHoverTextComponents(item);
                        itemComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverTextComponents));

                        if (messageComponents.length != 0)
                            messageComponents = TextComponentUtil.append(messageComponents, itemComponent);
                    } else {
                        TextComponent itemComponent = new TextComponent(ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + item.getAmount() + "x " + ChatColor.RESET + item.getType() + ChatColor.DARK_GRAY + "]" + ChatColor.RESET);
                        BaseComponent[] hoverTextComponents = DisplayItem.getItemHoverTextComponents(item);
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
}
