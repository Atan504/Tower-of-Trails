package Atan.Commands;

import Atan.GUI.QuestsGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Quests implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player))return false;
        Player p = (Player) commandSender;
        QuestsGUI gui = new QuestsGUI();
        gui.openInventory(p);


        return false;
    }
}
