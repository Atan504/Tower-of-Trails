package ToT.Commands;

import ToT.Data.SpigotData;
import ToT.Quests.Quest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class QuestStatus implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            p.sendMessage(SpigotData.getInstance().toString());
            p.sendMessage(((Quest) SpigotData.getInstance().getEntity(p.getUniqueId()).get(0)).getCurrentState().getInfo());
        }
        return true;
    }
}
