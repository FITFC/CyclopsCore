package org.cyclops.cyclopscore.config.extendedconfig;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import org.cyclops.cyclopscore.client.model.IDynamicModelElement;
import org.cyclops.cyclopscore.config.ConfigurableType;
import org.cyclops.cyclopscore.config.configurabletypeaction.ItemAction;
import org.cyclops.cyclopscore.helper.MinecraftHelpers;
import org.cyclops.cyclopscore.init.ModBase;
import org.cyclops.cyclopscore.item.ItemInformationProvider;

import javax.annotation.Nullable;
import java.util.function.Function;

/**
 * Config for items.
 * @author rubensworks
 * @see ExtendedConfig
 */
public class ItemConfig extends ExtendedConfigForge<ItemConfig, Item> implements IModelProviderConfig {

    @OnlyIn(Dist.CLIENT)
    public ModelResourceLocation dynamicItemVariantLocation;

    /**
     * Make a new instance.
     * @param mod     The mod instance.
     * @param namedId The unique name ID for the configurable.
     * @param elementConstructor The element constructor.
     */
    public ItemConfig(ModBase mod, String namedId, Function<ItemConfig, ? extends Item> elementConstructor) {
        super(mod, namedId, elementConstructor);
        if(MinecraftHelpers.isClientSide()) {
            dynamicItemVariantLocation  = null;
        }
    }

    @Override
    public String getModelName(ItemStack itemStack) {
        return getNamedId();
    }

    @Override
    public String getTranslationKey() {
        return "item." + getMod().getModId() + "." + getNamedId();
    }

    @Override
    public String getFullTranslationKey() {
        return getTranslationKey();
    }

    @Override
    public ConfigurableType getConfigurableType() {
        return ConfigurableType.ITEM;
    }

    /**
     * Register default item models for this item.
     * This should only be used when registering dynamic models.
     * @return The item resource location.
     */
    @OnlyIn(Dist.CLIENT)
    public ModelResourceLocation registerDynamicModel() {
        String itemName = getMod().getModId() + ":" + getNamedId();
        return new ModelResourceLocation(itemName, "inventory");
    }

    @Override
    public void onForgeRegistered() {
        super.onForgeRegistered();

        // Add item to information provider
        ItemInformationProvider.registerItem(getInstance());

        // Handle dynamic models
        if(MinecraftHelpers.isClientSide() && getInstance() instanceof IDynamicModelElement &&
                ((IDynamicModelElement) getInstance()).hasDynamicModel()) {
            ItemAction.handleItemModel(this);
        }
    }

    /**
     * Get the creative tab for this item.
     * @return The creative tab, by default the value in {@link org.cyclops.cyclopscore.init.ModBase#getDefaultItemGroup()}.
     */
    public CreativeModeTab getTargetTab() {
        return getMod().getDefaultItemGroup();
    }

    @Override
    public IForgeRegistry<Item> getRegistry() {
        return ForgeRegistries.ITEMS;
    }

    /**
     * @return The color handler for the item instance.
     */
    @Nullable
    @OnlyIn(Dist.CLIENT)
    public ItemColor getItemColorHandler() {
        return null;
    }

}
