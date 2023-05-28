package ToT;

import ToT.Commands.Quests;
import ToT.Data.SpigotData;
import ToT.Commands.Progress;
import ToT.Commands.QuestStatus;
import ToT.Commands.StartQuest;
import ToT.GUI.QuestsGUI;
import ToT.Listener.*;
import fabulus.fabulus.ClassManagement.ClasssList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Main extends JavaPlugin {

    public static Main plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic

        plugin = this;

        Objects.requireNonNull(this.getCommand("progress")).setExecutor(new Progress());
        Objects.requireNonNull(this.getCommand("queststatus")).setExecutor(new QuestStatus());
        Objects.requireNonNull(this.getCommand("startquest")).setExecutor(new StartQuest());
        Objects.requireNonNull(this.getCommand("quests")).setExecutor(new Quests());

        getServer().getPluginManager().registerEvents(new QuestsGUI(), this);




        getServer().getPluginManager().registerEvents(new PlayerChat(), this);
        getServer().getPluginManager().registerEvents(new SkillSelector(), this);
        getServer().getPluginManager().registerEvents(new ProfileMenu(), this);

        Tasks.updateStats();
        YamlConfigLoader.createAllConfig();
    }

    @Override
    public void onDisable() {
        SpigotData.getInstance().save();
    }
}
