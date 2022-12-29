package io.thadow.simplespleef.api.party;

import io.thadow.simplespleef.managers.PartyManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.ArrayList;
import java.util.List;

public class Party {

    @Getter
    Player leader;
    @Getter
    List<Player> members = new ArrayList<>();
    @Getter @Setter
    PartyPrivacy privacy;
    @Getter
    int maxSize;

    public Party(Player leader) {
        this.leader = leader;
        members.add(leader);
        this.privacy = PartyPrivacy.PRIVATE;
    }

    public int getSize() {
        return members.size();
    }

    public void setLeader(Player player) {
        leader = player;
    }

    public boolean isLeader(Player player) {
        return leader.getName().equalsIgnoreCase(player.getName());
    }

    public void addMember(Player player) {
        members.add(player);
    }

    public void removeMember(Player player) {
        members.remove(player);
    }
}
