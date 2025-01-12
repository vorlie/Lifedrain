package vorlie.lifedrain;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;

import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vorlie.lifedrain.commands.ConfigCheckCommand;
import vorlie.lifedrain.config.ConfigManager;

public class LifeDrain implements ModInitializer {
	public static final String MOD_ID = "lifedrain";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	private long lastLifestealTime = 0; // Time of the last lifesteal
	private long COOLDOWN_TIME; // Cooldown time in milliseconds (1 second)

	@Override
	public void onInitialize() {
		LOGGER.info("LifeDrain mod initialized!");
		ConfigManager.load(); // Load configuration

		COOLDOWN_TIME = ConfigManager.CONFIG.lifestealCooldown;

		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			if (!world.isClient && entity instanceof HostileEntity) {
				if (canActivateLifesteal()) {
					handleLifesteal(player, (HostileEntity) entity);
				}
			}
			return ActionResult.PASS;
		});

		// Register the config reload event
		ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, resourceManager, success) -> {
			ConfigManager.load();
			LOGGER.info("LifeDrain config reloaded.");
			COOLDOWN_TIME = ConfigManager.CONFIG.lifestealCooldown;
		});

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			ConfigCheckCommand.register(dispatcher);  // Only two parameters are expected here
		});
	}
	private void handleLifesteal(PlayerEntity player, HostileEntity mob) {
		if (player != null && mob != null && mob.isAlive()) {
			// Base healing amount from configuration
			float baseHeal = switch (mob.getWorld().getDifficulty()) {
				case EASY -> ConfigManager.CONFIG.baseHealEasy;
				case NORMAL -> ConfigManager.CONFIG.baseHealNormal;
				case HARD -> ConfigManager.CONFIG.baseHealHard;
				default -> 0.0F;
			};

			// Bonus healing based on damage dealt
			float damageDealt = mob.getMaxHealth() - mob.getHealth();
			float bonusHeal = damageDealt * ConfigManager.CONFIG.bonusHealMultiplier;

			// Total healing (base + bonus)
			float totalHeal = baseHeal + bonusHeal;
			player.heal(totalHeal);

			// Show particles if enabled
			if (ConfigManager.CONFIG.enableParticles && player.getWorld() instanceof ServerWorld serverWorld) {
				Random random = player.getRandom();
				for (int i = 0; i < 10; i++) {
					serverWorld.spawnParticles(
							ParticleTypes.HEART,
							player.getX() + random.nextDouble() - 0.5,
							player.getY() + random.nextDouble(),
							player.getZ() + random.nextDouble() - 0.5,
							1, 0, 0, 0, 0);
				}
			}

			LOGGER.info("Lifesteal: {} healed {} (Base: {}, Bonus: {}).",
					player.getName().getString(), totalHeal, baseHeal, bonusHeal);

			// Update last lifesteal activation time
			lastLifestealTime = System.currentTimeMillis();
		}
	}

	// Check if enough time has passed since the last lifesteal to activate it again
	private boolean canActivateLifesteal() {
		long currentTime = System.currentTimeMillis();
		return (currentTime - lastLifestealTime) >= COOLDOWN_TIME;
	}
}
