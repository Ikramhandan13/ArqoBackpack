package com.arqobackpack.util;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
import java.io.*;

public class SerUtil {
    public static String toBase64(ItemStack[] items) {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream();
             BukkitObjectOutputStream dos = new BukkitObjectOutputStream(os)) {
            dos.writeInt(items.length);
            for (ItemStack i : items) dos.writeObject(i);
            return Base64Coder.encodeLines(os.toByteArray());
        } catch (Exception e) { return ""; }
    }

    public static ItemStack[] fromBase64(String d) {
        if (d == null || d.isEmpty()) return new ItemStack[0];
        try (ByteArrayInputStream is = new ByteArrayInputStream(Base64Coder.decodeLines(d));
             BukkitObjectInputStream dis = new BukkitObjectInputStream(is)) {
            ItemStack[] items = new ItemStack[dis.readInt()];
            for (int i = 0; i < items.length; i++) items[i] = (ItemStack) dis.readObject();
            return items;
        } catch (Exception e) { return new ItemStack[0]; }
    }
}

