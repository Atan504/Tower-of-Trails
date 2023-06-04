package ToT.Commands;

import ToT.Data.SpigotData;
import ToT.Objects.TPlayer;
import ToT.Quests.Quest;
import ToT.Quests.QuestState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartQuest implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            Quest q = new Quest("habib");
            QuestState s1 = new QuestState("phhhhhh", q);
            q.append(s1);
            QuestState s2 = new QuestState("pee pee poo poo", q);
            q.append(s2);
            SpigotData.getInstance().enterEntity(p.getUniqueId());
            ((TPlayer)SpigotData.getInstance().getEntity(p.getUniqueId())).getQm().add(q);
            p.sendMessage("quest "+q.getName() + " started!");
            p.sendMessage(SpigotData.getInstance().toString());

        }
        return false;
    }
}