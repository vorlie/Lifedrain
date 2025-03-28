# **🩸 LifeDrain**

**LifeDrain** adds a simple but powerful mechanic to Minecraft: stealing life from hostile mobs. Every time you attack, you’ll heal yourself based on the damage dealt, making combat a way to stay alive rather than just survive. 🛡️✨

### 🌟 Recommended mods that suit the mechanic:
- [RpgDifficulty](https://modrinth.com/mod/rpgdifficulty) - 🔥 Strengthen mobs over distance and/or time

### 🩸 Features:
- **Healing mechanics:**
    - Base healing scales with the difficulty level:
        - 🟢 Easy: 2.0 HP
        - 🟡 Normal: 1.0 HP
        - 🔴 Hard: 0.5 HP
    - Bonus healing based on the damage dealt: 🪓 **20% of the damage dealt.**
    - Lifesteal cooldown is set to **1000 milliseconds (1 second)** by default.
- **Mob Healing:**
    - Hostile mobs now heal when they hit the player. The healing amount is also difficulty-dependent:
        - 🟢 Easy: 0.5 HP
        - 🟡 Normal: 1.0 HP
        - 🔴 Hard: 2.0 HP
    - This feature adds a challenge by making mobs more dangerous.
    - **Optional Disabling:** If you prefer to disable mob healing, you can do so in the configuration settings.
- **✨ Client-Side Features:**
    - **Particle Effects:** 🎇 Enable or disable particle effects when healing is triggered.
    - **Client-Side Configuration Options:** Through **Cloth Config** and **Mod Menu**, you can customize particle effects, cooldown settings, and other features directly from the in-game settings menu.
- Applies only to hostile mobs, ensuring lifesteal is balanced and works as intended in combat. ⚔️
- **Update Checker:** 📡 The mod checks for updates on GitHub and notifies you when a new version is available. If a new version is found, it provides a link to the Modrinth page to download the update.

### ⚙️ Configuration:
- **Server-Side Customization:**
    - Customize healing values for different difficulty levels:
        - 🟢 Player Base healing for Easy, Normal, and Hard modes.
        - 🔴 Mob healing for Easy, Normal, and Hard modes.
        - 🔧 Bonus healing multiplier based on the damage dealt.
        - ⏳ Adjust the cooldown for lifesteal activation (time between consecutive lifesteal uses).
    - **Disable Mob Healing:** You can disable mob healing entirely by setting `mobsHealOnHit` to `false` in the configuration.
- **Client-Side Customization:**
    - 🎆 Enable or disable particle effects when healing is triggered.
    - 🔍 Configuration options are accessible through **Cloth Config** and **Mod Menu.**
- 📁 All settings are saved to `lifedrain.json` and can be modified directly.
- The config file is automatically updated to add missing values if they are not found. ✅
- Use the `/check_config` command to automatically check and update the config file if necessary.

### 🌐 Translations Available:
The mod is fully translated into the following languages:
- 🇺🇸 **English**
- 🇵🇱 **Polish**
- 🇲🇽 **Spanish (Mexico)**
- 🇪🇸 **Spanish**
- 🇨🇳 **Chinese (Simplified)**
- 🇰🇷 **Korean**
- 🇷🇺 **Russian**
- 🇩🇪 **German**
- 🇯🇵 **Japanese**
- 🇫🇷 **French**
- 🇨🇿 **Czech** - Thanks to [DvopHeh](https://github.com/DvopHeh)

This mod is lightweight and perfect for anyone who enjoys combat-focused gameplay or just wants a little extra survivability. ⚔️🛡️

**Note:** ⚠️ Although the mod can be loaded in Minecraft `1.21.X`, it is not recommended. Attacking a hostile mob in versions other than `1.21` will cause your game to crash.

### 🛠️ Requirements:
- 📦 Fabric Loader 0.16.10
- 🧩 Fabric API 0.102.0 (for Minecraft 1.21)
- 🖥️ Minecraft 1.21
- ☕ Java 21
- **Cloth Config 15.0.140** (Client-side, optional)
- **Mod Menu 11.0.3** (Client-side, optional)
