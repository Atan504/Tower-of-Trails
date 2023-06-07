package ToT.PartyManagment.Commands;

import ToT.Data.SpigotData;
import ToT.Objects.TPlayer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

import static ToT.Main.plugin;

public class PartyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {

        if (!(sender instanceof Player player)) return true;

        TPlayer data = getData(player);

        if (args.length < 1) {
            player.sendMessage("Incorrect Syntax!");
            return true;
        }

        String tab = args[0];

        if (tab.equalsIgnoreCase("create")) {

            if(inParty(getParty(player), player)) {
                player.sendMessage(ChatColor.RED + "You already inside a Party.");
                return true;
            }

            player.sendMessage(ChatColor.GREEN + "You Created new Party!");
            data.getParty()[0] = player;
            return true;
        }

        if(tab.equalsIgnoreCase("invite")) {

            if(args.length == 1) {
                player.sendMessage(ChatColor.RED + "You need specific Player to Invite");
                return true;
            }

            Player member = getPlayer(player, args[1]);

            if (!inParty(getParty(player), player)) {
                player.sendMessage(ChatColor.GREEN + "You Created new Party!");
                data.getParty()[0] = player;
            }

            Player[] party = getParty(player);
            Player[] members = getMembers(party);

            if (members.length == 4) {
                player.sendMessage(ChatColor.RED + "Party is Full");
                return true;
            }

            if(!isOwner(party, player)) {
                player.sendMessage(ChatColor.RED + "You are not the Party Owner");
                return true;
            }

            if(inParty(getParty(player), member)) {
                player.sendMessage(ChatColor.RED + "Player " + member.getName() + " already on the Party");
                return true;
            }

            player.sendMessage(ChatColor.YELLOW + "You Sent Party Invitation to " + member.getName());

            member.sendMessage(ChatColor.YELLOW + player.getName() + " Invited you to the Party, Join the party via /party join " + player.getName());

            member.setMetadata("party.invite.timer." + player.getUniqueId(), new FixedMetadataValue(plugin, new Date().getTime()));
        }

        if(tab.equalsIgnoreCase("kick")) {

            if(args.length == 1) {
                player.sendMessage(ChatColor.RED + "You need specific Player to Kick");
                return true;
            }

            Player member = Bukkit.getServer().getPlayer(args[1]);

            Player[] party = getParty(player);
            Player[] members = getMembers(party);

            if (!inParty(party, player)) {
                player.sendMessage(ChatColor.RED + "You are not in Party");
                return true;
            }

            if(!isOwner(party, player)) {
                player.sendMessage(ChatColor.RED + "You are not the Party Owner");
                return true;
            }

            if(member == null) {

                OfflinePlayer member2 = Bukkit.getServer().getOfflinePlayer(args[1]);

                if(member2 == null) {
                    player.sendMessage(ChatColor.RED + args[1] + " Player not found");
                    return true;
                }

                boolean inParty2 = false;

                for(Player p : party) {
                    if (p != null && p.getUniqueId() == member2.getUniqueId()) inParty2 = true;
                }

                if (!inParty2) {
                    player.sendMessage(ChatColor.RED + "Player " + member2.getName() + " not in the Party");
                    return true;
                }

                for (int i = 0; i < members.length; i++) {
                    System.out.println(members[i]);
                    if (members[i].getUniqueId() == member2.getUniqueId()) {
                        data.getParty()[i] = null;
                        player.sendMessage(ChatColor.RED + "You Kicked the Player " + member2.getName() + " from the Party");
                    } else {
                        members[i].sendMessage(ChatColor.RED + member2.getName() + " Left the party!");
                    }
                }
            } else {

                if (!inParty(getParty(player), member)) {
                    player.sendMessage(ChatColor.RED + "Player " + member.getName() + " not in the Party");
                    return true;
                }

                for (int i = 0; i < members.length; i++) {
                    if (members[i] == member) {
                        data.getParty()[i] = null;
                        player.sendMessage(ChatColor.RED + "You Kicked the Player " + member.getName() + " from the Party");
                        if (member.isOnline()) {
                            Objects.requireNonNull(Bukkit.getServer().getPlayer(member.getName())).sendMessage(ChatColor.RED + "You got Kicked from the Party");
                        }
                    } else {
                        members[i].sendMessage(ChatColor.RED + member.getName() + " Left the party!");
                    }
                }
            }
        }

