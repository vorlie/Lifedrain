**LifeDrain** adds a simple but powerful mechanic to Minecraft: stealing life from hostile mobs. Every time you attack, you’ll heal yourself based on the damage dealt, making combat a way to stay alive rather than just survive.

🩸 **Features:**
- Healing mechanics:
    - Base healing scales with the difficulty level:
        - Easy: 2.0 HP
        - Normal: 1.0 HP
        - Hard: 0.5 HP
    - Bonus healing based on the damage dealt: 20% of the damage dealt.
- Applies only to hostile mobs, ensuring lifesteal is balanced and works as intended in combat.

⚙️ **Configuration:**
- Customize healing values for different difficulty levels:
    - Base healing for Easy, Normal, and Hard modes.
    - Bonus healing multiplier based on the damage dealt.
- Enable or disable particle effects when healing is triggered.
- Adjust the cooldown for lifesteal activation (time between consecutive lifesteal uses).
- Configuration is saved to `PATH-TO-MINECRAFT-INSTANCE/config/lifedrain.json` and can be modified directly.
- The config file is automatically updated to add missing values if they are not found.
- Use the `/check_config` command to automatically check and update the config file if necessary.

🔧 **Technical Details:**
- Fully implemented lifesteal logic triggered upon attacking hostile mobs.
- Debugging logs added to track healing values and lifesteal performance.
- Added config file support to customize mod behavior through the configuration file.
- Config can be updated to add missing fields if necessary.
- Added a command to check and update the config file (`/check_config`), ensuring it always has the correct values.

This mod is lightweight and perfect for anyone who enjoys combat-focused gameplay or just wants a little extra survivability.

**Requirements:**
- Fabric Loader 0.16.10+
- Minecraft 1.21.X
- Java 21+