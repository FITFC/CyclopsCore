package org.cyclops.cyclopscore;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import org.cyclops.commoncapabilities.api.capability.inventorystate.IInventoryState;
import org.cyclops.cyclopscore.capability.fluid.IFluidHandlerItemCapacity;

/**
 * Used capabilities for this mod.
 * @author rubensworks
 */
public class Capabilities {
    public static Capability<IInventoryState> INVENTORY_STATE = CapabilityManager.get(new CapabilityToken<>(){});
    public static Capability<IFluidHandlerItemCapacity> FLUID_HANDLER_ITEM_CAPACITY = CapabilityManager.get(new CapabilityToken<>(){});
}
