package net.mcreator.survivalreimagined.compat.sereneseasons;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import sereneseasons.api.season.Season;
import sereneseasons.api.season.SeasonHelper;


public class GetCurrentSeason {
    public static String execute(LevelAccessor world) {
        if (!world.isClientSide()) {
            if (world instanceof Level level && SeasonHelper.getSeasonState(level).getSubSeason().getSeason() == Season.SPRING) {
                return "Spring";
            } else if (world instanceof Level level && SeasonHelper.getSeasonState(level).getSubSeason().getSeason() == Season.SUMMER) {
                return "Summer";
            } else if (world instanceof Level level && SeasonHelper.getSeasonState(level).getSubSeason().getSeason() == Season.AUTUMN) {
                return "Autumn";
            } else if (world instanceof Level level && SeasonHelper.getSeasonState(level).getSubSeason().getSeason() == Season.WINTER) {
                return "Winter";
            }
        }
        return "Unknown";
    }
}
