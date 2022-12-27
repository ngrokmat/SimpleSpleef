package io.thadow.simplespleef.managers;

import io.thadow.simplespleef.Main;
import io.thadow.simplespleef.api.arena.Status;
import io.thadow.simplespleef.api.player.SpleefPlayer;
import io.thadow.simplespleef.arena.Arena;
import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ArenaManager {
    @Getter
    private static final ArenaManager manager = new ArenaManager();
    @Getter
    private List<Arena> arenas = new ArrayList<>();

    public Arena getArenaByID(String id) {
        for (Arena arena : arenas) {
            if (arena.getArenaID().equals(id)) {
                return arena;
            }
        }
        return null;
    }

    public Arena getArenaByName(String name) {
        for (Arena arena : arenas) {
            if (arena.getArenaName().equals(name)) {
                return arena;
            }
        }
        return null;
    }

    public Arena getArenaByNameOrID(String s) {
        s = ChatColor.stripColor(s);
        Arena arena = getArenaByID(s);
        if (arena == null) {
            arena = getArenaByName(s);
        }
        return arena;
    }

    public Arena getPlayerArena(Player player) {
        for (Arena arena : arenas) {
            for (SpleefPlayer spleefPlayer : arena.getPlayers()) {
                if (spleefPlayer.getPlayer().getName().equals(player.getName())) {
                    return arena;
                }
            }
        }
        return null;
    }

    public boolean handleJoin(Player player, Arena arena) {
        if (arena == null) {
            player.sendMessage("La arena no existe");
            return false;
        }
        if (PlayerDataManager.getManager().getSpleefPlayer(player).getArena() != null) {
            player.sendMessage("Ya estas en una arena!");
            return false;
        }
        if (arena.getWaitLocation() == null) {
            player.sendMessage("Unable to locate the WaitLocation");
            return false;
        }
        if (arena.getStatus() == Status.DISABLED || !arena.isEnabled()) {
            player.sendMessage("La arena esta desactivada");
            return false;
        }
        if (arena.getStatus() == Status.ENDING) {
            player.sendMessage("La arena esta finalizando");
            return false;
        }
        if (arena.getStatus() == Status.RESTARTING) {
            player.sendMessage("La arena se esta reiniciando");
            return false;
        }
        if (arena.getStatus() == Status.PLAYING) {
            player.sendMessage("La arena ya a iniciado");
            return false;
        }
        if (arena.getPlayers().size() == arena.getMaxPlayers()) {
            player.sendMessage("La arena esta llena!");
            return false;
        }
        arena.addPlayer(PlayerDataManager.getManager().getSpleefPlayer(player));
        return true;
    }

    public boolean handleLeave(SpleefPlayer player, Arena arena) {
        arena.removePlayer(player);
        return true;
    }

    public void loadArenas() {
        File dir = new File(Main.getInstance().getDataFolder() + "/Arenas");
        if (dir.exists()) {
            List<File> files = new ArrayList<>();
            File[] list = dir.listFiles();
            if (list == null) {
                return;
            }
            for (File file : list) {
                if (file.isFile()) {
                    if (file.getName().endsWith(".yml")) {
                        files.add(file);
                    }
                }
            }

            for (File file : files) {
                Arena arena = new Arena(file.getName().replace(".yml", ""));
                arenas.add(arena);
            }
        }
    }

    public boolean createArena(String id) {
        File file = new File(Main.getInstance().getDataFolder() + "/Arenas/" + id + ".yml");
        if (!file.exists()) {
            Arena arena = new Arena(id);
            arenas.add(arena);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteArena(String id) {
        File arenaFile = new File(Main.getInstance().getDataFolder() + "/Arena/" + id + ".yml");
        if (!arenaFile.exists()) {
            return false;
        }

        Arena arena = getArenaByID(id);
        if (arena.isEnabled()) {
            return false;
        }
        arenas.remove(arena);
        FileUtils.deleteQuietly(arenaFile);
        return true;
    }
}