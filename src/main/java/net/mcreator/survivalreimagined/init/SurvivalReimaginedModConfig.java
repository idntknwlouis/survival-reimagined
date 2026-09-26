package net.mcreator.survivalreimagined.init;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SurvivalReimaginedModConfig {
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("survival_reimagined.json").toFile();

    //Value Storage
    public static ConfigValues VALUES = new ConfigValues();

    public static class ConfigValues {

       // Zombification
       public boolean zombification = true;
       public boolean zombification_immunity = true;
       public double zombification_time = 6000.0;
       public double zombification_chance = 0.25; //(0.0 is 0%, 1.0 is 100%)

       // Paranoia / Fear
       public boolean paranoia_and_fear = true;
       public double darkness_level = 0.0;
       public double light_level = 5.0;
       public double paranoia_to_fear_delay = 1000.0;

       // Binding Sculk
        public boolean binding_sculk = true;

       // BloodMoon
       public boolean blood_moon = true;
       public boolean bloodmoon_sleep = false;
       public boolean disable_normal_mob_spawning = true;
       public double full_moon_chance = 0.15;
       public double gibbous_quarter_chance = 0.1;
       public double new_moon_crescent_chance = 0.075;

       // Mechanic Overrides
        public double block_break_speed = 0.45;
        public boolean vanilla_portal = false;

        // Hunger
        public boolean vanilla_hunger = false;
        public boolean hunger_debuffs = true;
        public boolean vanilla_hunger_effect = false;
        public boolean raw_food = true;
        public boolean food_spoil = true;

        // Experimental Features
        public boolean weight_system = true;
        public boolean bleeding = true;
        public double bleeding_chance = 0.1;
        public boolean broken_legs = true;
        public double broken_leg_chance = 0.2;

        // Lore
        public boolean enables_ghosts = false;
    }

    public static void load() {
        if (!CONFIG_FILE.exists()) {
            save();
            return;
        }
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            VALUES = GSON.fromJson(reader, ConfigValues.class);
            if (VALUES == null) {
                VALUES = new ConfigValues();
            }
        } catch (IOException e) {
            System.err.println("Failed to load Survival Reimagined Config, using defaults.");
            e.printStackTrace();
        }
    }
    public static void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(VALUES, writer);
        } catch (IOException e) {
            System.err.println("Failed to save Survival Reimagined Config.");
            e.printStackTrace();
        }
    }
}
