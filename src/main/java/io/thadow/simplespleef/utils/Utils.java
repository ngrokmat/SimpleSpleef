package io.thadow.simplespleef.utils;

import io.thadow.simplespleef.Main;
import io.thadow.simplespleef.api.configuration.ConfigurationFile;
import io.thadow.simplespleef.arena.Arena;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    @Getter
    private static final List<Player> builders = new ArrayList<>();

    public static String format(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static List<String> format(List<String> ls) {
        List<String> newLs = new ArrayList<>();
        for (String line : ls) {
            line = format(line);
            newLs.add(line);
        }
        return newLs;
    }

    public static String getMessage(String path) {
        String message = Main.getConfiguration().getString(path);
        return format(message);
    }

    public static void playSound(Player player, String path) {
        String[] split = path.split(":");
        try {
            Sound sound = Sound.valueOf(split[0]);
            int volume = Integer.parseInt(split[1]);
            float pitch = Float.parseFloat(split[2]);
            player.playSound(player.getLocation(), sound, volume, pitch);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static boolean isPlayer(String name) {
        Player target = Bukkit.getPlayer(name);
        if (target == null) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getName().equalsIgnoreCase(name)) {
                    return true;
                }
            }
        }
        return true;
    }

    public static String getFormattedTime(int time) {
        int minutes = time / 60;
        int seconds = time - (minutes * 60);
        String seconds_string;
        String minutes_string;
        if (seconds >= 0 && seconds <= 9) {
            seconds_string = "0" + seconds;
        } else {
            seconds_string = String.valueOf(seconds);
        }
        if (minutes >= 0 && minutes <= 9) {
            minutes_string = "0" + minutes;
        } else {
            minutes_string = String.valueOf(minutes);
        }

        return minutes_string + ":" + seconds_string;
    }

    public static String getFormattedStatus(Arena arena) {
        String waiting = Main.getConfiguration().getString("Configuration.Arenas.Status.Waiting");
        String starting = Main.getConfiguration().getString("Configuration.Arenas.Status.Starting");
        String playing = Main.getConfiguration().getString("Configuration.Arenas.Status.Playing");
        String ending = Main.getConfiguration().getString("Configuration.Arenas.Status.Ending");
        String restarting = Main.getConfiguration().getString("Configuration.Arenas.Status.Restarting");
        String disabled = Main.getConfiguration().getString("Configuration.Arenas.Status.Disabled");
        String status;
        switch (arena.getStatus()) {
            case WAITING:
                status = waiting;
                break;
            case STARTING:
                status = starting;
                break;
            case PLAYING:
                status = playing;
                break;
            case ENDING:
                status = ending;
                break;
            case RESTARTING:
                status = restarting;
                break;
            case DISABLED:
                status = disabled;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + arena.getStatus());
        }
        return format(status);
    }

    public static Location getLocationFromConfig(ConfigurationFile configuration, String path) {
        if (!configuration.contains(path)) {
            return null;
        }
        String[] locationSplit = configuration.getString(path).split(";");
        World world = Bukkit.getWorld(locationSplit[0]);
        double x = Double.parseDouble(locationSplit[1]);
        double y = Double.parseDouble(locationSplit[2]);
        double z = Double.parseDouble(locationSplit[3]);
        float yaw = Float.parseFloat(locationSplit[4]);
        float pitch = Float.parseFloat(locationSplit[5]);
        return new Location(world, x, y, z, yaw, pitch);
    }

    public static String getStringFromLocation(Location location) {
        String world = location.getWorld().getName();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float yaw = location.getYaw();
        float pitch = location.getPitch();
        return world + ";" + x + ";" + y + ";" + z + ";" + yaw + ";" + pitch;
    }
}
