package vorlie.lifedrain.config;

public class LifeDrainConfig {
    public static final int DEFAULT_LIFESTEAL_COOLDOWN = 1000; // Default cooldown time in milliseconds
    public static final boolean DEFAULT_ENABLE_PARTICLES = true;
    public static final float DEFAULT_BASE_HEAL_EASY = 2.0F;
    public static final float DEFAULT_BASE_HEAL_NORMAL = 1.0F;
    public static final float DEFAULT_BASE_HEAL_HARD = 0.5F;
    public static final float DEFAULT_BONUS_HEAL_MULTIPLIER = 0.2F;

    public static final boolean DEFAULT_ENABLE_MOB_HEAL = true;
    public static final float DEFAULT_MOB_HEAL_AMOUNT_EASY = 0.5F;
    public static final float DEFAULT_MOB_HEAL_AMOUNT_NORMAL = 1.0F;
    public static final float DEFAULT_MOB_HEAL_AMOUNT_HARD = 4.0F;

    public boolean enableParticles = DEFAULT_ENABLE_PARTICLES;
    public boolean mobsHealOnHit = DEFAULT_ENABLE_MOB_HEAL;
    public float mobHealAmountEasy = DEFAULT_MOB_HEAL_AMOUNT_EASY;
    public float mobHealAmountNormal = DEFAULT_MOB_HEAL_AMOUNT_NORMAL;
    public float mobHealAmountHard = DEFAULT_MOB_HEAL_AMOUNT_HARD;
    public float baseHealEasy = DEFAULT_BASE_HEAL_EASY;
    public float baseHealNormal = DEFAULT_BASE_HEAL_NORMAL;
    public float baseHealHard = DEFAULT_BASE_HEAL_HARD;
    public float bonusHealMultiplier = DEFAULT_BONUS_HEAL_MULTIPLIER;
    public int lifestealCooldown = DEFAULT_LIFESTEAL_COOLDOWN;
}
