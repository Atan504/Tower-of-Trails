package ToT.System.BankManagment;

import ToT.Objects.TPlayer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

import static ToT.Utils.Utils.getPlayerData;

public class Utils {

    public static Inventory openInventory(Player player, int page) {

        if(page == 0) {
            Inventory gui = Bukkit.createInventory(null, 27, player.getName() + "'s Bank");

            for(int i = 0; i < gui.getSize(); i++) {
                ItemStack none = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
                ItemStack locked = new ItemStack(Material.RED_STAINED_GLASS_PANE);

                ItemMeta none_meta = none.getItemMeta();
                ItemMeta locked_meta = locked.getItemMeta();

                if(none_meta != null) none_meta.setDisplayName(ChatColor.RESET + "");
                if(locked_meta != null) {
                    locked_meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "LOCKED");
                    locked_meta.setLore(Arrays.asList("", ChatColor.YELLOW + "Page " + (i - 10), "", ChatColor.GREEN + "" + ChatColor.BOLD + "COST 0.00$", ""));
                }

                none.setItemMeta(none_meta);
                locked.setItemMeta(locked_meta);

                gui.setItem(i, none);
                if(i > 10 && i < 16) gui.setItem(i, locked);
            }

            return gui;
        }

        TPlayer data = getPlayerData(player.getUniqueId());

        Inventory gui = Bukkit.createInventory(null, 27, "Bank Page " + page);
        for (int i = 0; i < gui.getSize(); i++) {
            if (i < 7 || (i > 8 && i < 16) || (i > 17 && i < 25) || (i > 26 && i < 34) || (i > 35 && i < 43) || (i > 44 && i < 52))
                gui.setItem(i, new ItemStack(Material.AIR));
        }

        if (Arrays.stream(data.getBank().toArray()).toArray().length != 0) {
            ArrayList<ItemStack[]> bank = data.getBank();
            if(bank.size() < page) bank.add(new ItemStack[gui.getSize()]);
            ItemStack[] list = bank.get(page - 1);
            for (int i = 0; i < list.length; i++) {
                if (i < 7 || (i > 8 && i < 16) || (i > 17 && i < 25) || (i > 26 && i < 34) || (i > 35 && i < 43) || (i > 44 && i < 52))
                    gui.setItem(i, list[i]);
            }
        }

        return gui;
    }

}
