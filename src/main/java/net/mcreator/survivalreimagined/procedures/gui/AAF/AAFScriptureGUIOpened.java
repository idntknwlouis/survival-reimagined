package net.mcreator.survivalreimagined.procedures.gui.AAF;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.ScoreHolder;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;

public class AAFScriptureGUIOpened {
    public static void execute(LevelAccessor level, double x, double y, double z, Entity entity) {
        if (entity == null) {
            return;
        }
        if (!level.isClientSide()) {
            level.playSound(null, entity.blockPosition(), SoundEvents.ARMOR_EQUIP_LEATHER.value(), SoundSource.PLAYERS, 1.0f, 1.0f);
            level.playSound(null, entity.blockPosition(), SoundEvents.BOOK_PAGE_TURN, SoundSource.PLAYERS, 0.8f, 1.0f);
        }
        if (!(entity instanceof Player player) || level.isClientSide()) {
            return;
        }
        Scoreboard scoreboard = player.getScoreboard();
        Objective objective = scoreboard.getObjective("SR_PageNumber");

        if (objective == null) {
            objective = scoreboard.addObjective(
                    "SR_PageNumber",
                    ObjectiveCriteria.DUMMY,
                    Component.literal("Page Number"),
                    ObjectiveCriteria.RenderType.INTEGER,
                    false,
                    null
            );
        }

        ScoreHolder holder = ScoreHolder.forNameOnly(player.getScoreboardName());
        var score = scoreboard.getOrCreatePlayerScore(holder, objective);

        score.set(1);
    }
}
