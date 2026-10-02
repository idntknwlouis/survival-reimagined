package net.mcreator.survivalreimagined.compat.sereneseasons;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import sereneseasons.api.season.Season;
import sereneseasons.api.season.SeasonHelper;


public class GetCurrentSeason {
    public static String execute(LevelAccessor world) {
        if (!world.isClientSide()) {
            switch (world) {
                case Level level when SeasonHelper.getSeasonState(level).getSubSeason().getSeason() == Season.SPRING -> {
                    return "Spring";
                }
                case Level level when SeasonHelper.getSeasonState(level).getSubSeason().getSeason() == Season.SUMMER -> {
                    return "Summer";
                }
                case Level level when SeasonHelper.getSeasonState(level).getSubSeason().getSeason() == Season.AUTUMN -> {
                    return "Autumn";
                }
                case Level level when SeasonHelper.getSeasonState(level).getSubSeason().getSeason() == Season.WINTER -> {
                    return "Winter";
                }
                default -> {
                }
            }
        }
        return "Unknown";
    }
}
