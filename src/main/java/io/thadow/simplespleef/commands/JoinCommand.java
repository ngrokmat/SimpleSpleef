package io.thadow.simplespleef.commands;

import io.thadow.simplespleef.arena.Arena;
import io.thadow.simplespleef.managers.ArenaManager;
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
                    StringBuilder s = new StringBuilder(args[0]);
                    for (int i = 1; i < args.length; i++) {
                        s.append(" ").append(args[i]);
                    }
                    Arena arena = ArenaManager.getManager().getArenaByNameOrID(String.valueOf(s));
                    ArenaManager.getManager().handleJoin(player, arena);
                }
            }
        }
        return false;
    }
}
