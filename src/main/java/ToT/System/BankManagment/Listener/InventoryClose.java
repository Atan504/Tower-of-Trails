package ToT.System.BankManagment.Listener;

import ToT.Objects.TPlayer;
import ToT.System.PartyManagment.Utils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

import static ToT.System.BankManagment.Utils.openInventory;
import static ToT.Utils.Utils.getPlayerData;

public class InventoryClose implements Listener {

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        String title = event.getView().getTitle();

        if(title.contains("Bank")) {
            player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1.0f, 1.0f);
        }
        if (title.contains("Bank Page")) {
            Inventory inv = event.getInventory();

            int page = Integer.parseInt(title.split(" ")[2]);

            TPlayer data = getPlayerData(player.getUniqueId());

            ItemStack[] items = new ItemStack[inv.getSize()];

            for(int i = 0; i < inv.getSize(); i++) {
                items[i] = inv.getItem(i);
            }

            ArrayList<ItemStack[]> bank = data.getBank();

            if(bank.size() == 0) bank = new ArrayList<>(Arrays.asList( new ItemStack[]{}, new ItemStack[]{}, new ItemStack[]{}, new ItemStack[]{}, new ItemStack[]{} ));

            bank.set(page - 1, items);

            data.setBank(bank);
        }
    }

}
