package org.cyclops.cyclopscore.config.extendedconfig;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import org.cyclops.cyclopscore.config.ConfigurableType;
import org.cyclops.cyclopscore.init.ModBase;

import java.util.function.Function;

/**
 * Config for potion effects.
 * @author rubensworks
 * @see ExtendedConfig
 */
public abstract class EffectConfig extends ExtendedConfigForge<EffectConfig, MobEffect> {

    /**
     * Make a new instance.
     * @param mod     The mod instance.
     * @param namedId The unique name ID for the configurable.
     * @param elementConstructor The element constructor.
     */
    public EffectConfig(ModBase mod, String namedId, Function<EffectConfig, ? extends MobEffect> elementConstructor) {
        super(mod, namedId, elementConstructor);
    }

    @Override
    public String getTranslationKey() {
        return "potions." + getMod().getModId() + "." + getNamedId();
    }

    @Override
    public ConfigurableType getConfigurableType() {
        return ConfigurableType.EFFECT;
    }

    @Override
    public IForgeRegistry<MobEffect> getRegistry() {
        return ForgeRegistries.MOB_EFFECTS;
    }

}
