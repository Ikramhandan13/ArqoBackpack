<p align="center">
  <img src="https://img.shields.io/badge/Version-1.0.0-blue.svg" />
  <img src="https://img.shields.io/badge/Minecraft-1.20.1--1.21.1-green.svg" />
  <img src="https://img.shields.io/badge/License-MIT-yellow.svg" />
  <img src="https://img.shields.io/badge/Java-21+-orange.svg" />
  <img src="https://img.shields.io/badge/Folia-Supported-brightgreen.svg" />
</p>

<h1 align="center">🎒 ArqoBackpack</h1>

<p align="center">
  <b>ArqoBackpack</b> adalah solusi penyimpanan portabel tingkat lanjut yang dirancang untuk performa tinggi, keamanan data maksimal, dan kompatibilitas penuh dengan server modern berbasis <i>Regionized Multithreading</i> (Folia).
</p>

---

## 📖 Overview
**ArqoBackpack** dirancang dengan standar DevOps untuk memastikan beban server seminimal mungkin. Menggunakan sistem penyimpanan **Base64/Binary BLOB** melalui SQLite atau MySQL, plugin ini menjamin data NBT (Enchantment, Lore, Custom NBT) tersimpan dengan aman tanpa risiko korupsi data saat server restart atau crash.

## ✨ Features
* **Folia Native:** Sepenuhnya aman digunakan di server Folia dengan implementasi *Regionized Schedulers*.
* **Smart Sneak Trigger:** Buka tas dengan melakukan jongkok (shift) sebanyak 5 kali secara cepat tanpa jeda kaku.
* **Multi-Language Support:** Dilengkapi dengan lokalisasi Bahasa Indonesia (ID), English (EN), dan Portugal (PT).
* **Anti-Dupe & Session Lock:** Mencegah eksploitasi penggandaan item dengan sistem penguncian sesi (locking) saat tas sedang dibuka.
* **Storage Protection:** Item tetap tersimpan di database saat mati (Keep on Death) dan perlindungan otomatis jika kapasitas slot diturunkan.
* **Ultra-Lightweight:** Ukuran JAR sangat kecil (~40KB) dengan penggunaan CPU/RAM yang hampir tidak terasa.

## 🛠️ Installation
1. Pastikan server Anda menggunakan **Java 21**.
2. Download file `ArqoBackpack.jar`.
3. Masukkan ke dalam folder `/plugins/` server Anda.
4. Restart server atau gunakan plugin manager untuk me-load plugin.
5. Sesuaikan bahasa dan pengaturan database pada `config.yml`.

## 📜 Commands
| Command | Alias | Permission | Description |
| :--- | :--- | :--- | :--- |
| `/backpack` | `/bp` | `arqobackpack.use` | Membuka tas pribadi milik Anda. |
| `/bp <player>` | - | `arqobackpack.open.others` | Admin: Melihat atau mengedit tas pemain lain. |

## 🔑 Permissions
Kapasitas slot diberikan secara dinamis berdasarkan permission tertinggi:
- `arqobackpack.use` - Akses dasar (Default 9 Slot).
- `arqobackpack.size.18` - Upgrade ke 18 slot.
- `arqobackpack.size.27` - Upgrade ke 27 slot.
- `arqobackpack.size.36` - Upgrade ke 36 slot.
- `arqobackpack.size.45` - Upgrade ke 45 slot.
- `arqobackpack.size.54` - Kapasitas maksimal (54 slot).
- `arqobackpack.open.others` - Akses administratif untuk mengedit tas orang lain.
- `arqobackpack.reload` - Mengizinkan reload konfigurasi plugin.

## ⚙️ Configuration
Plugin ini mendukung dua jenis penyimpanan data. Gunakan MySQL jika Anda menjalankan jaringan server (Proxy).

```yaml
storage:
  type: sqlite    # Opsi: sqlite | mysql
  mysql:
    host: localhost
    port: 3306
    database: arqobackpack
    username: root
    password: "yourpassword"
```

## 🔒 Security & Anti-Dupe
Kami menerapkan **Atomic Write Strategy**. Data hanya ditulis ke database saat inventaris ditutup, pemain keluar (quit), atau plugin dimatikan. Sistem penguncian sesi aktif secara otomatis jika tas sedang diedit oleh admin untuk mencegah konflik data yang bisa memicu duplikasi item.

## 🏗️ Building from Source
Untuk melakukan kompilasi mandiri menggunakan Maven:
```bash
git clone https://github.com/arqonara/ArqoBackpack.git
cd ArqoBackpack
mvn clean package
```

🤝 Contributing
Contributions are welcome! Please follow these steps:

Fork the repository
Create a feature branch (git checkout -b feature/AmazingFeature)
Commit your changes (git commit -m 'Add some AmazingFeature')
Push to the branch (git push origin feature/AmazingFeature)
Open a Pull Request

## 📞 Support
- **Discord:** [Arqonara Hosting Community](https://discord.gg/arqonara)
- **Issues:** GitHub Issues
- **Wiki:** Documentation

🌟 Credits
Developed by: Arqonara Hosting: Arqonara Hosting

Special thanks to:
Paper/Purpur development teams
Minecraft server community
All beta testers and contributors

**Author:** Dzakiri - Arqonara
