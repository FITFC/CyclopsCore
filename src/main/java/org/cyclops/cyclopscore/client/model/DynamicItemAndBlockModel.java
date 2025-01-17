package org.cyclops.cyclopscore.client.model;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * A dynamic model that can be used for items and blocks.
 * @author rubensworks
 */
public abstract class DynamicItemAndBlockModel extends DynamicBaseModel {

    private final boolean factory;
    private final boolean item;
    private final ItemOverrides itemOverrides;

    private Direction renderingSide;

    public DynamicItemAndBlockModel(boolean factory, boolean item) {
        this.factory = factory;
        this.item = item;
        this.itemOverrides = new ItemOverridesInner();
    }

    protected boolean isItemStack() {
        return item;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand) {
        return this.getQuads(state, side, rand, ModelData.EMPTY, RenderType.cutout());
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side,
                                    @Nonnull RandomSource rand, @Nonnull ModelData extraData,
                                    @Nullable RenderType renderType) {
        this.renderingSide = side;
        if(factory) {
            BakedModel bakedModel;
            if(isItemStack()) {
                bakedModel = handleItemState(null, null, null);
            } else {
                bakedModel = handleBlockState(state, side, rand, extraData, renderType);
            }
            if (bakedModel != null) {
                return bakedModel.getQuads(state, side, rand);
            }
        }
        return getGeneralQuads();
    }

    public List<BakedQuad> getGeneralQuads() {
        return Collections.emptyList();
    }

    public abstract BakedModel handleBlockState(@Nullable BlockState state, @Nullable Direction side,
                                                 @Nonnull RandomSource rand, @Nonnull ModelData extraData,
                                                @Nullable RenderType renderType);
    public abstract BakedModel handleItemState(@Nullable ItemStack stack, @Nullable Level world,
                                                @Nullable LivingEntity entity);

    @Override
    public ItemOverrides getOverrides() {
        return itemOverrides;
    }

    public Direction getRenderingSide() {
        return renderingSide;
    }

    public class ItemOverridesInner extends ItemOverrides {
        @Nullable
        @Override
        public BakedModel resolve(BakedModel model, ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity entity, int entityId) {
            return DynamicItemAndBlockModel.this.handleItemState(stack, world, entity);
        }
    }

}
