package io.thadow.simplespleef.utils.configuration;

import io.thadow.simplespleef.api.configuration.ConfigurationFile;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Arrays;

public class ScoreboardsConfiguration extends ConfigurationFile {

    public ScoreboardsConfiguration(String configName, String dir) {
        super(configName, dir);
        if (isFirstTime()) {
            YamlConfiguration configuration = getConfiguration();

            configuration.addDefault("Scoreboards.Lobby.Enabled", true);
            configuration.addDefault("Scoreboards.Lobby.Update", 20);
            configuration.addDefault("Scoreboards.Lobby.Title", "&eSimpleSpleef");
            configuration.addDefault("Scoreboards.Lobby.Lines", Arrays.asList("%server_time_yyyy/MM/dd%",
                    "&7",
                    "%player_name%",
                    "&7Wins: &a%wins%",
                    "&Losses: &c%losses%",
                    "&7",
                    "&7mc.myserver.com"));

            configuration.addDefault("Scoreboards.Waiting.Enabled", true);
            configuration.addDefault("Scoreboards.Waiting.Update", 20);
            configuration.addDefault("Scoreboards.Waiting.Title", "&eWaiting");
            configuration.addDefault("Scoreboards.Waiting.Line", Arrays.asList("%server_time_yyyy/MM/dd%",
                    "&7",
                    "&aMap&7: &a%arenaName%",
                    "&7",
                    "&7Waiting...",
                    "&7",
                    "&7mc.myserver.com"));

            configuration.addDefault("Scoreboards.Starting.Enabled", true);
            configuration.addDefault("Scoreboards.Starting.Update", 20);
            configuration.addDefault("Scoreboards.Starting.Title", "&eStarting");
            configuration.addDefault("Scoreboards.Starting.Lines", Arrays.asList("%server_time_yyyy/MM/dd%",
                    "&7",
                    "&aMap&7: &a%arenaName%",
                    "&7",
                    "&eStarting in %seconds%",
                    "&7",
                    "&7mc.myserver.com"));

            configuration.addDefault("Scoreboards.Playing.Enabled", true);
            configuration.addDefault("Scoreboards.Playing.Update", 20);
            configuration.addDefault("Scoreboards.Playing.Title", "&eSimpleSpleef");
            configuration.addDefault("Scoreboards.Playing.Lines", Arrays.asList("%server_time_yyyy/MM/dd%",
                    "&7",
                    "&aMap&7: &a%arenaName%",
                    "&7",
                    "&eEnding in %time%",
                    "&7",
                    "&7mc.myserver.com"));

            configuration.addDefault("Scoreboards.Ending.Enabled", true);
            configuration.addDefault("Scoreboards.Ending.Update", 20);
            configuration.addDefault("Scoreboards.Ending.Title", "&eSimpleSpleef");
            configuration.addDefault("Scoreboards.Ending.Lines", Arrays.asList("%server_time_yyyy/MM/dd%",
                    "&7",
                    "&aMap&7: &a%arenaName%",
                    "&7",
                    "&eWinner: &a%winner%",
                    "&7",
                    "&7mc.myserver.com"));

            configuration.options().copyDefaults(true);
            save();
        }
    }
}
