package Atan;

import Atan.Commands.Quests;
import Atan.Data.SpigotData;
import Atan.Commands.Progress;
import Atan.Commands.QuestStatus;
import Atan.Commands.StartQuest;
import Atan.GUI.QuestsGUI;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        this.getCommand("progress").setExecutor(new Progress());
        this.getCommand("queststatus").setExecutor(new QuestStatus());
        this.getCommand("startquest").setExecutor(new StartQuest());
        this.getCommand("quests").setExecutor(new Quests());

        getServer().getPluginManager().registerEvents(new QuestsGUI(), this);
    }

    @Override
    public void onDisable() {
        SpigotData.getInstance().save();
    }
}
