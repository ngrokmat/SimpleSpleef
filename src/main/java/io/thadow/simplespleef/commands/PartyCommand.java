package io.thadow.simplespleef.commands;

import io.thadow.simplespleef.Main;
import io.thadow.simplespleef.api.party.Invite;
import io.thadow.simplespleef.api.party.Party;
import io.thadow.simplespleef.api.party.PartyPrivacy;
import io.thadow.simplespleef.managers.PartyManager;
import io.thadow.simplespleef.managers.PlayerDataManager;
import io.thadow.simplespleef.utils.Utils;
import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class PartyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (PlayerDataManager.getManager().getSpleefPlayer(player).getArena() != null) {
                String message = Utils.getMessage("Messages.Commands.Party Command.Can't Use In Game");
                player.sendMessage(message);
                return true;
            }
            if (args.length == 1
            && !args[0].equalsIgnoreCase("public")
            && !args[0].equalsIgnoreCase("private")
            && !args[0].equalsIgnoreCase("create")
            && !args[0].equalsIgnoreCase("leave")
            && !args[0].equalsIgnoreCase("publica")
            && !args[0].equalsIgnoreCase("privada")
            && !args[0].equalsIgnoreCase("crear")
            && !args[0].equalsIgnoreCase("salir")) {
                if (args[0].equalsIgnoreCase("accept") || args[0].equalsIgnoreCase("aceptar")) {
                    String message = Utils.getMessage("Messages.Commands.Party Command.Accept Usage");
                    player.sendMessage(message);
                    return true;
                }
                if (args[0].equalsIgnoreCase("invite") || args[0].equalsIgnoreCase("invitar")) {
                    String message = Utils.getMessage("Messages.Commands.Party Command.Invite Usage");
                    player.sendMessage(message);
                    return true;
                }
                if (args[0].equalsIgnoreCase("kick") || args[0].equalsIgnoreCase("expulsar")) {
                    String message = Utils.getMessage("Messages.Commands.Party Command.Kick Usage");
                    player.sendMessage(message);
                    return true;
                }
                if (args[0].equalsIgnoreCase("leader") || args[0].equalsIgnoreCase("lider")) {
                    String message = Utils.getMessage("Messages.Commands.Party Command.Leader Usage");
                    player.sendMessage(message);
                    return true;
                }
                if (Utils.isPlayer(args[0])) {
                    Party party = PartyManager.getManager().getPlayerParty(player);
                    if (party == null) {
                        PartyManager.getManager().createParty(player, true);
                    }
                    Bukkit.dispatchCommand(player, "party invite " + args[0]);
                }
            } else if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
                List<String> usageMessage = Main.getConfiguration().getStringList("Messages.Commands.Party Command.Usage");
                usageMessage = Utils.format(usageMessage);
                for (String line : usageMessage) {
                    player.sendMessage(line);
                }
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
                    PartyManager.getManager().createParty(player, false);
                }
            } else if (args[0].equalsIgnoreCase("accept") || args[0].equalsIgnoreCase("aceptar")) {
                if (player.hasPermission("simplespleef.commands.party.accept")) {
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
                    Invite invite = PartyManager.getManager().findInvite(target.getUniqueId(), player.getUniqueId());
                    if (invite == null) {
                        String message = Utils.getMessage("Messages.Commands.Party Command.Not Invited");
                        message = message.replace("%target%", target.getName());
                        player.sendMessage(message);
                        return true;
                    }
                    invite.accept();
                }
            }  else if (args[0].equalsIgnoreCase("invite") || args[0].equalsIgnoreCase("invitar")) {
                if (player.hasPermission("simplespleef.commands.party.invite")) {
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
                    if (target == player) {
                        String message = Utils.getMessage("Messages.Commands.Party Command.Can't Invite Self");
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
                    Invite invite = new Invite(target.getUniqueId(), player.getUniqueId());
                    if (PartyManager.getManager().alreadyInvited(player.getUniqueId(), target.getUniqueId())) {
                        String message = Utils.getMessage("Messages.Commands.Party Command.Already Invited");
                        player.sendMessage(message);
                        return true;
                    }
                    int expireTime = Main.getConfiguration().getInt("Configuration.Parties.Invite Expire Time");
                    String message = Utils.getMessage("Messages.Commands.Party Command.Invite Sent");
                    player.sendMessage(message);
                    String inviteMessage = Utils.getMessage("Messages.Commands.Party Command.Invite Message");
                    inviteMessage = inviteMessage.replace("%from%", player.getName());
                    inviteMessage = inviteMessage.replace("%time%", String.valueOf(expireTime));
                    String hoverMessage = Utils.getMessage("Messages.Commands.Party Command.Invite Message Hover");
                    TextComponent component = new TextComponent();
                    component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hoverMessage).create()));
                    component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party accept " + player.getName()));
                    component.setText(Utils.format(inviteMessage));
                    target.spigot().sendMessage(component);
                    invite.create();
                }
            } else if (args[0].equalsIgnoreCase("kick") || args[0].equalsIgnoreCase("expulsar")) {
                if (player.hasPermission("simplespleef.commands.party.kick")) {
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
                    if (target == player) {
                        String message = Utils.getMessage("Messages.Commands.Party Command.Can't Kick Self");
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
                    if (party.getLeader() == target) {
                        String message = Utils.getMessage("Messages.Commands.Party Command.Already Leader");
                        player.sendMessage(message);
                        return true;
                    }
                    if (!party.getMembers().contains(target)) {
                        String message = Utils.getMessage("Messages.Commands.Party Command.Not In Members");
                        message = message.replace("%target%", target.getName());
                        player.sendMessage(message);
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
