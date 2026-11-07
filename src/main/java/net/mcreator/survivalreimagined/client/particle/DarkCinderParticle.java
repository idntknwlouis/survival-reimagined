package net.mcreator.survivalreimagined.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.mcreator.survivalreimagined.procedures.particle.DarkCinderParticleVisualScale;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.Level;

@Environment(EnvType.CLIENT)
public class DarkCinderParticle extends TextureSheetParticle {
    public static DarkCinderParticleProvider provider(SpriteSet spriteSet) {
        return new DarkCinderParticleProvider(spriteSet);
    }

    public static class DarkCinderParticleProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public DarkCinderParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new DarkCinderParticle(worldIn, x,y,z,xSpeed,ySpeed,zSpeed, this.spriteSet);
        }
    }

    private final SpriteSet spriteSet;
    private float angularVelocity;
    private float angularAcceleration;

    protected DarkCinderParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x,y, z);
        this.spriteSet = spriteSet;
        this.setSize(0.2f,0.2f);
        this.lifetime = 20;
        this.gravity = 0f;
        this.hasPhysics = false;
        this.xd = vx * 1;
        this.yd = vy * 1;
        this.zd = vz * 1;
        this.angularVelocity = 0.25f;
        this.angularAcceleration = 0f;
        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public float getQuadSize(float scale) {
        Level world = this.level;
        return 0.15f * (float) DarkCinderParticleVisualScale.execute(age);
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.spriteSet);
        this.oRoll = this.roll;
        this.roll += this.angularVelocity;
        this.angularVelocity += this.angularAcceleration;
    }
}
