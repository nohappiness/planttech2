package net.kaneka.planttech2.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.kaneka.planttech2.gui.guide.GuideScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;

public class GuideCommand
{
    public GuideCommand() {}
    public static ArgumentBuilder<CommandSource, LiteralArgumentBuilder<CommandSource>> register(CommandDispatcher<CommandSource> dispatcher)
    {
        return Commands.literal("guide").executes((context) ->
        {
            DistExecutor.runWhenOn(Dist.CLIENT, () -> GuideCommand::openScreen);
            return 0;
        });
    }

    @OnlyIn(Dist.CLIENT)
    private static void openScreen()
    {
        Minecraft.getInstance().displayGuiScreen(new GuideScreen());
    }
}
