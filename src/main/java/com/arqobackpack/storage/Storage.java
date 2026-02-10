package com.arqobackpack.storage;

import com.arqobackpack.ArqoBackpack;
import com.arqobackpack.util.SerUtil;
import org.bukkit.inventory.ItemStack;
import java.io.File;
import java.sql.*;
import java.util.UUID;

public class Storage {
    private final ArqoBackpack plugin;
    private Connection conn;

    public Storage(ArqoBackpack plugin) { this.plugin = plugin; }

    public void init() {
        try {
            File db = new File(plugin.getDataFolder(), "storage.db");
            if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdirs();
            conn = DriverManager.getConnection("jdbc:sqlite:" + db.getAbsolutePath());
            try (Statement s = conn.createStatement()) {
                s.execute("CREATE TABLE IF NOT EXISTS bp (uuid TEXT PRIMARY KEY, data TEXT)");
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void save(UUID id, ItemStack[] items) {
        try (PreparedStatement ps = conn.prepareStatement("INSERT OR REPLACE INTO bp VALUES (?, ?)")) {
            ps.setString(1, id.toString());
            ps.setString(2, SerUtil.toBase64(items));
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public ItemStack[] load(UUID id) {
        try (PreparedStatement ps = conn.prepareStatement("SELECT data FROM bp WHERE uuid = ?")) {
            ps.setString(1, id.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return SerUtil.fromBase64(rs.getString("data"));
        } catch (SQLException e) { e.printStackTrace(); }
        return new ItemStack[0];
    }
}

