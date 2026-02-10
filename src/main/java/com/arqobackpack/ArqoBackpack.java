package com.arqobackpack;

import com.arqobackpack.commands.BPCommand;
import com.arqobackpack.listeners.BackpackListener;
import com.arqobackpack.storage.Storage;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ArqoBackpack extends JavaPlugin {
    private static ArqoBackpack instance;
    private Storage storage;
    private FileConfiguration messages;
    private BackpackListener listener;
    private final Set<UUID> locks = new HashSet<>();

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        loadMessages();
        storage = new Storage(this);
        storage.init();
        listener = new BackpackListener(this);
        getServer().getPluginManager().registerEvents(listener, this);
        getCommand("backpack").setExecutor(new BPCommand(this));
    }

    private void loadMessages() {
        File f = new File(getDataFolder(), "messages.yml");
        if (!f.exists()) saveResource("messages.yml", false);
        messages = YamlConfiguration.loadConfiguration(f);
    }

    public String getMsg(String key) {
        String lang = getConfig().getString("language", "id");
        return messages.getString(lang + "." + key, "Missing Key").replace("&", "§");
    }

    public static ArqoBackpack inst() { return instance; }
    public Storage getStorage() { return storage; }
    public BackpackListener getListener() { return listener; }
    public Set<UUID> getLocks() { return locks; }
}

