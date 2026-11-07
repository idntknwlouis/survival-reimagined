package net.mcreator.survivalreimagined.util;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import net.mcreator.survivalreimagined.entity.BoarEntity;
import net.mcreator.survivalreimagined.entity.PigletEntity;
import net.mcreator.survivalreimagined.entity.SowEntity;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModSounds;

public final class PigFamilyAlarm {
	private PigFamilyAlarm() {
	}

	public static void alertNearby(Entity source) {
		Level level = source.level();
		if (level.isClientSide()) {
			return;
		}

		AABB area = source.getBoundingBox().inflate(4.0D);
		for (Entity nearby : level.getEntities(source, area,
				entity -> entity instanceof BoarEntity || entity instanceof SowEntity || entity instanceof PigletEntity)) {
			if (nearby instanceof PigletEntity) {
				level.playSound(null, nearby.blockPosition(), SurvivalReimaginedModSounds.SQUEAL_PIGLET.get(),
						SoundSource.AMBIENT, 1.0F, 1.0F);
			} else {
				level.playSound(null, nearby.blockPosition(), SurvivalReimaginedModSounds.SQUEAL_BOAR.get(),
						SoundSource.AMBIENT, 1.0F, 1.0F);
			}
		}

		if (source instanceof PigletEntity) {
			level.playSound(null, source.blockPosition(), SurvivalReimaginedModSounds.SQUEAL_PIGLET.get(),
					SoundSource.AMBIENT, 1.0F, 1.0F);
		} else {
			level.playSound(null, source.blockPosition(), SurvivalReimaginedModSounds.SQUEAL_BOAR.get(),
					SoundSource.AMBIENT, 1.0F, 1.0F);
		}
	}
}
