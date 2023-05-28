package Atan.Commands;

import Atan.Data.SpigotData;
import Atan.Quests.Quest;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Progress implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {

        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            ((Quest) SpigotData.getInstance().getEntity(p.getUniqueId()).get(0)).getCurrentState().finish();
        }
        return false;
    }
}