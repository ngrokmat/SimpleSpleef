package io.thadow.simplespleef.commands;

import io.thadow.simplespleef.Main;
import io.thadow.simplespleef.api.arena.Status;
import io.thadow.simplespleef.arena.Arena;
import io.thadow.simplespleef.managers.ArenaManager;
import io.thadow.simplespleef.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class MainCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("simplespleef.commands.admin")) {
                if (args.length == 0) {
                    List<String> usageMessage = Main.getConfiguration().getStringList("Configuration.Messages.Commands.Main Command.Usage");
                    usageMessage = Utils.format(usageMessage);
                    for (String line : usageMessage) {
                        player.sendMessage(line);
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("setLobby")) {
                    Main.setLobbyLocation(player.getLocation());
                    Main.getConfiguration().set("Configuration.Lobby.Location", Utils.getStringFromLocation(player.getLocation()));
                    Main.getConfiguration().save();
                    player.sendMessage("Lobby location set");
                    return true;
                } else if (args[0].equalsIgnoreCase("forceStart")) {
                    Arena arena = ArenaManager.getManager().getPlayerArena(player);
                    if (arena == null) {
                        player.sendMessage("Solo arena");
                        return true;
                    }
                    if (arena.getStatus() == Status.STARTING) {
                        player.sendMessage("La arena ya esta iniciando!");
                        return true;
                    }
                    arena.initCountdown(true);
                    player.sendMessage("El inicio a sido forzado.");
                } else if (args[0].equalsIgnoreCase("createArena") && args.length == 2) {
                    player.sendMessage(ArenaManager.getManager().createArena(args[1]) ? "Arena creada" : "La arena ya existe");
                    return true;
                } else if (args[0].equalsIgnoreCase("deleteArena") && args.length == 2) {
                    player.sendMessage(ArenaManager.getManager().deleteArena(args[1]) ? "Arena eliminada" : "La arena esta activada o no existe!");
                    return true;
                } else if (args[0].equalsIgnoreCase("enableArena") && args.length == 2) {
                    Arena arena = ArenaManager.getManager().getArenaByID(args[1]);
                    if (arena == null) {
                        player.sendMessage("La arena no existe");
                        return true;
                    }
                    if (arena.isEnabled()) {
                        player.sendMessage("La arena esta activada!");
                        return true;
                    }
                    arena.setEnabled(true);
                    player.sendMessage("La arena ha sido activada.");
                    return true;
                } else if (args[0].equalsIgnoreCase("disableArena") && args.length == 2) {
                    Arena arena = ArenaManager.getManager().getArenaByID(args[1]);
                    if (arena == null) {
                        player.sendMessage("La arena no existe");
                        return true;
                    }
                    if (arena.getStatus() == Status.PLAYING) {
                        player.sendMessage("No puedes desactivar la arena mientras esta en juego!");
                        return true;
                    }
                    arena.setEnabled(false);
                    player.sendMessage("La arena ha sido desactivada");
                    return true;
                } else if (args[0].equalsIgnoreCase("setArenaName") && args.length >= 3) {
                    Arena arena = ArenaManager.getManager().getArenaByID(args[1]);
                    if (arena == null) {
                        player.sendMessage("La arena no existe");
                        return true;
                    }
                    if (arena.isEnabled()) {
                        player.sendMessage("La arena esta activada!");
                        return true;
                    }
                    StringBuilder name = new StringBuilder(args[2]);
                    for (int i = 3; i < args.length; i++) {
                        name.append(" ").append(args[i]);
                    }
                    arena.setArenaName(String.valueOf(name));
                    player.sendMessage("Nombre de la arena cambiado.");
                } else if (args[0].equalsIgnoreCase("setMaxPlayers") && args.length == 3) {
                    Arena arena = ArenaManager.getManager().getArenaByID(args[1]);
                    if (arena == null) {
                        player.sendMessage("La arena no existe");
                        return true;
                    }
                    if (arena.isEnabled()) {
                        player.sendMessage("La arena esta activada!");
                        return true;
                    }
                    arena.setMaxPlayers(Integer.parseInt(args[2]));
                    player.sendMessage("Cantidad maxima de jugadores cambiada");
                    return true;
                } else if (args[0].equalsIgnoreCase("setMinPlayers") && args.length == 3) {
                    Arena arena = ArenaManager.getManager().getArenaByID(args[1]);
                    if (arena == null) {
                        player.sendMessage("La arena no existe");
                        return true;
                    }
                    if (arena.isEnabled()) {
                        player.sendMessage("La arena esta activada!");
                        return true;
                    }
                    arena.setMinPlayers(Integer.parseInt(args[2]));
                    player.sendMessage("Cantidad minima de jugadores cambiada");
                    return true;
                } else if (args[0].equalsIgnoreCase("setMaxTime") && args.length == 3) {
                    Arena arena = ArenaManager.getManager().getArenaByID(args[1]);
                    if (arena == null) {
                        player.sendMessage("La arena no existe");
                        return true;
                    }
                    if (arena.isEnabled()) {
                        player.sendMessage("La arena esta activada!");
                        return true;
                    }
                    arena.setMaxTime(Integer.parseInt(args[2]), true);
                    player.sendMessage("Tiempo maximo cambiado");
                    return true;
                } else if (args[0].equalsIgnoreCase("setWaitLocation") && args.length == 2) {
                    Arena arena = ArenaManager.getManager().getArenaByID(args[1]);
                    if (arena == null) {
                        player.sendMessage("La arena no existe");
                        return true;
                    }
                    if (arena.isEnabled()) {
                        player.sendMessage("La arena esta activada!");
                        return true;
                    }
                    arena.setWaitLocation(player.getLocation());
                    player.sendMessage("WaitLocation cambiada");
                    return true;
                } else if (args[0].equalsIgnoreCase("setSpawnLocation") && args.length == 2) {
                    Arena arena = ArenaManager.getManager().getArenaByID(args[1]);
                    if (arena == null) {
                        player.sendMessage("La arena no existe");
                        return true;
                    }
                    if (arena.isEnabled()) {
                        player.sendMessage("La arena esta activada!");
                        return true;
                    }
                    arena.setSpawnLocation(player.getLocation());
                    player.sendMessage("SpawnLocation cambiada");
                    return true;
                } else if (args[0].equalsIgnoreCase("setCorner1") && args.length == 2) {
                    Arena arena = ArenaManager.getManager().getArenaByID(args[1]);
                    if (arena == null) {
                        player.sendMessage("La arena no existe");
                        return true;
                    }
                    if (arena.isEnabled()) {
                        player.sendMessage("La arena esta activada!");
                        return true;
                    }
                    arena.setCorner1(player.getLocation());
                    player.sendMessage("Corner 1 cambiado");
                    return true;
                } else if (args[0].equalsIgnoreCase("setCorner2") && args.length == 2) {
                    Arena arena = ArenaManager.getManager().getArenaByID(args[1]);
                    if (arena == null) {
                        player.sendMessage("La arena no existe");
                        return true;
                    }
                    if (arena.isEnabled()) {
                        player.sendMessage("La arena esta activada!");
                        return true;
                    }
                    arena.setCorner2(player.getLocation());
                    player.sendMessage("Corner 2 cambiado");
                    return true;
                } else {
                    List<String> usageMessage = Main.getConfiguration().getStringList("Messages.Commands.Main Command.Usage");
                    usageMessage = Utils.format(usageMessage);
                    for (String line : usageMessage) {
                        player.sendMessage(line);
                    }
                }
            }
        }
        return false;
    }
}
