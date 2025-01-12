package vorlie.lifedrain.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import net.fabricmc.loader.api.FabricLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static vorlie.lifedrain.LifeDrain.MOD_ID;

public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "lifedrain.json");
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static LifeDrainConfig CONFIG = new LifeDrainConfig();

    public static void load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                JsonObject configJson = GSON.fromJson(reader, JsonObject.class);

                // Check for missing fields and add default values
                addMissingFields(configJson);

                // Now convert the JsonObject to our config class
                CONFIG = GSON.fromJson(configJson, LifeDrainConfig.class);
            } catch (IOException e) {
                LOGGER.error("Error reading config: ", e);
            }
        } else {
            save(); // Save defaults if the config file doesn't exist
        }
    }

    public static JsonObject getConfigJson() {
        JsonObject configJson = new JsonObject();
        // Add current configuration values to a JsonObject
        configJson.addProperty("lifestealCooldown", CONFIG.lifestealCooldown);
        configJson.addProperty("enableParticles", CONFIG.enableParticles);
        configJson.addProperty("baseHealEasy", CONFIG.baseHealEasy);
        configJson.addProperty("baseHealNormal", CONFIG.baseHealNormal);
        configJson.addProperty("baseHealHard", CONFIG.baseHealHard);
        configJson.addProperty("bonusHealMultiplier", CONFIG.bonusHealMultiplier);

        return configJson;
    }

    public static void addMissingFields(JsonObject configJson) {
        // Add new fields if they don't exist
        if (!configJson.has("lifestealCooldown")) {
            LOGGER.info("Missing 'lifestealCooldown', adding default value.");
            configJson.addProperty("lifestealCooldown", LifeDrainConfig.DEFAULT_LIFESTEAL_COOLDOWN);
        }

        if (!configJson.has("enableParticles")) {
            LOGGER.info("Missing 'enableParticles', adding default value.");
            configJson.addProperty("enableParticles", LifeDrainConfig.DEFAULT_ENABLE_PARTICLES);
        }

        if (!configJson.has("baseHealEasy")) {
            LOGGER.info("Missing 'baseHealEasy', adding default value.");
            configJson.addProperty("baseHealEasy", LifeDrainConfig.DEFAULT_BASE_HEAL_EASY);
        }

        if (!configJson.has("baseHealNormal")) {
            LOGGER.info("Missing 'baseHealNormal', adding default value.");
            configJson.addProperty("baseHealNormal", LifeDrainConfig.DEFAULT_BASE_HEAL_NORMAL);
        }

        if (!configJson.has("baseHealHard")) {
            LOGGER.info("Missing 'baseHealHard', adding default value.");
            configJson.addProperty("baseHealHard", LifeDrainConfig.DEFAULT_BASE_HEAL_HARD);
        }

        if (!configJson.has("bonusHealMultiplier")) {
            LOGGER.info("Missing 'bonusHealMultiplier', adding default value.");
            configJson.addProperty("bonusHealMultiplier", LifeDrainConfig.DEFAULT_BONUS_HEAL_MULTIPLIER);
        }
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(CONFIG, writer);
        } catch (IOException e) {
            LOGGER.error("Error saving config: ", e);
        }
    }
}