package io.thadow.simplespleef.commands;

import io.thadow.simplespleef.arena.Arena;
import io.thadow.simplespleef.managers.PlayerDataManager;
import io.thadow.simplespleef.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuildCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("simplespleef.commands.build")) {
                Arena arena = PlayerDataManager.getManager().getSpleefPlayer(player).getArena();
                if (arena != null) {
                    player.sendMessage("Este comando no puede ser usado dentro de una arena!");
                    return true;
                }
                if (Utils.getBuilders().contains(player)) {
                    Utils.getBuilders().remove(player);
                    player.sendMessage("Modo construcción desactivado!");
                } else {
                    Utils.getBuilders().add(player);
                    player.sendMessage("Modo construcción activado!");
                }
            }
        }
        return false;
    }
}
