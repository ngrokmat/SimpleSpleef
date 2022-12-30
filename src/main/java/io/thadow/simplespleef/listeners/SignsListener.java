package io.thadow.simplespleef.listeners;

import io.thadow.simplespleef.Main;
import io.thadow.simplespleef.arena.Arena;
import io.thadow.simplespleef.managers.ArenaManager;
import io.thadow.simplespleef.managers.SignsManager;
import io.thadow.simplespleef.utils.Utils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SignsListener implements Listener {

    @EventHandler
    public void onPlayerBreakSignEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("simplespleef.signs.break")) {
            Block block = event.getBlock();
            Location location = block.getLocation();
            if (location.getBlock().getType().toString().endsWith("_SIGN") || location.getBlock().getType().toString().endsWith("_WALL_SIGN")) {
                for (Arena arena : ArenaManager.getManager().getArenas()) {
                    if (arena.getSigns().contains(block)) {
                        arena.getSigns().remove(block);
                        Sign sign = (Sign) block.getState();
                        
                        Arena sArena = null;
                        for (String loc : Main.getSignsConfiguration().getStringList("Locations")) {
                            String[] locSplt = loc.split(";");
                            String[] myString = locationToString(sign.getLocation()).split(";");
                            if (locSplt[1].equals(myString[0])) {
                                if (locSplt[2].equals(myString[1])) {
                                    if (locSplt[3].equals(myString[2])) {
                                        if (locSplt[4].equals(myString[3])) {
                                            if (locSplt[5].equals(myString[4])) {
                                                if (locSplt[6].equals(myString[5])) {
                                                    sArena = ArenaManager.getManager().getArenaByID(locSplt[0]);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        
                        if (sArena == null) {
                            return;
                        }

                        List<String> locations = Main.getSignsConfiguration().getStringList("Locations");
                        if (locations.contains(arena.getArenaID() + ";" + locationToString(block.getLocation()))) {
                            locations.remove(arena.getArenaID() + ";" + locationToString(block.getLocation()));
                            Main.getSignsConfiguration().set("Locations", locations);
                            Main.getSignsConfiguration().save();
                            String message = Main.getConfiguration().getString("Messages.Signs.Sign Removed");
                            message = message.replace("%arenaID%", sArena.getArenaID());
                            message = Utils.format(message);
                            player.sendMessage(message);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void checkSign(SignChangeEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("simplespleef.signs.place")) {
            for (Arena arena : ArenaManager.getManager().getArenas()) {
                for (Block block : arena.getSigns()) {
                    for (String locations : Main.getSignsConfiguration().getStringList("Locations")) {
                        String[] locationsSplit = locations.split(";");
                        Arena update = ArenaManager.getManager().getArenaByID(locationsSplit[0]);
                        if (update == null) {
                            update = ArenaManager.getManager().getArenaByName(locationsSplit[0]);
                        }
                        if (areSameLocation(event.getBlock().getLocation(), block.getLocation())) {
                            Sign sign = (Sign) event.getBlock().getState();
                            update.refreshSigns();
                            SignsManager.getManager().updateSigns(arena);
                            int line = 0;
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
                            for (String string : Main.getSignsConfiguration().getStringList("Format")) {
                                string = string.replace("[arena]", arena.getArenaName());
                                string = string.replace("[current]", String.valueOf(arena.getTotalPlayersSize()));
                                string = string.replace("[max]", String.valueOf(arena.getMaxPlayers()));
                                string = string.replace("[status]", status);
                                event.setLine(line, Utils.format(string));
                                line++;
                            }
                            sign.update(true);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        if (event == null) {
            return;
        }
        Player player = event.getPlayer();
        if (player.hasPermission("simplespleef.signs.place")) {
            if (event.getLine(0).equalsIgnoreCase("[simplespleef]")) {
                File dir = new File(Main.getInstance().getDataFolder(), "/Arenas");
                boolean exits = false;
                if (dir.exists()) {
                    for (File file : Objects.requireNonNull(dir.listFiles())) {
                        if (file.isFile()) {
                            if (file.getName().contains(".yml")) {
                                if (Objects.equals(event.getLine(1), file.getName().replace(".yml", ""))) {
                                    exits = true;
                                }
                                if (Objects.equals(event.getLine(2), file.getName().replace(".yml", ""))) {
                                    exits = true;
                                }
                                if (Objects.equals(event.getLine(3), file.getName().replace(".yml", ""))) {
                                    exits = true;
                                }
                            }
                        }
                    }
                    boolean saved = false;
                    List<String> locations;
                    if (Main.getSignsConfiguration().getConfiguration().get("Locations") == null) {
                        locations = new ArrayList<>();
                    } else {
                        locations = new ArrayList<>(Main.getSignsConfiguration().getStringList("Locations"));
                    }
                    if (exits && !locations.contains(event.getLine(1) + ";" + locationToString(event.getBlock().getLocation()))) {
                        locations.add(event.getLine(1) + ";" + locationToString(event.getBlock().getLocation()));
                        Main.getSignsConfiguration().set("Locations", locations);
                        Main.getSignsConfiguration().save();
                        saved = true;
                    }
                    if (!saved && exits && !locations.contains(event.getLine(2) + ";" + locationToString(event.getBlock().getLocation()))) {
                        locations.add(event.getLine(2) + ";" + locationToString(event.getBlock().getLocation()));
                        Main.getSignsConfiguration().set("Locations", locations);
                        Main.getSignsConfiguration().save();
                        saved = true;
                    }
                    if (!saved && exits && !locations.contains(event.getLine(3) + ";" + locationToString(event.getBlock().getLocation()))) {
                        locations.add(event.getLine(3) + ";" + locationToString(event.getBlock().getLocation()));
                        Main.getSignsConfiguration().set("Locations", locations);
                        Main.getSignsConfiguration().save();
                        saved = true;
                    }
                    Arena arena = ArenaManager.getManager().getArenaByID(event.getLine(1));
                    if (arena == null) {
                        arena = ArenaManager.getManager().getArenaByID(event.getLine(2));
                    }
                    if (arena == null) {
                        arena = ArenaManager.getManager().getArenaByID(event.getLine(3));
                    }
                    if (arena != null) {
                        String message = Main.getConfiguration().getString("Messages.Signs.Sign Added");
                        message = message.replace("%arenaID%", arena.getArenaID());
                        message = Utils.format(message);
                        player.sendMessage(message);
                        arena.addSign(event.getBlock().getLocation());

                        Sign sign = (Sign) event.getBlock().getState();
                        int line = 0;
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
                        for (String string : Main.getSignsConfiguration().getStringList("Format")) {
                            string = string.replace("[arena]", arena.getArenaName());
                            string = string.replace("[current]", String.valueOf(arena.getTotalPlayersSize()));
                            string = string.replace("[max]", String.valueOf(arena.getMaxPlayers()));
                            string = string.replace("[status]", status);
                            event.setLine(line, Utils.format(string));
                            line++;
                        }
                        sign.update(true);
                        SignsManager.getManager().updateSigns(arena);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onSignJoinInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();
            if (block.getState() instanceof Sign) {
                Sign sign = (Sign) block.getState();
                String toJoin = sign.getLine(0);
                Arena arenaToJoin = ArenaManager.getManager().getArenaByNameOrID(toJoin);
                if (arenaToJoin == null) {
                    toJoin = sign.getLine(1);
                    arenaToJoin = ArenaManager.getManager().getArenaByNameOrID(toJoin);
                }
                if (arenaToJoin == null) {
                    toJoin = sign.getLine(2);
                    arenaToJoin = ArenaManager.getManager().getArenaByNameOrID(toJoin);
                }
                if (arenaToJoin == null) {
                    toJoin = sign.getLine(3);
                    arenaToJoin = ArenaManager.getManager().getArenaByNameOrID(toJoin);
                }
                ArenaManager.getManager().handleJoin(event.getPlayer(), arenaToJoin, false);
            }
        }
    }

    private String locationToString(Location location) {
        return location.getX() + ";" + location.getY() + ";" + location.getZ() + ";" + (double) location.getYaw() + ";" + (double) location.getPitch() + ";" + location.getWorld().getName();
    }

    private boolean areSameLocation(Location location1, Location location2) {
        if (location1.getX() == location2.getX()) {
            if (location2.getY() == location2.getY()) {
                if (location1.getZ() == location2.getZ()) {
                    if (location1.getYaw() == location2.getYaw()) {
                        if (location1.getPitch() == location2.getPitch()) {
                            return location1.getWorld() == location2.getWorld();
                        }
                    }
                }
            }
        }
        return false;
    }
}
