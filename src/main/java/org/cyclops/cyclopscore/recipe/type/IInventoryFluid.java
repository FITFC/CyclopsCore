package org.cyclops.cyclopscore.recipe.type;

import net.minecraft.world.Container;
import net.minecraftforge.fluids.capability.IFluidHandler;

/**
 * An inventory that can also contain fluids.
 * @author rubensworks
 */
public interface IInventoryFluid extends Container {

    public IFluidHandler getFluidHandler();

}
