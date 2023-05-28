package Atan.Listener;

import Atan.CustomMenu;
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

        if(!(Objects.requireNonNull(event.getClickedInventory()).getType().equals(InventoryType.CHEST) && Objects.equals(event.getClickedInventory().getHolder(), player))) return;

        event.setCancelled(true);

        ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem == null || clickedItem.getType() == Material.AIR || clickedItem.getType() == Material.LIGHT_GRAY_STAINED_GLASS_PANE) {
            return;
        }

        if(ChatColor.stripColor(Objects.requireNonNull(clickedItem.getItemMeta()).getDisplayName()).contains("Skill Points")) {
            if (event.isShiftClick()) {
                if (event.isLeftClick() || event.isRightClick()) {
                    CustomMenu.update_values(player, event.getClickedInventory(), "mana", -player.getMetadata("skills.point.mana").get(0).asInt());
                    CustomMenu.update_values(player, event.getClickedInventory(), "str", -player.getMetadata("skills.point.str").get(0).asInt());
                    CustomMenu.update_values(player, event.getClickedInventory(), "hp", -player.getMetadata("skills.point.hp").get(0).asInt());
                    CustomMenu.update_values(player, event.getClickedInventory(), "def", -player.getMetadata("skills.point.def").get(0).asInt());
                    CustomMenu.update_values(player, event.getClickedInventory(), "speed", -player.getMetadata("skills.point.speed").get(0).asInt());
                    CustomMenu.update_values(player, event.getClickedInventory(), "magic", -player.getMetadata("skills.point.magic").get(0).asInt());
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
