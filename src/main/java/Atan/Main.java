package Atan;

import Atan.Commands.Quests;
import Atan.Data.SpigotData;
import Atan.Commands.Progress;
import Atan.Commands.QuestStatus;
import Atan.Commands.StartQuest;
import Atan.GUI.QuestsGUI;
import Atan.Listener.*;
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
