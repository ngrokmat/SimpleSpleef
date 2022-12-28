package io.thadow.simplespleef.api.configuration;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ConfigurationFile {

    private YamlConfiguration configuration;
    private File file;
    private String configName;
    private boolean firstTime = false;

    public ConfigurationFile(String configName, String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                return;
            }
        }

        this.file = new File(dir, configName + ".yml");
        if (!this.file.exists()) {
            firstTime = true;
            try {
                if (!this.file.createNewFile())  {
                    return;
                }
            } catch (IOException ignored) {

            }
        }


        configuration = YamlConfiguration.loadConfiguration(this.file);
        this.configName = configName;
    }

    public void reload() {
        configuration = YamlConfiguration.loadConfiguration(file);
    }

    public void set(String path, Object value) {
        configuration.set(path, value);
    }

    public void save() {
        try {
            configuration.save(file);
            reload();
        } catch (IOException ignored) {

        }
    }

    public YamlConfiguration getConfiguration() {
        return configuration;
    }

    public List<String> getStringList(String path) {
        return configuration.getStringList(path);
    }

    public boolean getBoolean(String path) {
        return configuration.getBoolean(path);
    }

    public int getInt(String path) {
        return configuration.getInt(path);
    }

    public String getString(String path) {
        return configuration.getString(path);
    }

    public boolean contains(String path) {
        return configuration.contains(path);
    }

    public ConfigurationSection getConfigurationSection(String path) {
        return configuration.getConfigurationSection(path);
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public boolean isFirstTime() {
        return firstTime;
    }
}
