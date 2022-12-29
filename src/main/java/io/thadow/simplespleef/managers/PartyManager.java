package io.thadow.simplespleef.managers;

import io.thadow.simplespleef.Main;
import io.thadow.simplespleef.api.party.Party;
import io.thadow.simplespleef.utils.Utils;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PartyManager {
    @Getter
    private static final PartyManager manager = new PartyManager();
    @Getter
    private final HashMap<UUID, UUID> partyInvites = new HashMap<>();
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
}
