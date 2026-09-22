package net.mcreator.survivalreimagined.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModSounds;

public class AlphaCrimsonArachnidEntity extends Spider {
	public AlphaCrimsonArachnidEntity(EntityType<? extends AlphaCrimsonArachnidEntity> type, Level level) {
		super(type, level);
		xpReward = 5;
	}

	@Override
	protected void registerGoals() {
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AlphaCrimsonArachnidEntity.class, false));
		this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.25F));
		this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.25D, true));
		this.targetSelector.addGoal(5, new HurtByTargetGoal(this).setAlertOthers());
		this.goalSelector.addGoal(6, new RandomStrollGoal(this, 0.8D));
		this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 6.0F));
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SurvivalReimaginedModSounds.CRIMSON_ARACHNID_AMBIENT.get();
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(SoundEvents.SPIDER_STEP, 0.15F, 1.0F);
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return SurvivalReimaginedModSounds.CRIMSON_ARACHNID_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SurvivalReimaginedModSounds.CRIMSON_ARACHNID_DEATH.get();
	}

	@Override
	public void awardKillScore(Entity killed, int score, DamageSource source) {
		super.awardKillScore(killed, score, source);
		if (killed instanceof AlphaCrimsonArachnidEntity) {
			setHealth(getMaxHealth());
		}
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.25D)
				.add(Attributes.MAX_HEALTH, 30.0D)
				.add(Attributes.ARMOR, 0.0D)
				.add(Attributes.ATTACK_DAMAGE, 6.0D)
				.add(Attributes.FOLLOW_RANGE, 64.0D)
				.add(Attributes.STEP_HEIGHT, 0.6D);
	}
}