        if(tab.equalsIgnoreCase("leave")) {

            if(!inParty(getParty(player), player)) {
                player.sendMessage(ChatColor.RED + "You are not in Party");
                return true;
            }

            Player[] party = getParty(player);
            Player[] members = getMembers(party);

            if(members.length > 1) {
                for (int i = 0; i < party.length; i++) {
                    if (party[i] == player) {
                        if(isOwner(party, player)) {
                            if(members.length == 4) {
                                if (party[1] != null) party[0] = party[1];
                                if (party[2] != null) party[1] = party[2];
                                if (party[3] != null) party[2] = party[3];
                                party[3] = null;
                            } else if(members.length == 3) {
                                if (party[1] != null) party[0] = party[1];
                                if (party[2] != null) party[1] = party[2];
                                party[2] = null;
                            } else if(members.length == 2) {
                                if (party[1] != null) party[0] = party[1];
                                party[1] = null;
                            }

                            party[0].sendMessage(ChatColor.YELLOW + "Previous Party Owner left the Party you become the new Party Owner");
                            player.sendMessage(ChatColor.RED + "You Left the party!");
                        } else {
                            TPlayer data2 = getData(getOwner(party));
                            data2.getParty()[i] = null;
                            player.sendMessage(ChatColor.RED + "You Left the party!");
                        }
                    } else {
                        if(party[i] != null) party[i].sendMessage(ChatColor.RED + player.getName() + " Left the party!");
                    }
                }
            } else {
                player.sendMessage(ChatColor.RED + "You are the only one who left in the party");
                player.sendMessage(ChatColor.RED + "Your Previous Party has been Disbanded");
                data.getParty()[0] = null;
            }
            return true;
        }

        if(tab.equalsIgnoreCase("disband")) {

            if(!inParty(getParty(player), player)) {
                player.sendMessage(ChatColor.RED + "You are not in Party");
                return true;
            }

            Player[] party = getParty(player);
            Player[] members = getMembers(party);

            if(!isOwner(party, player)) {
                player.sendMessage(ChatColor.RED + "You are not the Party Owner");
                return true;
            }

            for(int i = 0; i < members.length; i++) {
                members[i].sendMessage(ChatColor.RED + "Your Previous Party has been Disbanded");
                data.getParty()[i] = null;
            }

            return true;
        }

        if(tab.equalsIgnoreCase("promote")) {

            if(args.length == 1) {
                player.sendMessage(ChatColor.RED + "You need specific Player to Promote");
                return true;
            }

            if(!inParty(getParty(player), player)) {
                player.sendMessage(ChatColor.RED + "You are not inside a Party");
                return true;
            }

            Player[] party = getParty(player);

            if(!isOwner(party, player)) {
                player.sendMessage(ChatColor.RED + "You are not the Party Owner");
                return true;
            }

            Player member = Bukkit.getServer().getPlayer(args[1]);

            if(isOwner(party, member)) {
                assert member != null;
                player.sendMessage(ChatColor.RED + member.getName() + " is already the Party Owner");
                return true;
            }

            for(var i = 0; i < party.length; i++) {
                if(party[i] == member) {
                    party[i] = party[0];
                    party[0] = member;

                    assert member != null;
                    member.sendMessage(ChatColor.YELLOW + "You got Promoted to Party Owner");
                    player.sendMessage(ChatColor.YELLOW + "You transferred the Party Owner to " + member.getName());
                }

            }

        }

