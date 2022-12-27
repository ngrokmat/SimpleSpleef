package io.thadow.simplespleef.utils;

import io.thadow.simplespleef.api.configuration.ConfigurationFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class Utils {
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
