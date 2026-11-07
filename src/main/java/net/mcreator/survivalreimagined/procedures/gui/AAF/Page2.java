package net.mcreator.survivalreimagined.procedures.gui.AAF;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.ScoreHolder;
import net.minecraft.world.scores.Scoreboard;

public class Page2 {
    public static boolean execute(Entity entity) {
        if (!(entity instanceof Player player)) { return false; }

        Scoreboard scoreboard = player.getScoreboard();
        Objective objective = scoreboard.getObjective("SR_PageNumber");

        if (objective == null) { return false; }

        ScoreHolder holder = ScoreHolder.forNameOnly(player.getScoreboardName());
        return scoreboard.getOrCreatePlayerScore(holder, objective).get() == 2;
    }
}
