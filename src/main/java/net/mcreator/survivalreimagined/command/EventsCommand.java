package net.mcreator.survivalreimagined.command;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerLevel;

public class EventsCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(Commands.literal("event")
                    .requires(source -> source.hasPermission(2))
                    .then(Commands.literal("reset").executes(arguments -> {
                        ServerLevel world = arguments.getSource().getLevel();

                        EventsReset.execute(world);
                        return 0;
                    }))
                    .then(Commands.literal("bloodmoon").executes(arguments -> {
                        ServerLevel world = arguments.getSource().getLevel();

                        BloodMoonCommand.execute(world);
                        return 0;
                    }))
            );
        });
    }
}
