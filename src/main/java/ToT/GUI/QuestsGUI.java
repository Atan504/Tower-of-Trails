package ToT.GUI;


import ToT.Quests.QuestManger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class QuestsGUI implements Listener {


    private final Inventory inv;
    private QuestManger qm;
    private int page;

    public QuestsGUI() {
        // Create a new inventory, with no owner (as this isn't a real inventory), a size of nine, called example
        inv = Bukkit.createInventory(null, 54, "Quests");

        //qm= (QuestManger) SpigotData.getInstance().getEntity(pUUID).get(0);

        // Put the items into the inventory
        initializeItems();
    }

    // You can call this whenever you want to put the items in
    public void initializeItems() {
        inv.setItem(45, createGuiItem(Material.ARROW, ChatColor.WHITE + "Left"));
        inv.setItem(46, createGuiItem(Material.YELLOW_STAINED_GLASS_PANE, ChatColor.DARK_GRAY + ""));
        inv.setItem(47, createGuiItem(Material.YELLOW_STAINED_GLASS_PANE, ChatColor.DARK_GRAY + ""));
        inv.setItem(48, createGuiItem(Material.YELLOW_STAINED_GLASS_PANE, ChatColor.DARK_GRAY + ""));
        inv.setItem(49, createGuiItem(Material.BARRIER, ChatColor.RED + "Left"));
        inv.setItem(50, createGuiItem(Material.YELLOW_STAINED_GLASS_PANE, ChatColor.DARK_GRAY + ""));
        inv.setItem(51, createGuiItem(Material.YELLOW_STAINED_GLASS_PANE, ChatColor.DARK_GRAY + ""));
        inv.setItem(52, createGuiItem(Material.YELLOW_STAINED_GLASS_PANE, ChatColor.DARK_GRAY + ""));
        inv.setItem(53, createGuiItem(Material.ARROW, ChatColor.WHITE + "Left"));

    }

    // Nice little method to create a gui item with a custom name, and description
    protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    // You can open the inventory with this
    public void openInventory(final HumanEntity ent) {
        ent.openInventory(inv);
    }

    // Check for clicks on items
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        System.out.println(e.getInventory());
        System.out.println(inv);
        if (!e.getInventory().equals(inv)) {
            e.getWhoClicked().sendMessage("bilbil");
            return;
        }

        System.out.println("sdsdsdsd");
        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType().isAir()) return;

        final Player p = (Player) e.getWhoClicked();

        // Using slots click is a best option for your inventory click's
        p.sendMessage("You clicked at slot " + e.getRawSlot());
    }

    // Cancel dragging in our inventory
    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (e.getInventory().equals(inv)) {
            e.setCancelled(true);
        }
    }

}
