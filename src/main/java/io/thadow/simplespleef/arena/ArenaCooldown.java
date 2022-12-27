package io.thadow.simplespleef.arena;

import io.thadow.simplespleef.Main;
import io.thadow.simplespleef.api.arena.Status;
import io.thadow.simplespleef.api.player.SpleefPlayer;
import io.thadow.simplespleef.api.playerdata.PlayerData;
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
            arena.broadcast("Starting in: " + String.valueOf(arena.getTime()));
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
                arena.degreeTime();
                time--;
                return true;
            }
        } else {
            arena.broadcast("El inicio ha sido parado");
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
            arena.broadcast("Time: " + time);
            if (time == 0) {
                Bukkit.broadcastMessage("Finalizada 2");
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
