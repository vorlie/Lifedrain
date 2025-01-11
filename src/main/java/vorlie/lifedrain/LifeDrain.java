package vorlie.lifedrain;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LifeDrain implements ModInitializer {
	public static final String MOD_ID = "lifedrain";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}

	@Override
	public void onInitialize() {
		LOGGER.info("LifeDrain mod initialized!");

		// Register the attack event
		AttackEntityCallback.EVENT.register((
				player,
				world,
				hand,
				entity,
				hitResult) -> {
			// Ensure the code runs on the server and the target is a valid mob
			if (!world.isClient && entity instanceof MobEntity) {
				handleLifesteal(player, (MobEntity) entity);
			}
			return ActionResult.PASS;
		});
	}

	private void handleLifesteal(PlayerEntity player, MobEntity mob) {
		if (player != null && mob != null && mob.isAlive()) {
			// Base healing amount based on difficulty
			float baseHeal = switch (mob.getWorld().getDifficulty()) {
				case EASY -> 2.0F;
				case NORMAL -> 1.0F;
				case HARD -> 0.5F;
				default -> 0.0F;
			};

			// Bonus healing based on damage dealt
			float damageDealt = mob.getMaxHealth() - mob.getHealth(); // Approximation of damage dealt
			float bonusHeal = damageDealt * 0.2F; // 20% of the damage dealt

			// Total healing (base + bonus)
			float totalHeal = baseHeal + bonusHeal;

			// Heal the player
			player.heal(totalHeal);

			// Log for debugging
			LOGGER.info("Lifesteal activated: {} healed {} health (Base: {}, Bonus: {}, Mob Health: {} -> {})!",
					player.getName().getString(),
					totalHeal,
					baseHeal,
					bonusHeal,
					mob.getMaxHealth(),
					mob.getHealth());
		}
	}
}
