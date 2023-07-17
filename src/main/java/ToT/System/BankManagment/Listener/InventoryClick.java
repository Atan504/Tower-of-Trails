package ToT.System.BankManagment.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ToT.System.BankManagment.Utils;

import java.util.List;

public class InventoryClick implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        if(event.getView().getTitle().equals(player.getName() + "'s Bank")) {
            event.setCancelled(true);

            ItemStack item = event.getCurrentItem();
            if(item == null) return;
            ItemMeta meta = item.getItemMeta();
            if(meta == null) return;

            List<String> lore = meta.getLore();
            if(lore == null) return;
            if(lore.size() == 0) return;

            String line = lore.get(1);
            int page = Integer.parseInt(line.split(" ")[1]);

            Inventory gui = Utils.openInventory(player, page);

            player.openInventory(gui);
        }
    }

}
