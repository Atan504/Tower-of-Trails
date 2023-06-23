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
import ToT.Objects.TPlayer;
import ToT.PartyManagment.Commands.PartyChatCommand;
import ToT.PartyManagment.Commands.PartyCommand;
import ToT.PartyManagment.Listener.PlayerHit;
import ToT.PartyManagment.Listener.PlayerJoin;
import ToT.PartyManagment.Listener.PlayerLeft;
import ToT.Utils.Config;
import ToT.Utils.Data;
import ToT.Utils.PartyManagment;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;
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
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerDamage(), this);
        getServer().getPluginManager().registerEvents(new PlayerRespawn(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeath(), this);

        Objects.requireNonNull(this.getCommand("p")).setExecutor(new PartyChatCommand());
        Objects.requireNonNull(this.getCommand("party")).setExecutor(new PartyCommand());
        Objects.requireNonNull(this.getCommand("party")).setTabCompleter((commandSender, command, s, args) -> {

            if (args.length == 1) return new ArrayList<>(Arrays.asList("create", "chat", "invite", "kick", "leave", "disband", "promote", "join", "gui", "info"));

            if (args.length == 2) {
                if(args[0].equals("invite") || args[0].equals("join")) {
                    return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
                }
                if (args[0].equals("promote") || args[0].equals("kick")) {
                    String name = commandSender.getName();
                    Player player = Bukkit.getServer().getPlayer(name);

                    assert player != null;
                    ArrayList<UUID> party = PartyManagment.getParty(player.getUniqueId());
                    ArrayList<UUID> members = PartyManagment.getMembers(party);

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

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (Player p : getServer().getOnlinePlayers()) {

                TPlayer data = PartyManagment.getData(p.getUniqueId());
                int[] stats = data.getStats();
                int[] statPoints = data.getStatPoints();

                if(stats[1] <= 0) {
                    statPoints[1] = 20;
                }

                if(stats[4] <= 0) {
                    statPoints[4] = 10;
                }

                if (stats[0] < 0) {
                    statPoints[0] = 0;
                }
                if (stats[0] < stats[1]) {
                    statPoints[0] += 1;
                } else if (stats[0] > stats[1]) {
                    statPoints[0] = stats[1];
                }

                if (p.getWalkSpeed() != ((float)(0.2 + (0.01 * (stats[6]))))){
                    p.setWalkSpeed((float) (0.2 + (0.01 * (stats[6]))));
                }

                if(stats[3] > stats[4]) {
                    statPoints[3] = stats[4];
                }

                double percentage = (double) stats[3] / stats[4];
                double percentage2 = (double) stats[0] / stats[1];
                int hp = (int) (percentage * 20);
                int mana = (int) (percentage2 * 20);

                if(hp < 0) hp = 0;
                if(mana < 0) mana = 0;

                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + "❤ "+ stats[3] + "/" + stats[4] + " ❤" + ChatColor.WHITE + "                  " + ChatColor.AQUA + "Mana: " + stats[0] + "/" + stats[1]));
                p.setHealth(hp);
                p.setFoodLevel(mana);

                PartyManagment.updatePartyScoreboard(p);

            }
        }, 0L, 20L);

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                    for (Player p : getServer().getOnlinePlayers()) {
                        if(!p.isDead()) {
                            TPlayer data = PartyManagment.getData(p.getUniqueId());
                            int[] stats = data.getStats();
                            int[] statPoints = data.getStatPoints();

                            if (stats[3] < stats[4]) {
                                statPoints[3] += 1;
                            }
                        }
                    }
                }, 0L, 80L);

        /*
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
        */
    }

    @Override
    public void onDisable() {
        SpigotData.getInstance().save();
    }
}