        if(tab.equalsIgnoreCase("join")) {

            if(args.length == 1) {
                player.sendMessage(ChatColor.RED + "You need specific Party Owner Player to Join to");
                return true;
            }

            if(inParty(getParty(player), player)) {
                player.sendMessage(ChatColor.RED + "You are already in a Party, leave this one first");
                return true;
            }

            Player owner = Bukkit.getServer().getPlayer(args[1]);

            if(owner == null) {
                player.sendMessage(ChatColor.RED + "Player not found");
                return true;
            }

            System.out.println(owner);

            if(!inParty(getParty(owner), owner)) {
                player.sendMessage(ChatColor.RED + owner.getName() + " is not inside a Party");
                return true;
            }

            Player[] party = getParty(owner);

            List<MetadataValue> timerData = player.getMetadata("party.invite.timer." + owner.getUniqueId());

            if(timerData.isEmpty()) {
                player.sendMessage(ChatColor.RED + "there is no Party Invite Request");
                return true;
            }

            Instant currentTime = Instant.now();

            Instant savedTime = Instant.ofEpochMilli(timerData.get(0).asLong());

            Duration durationToCheck = Duration.ofMinutes(2);

            Duration elapsedTime = Duration.between(savedTime, currentTime);

            if (elapsedTime.compareTo(durationToCheck) >= 0) {
                player.sendMessage(ChatColor.RED + "there is no Party Invite Request");
            } else {

                boolean playerJoined = false;

                for (int i = 0; i < party.length; i++) {
                    if (party[i] == null) {
                        if (!playerJoined) {
                            party[i] = player;
                            player.sendMessage(ChatColor.YELLOW + "You Joined the Party of " + owner.getName());
                            playerJoined = true;
                        }
                    } else {
                        party[i].sendMessage(ChatColor.YELLOW + player.getName() + " Joined the Party");
                    }
                }

            }
            return true;
        }

        if(tab.equalsIgnoreCase("gui")) {}

        if (tab.equalsIgnoreCase("info")) {

            if(!inParty(getParty(player), player)) {
                player.sendMessage(ChatColor.RED + "You are not inside a Party");
                return true;
            }

            Player[] party = getParty(player);
            Player[] members = getMembers(party);

            if(members.length != 0) {
                player.sendMessage(ChatColor.AQUA + "Here your Party Information:");

                for (var i = 0; i < party.length; i++) {
                    if (isOwner(party, party[i])) {
                        TPlayer data2 = getData(members[i]);

                        player.sendMessage(ChatColor.GOLD + members[i].getName() + ChatColor.GRAY + " - " + ChatColor.RED + data2.getStats()[3] + "/" + data2.getStats()[4]);
                    } else {
                        if(party[i] == null) {
                            player.sendMessage(ChatColor.GRAY + "- Empty");
                        } else {
                            if(members[i].isOnline()) {
                                TPlayer data2 = getData(members[i]);

                                player.sendMessage(ChatColor.YELLOW + members[i].getName() + ChatColor.GRAY + " - " + ChatColor.RED + data2.getStats()[3] + "/" + data2.getStats()[4]);
                            } else {
                                player.sendMessage(ChatColor.GRAY + members[i].getName());
                            }
                        }
                    }
                }
            }
            return true;
        }

        return true;
    }

    public TPlayer getData(Player player) {
        SpigotData.getInstance().enterEntity(player.getUniqueId());

        return ((TPlayer) SpigotData.getInstance().getEntity(player.getUniqueId()));
    }

    public Player[] getParty(Player player) {
        Player[] party = new Player[4];

        for(Player p : Bukkit.getServer().getOnlinePlayers()) {
            TPlayer data = getData(p);

            if(Arrays.stream(data.getParty()).toList().contains(player)) {
                party = data.getParty();
                break;
            }
        }

        return party;
    }

    public Boolean inParty(Player[] party, Player member) {

        for(Player p : party) {
            if (p != null && p.equals(member)) {
                return true;
            }
        }
        return false;
    }

    public Boolean isOwner(Player[] party, Player member) {
        return getOwner(party) == member;
    }

    public Player getOwner(Player[] party) {
        return party[0];
    }

    public Player[] getMembers(Player[] party) {

        int count = 0;
        for (Player player : party) {
            if (player != null) {
                count++;
            }
        }

        Player[] members = new Player[count];

        int i = 0;
        for (Player player : party) {
            if (player != null) {
                members[i] = player;
                i++;
            }
        }

        return members;
    }

    public Player getPlayer(Player player, String username) {

        Player member = Bukkit.getServer().getPlayer(username);

        if(member == null) {
            player.sendMessage(ChatColor.RED + username + " Player not found");
            return null;
        }

        return member;

    }

}
