package ToT.Listener;

import ToT.Utils.TextComponentUtil;
import ToT.Utils.Utils;
import de.tr7zw.nbtapi.NBTItem;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.hover.content.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ToT.Main.plugin;

public class DisplayItemChat implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (event.getMessage().contains("[item]")) {

            event.setCancelled(true);

            String message = "<" + player.getDisplayName() + "> " + event.getMessage();

            ItemStack item = player.getInventory().getItemInMainHand();
            NBTItem nbti = new NBTItem(item);
            String type = item.getType().getKey().toString();
            ItemTag NBT = ItemTag.ofNbt(String.valueOf(nbti));
            int amount = Arrays.stream(player.getInventory().getContents())
                    .filter(inventoryItem -> inventoryItem != null && inventoryItem.getType().getKey().toString().equals(type))
                    .filter(inventoryItem -> {
                        NBTItem nbtItem = new NBTItem(inventoryItem);
                        return nbtItem.hasNBTData() && nbtItem.toString().equals(nbti.toString());
                    })
                    .mapToInt(ItemStack::getAmount)
                    .sum();
            Item itemJSON = new Item(type, amount, NBT);

            String name = Utils.toProperCase(item.getType().name());
            ItemMeta meta = item.getItemMeta();
            if(meta != null) {
                if(meta.hasDisplayName()) {
                    name = item.getItemMeta().getDisplayName();
                }
            }

            String item_text = ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + amount + "x " + ChatColor.RESET + name + ChatColor.DARK_GRAY + "]" + ChatColor.RESET;

            List<String> list = new ArrayList<>(List.of(message.split(" ")));

            TextComponent textComponent;
            TextComponent itemComponent;
            BaseComponent[] messageComponents = new BaseComponent[0];

            for(int i = 0; i < list.size(); i++) {
                if(list.get(i).contains("[item]")) {
                    list.set(i, list.get(i).replace("[item]", item_text));
                    itemComponent = new TextComponent(item_text + " ");

                    HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_ITEM, itemJSON);
                    itemComponent.setHoverEvent(hoverEvent);

                    messageComponents = TextComponentUtil.append(messageComponents, itemComponent);
                } else {
                    textComponent = new TextComponent(list.get(i) + " ");
                    messageComponents = TextComponentUtil.append(messageComponents, textComponent);
                }
            }

            for (Player p : plugin.getServer().getOnlinePlayers()) {
                p.spigot().sendMessage(ChatMessageType.CHAT, messageComponents);
            }

        }
    }
}
