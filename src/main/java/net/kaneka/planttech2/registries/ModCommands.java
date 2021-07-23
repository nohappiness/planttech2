package net.kaneka.planttech2.registries;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.kaneka.planttech2.commands.DevListCommand;
import net.kaneka.planttech2.commands.GuideCommand;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;

public class ModCommands
{
    public ModCommands() {}

    public static void onCommandRegister(RegisterCommandsEvent event)
    {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        LiteralArgumentBuilder<CommandSourceStack> pt2command = Commands.literal("pt2")
                .then(DevListCommand.register())

                // Temporary disabled as PlantTopia is not implemented yet
//                .then(RadiationLevelCommand.register())

                .then(GuideCommand.register(dispatcher));
//        if (event.getEnvironment() == Commands.EnvironmentType. INTEGRATED)
//            pt2command.then(GuideCommand.register(dispatcher));
        //Client Only Commands - currently unavailable
        /*if (!isDedicatedServer)
        {
            pt2command.then(GuideCommand.register(dispatcher));
        }*/
        dispatcher.register(pt2command);
    }
}
