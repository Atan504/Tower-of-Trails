package ToT.PartyManagment.Commands;

import ToT.Objects.TPlayer;
import ToT.Utils.PartyManagment;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
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

        if (args.length < 1) {
            player.sendMessage("Incorrect Syntax!");
            return true;
        }

        String tab = args[0];

        if (tab.equalsIgnoreCase("create")) {

            if(PartyManagment.inParty(PartyManagment.getParty(player), player)) {
                player.sendMessage(ChatColor.RED + "You already inside a Party.");
                return true;
            }

            TPlayer data = PartyManagment.getData(player);

            player.sendMessage(ChatColor.GREEN + "You Created new Party!");
            List<Player> party = data.getParty();
            party.add(player);
            data.setParty(party);
            return true;
        }

        if (tab.equalsIgnoreCase("chat")) {

            if(!PartyManagment.inParty(PartyManagment.getParty(player), player)) {
                player.sendMessage(ChatColor.RED + "You are not in Party");
                return true;
            }

            List<MetadataValue> active = player.getMetadata("party.chat.toggle");

            if(active.isEmpty()) {
                player.sendMessage(ChatColor.YELLOW + "You toggled Party Chat " + ChatColor.GREEN + ChatColor.BOLD + "ON");
                player.setMetadata("party.chat.toggle", new FixedMetadataValue(plugin, true));
            } else {
                if(active.get(0).asBoolean()) {
                    player.sendMessage(ChatColor.YELLOW + "You toggled Party Chat " + ChatColor.RED + ChatColor.BOLD + "OFF");
                    player.setMetadata("party.chat.toggle", new FixedMetadataValue(plugin, false));
                } else {
                    player.sendMessage(ChatColor.YELLOW + "You toggled Party Chat " + ChatColor.GREEN + ChatColor.BOLD + "ON");
                    player.setMetadata("party.chat.toggle", new FixedMetadataValue(plugin, true));
                }
            }

            return true;
        }

        if(tab.equalsIgnoreCase("invite")) {

            if(args.length == 1) {
                player.sendMessage(ChatColor.RED + "You need specific Player to Invite");
                return true;
            }

            Player member = Bukkit.getServer().getPlayer(args[1]);

            List<Player> party = PartyManagment.getParty(player);

            if (!PartyManagment.inParty(PartyManagment.getParty(player), player)) {
                TPlayer data = PartyManagment.getData(player);
                player.sendMessage(ChatColor.GREEN + "You Created new Party!");
                party.add(player);
                data.setParty(party);
            }

            TPlayer data = PartyManagment.getData(PartyManagment.getOwner(party));

            if(!PartyManagment.isOwner(party, player)) {
                player.sendMessage(ChatColor.RED + "You are not the Party Owner");
                return true;
            }

            if(member == null) {
                player.sendMessage(ChatColor.RED + args[1] + " Player not found");
                return true;
            }

            if(PartyManagment.inParty(PartyManagment.getParty(player), member)) {
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

            List<Player> party = PartyManagment.getParty(player);

            if (!PartyManagment.inParty(party, player)) {
                player.sendMessage(ChatColor.RED + "You are not in Party");
                return true;
            }

            TPlayer data = PartyManagment.getData(PartyManagment.getOwner(party));

            List<Player> members = PartyManagment.getMembers(party);

            Player member = PartyManagment.getPlayer(members, args[1]);

            if(!PartyManagment.isOwner(party, player)) {
                player.sendMessage(ChatColor.RED + "You are not the Party Owner");
                return true;
            }

            if(member == null) {
                player.sendMessage(ChatColor.RED + args[1] + " Player not found");
                return true;
            }

            if(member == player) {
                player.sendMessage(ChatColor.RED + "You cant kick yourself");
                return true;
            }

            party.remove(member);
            data.setParty(party);
            player.sendMessage(ChatColor.RED + "You Kicked the Player " + member.getName() + " from the Party");

            if(member.isOnline()) member.sendMessage(ChatColor.RED + "You got Kicked from the Party");

            members.stream().filter(p -> p != member).forEach(p -> p.sendMessage(ChatColor.RED + member.getName() + " Left the party!"));
        }

        if(tab.equalsIgnoreCase("leave")) {

            if(!PartyManagment.inParty(PartyManagment.getParty(player), player)) {
                player.sendMessage(ChatColor.RED + "You are not in Party");
                return true;
            }

            List<Player> party = PartyManagment.getParty(player);
            List<Player> members = PartyManagment.getMembers(party);

            if(members.size() == 1) {
                TPlayer data = PartyManagment.getData(PartyManagment.getOwner(party));

                player.sendMessage(ChatColor.RED + "You are the only one who left in the party");
                player.sendMessage(ChatColor.RED + "Your Previous Party has been Disbanded");
                party.remove(player);
                data.setParty(party);
                return true;
            }

            if(PartyManagment.isOwner(party, player)) party.get(1).sendMessage(ChatColor.YELLOW + "Previous Party Owner left the Party you become the new Party Owner");

            party.remove(player);
            party = party.stream().filter(Objects::nonNull).toList();

            TPlayer data = PartyManagment.getData(player);
            TPlayer data2 = PartyManagment.getData(PartyManagment.getOwner(party));

            data.setParty(new ArrayList<>());
            data2.setParty(party);

            player.sendMessage(ChatColor.RED + "You Left the party!");

            members.stream().filter(p -> p != player).forEach(p -> p.sendMessage(ChatColor.RED + player.getName() + " Left the party!"));
            return true;
        }

        if(tab.equalsIgnoreCase("disband")) {

            if(!PartyManagment.inParty(PartyManagment.getParty(player), player)) {
                player.sendMessage(ChatColor.RED + "You are not in Party");
                return true;
            }

            List<Player> party = PartyManagment.getParty(player);
            List<Player> members = PartyManagment.getMembers(party);

            TPlayer data = PartyManagment.getData(PartyManagment.getOwner(party));

            if(!PartyManagment.isOwner(party, player)) {
                player.sendMessage(ChatColor.RED + "You are not the Party Owner");
                return true;
            }

            members.forEach(p -> {
                party.remove(p);
                p.sendMessage(ChatColor.RED + "Your Previous Party has been Disbanded");
            });

            data.setParty(party);

            return true;
        }

        if(tab.equalsIgnoreCase("promote")) {

            if(args.length == 1) {
                player.sendMessage(ChatColor.RED + "You need specific Player to Promote");
                return true;
            }

            if(!PartyManagment.inParty(PartyManagment.getParty(player), player)) {
                player.sendMessage(ChatColor.RED + "You are not inside a Party");
                return true;
            }

            List<Player> party = PartyManagment.getParty(player);

            if(!PartyManagment.isOwner(party, player)) {
                player.sendMessage(ChatColor.RED + "You are not the Party Owner");
                return true;
            }

            Player member = PartyManagment.getPlayer(party, args[1]);

            if(member == null) {
                player.sendMessage(ChatColor.RED + args[1] + " Player not found");
                return true;
            }

            if(PartyManagment.isOwner(party, member)) {
                player.sendMessage(ChatColor.RED + member.getName() + " is already the Party Owner");
                return true;
            }

            party.set(party.indexOf(member), player);
            party.set(0, member);

            TPlayer data = PartyManagment.getData(player);
            TPlayer data2 = PartyManagment.getData(PartyManagment.getOwner(party));

            data.setParty(new ArrayList<>());
            data2.setParty(party);

            member.sendMessage(ChatColor.YELLOW + "You got Promoted to Party Owner");
            player.sendMessage(ChatColor.YELLOW + "You transferred the Party Owner to " + member.getName());
        }

        if(tab.equalsIgnoreCase("join")) {

            if(args.length == 1) {
                player.sendMessage(ChatColor.RED + "You need specific Party Owner Player to Join to");
                return true;
            }

            if(PartyManagment.inParty(PartyManagment.getParty(player), player)) {
                player.sendMessage(ChatColor.RED + "You are already in a Party, leave this one first");
                return true;
            }

            Player owner = Bukkit.getServer().getPlayer(args[1]);

            if(owner == null) {
                player.sendMessage(ChatColor.RED + "Player not found");
                return true;
            }

            if(!PartyManagment.inParty(PartyManagment.getParty(owner), owner)) {
                player.sendMessage(ChatColor.RED + owner.getName() + " is not inside a Party");
                return true;
            }

            List<Player> party = PartyManagment.getParty(owner);
            List<Player> members = PartyManagment.getMembers(party);

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
                player.removeMetadata("party.invite.timer." + owner.getUniqueId(), plugin);
                player.sendMessage(ChatColor.RED + "there is no Party Invite Request");
            } else {
                player.removeMetadata("party.invite.timer." + owner.getUniqueId(), plugin);
                members.forEach(p -> p.sendMessage(ChatColor.YELLOW + player.getName() + " Joined the Party"));

                TPlayer data2 = PartyManagment.getData(owner);

                party.add(player);
                data2.setParty(party);

                player.sendMessage(ChatColor.YELLOW + "You Joined the Party of " + owner.getName());
            }
            return true;
        }

        if(tab.equalsIgnoreCase("gui")) {
            return true;
        }

        if (tab.equalsIgnoreCase("info")) {

            if(!PartyManagment.inParty(PartyManagment.getParty(player), player)) {
                player.sendMessage(ChatColor.RED + "You are not inside a Party");
                return true;
            }

            List<Player> party = PartyManagment.getParty(player);
            List<Player> members = PartyManagment.getMembers(party);

                player.sendMessage(ChatColor.AQUA + "Here your Party Information:");

                members.forEach(p -> {
                    TPlayer data2 = PartyManagment.getData(p);
                    if (PartyManagment.isOwner(party, p)) {
                        player.sendMessage(ChatColor.GOLD + p.getName() + ChatColor.GRAY + " - " + ChatColor.RED + data2.getStats()[3] + "/" + data2.getStats()[4]);
                    } else if(p.isOnline()) {
                        player.sendMessage(ChatColor.YELLOW + p.getName() + ChatColor.GRAY + " - " + ChatColor.RED + data2.getStats()[3] + "/" + data2.getStats()[4]);
                    } else {
                        player.sendMessage(ChatColor.GRAY + p.getName());
                    }
                });

            return true;
        }

        return true;
    }

}
