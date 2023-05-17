package atan.Commands;

import atan.Quests.Quest;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

public class StartQuest implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {

        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            Quest q = new Quest();
            q.append("phhhhhh");
            q.append("pee pee poo poo");
            p.setMetadata("Quest", (MetadataValue) q);
        }
        return false;
    }
}