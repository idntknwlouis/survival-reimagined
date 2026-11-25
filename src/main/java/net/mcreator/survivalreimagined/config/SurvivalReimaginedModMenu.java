package net.mcreator.survivalreimagined.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModConfig;
import net.minecraft.network.chat.Component;

public class SurvivalReimaginedModMenu implements ModMenuApi {
    private static Component text(String value) {
        return Component.literal(value);
    }

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(text("Survival Reimagined"));

            ConfigEntryBuilder entries = builder.entryBuilder();
            SurvivalReimaginedModConfig.ConfigValues values = SurvivalReimaginedModConfig.VALUES;

            // Keep the menu grouped by the part of gameplay each setting affects.
            ConfigCategory survival = builder.getOrCreateCategory(text("Survival"));
            ConfigCategory world = builder.getOrCreateCategory(text("World & Events"));
            ConfigCategory playerEffects = builder.getOrCreateCategory(text("Player Effects"));
            ConfigCategory gameplay = builder.getOrCreateCategory(text("Gameplay Rules"));
            ConfigCategory integrations = builder.getOrCreateCategory(text("Integrations"));
            ConfigCategory experimental = builder.getOrCreateCategory(text("Experimental & Lore"));

            // SURVIVAL
            survival.addEntry(entries.startBooleanToggle(text("Food Spoiling"), values.food_spoil)
                    .setDefaultValue(true)
                    .setTooltip(text("Enable food spoilage over time."))
                    .setSaveConsumer(v -> values.food_spoil = v).build());
            survival.addEntry(entries.startBooleanToggle(text("Raw Food Restrictions"), values.raw_food)
                    .setDefaultValue(true)
                    .setTooltip(text("Apply the mod's restrictions to eating raw foods."))
                    .setSaveConsumer(v -> values.raw_food = v).build());
            survival.addEntry(entries.startBooleanToggle(text("Vanilla Hunger"), values.vanilla_hunger)
                    .setDefaultValue(false)
                    .setTooltip(text("Use Minecraft's vanilla hunger behavior instead of the mod's hunger changes."))
                    .setSaveConsumer(v -> values.vanilla_hunger = v).build());
            survival.addEntry(entries.startBooleanToggle(text("Hunger Debuffs"), values.hunger_debuffs)
                    .setDefaultValue(true)
                    .setTooltip(text("Enable negative effects caused by low hunger."))
                    .setSaveConsumer(v -> values.hunger_debuffs = v).build());
            survival.addEntry(entries.startBooleanToggle(text("Vanilla Hunger Effect System"), values.vanilla_hunger_effect)
                    .setDefaultValue(false)
                    .setTooltip(text("Use vanilla's hunger effect handling for hunger-related effects."))
                    .setSaveConsumer(v -> values.vanilla_hunger_effect = v).build());
            survival.addEntry(entries.startBooleanToggle(text("Zombification"), values.zombification)
                    .setDefaultValue(true)
                    .setTooltip(text("Allow the player to become zombified through the mod's mechanics."))
                    .setSaveConsumer(v -> values.zombification = v).build());
            survival.addEntry(entries.startBooleanToggle(text("Zombification Immunity"), values.zombification_immunity)
                    .setDefaultValue(true)
                    .setTooltip(text("Enable the zombification immunity mechanic."))
                    .setSaveConsumer(v -> values.zombification_immunity = v).build());
            survival.addEntry(entries.startDoubleSlider(text("Zombification Time"), values.zombification_time, 0.0, 24000.0)
                    .setDefaultValue(6000.0)
                    .setTooltip(text("Time before zombification completes, in ticks. 20 ticks = 1 second."))
                    .setSaveConsumer(v -> values.zombification_time = v).build());
            survival.addEntry(entries.startDoubleSlider(text("Zombification Chance"), values.zombification_chance, 0.0, 1.0)
                    .setDefaultValue(0.25)
                    .setTooltip(text("Chance from 0% to 100%."))
                    .setSaveConsumer(v -> values.zombification_chance = v).build());
            survival.addEntry(entries.startBooleanToggle(text("Armor Weight"), values.weight_system)
                    .setDefaultValue(true)
                    .setTooltip(text("Enable the armor weight mechanic."))
                    .setSaveConsumer(v -> values.weight_system = v).build());

            // WORLD & EVENTS
            world.addEntry(entries.startBooleanToggle(text("Blood Moon"), values.blood_moon)
                    .setDefaultValue(true)
                    .setTooltip(text("Enable Blood Moon events."))
                    .setSaveConsumer(v -> values.blood_moon = v).build());
            world.addEntry(entries.startBooleanToggle(text("Allow Sleeping During Blood Moon"), values.bloodmoon_sleep)
                    .setDefaultValue(false)
                    .setTooltip(text("Allow players to sleep while a Blood Moon is active."))
                    .setSaveConsumer(v -> values.bloodmoon_sleep = v).build());
            world.addEntry(entries.startBooleanToggle(text("Disable Normal Mob Spawning"), values.disable_normal_mob_spawning)
                    .setDefaultValue(true)
                    .setTooltip(text("Disable regular hostile mob spawning during a Blood Moon."))
                    .setSaveConsumer(v -> values.disable_normal_mob_spawning = v).build());
            world.addEntry(entries.startDoubleSlider(text("Full Moon Chance"), values.full_moon_chance, 0.0, 1.0)
                    .setDefaultValue(0.15)
                    .setTooltip(text("Chance from 0% to 100% for the full-moon event roll."))
                    .setSaveConsumer(v -> values.full_moon_chance = v).build());
            world.addEntry(entries.startDoubleSlider(text("Gibbous / Quarter Chance"), values.gibbous_quarter_chance, 0.0, 1.0)
                    .setDefaultValue(0.1)
                    .setTooltip(text("Chance from 0% to 100% for the gibbous or quarter-moon event roll."))
                    .setSaveConsumer(v -> values.gibbous_quarter_chance = v).build());
            world.addEntry(entries.startDoubleSlider(text("New Moon / Crescent Chance"), values.new_moon_crescent_chance, 0.0, 1.0)
                    .setDefaultValue(0.075)
                    .setTooltip(text("Chance from 0% to 100% for the new-moon or crescent event roll."))
                    .setSaveConsumer(v -> values.new_moon_crescent_chance = v).build());
            world.addEntry(entries.startBooleanToggle(text("Binding Sculk"), values.binding_sculk)
                    .setDefaultValue(true)
                    .setTooltip(text("Enable the Binding Sculk changes."))
                    .setSaveConsumer(v -> values.binding_sculk = v).build());

