package ToT.Commands;

import ToT.Data.SpigotData;
import ToT.Objects.TPlayer;
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
            p.sendMessage(((TPlayer) SpigotData.getInstance().getEntity(p.getUniqueId())).getQm().getByName("habib").getCurrentState().getInfo());
        }
        return true;
    }
}
