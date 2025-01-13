package vorlie.lifedrain;

import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.random.Random;

import vorlie.lifedrain.config.ConfigManager;

public class LifeDrainClient implements ClientModInitializer {
	private long lastLifestealTime = 0; // Time of the last lifesteal
	private long COOLDOWN_TIME; // Cooldown time in milliseconds (1 second)

	@Override
	public void onInitializeClient() {

		COOLDOWN_TIME = ConfigManager.CONFIG.lifestealCooldown;

		// Initialization logic for client-specific features
		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			if (world.isClient && entity instanceof HostileEntity) {
				if (canActivateLifesteal()) {
					handleLifesteal(player, (HostileEntity) entity);
				}
			}
			return ActionResult.PASS;
		});

		ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, resourceManager, success) -> {
			ConfigManager.load();
			COOLDOWN_TIME = ConfigManager.CONFIG.lifestealCooldown;
		});
	}

	private void handleLifesteal(PlayerEntity player, HostileEntity mob) {
		if (player != null && mob != null && mob.isAlive()) {
			if (player.getHealth() < player.getMaxHealth()) {
				if (ConfigManager.CONFIG.enableParticles) {
					ClientWorld world = MinecraftClient.getInstance().world;
					if (world != null) {
						Random random = player.getRandom();
						for (int i = 0; i < 10; i++) {
							world.addParticle(
									ParticleTypes.HEART,  // The particle type (HEART in this case)
									player.getX() + random.nextDouble() - 0.5,  // X offset
									player.getY() + random.nextDouble(),  // Y offset
									player.getZ() + random.nextDouble() - 0.5,  // Z offset
									0, 0, 0);  // Particle velocity (no movement)
						}
					}
				}
			}
			lastLifestealTime = System.currentTimeMillis();
		}
	}

	private boolean canActivateLifesteal() {
		long currentTime = System.currentTimeMillis();
		return (currentTime - lastLifestealTime) >= COOLDOWN_TIME;
	}
}
