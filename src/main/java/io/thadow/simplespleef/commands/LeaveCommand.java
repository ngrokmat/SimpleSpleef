package io.thadow.simplespleef.commands;

import io.thadow.simplespleef.api.playerdata.PlayerData;
import io.thadow.simplespleef.arena.Arena;
import io.thadow.simplespleef.managers.ArenaManager;
import io.thadow.simplespleef.managers.PlayerDataManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaveCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("simplespleef.commands.leave")) {
                Arena playerArena = PlayerDataManager.getManager().getSpleefPlayer(player).getArena();
                if (playerArena == null) {
                    player.sendMessage("Este comando solo se puede ejecutar en arena!");
                    return true;
                }
                playerArena.removePlayer(PlayerDataManager.getManager().getSpleefPlayer(player));
            }
        }
        return false;
    }
}
