package io.thadow.simplespleef.utils;

import io.thadow.simplespleef.Main;
import io.thadow.simplespleef.api.arena.Status;
import io.thadow.simplespleef.api.configuration.ConfigurationFile;
import io.thadow.simplespleef.api.event.PlayerLeaveArenaEvent;
import io.thadow.simplespleef.api.player.SpleefPlayer;
import io.thadow.simplespleef.arena.Arena;
import io.thadow.simplespleef.items.ItemGiver;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
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

    public static void sendPlayerTo(Player player, String server) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(baos);
            out.writeUTF("Connect");
            out.writeUTF(server);
            player.sendPluginMessage(Main.getInstance(), "BungeeCord", baos.toByteArray());
            baos.close();
            out.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
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

    public static void teleportToLobby(SpleefPlayer player) {
        if (Main.getLobbyLocation() != null) {
            player.getPlayer().teleport(Main.getLobbyLocation());
        } else {
            if (player.getPlayer().isOp()) {
                player.sendMessage(format("&8[&6SimpleSpleef&8] &cLobby location is not set! Please set the lobby location to prevent errors!"));
            }
        }
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (player.isSpectating()) {
                if (!players.canSee(player.getPlayer())) {
                    players.showPlayer(player.getPlayer());
                }
            }
        }
        if (Main.getConfiguration().getBoolean("Configuration.Lobby.Clear Inventory")) {
            player.getPlayer().getInventory().clear();
        }
        if (Main.getConfiguration().getBoolean("Configuration.Lobby.Clear Armor Contents")) {
            player.getPlayer().getInventory().setArmorContents(null);
        }
        if (!Main.getConfiguration().getBoolean("Configuration.Lobby.Allow Flight")) {
            player.getPlayer().setAllowFlight(false);
        }
        if (!Main.getConfiguration().getBoolean("Configuration.Lobby.Set Flying")) {
            player.getPlayer().setFlying(false);
        }
        String mode = Main.getConfiguration().getString("Configuration.Lobby.GameMode");
        player.getPlayer().setGameMode(GameMode.valueOf(mode));
        player.getPlayer().setHealth(20.0D);
        player.getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);
        PlayerLeaveArenaEvent event = new PlayerLeaveArenaEvent(player.getArena(), player.getPlayer(), player);
        Bukkit.getPluginManager().callEvent(event);
        ItemGiver.getGiver().giveLobbyItems(player.getPlayer());
        player.setArena(null);
        player.setSpectating(false);
    }

    public static List<Arena> getSorted(List<Arena> arenas) {
        List<Arena> sorted = new ArrayList<>(arenas);
        sorted.sort(new Comparator<>() {
            public int compare(Arena arena_1, Arena arena_2) {
                if (arena_1.getStatus() == Status.STARTING && arena_2.getStatus() == Status.STARTING) {
                    return Integer.compare(arena_2.getPlayers().size(), arena_1.getPlayers().size());
                } else if (arena_1.getStatus() == Status.STARTING && arena_2.getStatus() != Status.STARTING) {
                    return -1;
                } else if (arena_2.getStatus() == Status.STARTING && arena_1.getStatus() != Status.STARTING) {
                    return 1;
                } else if (arena_1.getStatus() == Status.WAITING && arena_2.getStatus() == Status.WAITING) {
                    return Integer.compare(arena_2.getPlayers().size(), arena_1.getPlayers().size());
                } else if (arena_1.getStatus() == Status.WAITING && arena_2.getStatus() != Status.WAITING) {
                    return -1;
                } else if (arena_2.getStatus() == Status.WAITING && arena_1.getStatus() == Status.WAITING) {
                    return 1;
                } else if (arena_1.getStatus() == Status.PLAYING && arena_2.getStatus() == Status.PLAYING) {
                    return 0;
                } else if (arena_1.getStatus() == Status.PLAYING && arena_2.getStatus() != Status.PLAYING) {
                    return -1;
                } else return 1;
            }

            @Override
            public boolean equals(Object obj) {
                return obj instanceof Arena;
            }
        });
        return sorted;
    }

    public static class Region {
        private final Vector corner1;
        private final Vector corner2;

        public Region(Vector corner1, Vector corner2) {
            int x1 = Math.min(corner1.getBlockX(), corner2.getBlockX());
            int y1 = Math.min(corner1.getBlockY(), corner2.getBlockY());
            int z1 = Math.min(corner1.getBlockZ(), corner2.getBlockZ());
            int x2 = Math.max(corner1.getBlockX(), corner2.getBlockX());
            int y2 = Math.max(corner1.getBlockY(), corner2.getBlockY());
            int z2 = Math.max(corner1.getBlockZ(), corner2.getBlockZ());
            this.corner1 = new Vector(x1, y1, z1);
            this.corner2 = new Vector(x2, y2, z2);
        }

        public boolean isInside(Location location) {
            if (location == null) {
                return false;
            }
            return location.getBlockX() >= this.corner1.getBlockX() && location.getBlockX() <= this.corner2.getBlockX() && location.getBlockY() >= this.corner1.getBlockY() && location.getBlockY() <= this.corner2.getBlockY() && location.getBlockZ() >= this.corner1.getBlockZ() && location.getBlockZ() <= this.corner2.getBlockZ();
        }
    }
}
