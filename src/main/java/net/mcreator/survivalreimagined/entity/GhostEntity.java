package net.mcreator.survivalreimagined.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;

public class GhostEntity extends PathfinderMob {
	public GhostEntity(EntityType<? extends GhostEntity> type, Level level) {
		super(type, level);
		xpReward = 0;
		setNoAi(true);
		setNoGravity(true);
		setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(SurvivalReimaginedModItems.BRONZE_HOE.get()));
		this.moveControl = new FlyingMoveControl(this, 10, true);
	}

	@Override
	protected PathNavigation createNavigation(Level level) {
		return new FlyingPathNavigation(this, level);
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.GENERIC_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.GENERIC_DEATH;
	}

	@Override
	public boolean causeFallDamage(float distance, float multiplier, DamageSource source) {
		return false;
	}

	@Override
	public void tick() {
		super.tick();
		setNoGravity(true);
		if (!level().isClientSide()) {
			Player player = level().getNearestPlayer(this, 300.0D);
			if (player != null) {
				getLookControl().setLookAt(player, 30.0F, 30.0F);
				double dx = player.getX() - getX();
				double dz = player.getZ() - getZ();
				setYRot((float)(Math.toDegrees(Math.atan2(dz, dx)) - 90.0D));
				setYHeadRot(getYRot());
			}
		}
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	protected void doPush(Entity entity) {
	}

	@Override
	protected void pushEntities() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.3D)
				.add(Attributes.MAX_HEALTH, 10.0D)
				.add(Attributes.ARMOR, 0.0D)
				.add(Attributes.ATTACK_DAMAGE, 3.0D)
				.add(Attributes.FOLLOW_RANGE, 16.0D)
				.add(Attributes.STEP_HEIGHT, 0.6D)
				.add(Attributes.FLYING_SPEED, 0.3D);
	}
}
