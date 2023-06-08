package ToT.Listener;

import ToT.Utils.CustomMenu;
import ToT.Data.SpigotData;
import ToT.Objects.TPlayer;
import ToT.Utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class SkillSelector implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        SpigotData.getInstance().enterEntity(player.getUniqueId());
        TPlayer TP = ((TPlayer) (SpigotData.getInstance().getEntity(player.getUniqueId())));

        if(!(Objects.requireNonNull(event.getClickedInventory()).getType().equals(InventoryType.CHEST) && Objects.equals(event.getClickedInventory().getHolder(), player))) return;

        event.setCancelled(true);

        ItemStack clickedItem = event.getCurrentItem();

        Inventory skills_selector = CustomMenu.getInventory(player, "skills_selector", 0);
        Inventory skills_select;

        if (clickedItem == null || clickedItem.getType() == Material.AIR) {
            return;
        }

        if (event.getView().getTitle().contains("Skill Selector")) {
            if (event.getCurrentItem() == null) return;

            if(Objects.requireNonNull(clickedItem.getItemMeta()).getDisplayName().contains("Skill Slot #")) {
                player.closeInventory();
                Integer index = Integer.parseInt(clickedItem.getItemMeta().getDisplayName().split("#")[1]);
                skills_select = CustomMenu.getInventory(player, "skills_select", index);

                for(var i = 3; i < 8; i++) {
                    if(TP.getSkills()[i-3] != null) {
                        ItemStack item = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                        ItemMeta meta = item.getItemMeta();
                        String value = TP.getSkills()[i-3];
                        assert meta != null;
                        meta.setDisplayName(org.bukkit.ChatColor.RED + "[" + org.bukkit.ChatColor.WHITE + org.bukkit.ChatColor.BOLD + value + org.bukkit.ChatColor.RED + "] Already in Slot #" + i);
                        item.setItemMeta(meta);
                        skills_select.setItem(CustomMenu.getSlot(skills_select, value), item);
                    }
                }
                player.openInventory(skills_select);
            }
        }
        if (event.getView().getTitle().contains("Skill Select #")) {
            if (event.getCurrentItem() == null) return;

            if(Objects.requireNonNull(clickedItem.getItemMeta()).getDisplayName().contains("Already in Slot #")) {
                event.setCancelled(true);
            } else {

                String[] args = org.bukkit.ChatColor.stripColor(event.getView().getTitle()).split(" ");
                List<String> list = Arrays.stream(args).filter(ele -> ele.length() != 0).toList();
                int number = Integer.parseInt(list.get(list.size() - 1).replace("#", ""));

                if (CustomMenu.skills.stream()
                        .anyMatch(skill -> org.bukkit.ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).contains(skill.getSkillName()))) {
                    player.closeInventory();
                    for (var i = 0; i < skills_selector.getContents().length; i++) {
                        if (Objects.requireNonNull(skills_selector.getContents()[i].getItemMeta()).getDisplayName().contains("Skill Slot #" + number)) {
                            ItemStack item2 = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
                            ItemMeta meta = item2.getItemMeta();
                            assert meta != null;
                            meta.setDisplayName(org.bukkit.ChatColor.GREEN + "[" + org.bukkit.ChatColor.GREEN + clickedItem.getItemMeta().getDisplayName() + org.bukkit.ChatColor.GREEN + "] Skill Slot #" + number);
                            item2.setItemMeta(meta);
                            skills_selector.setItem(i, item2);
                            TP.getSkills()[number-3] = clickedItem.getItemMeta().getDisplayName();
                        }
                    }
                    CustomMenu.openInventory(player, CustomMenu.getInventory(player, "skills_selector", 0));
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        if (event.getView().getTitle().contains("Skill Select #")) {
            // Perform some actions when the player closes the custom menu
            CustomMenu.openInventory(player, CustomMenu.getInventory(player, "skills_selector", 0));
        }
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();

        SpigotData.getInstance().enterEntity(player.getUniqueId());
        TPlayer TP = ((TPlayer) (SpigotData.getInstance().getEntity(player.getUniqueId())));

        int previousSlot = event.getPreviousSlot();
        int newSlot = event.getNewSlot();
        if(player.isSneaking() && Utils.list("items/weapon", player.getInventory().getItem(previousSlot)).contains(player.getInventory().getItem(previousSlot))) {
            event.setCancelled(true);

            String skill = null;

            switch (newSlot) {
                case 2 -> skill = TP.getSkills()[0];
                case 3 -> skill = TP.getSkills()[1];
                case 4 -> skill = TP.getSkills()[2];
                case 5 -> skill = TP.getSkills()[3];
                case 6 -> skill = TP.getSkills()[4];
                default -> {
                }
            }

            if (previousSlot != newSlot && skill != null) {
                player.sendMessage("You switched to skill: " + ChatColor.BOLD + skill);
            }
        }
    }

}
