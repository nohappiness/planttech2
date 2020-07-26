package net.kaneka.planttech2.registries;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.kaneka.planttech2.commands.DevListCommand;
import net.kaneka.planttech2.commands.GuideCommand;
import net.kaneka.planttech2.commands.RadiationlevelCommand;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class ModCommands
{
    public ModCommands() {}

    public static void registerAll(CommandDispatcher<CommandSource> dispatcher)
    {
        LiteralArgumentBuilder<CommandSource> pt2command = Commands
                .literal("pt2")
                .then(DevListCommand.register(dispatcher))
                .then(RadiationlevelCommand.register(dispatcher));
//                .then(GuideCommand.register(dispatcher));
        //Client Only Commands - currently unavailable
        /*if (!isDedicatedServer)
        {
            pt2command.then(GuideCommand.register(dispatcher));
        }*/
        dispatcher.register(pt2command);
    }
}