            // PLAYER EFFECTS
            playerEffects.addEntry(entries.startBooleanToggle(text("Paranoia & Fear"), values.paranoia_and_fear)
                    .setDefaultValue(true)
                    .setTooltip(text("Enable the paranoia and fear mechanics."))
                    .setSaveConsumer(v -> values.paranoia_and_fear = v).build());
            playerEffects.addEntry(entries.startDoubleSlider(text("Darkness Level Trigger"), values.darkness_level, 0.0, 15.0)
                    .setDefaultValue(0.0)
                    .setTooltip(text("Darkness level at which paranoia can begin."))
                    .setSaveConsumer(v -> values.darkness_level = v).build());
            playerEffects.addEntry(entries.startDoubleSlider(text("Light Level Limit"), values.light_level, 0.0, 15.0)
                    .setDefaultValue(5.0)
                    .setTooltip(text("Maximum light level for the darkness-related trigger."))
                    .setSaveConsumer(v -> values.light_level = v).build());
            playerEffects.addEntry(entries.startDoubleSlider(text("Paranoia to Fear Delay"), values.paranoia_to_fear_delay, 0.0, 12000.0)
                    .setDefaultValue(1000.0)
                    .setTooltip(text("Delay before paranoia escalates into fear, in ticks."))
                    .setSaveConsumer(v -> values.paranoia_to_fear_delay = v).build());
            playerEffects.addEntry(entries.startBooleanToggle(text("Bleeding"), values.bleeding)
                    .setDefaultValue(true)
                    .setTooltip(text("Enable the bleeding mechanic."))
                    .setSaveConsumer(v -> values.bleeding = v).build());
            playerEffects.addEntry(entries.startDoubleSlider(text("Bleeding Chance"), values.bleeding_chance, 0.0, 1.0)
                    .setDefaultValue(0.1)
                    .setTooltip(text("Chance from 0% to 100% to trigger bleeding."))
                    .setSaveConsumer(v -> values.bleeding_chance = v).build());
            playerEffects.addEntry(entries.startBooleanToggle(text("Broken Legs"), values.broken_legs)
                    .setDefaultValue(true)
                    .setTooltip(text("Enable the broken-leg injury mechanic."))
                    .setSaveConsumer(v -> values.broken_legs = v).build());
            playerEffects.addEntry(entries.startDoubleSlider(text("Broken Leg Chance"), values.broken_leg_chance, 0.0, 1.0)
                    .setDefaultValue(0.2)
                    .setTooltip(text("Chance from 0% to 100% to trigger a broken-leg injury."))
                    .setSaveConsumer(v -> values.broken_leg_chance = v).build());

            // GAMEPLAY RULES
            gameplay.addEntry(entries.startDoubleSlider(text("Block Break Speed Modifier"), values.block_break_speed, 0.0, 2.0)
                    .setDefaultValue(0.45)
                    .setTooltip(text("Multiplier applied to the mod's block-breaking speed adjustment."))
                    .setSaveConsumer(v -> values.block_break_speed = v).build());
            gameplay.addEntry(entries.startBooleanToggle(text("Allow Vanilla Portals"), values.vanilla_portal)
                    .setDefaultValue(false)
                    .setTooltip(text("Allow vanilla portal behavior."))
                    .setSaveConsumer(v -> values.vanilla_portal = v).build());

            // INTEGRATIONS
            integrations.addEntry(entries.startBooleanToggle(text("Require Sneaking for Machine Details"), values.jade_machine_details_require_sneak)
                    .setDefaultValue(true)
                    .setTooltip(text("Require holding Shift to display additional machine information in Jade."))
                    .setSaveConsumer(v -> values.jade_machine_details_require_sneak = v).build());
            integrations.addEntry(entries.startBooleanToggle(text("Show Soil Nutrients in Jade"), values.jade_soil_nutrients)
                    .setDefaultValue(true)
                    .setTooltip(text("Display nitrogen, phosphorus and potassium values on fertility soil and planted crops."))
                    .setSaveConsumer(v -> values.jade_soil_nutrients = v).build());

            // EXPERIMENTAL & LORE
            experimental.addEntry(entries.startBooleanToggle(text("Ghosts"), values.enables_ghosts)
                    .setDefaultValue(false)
                    .setTooltip(text("Enable ghost-related content."))
                    .setSaveConsumer(v -> values.enables_ghosts = v).build());

            builder.setSavingRunnable(SurvivalReimaginedModConfig::save);
            return builder.build();
        };
    }
}
