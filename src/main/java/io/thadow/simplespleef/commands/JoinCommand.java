package io.thadow.simplespleef.commands;

import io.thadow.simplespleef.api.party.Party;
import io.thadow.simplespleef.api.playerdata.PlayerData;
import io.thadow.simplespleef.arena.Arena;
import io.thadow.simplespleef.managers.ArenaManager;
import io.thadow.simplespleef.managers.PartyManager;
import io.thadow.simplespleef.managers.PlayerDataManager;
import io.thadow.simplespleef.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JoinCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("simplespleef.commands.join")) {
                if (args.length >= 1) {
                    if (args[0].equalsIgnoreCase("random")) {
                        if (!ArenaManager.getManager().joinRandom(player, PlayerDataManager.getManager().getSpleefPlayer(player).isSpectating())) {
                            if (ArenaManager.getManager().getPlayerArena(player) != null) {
                                ArenaManager.getManager().handleLeave(PlayerDataManager.getManager().getSpleefPlayer(player), PlayerDataManager.getManager().getSpleefPlayer(player).getArena(), true);
                            }
                            return true;
                        }
                        return true;
                    }
                    StringBuilder s = new StringBuilder(args[0]);
                    for (int i = 1; i < args.length; i++) {
                        s.append(" ").append(args[i]);
                    }
                    if (PartyManager.getManager().hasParty(player)) {
                        Party party = PartyManager.getManager().getPlayerParty(player);
                        if (!party.isLeader(player)) {
                            String message = Utils.getMessage("Messages.Commands.Party Command.Only Leader");
                            player.sendMessage(message);
                            return true;
                        }
                    }
                    Arena arena = ArenaManager.getManager().getArenaByNameOrID(String.valueOf(s));
                    ArenaManager.getManager().handleJoin(player, arena, PlayerDataManager.getManager().getSpleefPlayer(player).isSpectating());
                }
            }
        }
        return false;
    }
}
