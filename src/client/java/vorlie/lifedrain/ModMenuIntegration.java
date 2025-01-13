package vorlie.lifedrain;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.text.Text;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vorlie.lifedrain.config.ConfigManager;

@Environment(EnvType.CLIENT) // Ensure this only runs on the client side
public class ModMenuIntegration implements ModMenuApi {
    private static final Logger LOGGER = LoggerFactory.getLogger("lifedrain");

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        LOGGER.info("Lifesteal: ModMenuIntegration loaded successfully.");

        return parent -> {
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(Text.translatable("config.lifedrain.title"));

            ConfigCategory general = builder.getOrCreateCategory(Text.translatable("config.lifedrain.general"));
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();

            // Enable Particles
            general.addEntry(entryBuilder
                    .startBooleanToggle(Text.translatable("config.lifedrain.enableParticles"), ConfigManager.CONFIG.enableParticles)
                    .setDefaultValue(true)
                    .setSaveConsumer(value -> ConfigManager.CONFIG.enableParticles = value)
                    .setTooltip(Text.translatable("config.lifedrain.enableParticles.tooltip"))
                    .build());

            // Lifesteal Cooldown
            general.addEntry(entryBuilder
                    .startIntField(Text.translatable("config.lifedrain.lifestealCooldown"), ConfigManager.CONFIG.lifestealCooldown)
                    .setDefaultValue(1000)
                    .setMin(0)
                    .setSaveConsumer(value -> ConfigManager.CONFIG.lifestealCooldown = value)
                    .setTooltip(Text.translatable("config.lifedrain.lifestealCooldown.tooltip"))
                    .build());

            // Base Heal (Easy)
            general.addEntry(entryBuilder
                    .startFloatField(Text.translatable("config.lifedrain.baseHealEasy"), ConfigManager.CONFIG.baseHealEasy)
                    .setDefaultValue(2.0F)
                    .setMin(0.0F)
                    .setSaveConsumer(value -> ConfigManager.CONFIG.baseHealEasy = value)
                    .setTooltip(Text.translatable("config.lifedrain.baseHealEasy.tooltip"))
                    .build());

            // Base Heal (Normal)
            general.addEntry(entryBuilder
                    .startFloatField(Text.translatable("config.lifedrain.baseHealNormal"), ConfigManager.CONFIG.baseHealNormal)
                    .setDefaultValue(1.0F)
                    .setMin(0.0F)
                    .setSaveConsumer(value -> ConfigManager.CONFIG.baseHealNormal = value)
                    .setTooltip(Text.translatable("config.lifedrain.baseHealNormal.tooltip"))
                    .build());

            // Base Heal (Hard)
            general.addEntry(entryBuilder
                    .startFloatField(Text.translatable("config.lifedrain.baseHealHard"), ConfigManager.CONFIG.baseHealHard)
                    .setDefaultValue(0.5F)
                    .setMin(0.0F)
                    .setSaveConsumer(value -> ConfigManager.CONFIG.baseHealHard = value)
                    .setTooltip(Text.translatable("config.lifedrain.baseHealHard.tooltip"))
                    .build());

            // Bonus Heal Multiplier
            general.addEntry(entryBuilder
                    .startFloatField(Text.translatable("config.lifedrain.bonusHealMultiplier"), ConfigManager.CONFIG.bonusHealMultiplier)
                    .setDefaultValue(0.2F)
                    .setMin(0.0F)
                    .setSaveConsumer(value -> ConfigManager.CONFIG.bonusHealMultiplier = value)
                    .setTooltip(Text.translatable("config.lifedrain.bonusHealMultiplier.tooltip"))
                    .build());

            builder.setSavingRunnable(ConfigManager::save);
            return builder.build();
        };
    }
}
