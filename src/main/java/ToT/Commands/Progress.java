package ToT.Commands;

import ToT.Data.SpigotData;
import ToT.Quests.Quest;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Progress implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] strings) {

        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            ((Quest) SpigotData.getInstance().getEntity(p.getUniqueId()).get(0)).getCurrentState().finish();
        }
        return false;
    }
}