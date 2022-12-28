package io.thadow.simplespleef.managers;

import io.thadow.simplespleef.Main;
import io.thadow.simplespleef.api.arena.Status;
import io.thadow.simplespleef.api.player.SpleefPlayer;
import io.thadow.simplespleef.arena.Arena;
import io.thadow.simplespleef.lib.scoreboard.Scoreboard;
import io.thadow.simplespleef.utils.Utils;
import lombok.Getter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardManager {
    @Getter
    private static ScoreboardManager manager = new ScoreboardManager();

    private static void updateLobbyScoreboard(SpleefPlayer player) {
        String title = getPath("Scoreboards.Lobby.Title");
        List<String> lines = getListPath("Scoreboards.Lobby.Lines");
        List<String> newLines = new ArrayList<>();
        Scoreboard scoreboard = Scoreboard.scoreboards.get(player.getUniqueId());
        if (scoreboard == null) {
            scoreboard = new Scoreboard(player.getPlayer());
            Scoreboard.scoreboards.put(player.getUniqueId(), scoreboard);
        }
        scoreboard.updateTitle(Utils.format(title));
        for (String line : lines) {
            line = line.replace( "%player_name%", player.getName());
            line = line.replace("%wins%", String.valueOf(player.getPlayerData().getWins()));
            line = line.replace("%losses%", String.valueOf(player.getPlayerData().getLosses()));
            line = PlaceholderAPI.setPlaceholders(player.getPlayer(), line);
            line = Utils.format(line);
            newLines.add(line);
        }
        scoreboard.updateLines(newLines);
    }

    private static void updateWaitingScoreboard(SpleefPlayer player) {
        Arena arena = player.getArena();
        String title = getPath("Scoreboards.Waiting.Title");
        List<String> lines = getListPath("Scoreboards.Waiting.Lines");
        List<String> newLines = new ArrayList<>();
        Scoreboard scoreboard = Scoreboard.scoreboards.get(player.getUniqueId());
        if (scoreboard == null) {
            scoreboard = new Scoreboard(player.getPlayer());
            Scoreboard.scoreboards.put(player.getUniqueId(), scoreboard);
        }
        scoreboard.updateTitle(Utils.format(title));
        for (String line : lines) {
            line = line.replace("%player_name%", player.getName());
            line = line.replace("%wins%", String.valueOf(player.getPlayerData().getWins()));
            line = line.replace("%losses%", String.valueOf(player.getPlayerData().getLosses()));
            line = line.replace("%arenaName%", arena.getArenaName());
            line = PlaceholderAPI.setPlaceholders(player.getPlayer(), line);
            line = Utils.format(line);
            newLines.add(line);
        }
        scoreboard.updateLines(newLines);
    }

    private static void updateStartingScoreboard(SpleefPlayer player) {
        Arena arena = player.getArena();
        String title = getPath("Scoreboards.Starting.Title");
        List<String> lines = getListPath("Scoreboards.Starting.Lines");
        List<String> newLines = new ArrayList<>();
        Scoreboard scoreboard = Scoreboard.scoreboards.get(player.getUniqueId());
        if (scoreboard == null) {
            scoreboard = new Scoreboard(player.getPlayer());
            Scoreboard.scoreboards.put(player.getUniqueId(), scoreboard);
        }
        scoreboard.updateTitle(Utils.format(title));
        for (String line : lines) {
            line = line.replace("%player_name%", player.getName());
            line = line.replace("%wins%", String.valueOf(player.getPlayerData().getWins()));
            line = line.replace("%losses%", String.valueOf(player.getPlayerData().getLosses()));
            line = line.replace("%arenaName%", arena.getArenaName());
            line = line.replace("%seconds%", String.valueOf(arena.getTime()));
            line = PlaceholderAPI.setPlaceholders(player.getPlayer(), line);
            line = Utils.format(line);
            newLines.add(line);
        }
        scoreboard.updateLines(newLines);
    }

    private static void updatePlayingScoreboard(SpleefPlayer player) {
        Arena arena = player.getArena();
        String title = getPath("Scoreboards.Playing.Title");
        List<String> lines = getListPath("Scoreboards.Playing.Lines");
        List<String> newLines = new ArrayList<>();
        Scoreboard scoreboard = Scoreboard.scoreboards.get(player.getUniqueId());
        if (scoreboard == null) {
            scoreboard = new Scoreboard(player.getPlayer());
            Scoreboard.scoreboards.put(player.getUniqueId(), scoreboard);
        }
        scoreboard.updateTitle(Utils.format(title));
        for (String line : lines) {
            line = line.replace("%player_name%", player.getName());
            line = line.replace("%wins%", String.valueOf(player.getPlayerData().getWins()));
            line = line.replace("%losses%", String.valueOf(player.getPlayerData().getLosses()));
            line = line.replace("%arenaName%", arena.getArenaName());
            line = line.replace("%time%", Utils.getFormattedTime(arena.getMaxTime()));
            line = PlaceholderAPI.setPlaceholders(player.getPlayer(), line);
            line = Utils.format(line);
            newLines.add(line);
        }
        scoreboard.updateLines(newLines);
    }

    private static void updateEndingScoreboard(SpleefPlayer player) {
        Arena arena = player.getArena();
        String title = getPath("Scoreboards.Ending.Title");
        List<String> lines = getListPath("Scoreboards.Ending.Lines");
        List<String> newLines = new ArrayList<>();
        Scoreboard scoreboard = Scoreboard.scoreboards.get(player.getUniqueId());
        if (scoreboard == null) {
            scoreboard = new Scoreboard(player.getPlayer());
            Scoreboard.scoreboards.put(player.getUniqueId(), scoreboard);
        }
        scoreboard.updateTitle(Utils.format(title));
        for (String line : lines) {
            line = line.replace("%player_name%", player.getName());
            line = line.replace("%wins%", String.valueOf(player.getPlayerData().getWins()));
            line = line.replace("%losses%", String.valueOf(player.getPlayerData().getLosses()));
            line = line.replace("%arenaName%", arena.getArenaName());
            line = arena.getWinner() != null ? line.replace("%winner%", arena.getWinner().getName()) : line.replace("%winner%", Main.getConfiguration().getString("Configuration.Arenas.Nobody"));
            line = PlaceholderAPI.setPlaceholders(player.getPlayer(), line);
            line = Utils.format(line);
            newLines.add(line);
        }
        scoreboard.updateLines(newLines);
    }

    public void run() {
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        int lobbyUpdate = getInt("Scoreboards.Lobby.Update");
        int waitingUpdate = getInt("Scoreboards.Waiting.Update");
        int startingUpdate = getInt("Scoreboards.Starting.Update");
        int playingUpdate = getInt("Scoreboards.Playing.Update");
        int endingUpdate = getInt("Scoreboards.Ending.Update");

        if (getBoolean("Scoreboards.Lobby.Enabled")) {
            scheduler.scheduleSyncRepeatingTask(Main.getInstance(), () -> {
                for (SpleefPlayer player : PlayerDataManager.getManager().getSpleefPlayers().values()) {
                    if (player.getArena() == null) {
                        updateLobbyScoreboard(player);
                    }
                }
            },0, lobbyUpdate);
        }

        if (getBoolean("Scoreboards.Waiting.Enabled")) {
            scheduler.scheduleSyncRepeatingTask(Main.getInstance(), () -> {
                for (SpleefPlayer player : PlayerDataManager.getManager().getSpleefPlayers().values()) {
                    if (player.getArena() == null)
                        return;
                    if (player.getArena().getStatus() == Status.WAITING) {
                        updateWaitingScoreboard(player);
                    }
                }
            }, 0, waitingUpdate);
        }

        if (getBoolean("Scoreboards.Starting.Enabled")) {
            scheduler.scheduleSyncRepeatingTask(Main.getInstance(), () -> {
                for (SpleefPlayer player : PlayerDataManager.getManager().getSpleefPlayers().values()) {
                    if (player.getArena() == null)
                        return;
                    if (player.getArena().getStatus() == Status.STARTING) {
                        updateStartingScoreboard(player);
                    }
                }
            }, 0, startingUpdate);
        }

        if (getBoolean("Scoreboards.Playing.Enabled")) {
            scheduler.scheduleSyncRepeatingTask(Main.getInstance(), () -> {
                for (SpleefPlayer player : PlayerDataManager.getManager().getSpleefPlayers().values()) {
                    if (player.getArena() == null)
                        return;
                    if (player.getArena().getStatus() == Status.PLAYING) {
                        updatePlayingScoreboard(player);
                    }
                }
            }, 0, playingUpdate);
        }

        if (getBoolean("Scoreboards.Ending.Enabled")) {
            scheduler.scheduleSyncRepeatingTask(Main.getInstance(), () -> {
                for (SpleefPlayer player : PlayerDataManager.getManager().getSpleefPlayers().values()) {
                    if (player.getArena() == null) {
                        return;
                    }
                    if (player.getArena().getStatus() == Status.ENDING) {
                        updateEndingScoreboard(player);
                    }
                }
            }, 0, endingUpdate);
        }

        scheduler.scheduleSyncRepeatingTask(Main.getInstance(), () -> {
            for (SpleefPlayer player : PlayerDataManager.getManager().getSpleefPlayers().values()) {
                Scoreboard scoreboard = Scoreboard.scoreboards.get(player.getUniqueId());
                Arena arena = player.getArena();
                if (arena == null && !getBoolean("Scoreboards.Lobby.Enabled")) {
                    Scoreboard.scoreboards.remove(player.getUniqueId());
                    scoreboard.delete();
                    return;
                }
                if (scoreboard != null) {
                    if (arena != null && arena.getStatus() == Status.WAITING && !getBoolean("Scoreboards.Waiting.Enabled")) {
                        Scoreboard.scoreboards.remove(player.getUniqueId());
                        scoreboard.delete();
                        return;
                    }
                    if (arena != null && arena.getStatus() == Status.STARTING && !getBoolean("Scoreboards.Starting.Enabled")) {
                        Scoreboard.scoreboards.remove(player.getUniqueId());
                        scoreboard.delete();
                        return;
                    }
                    if (arena != null && arena.getStatus() == Status.PLAYING && !getBoolean("Scoreboards.Playing.Enabled")) {
                        Scoreboard.scoreboards.remove(player.getUniqueId());
                        scoreboard.delete();
                        return;
                    }
                    if (arena != null && arena.getStatus() == Status.ENDING && !getBoolean("Scoreboards.Ending.Enabled")) {
                        Scoreboard.scoreboards.remove(player.getUniqueId());
                        scoreboard.delete();
                    }
                }
            }
        }, 20L, 20L);
    }

    private static String getPath(String path) {
        return Main.getScoreboardsConfiguration().getString(path);
    }

    private static List<String> getListPath(String path) {
        return Main.getScoreboardsConfiguration().getStringList(path);
    }

    private static Integer getInt(String path) {
        return Main.getScoreboardsConfiguration().getInt(path);
    }

    private static boolean getBoolean(String path) {
        return Main.getScoreboardsConfiguration().getBoolean(path);
    }
}
