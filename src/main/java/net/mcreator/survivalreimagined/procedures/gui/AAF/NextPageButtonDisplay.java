package net.mcreator.survivalreimagined.procedures.gui.AAF;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.ScoreHolder;
import net.minecraft.world.scores.Scoreboard;

public class NextPageButtonDisplay {
    public static boolean execute(Entity entity) {
        if (!(entity instanceof Player player)) { return false; }

        Scoreboard scoreboard = player.getScoreboard();
        Objective objective = scoreboard.getObjective("SR_PageNumber");

        if (objective == null) { return true; }

        ScoreHolder holder = ScoreHolder.forNameOnly(player.getScoreboardName());
        int PageNumber = scoreboard.getOrCreatePlayerScore(holder, objective).get();

        return PageNumber <= 1;
    }
}
