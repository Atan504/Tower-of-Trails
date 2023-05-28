package ToT.Commands;

import ToT.Data.SpigotData;
import ToT.Quests.Quest;
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