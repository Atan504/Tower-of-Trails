package ToT.Commands;

import ToT.GUI.QuestsGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Quests implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] strings) {
        if (!(commandSender instanceof Player))return false;
        Player p = (Player) commandSender;
        QuestsGUI gui = new QuestsGUI();
        gui.openInventory(p);


        return false;
    }
}
