package io.thadow.simplespleef.managers;

import io.thadow.simplespleef.Main;
import io.thadow.simplespleef.api.party.Invite;
import io.thadow.simplespleef.api.party.Party;
import io.thadow.simplespleef.api.party.PartyPrivacy;
import io.thadow.simplespleef.utils.Utils;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PartyManager {
    @Getter
    private static final PartyManager manager = new PartyManager();
    @Getter
    private final List<Invite> invites = new ArrayList<>();
    @Getter
    private final List<Player> inviting = new ArrayList<>();
    @Getter
    private final List<Party> parties = new ArrayList<>();
    @Getter
    private static final int maxSize = Main.getConfiguration().getInt("Configuration.Parties.Max Size");

    public Party getPlayerParty(Player player) {
        for (Party party : parties) {
            if (party.getLeader().getName().equalsIgnoreCase(player.getName())
            || party.getMembers().contains(player)) {
                return party;
            }
        }
        return null;
    }

    public void createParty(Player leader, boolean silent) {
        Party party = new Party(leader);
        parties.add(party);
        if (silent) {
            return;
        }
        String message = Utils.getMessage("Messages.Commands.Party Command.Party Created");
        leader.sendMessage(message);
    }

    public void disbandParty(Party party) {
        for (Player member : party.getMembers()) {
            String message = Utils.getMessage("Messages.Commands.Party Command.Party Disband Message");
            member.sendMessage(message);
        }
        party.setLeader(null);
        party.getMembers().clear();
        parties.remove(party);
    }

    public boolean hasParty(Player player) {
        for (Party party : parties) {
            if (party.getLeader().getName().equalsIgnoreCase(player.getName())
            || party.getMembers().contains(player)) {
                return true;
            }
        }
        return false;
    }

    public List<Party> getPublicParties() {
        List<Party> parties = new ArrayList<>();
        for (Party party : getParties()) {
            if (party.getPrivacy() == PartyPrivacy.PUBLIC) {
                parties.add(party);
            }
        }
        return parties;
    }

    public boolean alreadyInvited(UUID inviter, UUID invitedPlayer) {
        for (Invite invite : getInvites()) {
            if (invite.getInviter() == inviter && invite.getInvitedPlayer() == invitedPlayer) {
                return true;
            }
        }
        return false;
    }

    public Invite findInvite(UUID inviter, UUID invitedPlayer) {
        Invite invite = null;

        for (Invite it : getInvites()) {
            if (it.getInviter() == inviter && it.getInvitedPlayer() == invitedPlayer) {
                invite = it;
                break;
            }
        }

        return invite;
    }
}
