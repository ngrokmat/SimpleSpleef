package io.thadow.simplespleef.api.party;

import io.thadow.simplespleef.Main;
import io.thadow.simplespleef.managers.PartyManager;
import io.thadow.simplespleef.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Invite {
    private final UUID invitedPlayer;
    private final UUID inviter;

    public Invite(UUID invitedPlayer, UUID inviter) {
        this.invitedPlayer = invitedPlayer;
        this.inviter = inviter;
    }

    public UUID getInvitedPlayer() {
        return invitedPlayer;
    }

    public UUID getInviter() {
        return inviter;
    }

    public void create() {
        PartyManager.getManager().getInvites().add(this);
        int expireTime = Main.getConfiguration().getInt("Configuration.Parties.Invite Expire Time");
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            if (!PartyManager.getManager().getInvites().contains(this)) {
                return;
            }
            PartyManager.getManager().getInvites().remove(this);
            expire();
        }, 20L * expireTime);
    }

    public void accept() {
        PartyManager.getManager().getInvites().remove(this);
        Player target = Bukkit.getPlayer(inviter);
        Player player = Bukkit.getPlayer(invitedPlayer);
        if (target == null || player == null) {
            return;
        }
        Party party = PartyManager.getManager().getPlayerParty(target);
        if (!party.isLeader(target)) {
            String message = Utils.getMessage("Messages.Commands.Party Command.Not Leader");
            message = message.replace("%target%", target.getName());
            player.sendMessage(message);
            return;
        }
        if (party.getMembers().size() >= PartyManager.getMaxSize()) {
            String message = Utils.getMessage("Messages.Commands.Party Command.Party Full");
            player.sendMessage(message);
            return;
        }
        for (Player member : party.getMembers()) {
            String message = Utils.getMessage("Messages.Commands.Party Command.Player Joined");
            message = message.replace("%target%", player.getName());
            member.sendMessage(message);
        }
        party.addMember(player);
        String message = Utils.getMessage("Messages.Commands.Party Command.Invite Accept");
        player.sendMessage(message);
    }

    private void expire() {
        Player target = Bukkit.getPlayer(invitedPlayer);
        Player player = Bukkit.getPlayer(inviter);
        if (target == null || player == null) {
            return;
        }
        String exMessage = Utils.getMessage("Messages.Commands.Party Command.Invite Expired");
        exMessage = exMessage.replace("%from%", player.getName());
        target.sendMessage(exMessage);
    }
}
