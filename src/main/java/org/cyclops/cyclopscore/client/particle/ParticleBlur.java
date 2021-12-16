package org.cyclops.cyclopscore.client.particle;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.entity.LivingEntity;
import org.lwjgl.opengl.GL11;

/**
 * A blurred static fading particle with any possible color.
 * @author rubensworks
 *
 */
public class ParticleBlur extends TextureSheetParticle {

	private static final int MAX_VIEW_DISTANCE = 30;
	private static final RenderType RENDER_TYPE = new RenderType();

	protected float originalScale;
	protected float scaleLife;

	public ParticleBlur(ParticleBlurData data, ClientLevel world, double x, double y, double z,
						double motionX, double motionY, double motionZ) {
		super(world, x, y, z, motionX, motionY, motionZ);
		this.xd = motionX;
		this.yd = motionY;
		this.zd = motionZ;
		
		this.rCol = data.getRed();
		this.gCol = data.getGreen();
		this.bCol = data.getBlue();
		this.alpha = 0.9F;
		this.gravity = 0;
		
		this.originalScale = (this.random.nextFloat() * 0.5F + 0.5F) * 2.0F * data.getScale();
		this.lifetime = (int) ((random.nextFloat() * 0.33F + 0.66F) * data.getAgeMultiplier());
		this.setSize(0.01F, 0.01F);
		
		this.xo = x;
		this.yo = y;
		this.zo = z;
		
		this.scaleLife = (float) (lifetime / 2.5);
		
		validateDistance();
	}

	private void validateDistance() {
		LivingEntity renderentity = Minecraft.getInstance().player;
		int visibleDistance = MAX_VIEW_DISTANCE;
		
		if(Minecraft.getInstance().options.graphicsMode.getId() == 0) {
			visibleDistance = visibleDistance / 2;
		}

		if(renderentity == null
				|| renderentity.distanceToSqr(x, y, z) > visibleDistance * visibleDistance) {
			lifetime = 0;
		}
	}

	@Override
	public ParticleRenderType getRenderType() {
		return RENDER_TYPE;
	}

	@Override
	public void tick() {
		xo = x;
		yo = y;
		zo = z;

		if (age++ >= lifetime) {
			remove();
		}

		yd -= 0.04D * gravity;
		x += xd;
		y += yd;
		z += zd;
		xd *= 0.98000001907348633D;
		yd *= 0.98000001907348633D;
		zd *= 0.98000001907348633D;
	}

	@Override
	protected int getLightColor(float partialTicks) {
		return 0xF000F0;
	}

	/**
	 * Set the gravity for this particle.
	 * @param particleGravity The new gravity
	 */
	public void setGravity(float particleGravity) {
		this.gravity = particleGravity;
	}

	@Override
	public float getQuadSize(float p_217561_1_) {
		float agescale = age / this.scaleLife;
		if (agescale > 1F) {
			agescale = 2 - agescale;
		}
		quadSize = originalScale * agescale * 0.5F;
		return quadSize;
	}

	public static class RenderType implements ParticleRenderType {

		@Override
		public void begin(BufferBuilder bufferBuilder, TextureManager textureManager) {
			RenderSystem.depthMask(false);
			RenderSystem.enableBlend();
			RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			// TODO: restore blur rendering
			//RenderSystem.alphaFunc(GL11.GL_GREATER, 0.003921569F);
			//RenderSystem.disableLighting();

			//textureManager.bind(TextureAtlas.LOCATION_PARTICLES);
			textureManager.getTexture(TextureAtlas.LOCATION_PARTICLES).setBlurMipmap(true, false);

			bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
		}

		@Override
		public void end(Tesselator tessellator) {
			tessellator.end();
			Minecraft.getInstance().textureManager.getTexture(TextureAtlas.LOCATION_PARTICLES).restoreLastBlurMipmap();
			// TODO: restore blur rendering
			//RenderSystem.alphaFunc(GL11.GL_GREATER, 0.1F);
			RenderSystem.disableBlend();
			RenderSystem.depthMask(true);
		}
	}

}
