package io.thadow.simplespleef.commands;

import io.thadow.simplespleef.Main;
import io.thadow.simplespleef.api.party.Party;
import io.thadow.simplespleef.api.party.PartyPrivacy;
import io.thadow.simplespleef.managers.PartyManager;
import io.thadow.simplespleef.utils.Utils;
import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
                player.sendMessage("Usage");
                return true;
            } else if (args[0].equalsIgnoreCase("public") || args[0].equalsIgnoreCase("publica")) {
                if (player.hasPermission("simplespleef.commands.party.privacy")) {
                    if (!PartyManager.getManager().hasParty(player)) {
                        String message = Utils.getMessage("Messages.Commands.Party Command.Not In Party");
                        player.sendMessage(message);
                        return true;
                    }
                    Party party = PartyManager.getManager().getPlayerParty(player);
                    if (!party.isLeader(player)) {
                        String message = Utils.getMessage("Messages.Commands.Party Command.Only Leader");
                        player.sendMessage(message);
                        return true;
                    }
                    if (party.getPrivacy() == PartyPrivacy.PUBLIC) {
                        String message = Utils.getMessage("Messages.Commands.Party Command.Already In Public");
                        player.sendMessage(message);
                        return true;
                    }
                    party.setPrivacy(PartyPrivacy.PUBLIC);
                    String message = Utils.getMessage("Messages.Commands.Party Command.Changed To Public");
                    player.sendMessage(message);
                }
            } else if (args[0].equalsIgnoreCase("private") || args[0].equalsIgnoreCase("privada")) {
                if (player.hasPermission("simplespleef.commands.party.privacy")) {
                    if (!PartyManager.getManager().hasParty(player)) {
                        String message = Utils.getMessage("Messages.Commands.Party Command.Not In Party");
                        player.sendMessage(message);
                        return true;
                    }
                    Party party = PartyManager.getManager().getPlayerParty(player);
                    if (!party.isLeader(player)) {
                        String message = Utils.getMessage("Messages.Commands.Party Command.Only Leader");
                        player.sendMessage(message);
                        return true;
                    }
                    if (party.getPrivacy() == PartyPrivacy.PRIVATE) {
                        String message = Utils.getMessage("Messages.Commands.Party Command.Already In Private");
                        player.sendMessage(message);
                        return true;
                    }
                    party.setPrivacy(PartyPrivacy.PRIVATE);
                    String message = Utils.getMessage("Messages.Commands.Party Command.Changed To Private");
                    player.sendMessage(message);
                }
            } else if (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("crear")) {
                if (player.hasPermission("simplespleef.commands.party.create")) {
                    if (PartyManager.getManager().hasParty(player)) {
                        String message = Utils.getMessage("Messages.Commands.Party Command.Already In Party");
                        player.sendMessage(message);
                        return true;
                    }
                    PartyManager.getManager().createParty(player);
                }
            } else if (args[0].equalsIgnoreCase("accept") || args[0].equalsIgnoreCase("aceptar")) {
                if (player.hasPermission("simplespleef.commands.party.accept")) {
                    if (args.length == 1) {
                        String message = Utils.getMessage("Messages.Commands.Party Command.Accept Usage");
                        player.sendMessage(message);
                        return true;
                    }
                    if (PartyManager.getManager().hasParty(player)) {
                        String message = Utils.getMessage("Messages.Commands.Party Command.Already In Party");
                        player.sendMessage(message);
                        return true;
                    }
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target == null) {
                        String message = Utils.getMessage("Messages.Commands.Party Command.Player Offline");
                        message = message.replace("%target%", args[1]);
                        player.sendMessage(message);
                        return true;
                    }
                    if (!PartyManager.getManager().getPartyInvites().containsKey(target.getUniqueId())) {
                        String message = Utils.getMessage("Messages.Commands.Party Command.Not Invited");
                        message = message.replace("%target%", target.getName());
                        player.sendMessage(message);
                        return true;
                    }
                    if (PartyManager.getManager().getPartyInvites().get(target.getUniqueId()).equals(player.getUniqueId())) {
                        PartyManager.getManager().getPartyInvites().remove(target.getUniqueId());
                        if (PartyManager.getManager().hasParty(target)) {
                            Party party = PartyManager.getManager().getPlayerParty(target);
                            if (!party.isLeader(target)) {
                                String message = Utils.getMessage("Messages.Commands.Party Command.Not Leader");
                                message = message.replace("%target%", target.getName());
                                player.sendMessage(message);
                                return true;
                            }
                            if (party.getMembers().size() >= PartyManager.getMaxSize()) {
                                String message = Utils.getMessage("Messages.Commands.Party Command.Party Full");
                                player.sendMessage(message);
                                return true;
                            }
                            for (Player member : party.getMembers()) {
                                String message = Utils.getMessage("Messages.Commands.Party Command.Player Joined");
                                message = message.replace("%target%", player.getName());
                                member.sendMessage(message);
                            }
                            party.addMember(player);
                            String message = Utils.getMessage("Messages.Commands.Party Command.Invite Accept");
                            player.sendMessage(message);
                        } else {
                            String message = Utils.getMessage("Messages.Commands.Party Command.No Party Invited");
                            message = message.replace("%target%", target.getName());
                            player.sendMessage(message);
                        }
                    } else {
                        String message = Utils.getMessage("Messages.Commands.Party Command.Not Invited");
                        message = message.replace("%target%", target.getName());
                        player.sendMessage(message);
                    }
                }
            }  else if (args[0].equalsIgnoreCase("invite") || args[0].equalsIgnoreCase("invitar")) {
                if (player.hasPermission("simplespleef.commands.party.invite")) {
                    if (args.length == 1) {
                        String message = Utils.getMessage("Messages.Commands.Party Command.Invite Usage");
                        player.sendMessage(message);
                        return true;
                    }
                    if (!PartyManager.getManager().hasParty(player)) {
                        String message = Utils.getMessage("Messages.Commands.Party Command.Not In Party");
                        player.sendMessage(message);
                        return true;
                    }
                    Party party = PartyManager.getManager().getPlayerParty(player);
                    if (!party.isLeader(player)) {
                        String message = Utils.getMessage("Messages.Commands.Party Command.Only Leader");
                        player.sendMessage(message);
                        return true;
                    }
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target == null) {
                        String message = Utils.getMessage("Messages.Commands.Party Command.Player Offline");
                        message = message.replace("%target%", args[1]);
                        player.sendMessage(message);
                        return true;
                    }
                    if (party.getMembers().size() >= PartyManager.getMaxSize()) {
                        String message = Utils.getMessage("Messages.Commands.Party Command.Party Full");
                        player.sendMessage(message);
                        return true;
                    }
                    Party targetParty = PartyManager.getManager().getPlayerParty(target);
                    if (targetParty != null) {
                        String message = Utils.getMessage("Messages.Commands.Party Command.Invited Already In Party");
                        message = message.replace("%target%", target.getName());
                        player.sendMessage(message);
                        return true;
                    }
                    if (PartyManager.getManager().getPartyInvites().containsKey(player.getUniqueId())) {
                        PartyManager.getManager().getPartyInvites().replace(player.getUniqueId(), target.getUniqueId());
                    } else {
                        PartyManager.getManager().getPartyInvites().put(player.getUniqueId(), target.getUniqueId());
                    }
                    String message = Utils.getMessage("Messages.Commands.Party Command.Invite Sent");
                    player.sendMessage(message);
                    String inviteMessage = Utils.getMessage("Messages.Commands.Party Command.Invite Message");
                    int expireTime = Main.getConfiguration().getInt("Configuration.Parties.Expire Time");
                    inviteMessage = inviteMessage.replace("%from%", player.getName());
                    inviteMessage = inviteMessage.replace("%time%", String.valueOf(expireTime));
                    String hoverMessage = Utils.getMessage("Messages.Commands.Party Command.Invite Message Hover");
                    TextComponent component = new TextComponent(inviteMessage);
                    component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hoverMessage).create()));
                    component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party accept " + player.getName()));
                    target.spigot().sendMessage(component);
                    Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                        if (!PartyManager.getManager().getPartyInvites().containsKey(player.getUniqueId())) {
                            return;
                        }
                        PartyManager.getManager().getPartyInvites().remove(player.getUniqueId());
                        if (target.isOnline()) {
                            String exMessage = Utils.getMessage("Messages.Commands.Party Command.Invite Expired");
                            exMessage = exMessage.replace("%from%", player.getName());
                            target.sendMessage(exMessage);
                        }
                    }, 20L * expireTime);
                }
            } else if (args[0].equalsIgnoreCase("kick") || args[0].equalsIgnoreCase("expulsar")) {
                if (player.hasPermission("simplespleef.commands.party.kick")) {
                    if (args.length == 1) {
                        String message = Utils.getMessage("Messages.Commands.Party Command.Kick Usage");
                        player.sendMessage(message);
                        return true;
                    }
                    if (!PartyManager.getManager().hasParty(player)) {
                        String message = Utils.getMessage("Messages.Commands.Party Command.Not In Party");
                        player.sendMessage(message);
                        return true;
                    }
                    Party party = PartyManager.getManager().getPlayerParty(player);
                    if (!party.isLeader(player)) {
                        String message = Utils.getMessage("Messages.Commands.Party Command.Only Leader");
                        player.sendMessage(message);
                        return true;
                    }
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target == null) {
                        String message = Utils.getMessage("Messages.Commands.Party Command.Player Offline");
                        message = message.replace("%target%", args[1]);
                        player.sendMessage(message);
                        return true;
                    }
                    if (!party.getMembers().contains(target)) {
                        String message = Utils.getMessage("Messages.Commands.Party Command.Not In Members");
                        message = message.replace("%target%", target.getName());
                        player.sendMessage(message);
                        return true;
                    }
                    party.removeMember(target);
                    String message = Utils.getMessage("Messages.Commands.Patty Command.Kick Message");
                    target.sendMessage(message);
                    for (Player member : party.getMembers()) {
                        String kmessage = Utils.getMessage("Messages.Commands.Party Command.Player Kicked");
                        kmessage = kmessage.replace("%target%", target.getName());
                        member.sendMessage(kmessage);
                    }
                }
            } else if (args[0].equalsIgnoreCase("leader") || args[0].equalsIgnoreCase("lider")) {
                if (player.hasPermission("simplespleef.commands.party.leader")) {
                    if (args.length == 1) {
                        String message = Utils.getMessage("Messages.Commands.Party Command.Leader Usage");
                        player.sendMessage(message);
                        return true;
                    }
                    if (!PartyManager.getManager().hasParty(player)) {
                        String message = Utils.getMessage("Messages.Commands.Party Command.Not In Party");
                        player.sendMessage(message);
                        return true;
                    }
                    Party party = PartyManager.getManager().getPlayerParty(player);
                    if (party.isLeader(player)) {
                        String message = Utils.getMessage("Messages.Commands.Party Command.Only Leader");
                        player.sendMessage(message);
                        return true;
                    }
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target == null) {
                        String message = Utils.getMessage("Messages.Commands.Party Command.Player Offline");
                        message = message.replace("%target%", args[1]);
                        player.sendMessage(message);
                        return true;
                    }
                    if (party.getLeader() == player) {
                        String message = Utils.getMessage("Messages.Commands.Party Command.Already Leader");
                        player.sendMessage(message);
                        return true;
                    }
                    if (!party.getMembers().contains(target)) {
                        String message = Utils.getMessage("Messages.Commands.Party Command.Not In Members");
                        message = message.replace("%target%", target.getName());
                        return true;
                    }
                    party.setLeader(target);
                    String message = Utils.getMessage("Messages.Commands.Party Command.Leader Set");
                    target.sendMessage(message);
                    for (Player member : party.getMembers()) {
                        String lmessage = Utils.getMessage("Messages.Commands.Party Command.Leader Changed");
                        lmessage = lmessage.replace("%target%", target.getName());
                        member.sendMessage(lmessage);
                    }
                }
            } else if (args[0].equalsIgnoreCase("leave") || args[0].equalsIgnoreCase("salir")) {
                if (player.hasPermission("simplespleef.commands.party.leave")) {
                    if (!PartyManager.getManager().hasParty(player)) {
                        String message = Utils.getMessage("Messages.Commands.Party Command.Not In Party");
                        player.sendMessage(message);
                        return true;
                    }
                    Party party = PartyManager.getManager().getPlayerParty(player);
                    party.removeMember(player);
                    String message = Utils.getMessage("Messages.Commands.Party Command.Party Leave");
                    player.sendMessage(message);
                    if (party.getLeader() == player) {
                        PartyManager.getManager().disbandParty(party);
                        return true;
                    }
                    for (Player member : party.getMembers()) {
                        String lmessage = Utils.getMessage("Messages.Commands.Party Command.Player Left");
                        lmessage = lmessage.replace("%target%", player.getName());
                        member.sendMessage(lmessage);
                    }
                }
            }
        }
        return false;
    }
}
