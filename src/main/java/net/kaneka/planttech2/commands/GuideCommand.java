package net.kaneka.planttech2.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.kaneka.planttech2.gui.guide.GuideScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class GuideCommand
{
    public GuideCommand() {}
    public static ArgumentBuilder<CommandSource, LiteralArgumentBuilder<CommandSource>> register(CommandDispatcher<CommandSource> dispatcher)
    {
        return Commands.literal("guide").executes((context) ->
        {
            Minecraft.getInstance().displayGuiScreen(new GuideScreen());
            return 0;
        });
    }
}
