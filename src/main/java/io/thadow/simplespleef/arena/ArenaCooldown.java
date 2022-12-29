package io.thadow.simplespleef.arena;

import io.thadow.simplespleef.Main;
import io.thadow.simplespleef.api.arena.Status;
import io.thadow.simplespleef.api.player.SpleefPlayer;
import io.thadow.simplespleef.api.playerdata.PlayerData;
import io.thadow.simplespleef.lib.titles.Titles;
import io.thadow.simplespleef.utils.Utils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

public class ArenaCooldown {
    private int taskID;
    private int time;
    private Arena arena;

    public void start(Arena arena) {
        this.arena = arena;
        this.time = arena.getTime();
        if (arena.getStatus() != Status.STARTING) {
            String message = Utils.getMessage("Messages.Arenas.Starting.Starting In");
            message = message.replace("%time%", String.valueOf(time));
            arena.broadcast(message);
        }

        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        taskID = scheduler.scheduleSyncRepeatingTask(Main.getInstance(), () -> {
            if (!executeStart()) {
                cancel(taskID);
            }
        }, 0L, 20L);
    }


    protected boolean executeStart() {
        if (arena != null && arena.getStatus() == Status.STARTING) {
            if (time <= 0) {
                arena.start();
                return false;
            } else {
                if (Main.getConfiguration().contains("Messages.Arenas.Starting.Announce.Second " + time + ".Message")) {
                    String message = Utils.getMessage("Messages.Arenas.Starting.Announce.Second " + time + ".Message");
                    message = message.replace("%time%", String.valueOf(time));
                    arena.broadcast(message);
                }
                if (Main.getConfiguration().getBoolean("Messages.Arenas.Starting.Announce.Second " + time + ".Sound.Enabled")) {
                    String soundPath = Main.getConfiguration().getString("Messages.Arenas.Starting.Announce.Second " + time + ".Sound.Sound");
                    for (SpleefPlayer player : arena.getTotalPlayers()) {
                        Utils.playSound(player.getPlayer(), soundPath);
                    }
                }
                if (Main.getConfiguration().getBoolean("Messages.Arenas.Starting.Announce.Second " + time + ".Titles.Enabled")) {
                    String title = Main.getConfiguration().getString("Messages.Arenas.Starting.Announce.Second " + time + ".Titles.Title");
                    String subTitle = Main.getConfiguration().getString("Messages.Arenas.Starting.Announce.Second " + time + ".Titles.Sub Title");
                    int fadeIn = Main.getConfiguration().getInt("Messages.Arenas.Starting.Titles Settings.Fade In");
                    int stay = Main.getConfiguration().getInt("Messages.Arenas.Starting.Titles Settings.Stay");
                    int fadeOut = Main.getConfiguration().getInt("Messages.Arenas.Starting.Titles Settings.Fade Out");
                    for (SpleefPlayer player : arena.getTotalPlayers()) {
                        Titles.sendTitle(player.getPlayer(), fadeIn, stay, fadeOut, title, subTitle);
                    }
                }
                arena.degreeTime();
                time--;
                return true;
            }
        } else {
            String message = Utils.getMessage("Messages.Arenas.Starting.Countdown Stopped");
            arena.broadcast(message);
            arena.setStatus(Status.WAITING);
            arena.setTime(arena.getConfiguration().getInt("Wait To Start Time"), false);
            return false;
        }
    }

    public void startGameTime(Arena arena) {
        this.arena = arena;
        this.time = arena.getMaxTime();
        arena.setMaxTime(time, false);

        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        taskID = scheduler.scheduleSyncRepeatingTask(Main.getInstance(), () -> {
            if (time == 0) {
                arena.end(false);
                cancel(taskID);
                return;
            }
            if (!executeGameTime()) {
                cancel(taskID);
            }
        }, 0L, 20L);
    }

    public boolean executeGameTime() {
        if (arena != null && arena.getStatus() == Status.PLAYING) {
            arena.degreeMaxTime();
            if (time == 0) {
                arena.end(false);
                return false;
            } else {
                time--;
                return true;
            }
        } else {
            return false;
        }
    }

    private void cancel(int taskID) {
        Bukkit.getScheduler().cancelTask(taskID);
    }
}
