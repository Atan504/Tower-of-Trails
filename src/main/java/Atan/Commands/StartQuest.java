package Atan.Commands;

import Atan.Data.SpigotData;
import Atan.Quests.Quest;
import Atan.Quests.QuestState;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartQuest implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {

        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            Quest q = new Quest("habib");
            QuestState s1 = new QuestState("phhhhhh", q);
            q.append(s1);
            QuestState s2 = new QuestState("pee pee poo poo", q);
            q.append(s2);
            SpigotData.getInstance().enterEntity(p.getUniqueId());
            SpigotData.getInstance().getEntity(p.getUniqueId()).add(0,q);
            p.sendMessage("quest "+q.getName() + " started!");
            p.sendMessage(SpigotData.getInstance().toString());

        }
        return false;
    }
}