package atan;

import atan.Commands.Progress;
import atan.Commands.QuestStatus;
import atan.Commands.StartQuest;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getConsoleSender().sendMessage("sup fuckers");
        this.getCommand("progress").setExecutor(new Progress());
        this.getCommand("queststatus").setExecutor(new QuestStatus());
        this.getCommand("startquest").setExecutor(new StartQuest());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
