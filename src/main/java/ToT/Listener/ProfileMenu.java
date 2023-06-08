package ToT.Listener;

import ToT.Utils.CustomMenu;
import ToT.Data.SpigotData;
import ToT.Objects.TPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Objects;

public class ProfileMenu implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        SpigotData.getInstance().enterEntity(player.getUniqueId());
        TPlayer TP = ((TPlayer) (SpigotData.getInstance().getEntity(player.getUniqueId())));

        if(!(Objects.requireNonNull(event.getClickedInventory()).getType().equals(InventoryType.CHEST) && Objects.equals(event.getClickedInventory().getHolder(), player))) return;

        event.setCancelled(true);

        ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem == null || clickedItem.getType() == Material.AIR || clickedItem.getType() == Material.LIGHT_GRAY_STAINED_GLASS_PANE) {
            return;
        }

        if(ChatColor.stripColor(Objects.requireNonNull(clickedItem.getItemMeta()).getDisplayName()).contains("Skill Points")) {
            if (event.isShiftClick()) {
                if (event.isLeftClick() || event.isRightClick()) {
                    CustomMenu.update_values(player, event.getClickedInventory(), "mana", -TP.getStats()[1]);
                    CustomMenu.update_values(player, event.getClickedInventory(), "str", -TP.getStats()[2]);
                    CustomMenu.update_values(player, event.getClickedInventory(), "hp", -TP.getStats()[4]);
                    CustomMenu.update_values(player, event.getClickedInventory(), "def", -TP.getStats()[5]);
                    CustomMenu.update_values(player, event.getClickedInventory(), "speed", -TP.getStats()[6]);
                    CustomMenu.update_values(player, event.getClickedInventory(), "magic", -TP.getStats()[7]);
                }
            }
        }

        ItemMeta meta = clickedItem.getItemMeta();
        List<String> lore = meta.getLore();

        if(ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).equals("Mana")) {
            if (event.isShiftClick()) {
                if (event.isLeftClick()) {
                    CustomMenu.update_values(player, event.getClickedInventory(), "mana", 5);
                }
            } else {
                if (event.isLeftClick()) {
                    CustomMenu.update_values(player, event.getClickedInventory(), "mana", 1);
                }
            }
        }

        if(ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).equals("Strength")) {
            if (event.isShiftClick()) {
                if (event.isLeftClick()) {
                    CustomMenu.update_values(player, event.getClickedInventory(), "str", 5);
                }
            } else {
                if (event.isLeftClick()) {
                    CustomMenu.update_values(player, event.getClickedInventory(), "str", 1);
                }
            }
        }

        if(ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).equals("Health")) {
            if (event.isShiftClick()) {
                if (event.isLeftClick()) {
                    CustomMenu.update_values(player, event.getClickedInventory(), "hp", 5);
                }
            } else {
                if (event.isLeftClick()) {
                    CustomMenu.update_values(player, event.getClickedInventory(), "hp", 1);
                }
            }
        }

        if(ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).equals("Defense")) {
            if (event.isShiftClick()) {
                if (event.isLeftClick()) {
                    CustomMenu.update_values(player, event.getClickedInventory(), "def", 5);
                }
            } else {
                if (event.isLeftClick()) {
                    CustomMenu.update_values(player, event.getClickedInventory(), "def", 1);
                }
            }
        }

        if(ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).equals("Speed")) {
            if (event.isShiftClick()) {
                if (event.isLeftClick()) {
                    CustomMenu.update_values(player, event.getClickedInventory(), "speed", 5);
                }
            } else {
                if (event.isLeftClick()) {
                    CustomMenu.update_values(player, event.getClickedInventory(), "speed", 1);
                }
            }
        }

        if(ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).equals("Magic")) {
            if (event.isShiftClick()) {
                if (event.isLeftClick()) {
                    CustomMenu.update_values(player, event.getClickedInventory(), "magic", 5);
                }
            } else {
                if (event.isLeftClick()) {
                    CustomMenu.update_values(player, event.getClickedInventory(), "magic", 1);
                }
            }
        }

        meta.setLore(lore);
        clickedItem.setItemMeta(meta);

    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {

    }

}
