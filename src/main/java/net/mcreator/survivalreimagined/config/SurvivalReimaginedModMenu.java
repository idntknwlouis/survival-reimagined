package net.mcreator.survivalreimagined.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.DoubleSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModConfig;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class SurvivalReimaginedModMenu implements ModMenuApi {
    private static Component text(String value) {
        return Component.literal(value);
    }

    private static Option<Boolean> bool(String name, String description, boolean defaultValue,
                                        Supplier<Boolean> getter, Consumer<Boolean> setter) {
        return Option.<Boolean>createBuilder()
                .name(text(name))
                .description(OptionDescription.of(text(description)))
                .binding(defaultValue, getter, setter)
                .controller(TickBoxControllerBuilder::create)
                .build();
    }

    private static Option<Double> decimal(String name, String description, double defaultValue,
                                          double min, double max, double step,
                                          Supplier<Double> getter, Consumer<Double> setter) {
        return Option.<Double>createBuilder()
                .name(text(name))
                .description(OptionDescription.of(text(description)))
                .binding(defaultValue, getter, setter)
                .controller(option -> DoubleSliderControllerBuilder.create(option)
                        .range(min, max)
                        .step(step))
                .build();
    }

    private static OptionGroup group(String name, String description, Option<?>... options) {
        OptionGroup.Builder builder = OptionGroup.createBuilder()
                .name(text(name))
                .description(OptionDescription.of(text(description)));
        for (Option<?> option : options) {
            builder.option(option);
        }
        return builder.build();
    }

    private static ConfigCategory.Builder category(String name, String description) {
        return ConfigCategory.createBuilder()
                .name(text(name))
                .tooltip(text(description));
    }

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            SurvivalReimaginedModConfig.ConfigValues values = SurvivalReimaginedModConfig.VALUES;

            ConfigCategory survival = category("Survival", "Food, hunger, and core survival mechanics.")
                    .group(group("Hunger & Food", "Food handling and hunger behavior.",
                            bool("Food Spoiling", "Enable food spoilage over time.", true,
                                    () -> values.food_spoil, v -> values.food_spoil = v),
                            bool("Raw Food Restrictions", "Apply the mod's restrictions to eating raw foods.", true,
                                    () -> values.raw_food, v -> values.raw_food = v),
                            bool("Vanilla Hunger", "Use Minecraft's vanilla hunger behavior instead of the mod's hunger changes.", false,
                                    () -> values.vanilla_hunger, v -> values.vanilla_hunger = v),
                            bool("Hunger Debuffs", "Enable negative effects caused by low hunger.", true,
                                    () -> values.hunger_debuffs, v -> values.hunger_debuffs = v),
                            bool("Vanilla Hunger Effect System", "Use vanilla's hunger effect handling for hunger-related effects.", false,
                                    () -> values.vanilla_hunger_effect, v -> values.vanilla_hunger_effect = v)))
                    .group(group("Zombification", "Settings for the zombification mechanic.",
                            bool("Enable Zombification", "Allow the player to become zombified through the mod's mechanics.", true,
                                    () -> values.zombification, v -> values.zombification = v),
                            bool("Zombification Immunity", "Enable the zombification immunity mechanic.", true,
                                    () -> values.zombification_immunity, v -> values.zombification_immunity = v),
                            decimal("Zombification Time", "Time before zombification completes, in ticks (20 ticks = 1 second).", 6000.0, 0.0, 24000.0, 100.0,
                                    () -> values.zombification_time, v -> values.zombification_time = v),
                            decimal("Zombification Chance", "Chance from 0% to 100%.", 0.25, 0.0, 1.0, 0.01,
                                    () -> values.zombification_chance, v -> values.zombification_chance = v)))
                    .group(group("Armor", "Equipment weight mechanics.",
                            bool("Armor Weight", "Enable the armor weight mechanic.", true,
                                    () -> values.weight_system, v -> values.weight_system = v)))
                    .build();

            ConfigCategory world = category("World & Events", "World events, moon phases, and environmental changes.")
                    .group(group("Blood Moon", "Control the Blood Moon event and its moon-phase chances.",
                            bool("Enable Blood Moon", "Enable Blood Moon events.", true,
                                    () -> values.blood_moon, v -> values.blood_moon = v),
                            bool("Allow Sleeping During Blood Moon", "Allow players to sleep while a Blood Moon is active.", false,
                                    () -> values.bloodmoon_sleep, v -> values.bloodmoon_sleep = v),
                            bool("Disable Normal Mob Spawning", "Disable regular hostile mob spawning during a Blood Moon.", true,
                                    () -> values.disable_normal_mob_spawning, v -> values.disable_normal_mob_spawning = v),
                            decimal("Full Moon Chance", "Chance from 0% to 100% for the full-moon event roll.", 0.15, 0.0, 1.0, 0.01,
                                    () -> values.full_moon_chance, v -> values.full_moon_chance = v),
                            decimal("Gibbous / Quarter Chance", "Chance from 0% to 100% for the gibbous or quarter-moon event roll.", 0.1, 0.0, 1.0, 0.01,
                                    () -> values.gibbous_quarter_chance, v -> values.gibbous_quarter_chance = v),
                            decimal("New Moon / Crescent Chance", "Chance from 0% to 100% for the new-moon or crescent event roll.", 0.075, 0.0, 1.0, 0.01,
                                    () -> values.new_moon_crescent_chance, v -> values.new_moon_crescent_chance = v)))
                    .group(group("Sculk", "Changes related to Binding Sculk.",
                            bool("Binding Sculk", "Enable the Binding Sculk changes.", true,
                                    () -> values.binding_sculk, v -> values.binding_sculk = v)))
                    .build();

            ConfigCategory playerEffects = category("Player Effects", "Status effects and injuries affecting the player.")
                    .group(group("Paranoia & Fear", "Darkness-based paranoia and fear behavior.",
                            bool("Enable Paranoia & Fear", "Enable the paranoia and fear mechanics.", true,
                                    () -> values.paranoia_and_fear, v -> values.paranoia_and_fear = v),
                            decimal("Darkness Level Trigger", "Darkness level at which paranoia can begin.", 0.0, 0.0, 15.0, 1.0,
                                    () -> values.darkness_level, v -> values.darkness_level = v),
                            decimal("Light Level Limit", "Maximum light level for the darkness-related trigger.", 5.0, 0.0, 15.0, 1.0,
                                    () -> values.light_level, v -> values.light_level = v),
                            decimal("Paranoia to Fear Delay", "Delay before paranoia escalates into fear, in ticks.", 1000.0, 0.0, 12000.0, 20.0,
                                    () -> values.paranoia_to_fear_delay, v -> values.paranoia_to_fear_delay = v)))
                    .group(group("Bleeding", "Settings for the bleeding mechanic.",
                            bool("Enable Bleeding", "Enable the bleeding mechanic.", true,
                                    () -> values.bleeding, v -> values.bleeding = v),
                            decimal("Bleeding Chance", "Chance from 0% to 100% to trigger bleeding.", 0.1, 0.0, 1.0, 0.01,
                                    () -> values.bleeding_chance, v -> values.bleeding_chance = v)))
                    .group(group("Broken Legs", "Settings for leg injuries.",
                            bool("Enable Broken Legs", "Enable the broken-leg injury mechanic.", true,
                                    () -> values.broken_legs, v -> values.broken_legs = v),
                            decimal("Broken Leg Chance", "Chance from 0% to 100% to trigger a broken-leg injury.", 0.2, 0.0, 1.0, 0.01,
                                    () -> values.broken_leg_chance, v -> values.broken_leg_chance = v)))
                    .build();

            ConfigCategory gameplay = category("Gameplay Rules", "Overrides that change how vanilla gameplay behaves.")
                    .group(group("Mechanic Overrides", "Adjust global gameplay overrides.",
                            decimal("Block Break Speed Modifier", "Multiplier applied to the mod's block-breaking speed adjustment.", 0.45, 0.0, 2.0, 0.01,
                                    () -> values.block_break_speed, v -> values.block_break_speed = v),
                            bool("Allow Vanilla Portals", "Allow vanilla portal behavior.", false,
                                    () -> values.vanilla_portal, v -> values.vanilla_portal = v)))
                    .build();

            ConfigCategory integrations = category("Integrations", "Configure compatibility features provided by other mods.")
                    .group(group("Jade", "Configure information displayed by Jade.",
                            bool("Require Sneaking for Machine Details", "Require holding Shift to display additional machine information in Jade.", true,
                                    () -> values.jade_machine_details_require_sneak, v -> values.jade_machine_details_require_sneak = v),
                            bool("Display Soil Nutrients in Jade", "Choose where soil nutrients appear. Enabled: show NPK in Jade's block-info panel. Disabled: show NPK beside the crosshair instead.", true,
                                    () -> values.jade_soil_nutrients, v -> values.jade_soil_nutrients = v)))
                    .build();

            ConfigCategory experimental = category("Experimental & Lore", "Experimental mechanics and optional lore content.")
                    .group(group("Lore", "Optional lore-related content.",
                            bool("Ghosts", "Enable ghost-related content.", false,
                                    () -> values.enables_ghosts, v -> values.enables_ghosts = v)))
                    .build();

            return YetAnotherConfigLib.createBuilder()
                    .title(text("Survival Reimagined"))
                    .category(survival)
                    .category(world)
                    .category(playerEffects)
                    .category(gameplay)
                    .category(integrations)
                    .category(experimental)
                    .save(SurvivalReimaginedModConfig::save)
                    .build()
                    .generateScreen(parent);
        };
    }
}
