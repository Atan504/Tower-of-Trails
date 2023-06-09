package ToT;

import ToT.ClassManagement.ClassHandler;
import ToT.ClassManagement.ClassList;
import ToT.ClassManagement.Classes.UniqeSkills.Archer.ArrowHandler;
import ToT.ClassManagement.Classes.UniqeSkills.Archer.GuaranteedHit;
import ToT.ClassManagement.Classes.UniqeSkills.Archer.SwordHandler;
import ToT.Commands.*;
import ToT.Config.SetupConfig;
import ToT.Data.SpigotData;
import ToT.Events.ChatEvent;
import ToT.GUI.QuestsGUI;
import ToT.Listener.*;
import ToT.PartyManagment.Commands.PartyChatCommand;
import ToT.PartyManagment.Commands.PartyCommand;
import ToT.PartyManagment.Listener.PlayerHit;
import ToT.PartyManagment.Listener.PlayerLeft;
import ToT.Utils.Config;
import ToT.Utils.PartyManagment;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.stream.Stream;

public final class Main extends JavaPlugin {

    public static Main plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic

        plugin = this;

        // Atan
        Objects.requireNonNull(this.getCommand("progress")).setExecutor(new Progress());
        Objects.requireNonNull(this.getCommand("queststatus")).setExecutor(new QuestStatus());
        Objects.requireNonNull(this.getCommand("startquest")).setExecutor(new StartQuest());
        Objects.requireNonNull(this.getCommand("quests")).setExecutor(new Quests());

        getServer().getPluginManager().registerEvents(new QuestsGUI(), this);




        // Derpy
        getServer().getPluginManager().registerEvents(new PlayerChat(), this);
        getServer().getPluginManager().registerEvents(new SkillSelector(), this);
        getServer().getPluginManager().registerEvents(new ProfileMenu(), this);
        getServer().getPluginManager().registerEvents(new DisplayItemChat(), this);
        getServer().getPluginManager().registerEvents(new PlayerLeft(), this);
        getServer().getPluginManager().registerEvents(new PlayerHit(), this);

        Objects.requireNonNull(this.getCommand("p")).setExecutor(new PartyChatCommand());
        Objects.requireNonNull(this.getCommand("party")).setExecutor(new PartyCommand());
        Objects.requireNonNull(this.getCommand("party")).setTabCompleter((commandSender, command, s, args) -> {
            List<String> l = new ArrayList<>();

            if (args.length == 1) {
                l.add("create");
                l.add("chat");
                l.add("invite");
                l.add("kick");
                l.add("leave");
                l.add("disband");
                l.add("promote");
                l.add("join");
                l.add("gui");
                l.add("info");

                return l;
            }

            if (args.length == 2) {
                if(args[0].equals("invite") || args[0].equals("join")) {
                    return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
                }
                if (args[0].equals("promote") || args[0].equals("kick")) {
                    String name = commandSender.getName();
                    Player player = Bukkit.getServer().getPlayer(name);

                    assert player != null;
                    List<UUID> party = PartyManagment.getParty(player.getUniqueId());
                    List<UUID> members = PartyManagment.getMembers(party);

                    Stream<String> stream = members.stream().map(p -> {
                        Player p2 = Bukkit.getServer().getPlayer(p);
                        OfflinePlayer p3 = Bukkit.getServer().getOfflinePlayer(p);

                        if(p2 != null) return p2.getName();
                        else return p3.getName();
                    });

                    return stream.toList();
                }
            }


            return null;
        });

        Tasks.updateStats();
        Config.createAllConfig();




        // Matan
        SetupConfig.Setup();
        getServer().getPluginManager().registerEvents(new SetupConfig(), this);
        Objects.requireNonNull(getCommand("reset")).setExecutor(new ResetCommand());
        Objects.requireNonNull(getCommand("jreload")).setExecutor(new jreload());
        ClassList.register(this);
        getServer().getPluginManager().registerEvents(new ClassHandler(), this);
        getServer().getPluginManager().registerEvents(new ChatEvent(), this);
        getServer().getPluginManager().registerEvents(new FightingSystem(), this);
        getServer().getPluginManager().registerEvents(new GuaranteedHit(), this);
        getServer().getPluginManager().registerEvents(new ArrowHandler(), this);
        getServer().getPluginManager().registerEvents(new SwordHandler(), this);

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (Player p : getServer().getOnlinePlayers()) {
                PlayerData pd = new PlayerData(p.getUniqueId());
                if (pd.mana < 0) {
                    pd.set("mana", 0);
                }
                if (pd.mana < pd.maxmana) {
                    int j = 1;
                    pd.set("mana", pd.mana + j);
                } else if (pd.mana > pd.maxmana) {
                    pd.set("mana", pd.maxmana);
                }

                if (p.getWalkSpeed() != ((float)(0.2 + (0.01 * (pd.spe))))){
                    p.setWalkSpeed((float) (0.2 + (0.01 * (pd.spe))));
                }

                if (pd.hp <= 0){
                    p.setHealth(0);
                    pd.set("hp", pd.maxhp);
                }

                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + "HP: "+ pd.hp + "/" + pd.maxhp + ChatColor.WHITE + " _-_ " + ChatColor.BLUE + "Mana: " + pd.mana + "/" + pd.maxmana));


            }
        }, 0L, 20L);
    }

    @Override
    public void onDisable() {
        SpigotData.getInstance().save();
    }
}
