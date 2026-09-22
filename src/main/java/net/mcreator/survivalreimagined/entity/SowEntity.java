package net.mcreator.survivalreimagined.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModEntities;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModSounds;

public class SowEntity extends Animal {
	public SowEntity(EntityType<? extends SowEntity> type, Level level) {
		super(type, level);
		xpReward = 0;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.2D));
		this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 8.0F, 1.0D, 1.2D));
		this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.8D));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SurvivalReimaginedModSounds.AMBIENT_BAOR.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return SurvivalReimaginedModSounds.HIT_BOAR.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SurvivalReimaginedModSounds.DEATH_BOAR.get();
	}

	@Override
	protected void playStepSound(net.minecraft.core.BlockPos pos, net.minecraft.world.level.block.state.BlockState state) {
		this.playSound(SoundEvents.PIG_STEP, 0.15F, 1.0F);
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner) {
		return SurvivalReimaginedModEntities.SOW.get().create(level);
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return false;
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.3D)
				.add(Attributes.MAX_HEALTH, 5.0D)
				.add(Attributes.ARMOR, 0.0D)
				.add(Attributes.ATTACK_DAMAGE, 2.0D)
				.add(Attributes.FOLLOW_RANGE, 16.0D)
				.add(Attributes.STEP_HEIGHT, 0.6D);
	}
}
