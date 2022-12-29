package io.thadow.simplespleef.arena;

import io.thadow.simplespleef.Main;
import io.thadow.simplespleef.api.arena.DeathMode;
import io.thadow.simplespleef.api.arena.SpleefMode;
import io.thadow.simplespleef.api.arena.Status;
import io.thadow.simplespleef.api.arena.TeleportDeathMode;
import io.thadow.simplespleef.api.player.SpleefPlayer;
import io.thadow.simplespleef.arena.configuration.ArenaConfiguration;
import io.thadow.simplespleef.items.ItemBuilder;
import io.thadow.simplespleef.lib.titles.Titles;
import io.thadow.simplespleef.managers.PlayerDataManager;
import io.thadow.simplespleef.managers.SignsManager;
import io.thadow.simplespleef.playerdata.Storage;
import io.thadow.simplespleef.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Arena {
    @Getter
    boolean enabled;
    @Getter
    String arenaID;
    @Getter
    String arenaName;
    @Getter
    int maxPlayers;
    @Getter
    int minPlayers;
    @Getter
    List<SpleefPlayer> players = new ArrayList<>();
    @Getter
    List<SpleefPlayer> spectatingPlayers = new ArrayList<>();
    @Getter
    Location waitLocation, spawnLocation;
    @Getter
    Status status;
    @Getter @Setter
    TeleportDeathMode teleportDeathMode;
    @Getter @Setter
    SpleefMode spleefMode;
    @Getter @Setter
    DeathMode deathMode;
    @Getter
    int time, maxTime, endingTime, reEnableTime;
    int fwTaskID;
    @Getter
    int yLevelMode;
    @Getter
    ArenaConfiguration configuration;
    @Getter @Setter
    Player winner = null;
    @Getter
    List<Block> signs = new ArrayList<>();
    @Getter
    HashMap<Location, Material> brokenBlocks = new HashMap<>();
    @Getter
    List<String> allowedBreakableBlocks;
    @Getter
    boolean snowSpecialEnabled, eggSpecialEnabled, bowSpecialEnabled;
    @Getter
    int snowSpecialAmount, eggSpecialAmount, arrowSpecialAmount;
    @Getter
    HashMap<Integer, ItemStack> items = new HashMap<>();

    public Arena(String arenaID) {
        this.arenaID = arenaID;
        configuration = new ArenaConfiguration(arenaID, "plugins/SimpleSpleef/Arenas");

        enabled = configuration.getBoolean("Enabled");
        deathMode = DeathMode.valueOf(configuration.getString("Death Mode.Mode"));
        yLevelMode = configuration.getInt("Death Mode.Y Level Mode.Y Level");
        teleportDeathMode = TeleportDeathMode.valueOf(configuration.getString("Teleport Death Mode.Mode"));
        spleefMode = SpleefMode.valueOf(configuration.getString("Spleef Mode.Mode"));
        snowSpecialEnabled = configuration.getBoolean("Spleef Mode.Special To Give.Snow.Enabled");
        eggSpecialEnabled = configuration.getBoolean("Spleef Mode.Special To Give.Egg.Enabled");
        bowSpecialEnabled = configuration.getBoolean("Spleef Mode.Special To Give.Bow.Enabled");
        snowSpecialAmount = configuration.getInt("Spleef Mode.Special To Give.Snow.Amount");
        eggSpecialAmount = configuration.getInt("Spleef Mode.Special To Give.Egg.Amount");
        arrowSpecialAmount = configuration.getInt("Spleef Mode.Special To Give.Bow.Arrow Amount");
        allowedBreakableBlocks = configuration.getStringList("Spleef Mode.Allowed Breakable Blocks");
        arenaName = configuration.getString("Arena Name");
        maxPlayers = configuration.getInt("Max Players");
        minPlayers = configuration.getInt("Min Players");
        maxTime = configuration.getInt("Max Time");
        endingTime = configuration.getInt("Ending Time");
        reEnableTime = configuration.getInt("Re-Enable Time");
        time = configuration.getInt("Wait To Start Time");
        waitLocation = Utils.getLocationFromConfig(configuration, "Wait Location");
        spawnLocation = Utils.getLocationFromConfig(configuration, "Spawn Location");

        for (String key : configuration.getConfigurationSection("Inventory.Items").getKeys(false)) {
            if (key.startsWith("Slot-")) {
                int slot = Integer.parseInt(key.replace("Slot-", ""));
                String material = configuration.getString("Inventory.Items." + key + ".Material");
                String name = configuration.getString("Inventory.Items." + key + ".Name");
                boolean unbreakable = configuration.getBoolean("Inventory.Items." + key + ".Unbreakable");
                int amount = configuration.getInt("Inventory.Items." + key + ".Amount");
                List<String> lore = configuration.getStringList("Inventory.Items." + key + ".Lore");
                List<String> enchantments = configuration.getStringList("Inventory.Items." + key + ".Enchantments");
                ItemBuilder item = new ItemBuilder(Material.valueOf(material), amount);
                item = item.setDisplayName(name);
                item = item.setUnbreakable(unbreakable);
                item = item.setLore(lore);
                item = item.addEnchantments(enchantments);

                items.put(slot, item.build());
            }
        }

        if (enabled) {
            setStatus(Status.WAITING);
        } else {
            setStatus(Status.DISABLED);
        }

        registerSigns();
        refreshSigns();
    }

    public void start() {
        setWinner(null);
        setStatus(Status.PLAYING);
        ArenaCooldown cooldown = new ArenaCooldown();
        cooldown.startGameTime(this);
        for (SpleefPlayer player : getTotalPlayers()) {
            player.teleport(spawnLocation);
            for (int i : items.keySet()) {
                player.getPlayer().getInventory().setItem((i - 1), items.get(i));
            }
            player.getPlayer().setGameMode(GameMode.SURVIVAL);
        }
        List<String> message = Main.getConfiguration().getStringList("Messages.Arenas.Started.Message");
        message = Utils.format(message);
        for (String line : message) {
            broadcast(line);
        }
        if (Main.getConfiguration().getBoolean("Messages.Arenas.Started.Sound.Enabled")) {
            String soundPath = Main.getConfiguration().getString("Messages.Arenas.Started.Sound.Sound");
            for (SpleefPlayer player : getTotalPlayers()) {
                Utils.playSound(player.getPlayer(), soundPath);
            }
        }
        if (Main.getConfiguration().getBoolean("Messages.Arenas.Started.Titles.Enabled")) {
            String title = Main.getConfiguration().getString("Messages.Arenas.Started.Titles.Title");
            String subTitle = Main.getConfiguration().getString("Messages.Arenas.Started.Titles.Sub Title");
            int fadeIn = Main.getConfiguration().getInt("Messages.Arenas.Started.Titles Settings.Fade In");
            int stay = Main.getConfiguration().getInt("Messages.Arenas.Started.Titles Settings.Stay");
            int fadeOut = Main.getConfiguration().getInt("Messages.Arenas.Started.Titles Settings.Fade Out");
            for (SpleefPlayer player : getTotalPlayers()) {
                Titles.sendTitle(player.getPlayer(), fadeIn, stay, fadeOut, title, subTitle);
            }
        }
    }

    public void end(boolean closingServer) {
        if (closingServer) {
            setWinner(null);
            setStatus(Status.DISABLED);
            for (SpleefPlayer player : getTotalPlayers()) {
                removePlayer(player);
            }
            for (Location brokenLocation : brokenBlocks.keySet()) {
                brokenLocation.getWorld().getBlockAt(brokenLocation).setType(brokenBlocks.get(brokenLocation));
            }
            return;
        }

        setStatus(Status.ENDING);
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            for (SpleefPlayer player : getTotalPlayers()) {
                removePlayer(player);
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (!players.canSee(player.getPlayer())) {
                        players.showPlayer(players.getPlayer());
                    }
                }
            }
            getPlayers().clear();
            getSpectatingPlayers().clear();
            getTotalPlayers().clear();
            setStatus(Status.RESTARTING);

            for (Location brokenLocation : brokenBlocks.keySet()) {
                brokenLocation.getWorld().getBlockAt(brokenLocation).setType(brokenBlocks.get(brokenLocation));
            }

            brokenBlocks.clear();

            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                setWinner(null);
                time = configuration.getInt("Wait To Start Time");
                maxTime = configuration.getInt("Max Time");
                setStatus(Status.WAITING);
            }, 20L * getReEnableTime());
        }, 20L * getEndingTime());
    }

    public void endWithWinner(Player winner) {
        setWinner(winner);
        Storage.getStorage().addWin(winner);
        setStatus(Status.ENDING);

        fwTaskID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), () -> {
            if (getStatus() == Status.ENDING && getPlayers().contains(PlayerDataManager.getManager().getSpleefPlayer(winner)) && winner != null) {
                fireworks(winner);
            } else {
                Bukkit.getScheduler().cancelTask(fwTaskID);
            }
        }, 0L, 20L);

        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            for (SpleefPlayer player : getTotalPlayers()) {
                removePlayer(player);
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (!players.canSee(player.getPlayer())) {
                        players.showPlayer(players.getPlayer());
                    }
                }
            }
            getPlayers().clear();
            getSpectatingPlayers().clear();
            getTotalPlayers().clear();
            setStatus(Status.RESTARTING);

            for (Location brokenLocation : brokenBlocks.keySet()) {
                brokenLocation.getWorld().getBlockAt(brokenLocation).setType(brokenBlocks.get(brokenLocation));
            }

            brokenBlocks.clear();

            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                setWinner(null);
                time = configuration.getInt("Wait To Start Time");
                maxTime = configuration.getInt("Max Time");
                setStatus(Status.WAITING);
            }, 20L * getReEnableTime());
        }, 20L * getEndingTime());
    }

    public void checkArena() {
        if (getStatus() != Status.STARTING) {
            if (players.size() >= minPlayers) {
                ArenaCooldown cooldown = new ArenaCooldown();
                cooldown.start(this);
                setStatus(Status.STARTING);
            }
        }
    }

    public void fireworks(Player player) {
        Firework firework = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();
        FireworkEffect.Type type = FireworkEffect.Type.BALL;
        Color color_1 = Color.AQUA;
        Color color_2 = Color.LIME;
        Color color_3 = Color.YELLOW;
        Color fade = Color.WHITE;

        FireworkEffect effect = FireworkEffect.builder().withColor(color_1, color_2, color_3).withFade(fade).with(type).build();
        fireworkMeta.addEffect(effect);
        fireworkMeta.setPower(1);
        firework.setFireworkMeta(fireworkMeta);
    }

    public void addPlayer(SpleefPlayer player) {
        players.add(player);
        player.getPlayer().teleport(waitLocation);
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (player.isSpectating()) {
                if (!players.canSee(player.getPlayer())) {
                    players.showPlayer(player.getPlayer());
                }
            }
        }
        player.getPlayer().getInventory().clear();
        player.getPlayer().getInventory().setArmorContents(null);
        player.getPlayer().setHealth(20.0D);
        player.getPlayer().setFlying(false);
        player.getPlayer().setAllowFlight(false);
        player.getPlayer().setGameMode(GameMode.ADVENTURE);
        player.setArena(this);
        player.setSpectating(false);
        Utils.getBuilders().remove(player.getPlayer());
        refreshSigns();
        SignsManager.getManager().updateSigns(this);
        checkArena();
        String message = Utils.getMessage("Messages.Arenas.Player Join");
        message = message.replace("%player%", player.getName());
        message = message.replace("%current%", String.valueOf(getTotalPlayersSize()));
        message = message.replace("%max%", String.valueOf(getMaxPlayers()));
        broadcast(message);
    }

    public void removePlayer(SpleefPlayer player) {
        players.remove(player);
        spectatingPlayers.remove(player);
        String message = Utils.getMessage("Messages.Arenas.Player Leave");
        message = message.replace("%player%", player.getName());
        broadcast(message);
        player.getPlayer().teleport(player.getPlayer().getWorld().getSpawnLocation());
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (player.isSpectating()) {
                if (!players.canSee(player.getPlayer())) {
                    players.showPlayer(player.getPlayer());
                }
            }
        }
        player.setArena(null);
        player.setSpectating(false);
        player.getPlayer().setAllowFlight(false);
        player.getPlayer().setFlying(false);
        player.getPlayer().setHealth(20.0D);
        player.getPlayer().setFoodLevel(20);
        player.getPlayer().getInventory().clear();
        player.getPlayer().getInventory().setArmorContents(null);
        player.getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);
        if (getStatus() == Status.PLAYING && getPlayers().size() == 0) {
            end(false);
            return;
        }
        if (getStatus() == Status.PLAYING && getPlayers().size() == 1) {
            endWithWinner(players.get(0).getPlayer());
            return;
        }
        if (getPlayers().size() < minPlayers && status == Status.STARTING) {
            setStatus(Status.WAITING);
            return;
        }
        if (getStatus() == Status.WAITING) {
            checkArena();
        }
        refreshSigns();
        SignsManager.getManager().updateSigns(this);
    }

    public void killPlayer(SpleefPlayer player) {
        setSpectating(player);
        PlayerDataManager.getManager().addPlayerLose(player.getPlayer());
        player.getPlayer().setAllowFlight(true);
        player.getPlayer().setFlying(true);
        player.getPlayer().setHealth(20.0D);
        player.getPlayer().setFoodLevel(20);
        player.getPlayer().getInventory().clear();
        player.getPlayer().getInventory().setArmorContents(null);
        player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
        String message = Utils.getMessage("Messages.Arenas.Player Death");
        message = message.replace("%player%", player.getName());
        broadcast(message);
        if (getPlayers().size() == 0 && status == Status.PLAYING) {
            end(false);
            return;
        }
        if (getTotalPlayers().size() == 0 && status == Status.PLAYING) {
            end(false);
            return;
        }
        if (getPlayers().size() == 1 && status == Status.PLAYING) {
            endWithWinner(getPlayers().get(0).getPlayer());
        }
    }

    public void setSpectating(SpleefPlayer player) {
        if (teleportDeathMode == TeleportDeathMode.WAIT_LOCATION) {
            player.teleport(waitLocation);
        }
        if (teleportDeathMode == TeleportDeathMode.SPAWN_LOCATION) {
            player.teleport(spawnLocation);
        }
        for (SpleefPlayer players : getPlayers()) {
            if (!players.isSpectating()) {
                players.getPlayer().hidePlayer(player.getPlayer());
            }
        }
        players.remove(player);
        spectatingPlayers.add(player);
        player.setSpectating(true);
    }

    public void addSign(Location location) {
        if (location == null) {
            return;
        }
        if (location.getBlock().getType().toString().endsWith("_SIGN") || location.getBlock().getType().toString().endsWith("_WALL_SIGN")) {
            signs.add(location.getBlock());
            refreshSigns();
            SignsManager.getManager().updateSigns(this);
        }
    }

    private void registerSigns() {
        for (String location : Main.getSignsConfiguration().getStringList("Locations")) {
            String[] locationSplit = location.split(";");
            if (locationSplit[0].equalsIgnoreCase(arenaID)) {
                double x = Double.parseDouble(locationSplit[1]);
                double y = Double.parseDouble(locationSplit[2]);
                double z = Double.parseDouble(locationSplit[3]);
                float yaw = Float.parseFloat(locationSplit[4]);
                float pitch = Float.parseFloat(locationSplit[5]);
                World world = Bukkit.getWorld(locationSplit[6]);
                Location signLocation = new Location(world, x, y, z, yaw, pitch);
                if (signLocation.getBlock().getType().toString().endsWith("_SIGN")
                    || signLocation.getBlock().getType().toString().endsWith("_WALL_SIGN")) {
                    addSign(signLocation);
                }
            }
        }
    }

    public synchronized void refreshSigns() {
        for (Block block : getSigns()) {
            if (block == null) {
                return;
            }
            if (block.getType().toString().endsWith("_SIGN") || block.getType().toString().endsWith("_WALL_SIGN")) {
                if (block.getState() instanceof Sign) {
                    Sign sign = (Sign) block.getState();
                    if (sign == null) {
                        return;
                    }
                    int line = 0;
                    String waiting = Main.getConfiguration().getString("Configuration.Arenas.Status.Waiting");
                    String starting = Main.getConfiguration().getString("Configuration.Arenas.Status.Starting");
                    String playing = Main.getConfiguration().getString("Configuration.Arenas.Status.Playing");
                    String ending = Main.getConfiguration().getString("Configuration.Arenas.Status.Ending");
                    String restarting = Main.getConfiguration().getString("Configuration.Arenas.Status.Restarting");
                    String disabled = Main.getConfiguration().getString("Configuration.Arenas.Status.Disabled");
                    String status = null;
                    switch (getStatus()) {
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
                    }
                    for (String string : Main.getSignsConfiguration().getStringList("Format")) {
                        if (string == null) {
                            return;
                        }
                        string = string.replace("[arena]", getArenaName());
                        string = string.replace("[current]", String.valueOf(getTotalPlayersSize()));
                        string = string.replace("[max]", String.valueOf(getMaxPlayers()));
                        string = string.replace("[status]", status);
                        sign.setLine(line, Utils.format(string));
                        line++;
                    }
                    sign.update(true);
                }
            }
        }
    }

    public void setEnabled(boolean enabled) {
        if (enabled) {
            setStatus(Status.WAITING);
        } else {
            getPlayers().clear();
            setStatus(Status.DISABLED);
        }
        this.enabled = enabled;
        configuration.set("Enabled", enabled);
        configuration.save();
        refreshSigns();
        SignsManager.getManager().updateSigns(this);
    }

    public void setArenaName(String name) {
        arenaName = name;
        configuration.set("Arena Name", name);
        configuration.save();
    }

    public void setMaxPlayers(int size) {
        maxPlayers = size;
        configuration.set("Max Players", size);
        configuration.save();
    }

    public void setMinPlayers(int size) {
        minPlayers = size;
        configuration.set("Min Players", size);
        configuration.save();
    }


    public void setSpawnLocation(Location location) {
        spawnLocation = location;
        configuration.set("Spawn Location", Utils.getStringFromLocation(location));
        configuration.save();
    }

    public void setWaitLocation(Location location) {
        waitLocation = location;
        configuration.set("Wait Location", Utils.getStringFromLocation(location));
        configuration.save();
    }

    public void setStatus(Status status) {
        this.status = status;
        refreshSigns();
        SignsManager.getManager().updateSigns(this);
    }

    public void degreeTime() {
        time--;
    }

    public void degreeMaxTime() {
        maxTime--;
    }

    public void setMaxTime(int maxTime, boolean save) {
        this.maxTime = maxTime;
        if (save) {
            configuration.set("Max Time", maxTime);
            configuration.save();
        }
    }

    public void setTime(int time, boolean save) {
        this.time = time;
        if (save) {
            configuration.set("Wait To Start Time", time);
            configuration.save();
        }
    }

    public int getTotalPlayersSize() {
        return getPlayers().size() + getSpectatingPlayers().size();
    }

    public List<SpleefPlayer> getTotalPlayers() {
        List<SpleefPlayer> totalPlayers = new ArrayList<>();
        totalPlayers.addAll(getPlayers());
        totalPlayers.addAll(getSpectatingPlayers());
        return totalPlayers;
    }

    public void broadcast(String message) {
        for (SpleefPlayer spleefPlayer : getTotalPlayers()) {
            spleefPlayer.sendMessage(message);
        }
    }
}
