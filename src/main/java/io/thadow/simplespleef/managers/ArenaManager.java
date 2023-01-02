package io.thadow.simplespleef.managers;

import io.thadow.simplespleef.Main;
import io.thadow.simplespleef.api.arena.Status;
import io.thadow.simplespleef.api.party.Party;
import io.thadow.simplespleef.api.player.SpleefPlayer;
import io.thadow.simplespleef.arena.Arena;
import io.thadow.simplespleef.utils.Utils;
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
    private final List<Arena> arenas = new ArrayList<>();

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

    public boolean joinRandom(Player player, boolean spectating) {
        List<Arena> arenas = Utils.getSorted(getAvailableArenas());

        if (arenas.isEmpty()) {
            return false;
        }

        if (spectating) {
            Arena arena = PlayerDataManager.getManager().getSpleefPlayer(player).getArena();
            if (arena == null) {
                return false;
            }
            arena.remove(PlayerDataManager.getManager().getSpleefPlayer(player));
        }

        for (Arena arena : arenas) {
            if (arena.getStatus() == Status.STARTING) {
                if (arena.getPlayers().size() != arena.getMaxPlayers()) {
                    return handleJoin(player, arena, spectating);
                }
            } else if (arena.getStatus() == Status.WAITING) {
                return handleJoin(player, arena, spectating);
            }
        }
        return false;
    }

    public boolean handleJoin(Player player, Arena arena, boolean spectating) {
        if (arena == null) {
            player.sendMessage("La arena no existe");
            return false;
        }
        if (PlayerDataManager.getManager().getSpleefPlayer(player).getArena() != null && !spectating) {
            player.sendMessage("Ya estas en una arena!");
            return false;
        }
        if (PartyManager.getManager().hasParty(player)) {
            Party party = PartyManager.getManager().getPlayerParty(player);
            if (!party.isLeader(player)) {
                String message = Utils.getMessage("Messages.Commands.Party Command.Only Leader");
                player.sendMessage(message);
                return false;
            }
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
        if (PartyManager.getManager().hasParty(player)) {
            Party party = PartyManager.getManager().getPlayerParty(player);
            int size = party.getSize();
            int arenaPlayerSize = arena.getTotalPlayersSize();
            if ((arenaPlayerSize + size) > arena.getMaxPlayers()) {
                String message = Utils.getMessage("Messages.Arenas.Not Enough Space");
                player.sendMessage(message);
                return false;
            } else {
                boolean anyInArena = false;
                for (Player member : party.getMembers()) {
                    if (PlayerDataManager.getManager().getSpleefPlayer(member).getArena() != null) {
                        if (PlayerDataManager.getManager().getSpleefPlayer(member).getArena().getStatus() == Status.PLAYING) {
                            anyInArena = true;
                        }
                    }
                }
                if (anyInArena) {
                    String message = Utils.getMessage("Messages.Arenas.Party Member In Game");
                    player.sendMessage(message);
                    return false;
                }
                for (Player member : party.getMembers()) {
                    arena.addPlayer(PlayerDataManager.getManager().getSpleefPlayer(member));
                }
            }
            return true;
        } else {
            arena.addPlayer(PlayerDataManager.getManager().getSpleefPlayer(player));
        }
        return true;
    }

    public void handleLeave(SpleefPlayer player, Arena arena, boolean silent) {
        arena.removePlayer(player, silent);
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
        File arenaFile = new File(Main.getInstance().getDataFolder() + "/Arenas/" + id + ".yml");
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

    public List<Arena> getAvailableArenas() {
        List<Arena> arenas = new ArrayList<>();
        for (Arena arena : getArenas()) {
            if (arena.getStatus() == Status.STARTING || arena.getStatus() == Status.WAITING) {
                arenas.add(arena);
            }
        }
        return arenas;
    }
}
