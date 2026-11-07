package net.mcreator.survivalreimagined.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModConfig;
import net.minecraft.network.chat.Component;

public class SurvivalReimaginedModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            ConfigBuilder builder = ConfigBuilder.create().setParentScreen(parent).setTitle(Component.literal("Survival Reimagined Config"));

            ConfigEntryBuilder entryBuilder = builder.entryBuilder();
            SurvivalReimaginedModConfig.ConfigValues values = SurvivalReimaginedModConfig.VALUES;

            // Categories
            ConfigCategory zombification = builder.getOrCreateCategory(Component.literal("Zombification"));
            ConfigCategory darkness = builder.getOrCreateCategory(Component.literal("Paranoia/Fear"));
            ConfigCategory sculk = builder.getOrCreateCategory(Component.literal("Sculk Changes"));
            ConfigCategory bloodmoon = builder.getOrCreateCategory(Component.literal("Blood Moon"));
            ConfigCategory overrides = builder.getOrCreateCategory(Component.literal("Mechanic Overrides"));
            ConfigCategory hunger = builder.getOrCreateCategory(Component.literal("Hunger Changes"));
            ConfigCategory experimental = builder.getOrCreateCategory(Component.literal("Experimental Features"));
            ConfigCategory lore = builder.getOrCreateCategory(Component.literal("Lore Content"));


            // ZOMBIFICATION

            zombification.addEntry(entryBuilder.startBooleanToggle(Component.literal("Enable Zombification"), values.zombification).setDefaultValue(true).setSaveConsumer(v -> values.zombification = v).build());
            zombification.addEntry(entryBuilder.startBooleanToggle(Component.literal("Zombification Immunity"), values.zombification_immunity).setDefaultValue(true).setSaveConsumer(v -> values.zombification_immunity = v).build());
            zombification.addEntry(entryBuilder.startDoubleField(Component.literal("Zombification Timer"), values.zombification_time).setDefaultValue(6000.0).setSaveConsumer(v -> values.zombification_time = v).build());
            zombification.addEntry(entryBuilder.startDoubleField(Component.literal("Zombification Chance (0.0 - 1.0)"), values.zombification_chance).setDefaultValue(0.25).setSaveConsumer(v -> values.zombification_chance = v).build());

            // PARANOIA & FEAR

            darkness.addEntry(entryBuilder.startBooleanToggle(Component.literal("Enable Paranoia & Fear"), values.paranoia_and_fear).setDefaultValue(true).setSaveConsumer(v -> values.paranoia_and_fear = v).build());
            darkness.addEntry(entryBuilder.startDoubleField(Component.literal("Darkness Level Trigger"), values.darkness_level).setDefaultValue(0.0).setSaveConsumer(v -> values.darkness_level = v).build());
            darkness.addEntry(entryBuilder.startDoubleField(Component.literal("Light Level Limit"), values.light_level).setDefaultValue(5.0).setSaveConsumer(v -> values.light_level = v).build());
            darkness.addEntry(entryBuilder.startDoubleField(Component.literal("Paranoia to Fear Delay"), values.paranoia_to_fear_delay).setDefaultValue(1000.0).setSaveConsumer(v -> values.paranoia_to_fear_delay = v).build());

            // SCULK

            sculk.addEntry(entryBuilder.startBooleanToggle(Component.literal("Enable Binding Sculk"), values.binding_sculk).setDefaultValue(true).setSaveConsumer(v -> values.binding_sculk = v).build());

            // BLOOD MOON

            bloodmoon.addEntry(entryBuilder.startBooleanToggle(Component.literal("Enable Blood Moon"), values.blood_moon).setDefaultValue(true).setSaveConsumer(v -> values.blood_moon = v).build());
            bloodmoon.addEntry(entryBuilder.startBooleanToggle(Component.literal("Allow Sleeping During Blood Moon"), values.bloodmoon_sleep).setDefaultValue(true).setSaveConsumer(v -> values.bloodmoon_sleep = v).build());
            bloodmoon.addEntry(entryBuilder.startBooleanToggle(Component.literal("Disable Normal Mob Spawning during Blood Moon"), values.disable_normal_mob_spawning).setDefaultValue(true).setSaveConsumer(v -> values.disable_normal_mob_spawning = v).build());
            bloodmoon.addEntry(entryBuilder.startDoubleField(Component.literal("Full Moon Chance"), values.full_moon_chance).setDefaultValue(0.15).setSaveConsumer(v -> values.full_moon_chance = v).build());
            bloodmoon.addEntry(entryBuilder.startDoubleField(Component.literal("Gibbous / Quarter Chance"), values.gibbous_quarter_chance).setDefaultValue(0.1).setSaveConsumer(v -> values.gibbous_quarter_chance = v).build());
            bloodmoon.addEntry(entryBuilder.startDoubleField(Component.literal("New Moon / Crescent Chance"), values.new_moon_crescent_chance).setDefaultValue(0.075).setSaveConsumer(v -> values.new_moon_crescent_chance = v).build());

            // MECHANIC OVERRIDES

            overrides.addEntry(entryBuilder.startDoubleField(Component.literal("Block Break Speed Modifier"), values.block_break_speed).setDefaultValue(0.45).setSaveConsumer(v -> values.block_break_speed = v).build());
            overrides.addEntry(entryBuilder.startBooleanToggle(Component.literal("Allow Vanilla Portals"), values.vanilla_portal).setDefaultValue(false).setSaveConsumer(v -> values.vanilla_portal = v).build());

            // HUNGER

            hunger.addEntry(entryBuilder.startBooleanToggle(Component.literal("Use Vanilla Hunger"), values.vanilla_hunger).setDefaultValue(false).setSaveConsumer(v -> values.vanilla_hunger = v).build());
            hunger.addEntry(entryBuilder.startBooleanToggle(Component.literal("Enable Hunger Debuffs"), values.hunger_debuffs).setDefaultValue(true).setSaveConsumer(v -> values.hunger_debuffs = v).build());
            hunger.addEntry(entryBuilder.startBooleanToggle(Component.literal("Use Vanilla Hunger Effect System"), values.vanilla_hunger_effect).setDefaultValue(false).setSaveConsumer(v -> values.vanilla_hunger_effect = v).build());
            hunger.addEntry(entryBuilder.startBooleanToggle(Component.literal("Raw Food Restrictions"), values.raw_food).setDefaultValue(true).setSaveConsumer(v -> values.raw_food = v).build());
            hunger.addEntry(entryBuilder.startBooleanToggle(Component.literal("Food Spoiling Mechanics"), values.food_spoil).setDefaultValue(true).setSaveConsumer(v -> values.food_spoil= v).build());

            // EXPERIMENTAL FEATURES

            experimental.addEntry(entryBuilder.startBooleanToggle(Component.literal("Enable Bleeding"), values.bleeding).setDefaultValue(true).setSaveConsumer(v -> values.bleeding = v).build());
            experimental.addEntry(entryBuilder.startDoubleField(Component.literal("Bleeding Chance Trigger"), values.bleeding_chance).setDefaultValue(0.1).setSaveConsumer(v -> values.bleeding_chance = v).build());
            experimental.addEntry(entryBuilder.startBooleanToggle(Component.literal("Enable Broken Legs"), values.broken_legs).setDefaultValue(true).setSaveConsumer(v -> values.broken_legs = v).build());
            experimental.addEntry(entryBuilder.startDoubleField(Component.literal("Broken Injury Leg Chance"), values.broken_leg_chance).setDefaultValue(0.2).setSaveConsumer(v -> values.broken_leg_chance = v).build());

            // LORE

            lore.addEntry(entryBuilder.startBooleanToggle(Component.literal("Enables Ghosts"), values.enables_ghosts).setDefaultValue(false).setSaveConsumer(v -> values.enables_ghosts =v).build());

            builder.setSavingRunnable(SurvivalReimaginedModConfig::save);
            return builder.build();
        };
    }
}
