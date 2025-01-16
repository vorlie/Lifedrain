package vorlie.lifedrain.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vorlie.lifedrain.config.ConfigManager;

@Mixin(LivingEntity.class)
public abstract class MobHealHandlerMixin {

    @Inject(method = "damage", at = @At("HEAD"))
    private void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        // Ensure the entity taking damage is a player
        if ((Object) this instanceof net.minecraft.entity.player.PlayerEntity player) {
            // Check if the damage source is a hostile mob
            if (source.getAttacker() instanceof HostileEntity mob && mob.isAlive()) {
                handleMobHeal(mob);
            }
        }
    }

    @Unique
    private void handleMobHeal(HostileEntity mob) {
        if (!ConfigManager.CONFIG.mobsHealOnHit) {
            return; // Skip if the feature is disabled
        }

        float mobHeal = switch (mob.getWorld().getDifficulty()) {
            case EASY -> ConfigManager.CONFIG.mobHealAmountEasy;
            case NORMAL -> ConfigManager.CONFIG.mobHealAmountNormal;
            case HARD -> ConfigManager.CONFIG.mobHealAmountHard;
            default -> 0.0F;
        };

        // Heal the mob
        mob.heal(mobHeal);

        // Spawn particle effects if the mob's world is a server world
        if (mob.getWorld() instanceof ServerWorld world) {
            world.spawnParticles(
                    ParticleTypes.HEART,
                    mob.getX() + mob.getRandom().nextDouble() - 0.5,
                    mob.getY() + mob.getRandom().nextDouble(),
                    mob.getZ() + mob.getRandom().nextDouble() - 0.5,
                    5, // Count
                    0.5, 0.5, 0.5, 0 // Spread and speed
            );
        }

    }
}
