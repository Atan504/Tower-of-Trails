package atan.Commands;

import atan.Quests.Quest;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Progress implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {

        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            Quest q = (Quest) p.getMetadata("Quest");
            q.getCurrentState().finish();
        }
        return false;
    }
}