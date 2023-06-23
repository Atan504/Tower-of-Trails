package ToT.PartyManagment.Commands;

import ToT.Objects.TPlayer;
import ToT.Utils.PartyManagment;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
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
import java.util.List;

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

            if(PartyManagment.inParty(PartyManagment.getParty(player.getUniqueId()), player.getUniqueId())) {
                player.sendMessage(ChatColor.RED + "You already inside a Party.");
                return true;
            }

            TPlayer data = PartyManagment.getData(player.getUniqueId());

            player.sendMessage(ChatColor.GREEN + "You Created new Party!");
            ArrayList<UUID> party = data.getParty();
            party.add(player.getUniqueId());
            data.setParty(party);

            PartyManagment.updatePartyScoreboard(player);
            return true;
        }

        if (tab.equalsIgnoreCase("chat")) {

            ArrayList<UUID> party = PartyManagment.getParty(player.getUniqueId());

            if(!PartyManagment.inParty(party, player.getUniqueId())) {
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

            ArrayList<UUID> party = PartyManagment.getParty(player.getUniqueId());

            if (!PartyManagment.inParty(party, player.getUniqueId())) {
                TPlayer data = PartyManagment.getData(player.getUniqueId());
                player.sendMessage(ChatColor.GREEN + "You Created new Party!");
                party.add(player.getUniqueId());
                data.setParty(party);
            }

            if(!PartyManagment.isOwner(party, player.getUniqueId())) {
                player.sendMessage(ChatColor.RED + "You are not the Party Owner");
                return true;
            }

            if(member == null) {
                player.sendMessage(ChatColor.RED + args[1] + " Player not found");
                return true;
            }

            if(PartyManagment.inParty(party, member.getUniqueId())) {
                player.sendMessage(ChatColor.RED + "Player " + member.getName() + " already on the Party");
                return true;
            }

            player.sendMessage(ChatColor.YELLOW + "You Sent Party Invitation to " + member.getName());

            // member.sendMessage(ChatColor.YELLOW + player.getName() + " Invited you to the Party, Join the party via /party join " + player.getName());

            String message = ChatColor.YELLOW + player.getName() + " Invited you to the Party, click to join ";

            TextComponent clickComponent = new TextComponent("[JOIN]");
            clickComponent.setColor(ChatColor.GREEN);
            clickComponent.setBold(true);
            clickComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party join " + player.getName()));

            TextComponent hoverComponent = new TextComponent("Click to join the party");
            hoverComponent.setColor(ChatColor.YELLOW);
            clickComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(hoverComponent.toLegacyText())));

            TextComponent messageComponent = new TextComponent(message);
            messageComponent.addExtra(clickComponent);

            member.spigot().sendMessage(messageComponent);

            member.setMetadata("party.invite.timer." + player.getUniqueId(), new FixedMetadataValue(plugin, new Date().getTime()));
        }

        if(tab.equalsIgnoreCase("kick")) {

            if(args.length == 1) {
                player.sendMessage(ChatColor.RED + "You need specific Player to Kick");
                return true;
            }

            ArrayList<UUID> party = PartyManagment.getParty(player.getUniqueId());

            if (!PartyManagment.inParty(party, player.getUniqueId())) {
                player.sendMessage(ChatColor.RED + "You are not in Party");
                return true;
            }

            UUID owner = PartyManagment.getOwner(party);

            TPlayer data = PartyManagment.getData(owner);

            ArrayList<UUID> members = PartyManagment.getMembers(party);

            UUID uuid = PartyManagment.getPlayer(members, args[1]);

            if(!PartyManagment.isOwner(party, player.getUniqueId())) {
                player.sendMessage(ChatColor.RED + "You are not the Party Owner");
                return true;
            }

            if(uuid == null) {
                player.sendMessage(ChatColor.RED + args[1] + " Player not found");
                return true;
            }

            Player member = Bukkit.getServer().getPlayer(uuid);

            if(uuid.equals(player.getUniqueId())) {
                player.sendMessage(ChatColor.RED + "You cant kick yourself");
                return true;
            }

            party.remove(uuid);
            data.setParty(party);
            player.sendMessage(ChatColor.RED + "You Kicked the Player " + args[1] + " from the Party");

            if(member != null) {
                member.sendMessage(ChatColor.RED + "You got Kicked from the Party");
                PartyManagment.updatePartyScoreboard(member);
            }

            members.stream().filter(p -> p != uuid).forEach(p -> {
                Player p2 = Bukkit.getServer().getPlayer(p);
                if(p2 != null) {
                    PartyManagment.updatePartyScoreboard(p2);
                    p2.sendMessage(ChatColor.RED + args[1] + " Left the party!");
                }
            });
        }

        if(tab.equalsIgnoreCase("leave")) {

            ArrayList<UUID> party = PartyManagment.getParty(player.getUniqueId());

            if(!PartyManagment.inParty(party, player.getUniqueId())) {
                player.sendMessage(ChatColor.RED + "You are not in Party");
                return true;
            }

            ArrayList<UUID> members = PartyManagment.getMembers(party);
            TPlayer data = PartyManagment.getData(player.getUniqueId());

            if(members.size() == 1) {

                player.sendMessage(ChatColor.RED + "You are the only one who left in the party");
                player.sendMessage(ChatColor.RED + "Your Previous Party has been Disbanded");
                party.remove(player.getUniqueId());
                data.setParty(party);
                PartyManagment.updatePartyScoreboard(player);
                return true;
            }

            if(PartyManagment.isOwner(party, player.getUniqueId())) {
                Player p = Bukkit.getServer().getPlayer(party.get(1));
                if(p != null) p.sendMessage(ChatColor.YELLOW + "Previous Party Owner left the Party you become the new Party Owner");
            }

            party.remove(player.getUniqueId());
            party = new ArrayList<>(party.stream().filter(Objects::nonNull).toList());

            UUID owner = PartyManagment.getOwner(party);

            TPlayer data2 = PartyManagment.getData(owner);

            data.setParty(new ArrayList<>());
            data2.setParty(party);

            player.sendMessage(ChatColor.RED + "You Left the party!");
            PartyManagment.updatePartyScoreboard(player);

            ArrayList<UUID> members2 = PartyManagment.getMembers(party);

            members2.stream().filter(p -> p != player.getUniqueId()).forEach(p -> {
                Player p2 = Bukkit.getServer().getPlayer(p);
                if(p2 != null) {
                    PartyManagment.updatePartyScoreboard(p2);
                    p2.sendMessage(ChatColor.RED + player.getName() + " Left the party!");
                }
            });
            return true;
        }

        if(tab.equalsIgnoreCase("disband")) {

            ArrayList<UUID> party = PartyManagment.getParty(player.getUniqueId());

            if(!PartyManagment.inParty(party, player.getUniqueId())) {
                player.sendMessage(ChatColor.RED + "You are not in Party");
                return true;
            }

            ArrayList<UUID> members = PartyManagment.getMembers(party);

            TPlayer data = PartyManagment.getData(player.getUniqueId());

            if(!PartyManagment.isOwner(party, player.getUniqueId())) {
                player.sendMessage(ChatColor.RED + "You are not the Party Owner");
                return true;
            }

            members.forEach(p -> {
                party.remove(p);
                Player p2 = Bukkit.getServer().getPlayer(p);
                if(p2 != null) {
                    p2.sendMessage(ChatColor.RED + "Your Previous Party has been Disbanded");
                }
            });

            data.setParty(party);

            members.forEach(p -> {
                Player p2 = Bukkit.getServer().getPlayer(p);
                if(p2 != null) PartyManagment.updatePartyScoreboard(p2);
            });

            return true;
        }

        if(tab.equalsIgnoreCase("promote")) {

            if(args.length == 1) {
                player.sendMessage(ChatColor.RED + "You need specific Player to Promote");
                return true;
            }

            ArrayList<UUID> party = PartyManagment.getParty(player.getUniqueId());

            if(!PartyManagment.inParty(party, player.getUniqueId())) {
                player.sendMessage(ChatColor.RED + "You are not inside a Party");
                return true;
            }

            if(!PartyManagment.isOwner(party, player.getUniqueId())) {
                player.sendMessage(ChatColor.RED + "You are not the Party Owner");
                return true;
            }

            UUID uuid = PartyManagment.getPlayer(party, args[1]);

            if(uuid == null) {
                player.sendMessage(ChatColor.RED + args[1] + " Player not found");
                return true;
            }

            if(PartyManagment.isOwner(party, uuid)) {
                player.sendMessage(ChatColor.RED + args[1] + " is already the Party Owner");
                return true;
            }

            int index = party.indexOf(uuid);

            party.set(index, player.getUniqueId());
            party.set(0, uuid);

            UUID owner = PartyManagment.getOwner(party);

            TPlayer data = PartyManagment.getData(player.getUniqueId());
            TPlayer data2 = PartyManagment.getData(owner);

            data.setParty(new ArrayList<>());
            data2.setParty(party);

            Player member = Bukkit.getServer().getPlayer(uuid);

            ArrayList<UUID> members = PartyManagment.getMembers(party);

            members.forEach(p -> {
                Player p2 = Bukkit.getServer().getPlayer(p);
                if(p2 != null) PartyManagment.updatePartyScoreboard(p2);
            });

            if(member != null) member.sendMessage(ChatColor.YELLOW + "You got Promoted to Party Owner");
            player.sendMessage(ChatColor.YELLOW + "You transferred the Party Owner to " + args[1]);
        }

        if(tab.equalsIgnoreCase("join")) {

            if(args.length == 1) {
                player.sendMessage(ChatColor.RED + "You need specific Party Owner Player to Join to");
                return true;
            }

            ArrayList<UUID> party = PartyManagment.getParty(player.getUniqueId());

            if(PartyManagment.inParty(party, player.getUniqueId())) {
                player.sendMessage(ChatColor.RED + "You are already in a Party, leave this one first");
                return true;
            }

            Player owner = Bukkit.getServer().getPlayer(args[1]);

            if(owner == null) {
                player.sendMessage(ChatColor.RED + "Player not found");
                return true;
            }

            ArrayList<UUID> party2 = PartyManagment.getParty(owner.getUniqueId());

            if(!PartyManagment.inParty(party2, owner.getUniqueId())) {
                player.sendMessage(ChatColor.RED + owner.getName() + " is not inside a Party");
                return true;
            }

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

                TPlayer data2 = PartyManagment.getData(owner.getUniqueId());

                party2.add(player.getUniqueId());
                data2.setParty(party2);

                ArrayList<UUID> members = PartyManagment.getMembers(party2);

                members.forEach(p -> {
                    Player p2 = Bukkit.getServer().getPlayer(p);
                    if(p2 != null) {
                        PartyManagment.updatePartyScoreboard(p2);
                        if(p2 != player) p2.sendMessage(ChatColor.YELLOW + player.getName() + " Joined the Party");
                    }
                });

                player.sendMessage(ChatColor.YELLOW + "You Joined the Party of " + owner.getName());
            }
            return true;
        }

        if(tab.equalsIgnoreCase("gui")) {
            return true;
        }

        if (tab.equalsIgnoreCase("info")) {

            // PartyManagment.updatePartyScoreboard(player);

            ArrayList<UUID> party = PartyManagment.getParty(player.getUniqueId());

            if(!PartyManagment.inParty(party, player.getUniqueId())) {
                player.sendMessage(ChatColor.RED + "You are not inside a Party");
                return true;
            }

            ArrayList<UUID> members = PartyManagment.getMembers(party);

            player.sendMessage(ChatColor.AQUA + "Here your Party Information:");

            members.forEach(uuid -> {
                Player p = Bukkit.getServer().getPlayer(uuid);
                OfflinePlayer p2 = Bukkit.getServer().getOfflinePlayer(uuid);

                if(p != null) PartyManagment.updatePartyScoreboard(p);

                // if(p2 != null) PartyManagment.updatePartyScoreboard(p2);

                if (PartyManagment.isOwner(party, uuid)) {
                    if(p != null) player.sendMessage(ChatColor.GOLD + p.getName() + ChatColor.GRAY + " - " + PartyManagment.HealthBar(player));
                    else player.sendMessage(ChatColor.GRAY + p2.getName());
                } else {
                    if(p != null) player.sendMessage(ChatColor.YELLOW + p.getName() + ChatColor.GRAY + " - " + PartyManagment.HealthBar(player));
                    else player.sendMessage(ChatColor.GRAY + p2.getName());
                }
            });

            return true;
        }

        return true;
    }

}
