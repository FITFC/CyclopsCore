package org.cyclops.cyclopscore.recipe.type;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

/**
 * Default implementation of {@link IInventoryFluid}.
 * @author rubensworks
 */
public class InventoryFluid extends CraftingContainer implements IInventoryFluid {

    private final IFluidHandler fluidHandler;

    public InventoryFluid(NonNullList<ItemStack> itemStacks, NonNullList<FluidStack> fluidStacks) {
        super(new AbstractContainerMenu(null, 0) {
            @Override
            public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
                return ItemStack.EMPTY;
            }

            @Override
            public boolean stillValid(Player playerIn) {
                return false;
            }
        }, itemStacks.size(), 1);
        int slot = 0;
        for (ItemStack itemStack : itemStacks) {
            setItem(slot++, itemStack);
        }
        if (fluidStacks.size() == 1) {
            this.fluidHandler = new FluidTank(Integer.MAX_VALUE);
            this.fluidHandler.fill(fluidStacks.get(0), IFluidHandler.FluidAction.EXECUTE);
        } else {
            this.fluidHandler = new FluidHandlerListReadOnly(fluidStacks);
        }
    }

    @Override
    public IFluidHandler getFluidHandler() {
        return this.fluidHandler;
    }
}
