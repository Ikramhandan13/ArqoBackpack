package com.arqobackpack.listeners;

import com.arqobackpack.ArqoBackpack;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BackpackListener implements Listener {
    private final ArqoBackpack pl;
    private final Map<UUID, List<Long>> sneaks = new ConcurrentHashMap<>();

    public BackpackListener(ArqoBackpack pl) { this.pl = pl; }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent e) {
        if (!e.isSneaking()) return;
        Player p = e.getPlayer();
        long now = System.currentTimeMillis();
        List<Long> t = sneaks.computeIfAbsent(p.getUniqueId(), k -> new ArrayList<>());
        t.removeIf(l -> (now - l) > 2000);
        t.add(now);
        if (t.size() >= 5) {
            t.clear();
            open(p, p);
        }
    }

    public void open(Player v, Player o) {
        if (pl.getLocks().contains(o.getUniqueId()) && !v.equals(o)) {
            v.sendMessage(pl.getMsg("prefix") + pl.getMsg("locked"));
            return;
        }
        pl.getLocks().add(o.getUniqueId());
        Bukkit.getAsyncScheduler().runNow(pl, task -> {
            ItemStack[] items = pl.getStorage().load(o.getUniqueId());
            v.getScheduler().execute(pl, () -> {
                int s = getSize(o);
                String title = v.equals(o) ? pl.getMsg("title-self") : pl.getMsg("title-admin").replace("%player%", o.getName());
                Inventory inv = Bukkit.createInventory(null, s, title);
                for (int i = 0; i < Math.min(items.length, s); i++) inv.setItem(i, items[i]);
                v.openInventory(inv);
                v.playSound(v.getLocation(), Sound.valueOf(pl.getConfig().getString("animation.sound", "BLOCK_CHEST_OPEN")), 1f, 1f);
            }, null, 0);
        });
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        String title = e.getView().getTitle();
        if (!title.contains(pl.getMsg("title-self").substring(2))) return;
        Player p = (Player) e.getPlayer();
        UUID id = p.getUniqueId();
        if (title.contains(": ")) {
            String n = title.split(": ")[1].substring(2);
            id = Bukkit.getOfflinePlayer(n).getUniqueId();
        }
        ItemStack[] contents = e.getInventory().getContents();
        UUID finalId = id;
        Bukkit.getAsyncScheduler().runNow(pl, task -> {
            pl.getStorage().save(finalId, contents);
            pl.getLocks().remove(finalId);
        });
    }

    private int getSize(Player p) {
        if (p.hasPermission("arqobackpack.size.54")) return 54;
        if (p.hasPermission("arqobackpack.size.27")) return 27;
        return 9;
    }
}

