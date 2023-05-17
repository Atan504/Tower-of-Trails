package atan.Commands;

import atan.Quests.Quest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class QuestStatus implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            Quest q = (Quest) p.getMetadata("Quest");
            p.sendMessage(q.getCurrentState().getInfo());
        }
        return true;
    }
}
