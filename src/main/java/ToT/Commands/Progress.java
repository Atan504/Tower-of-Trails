package ToT.Commands;

import ToT.Data.SpigotData;
import ToT.Objects.TPlayer;
import ToT.Quests.Quest;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class Progress implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            ((TPlayer) SpigotData.getInstance().getEntity(p.getUniqueId())).getQm().getByName("habib").getCurrentState().finish();
        }
        return false;
    }
}