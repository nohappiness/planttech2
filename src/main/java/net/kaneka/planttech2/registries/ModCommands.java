package net.kaneka.planttech2.registries;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.kaneka.planttech2.commands.DevListCommand;
import net.kaneka.planttech2.commands.GuideCommand;
import net.kaneka.planttech2.commands.RadiationlevelCommand;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModCommands
{
    public ModCommands() {}

    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event)
    {
        CommandDispatcher<CommandSource> dispatcher = event.getDispatcher();
        LiteralArgumentBuilder<CommandSource> pt2command = Commands
                .literal("pt2")
                .then(DevListCommand.register(dispatcher))
                .then(RadiationlevelCommand.register(dispatcher))
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
