package net.kaneka.planttech2.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.kaneka.planttech2.gui.guide.GuideScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntitySelector;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class GuideCommand
{
    private static final ArrayList<String> POSSIBLE_OPTIONS = new ArrayList<String>() {{
           add("overview");
           add("plant");
    }};

    public GuideCommand() {}

    public static ArgumentBuilder<CommandSource, LiteralArgumentBuilder<CommandSource>> register(CommandDispatcher<CommandSource> dispatcher)
    {
        // This does nothing, just for auto completion usage
        LiteralArgumentBuilder<CommandSource> command = Commands.literal("guide");
        for(String entry : POSSIBLE_OPTIONS)
            command = command.then(Commands.literal(entry));
        return command;
    }

    @OnlyIn(Dist.CLIENT)
    private static void openScreen()
    {
        Minecraft.getInstance().displayGuiScreen(new GuideScreen());
    }

//    public static class GuideTypeArgument implements ArgumentType<String>
//    {
//        private static final ArrayList<String> POSSIBLE_OPTIONS = new ArrayList<String>() {{
//               add("overview");
//               add("plant");
//        }};
//
//        private static final SimpleCommandExceptionType EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("planttech2.command.argument.guide_type.invalid"));
//
//        public static GuideTypeArgument create()
//        {
//            return new GuideTypeArgument();
//        }
//
//        @Override
//        public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder)
//        {
//            StringReader stringreader = new StringReader(builder.getRemaining());
//            try
//            {
//                stringreader.readString();
//            }
//            catch (CommandSyntaxException commandsyntaxexception)
//            {
//                return builder.buildFuture();
//            }
//            return ISuggestionProvider.suggest(POSSIBLE_OPTIONS, builder.createOffset(builder.getStart() + stringreader.getCursor()));
//        }
//
//        @Override
//        public String parse(StringReader reader) throws CommandSyntaxException
//        {
//            if (POSSIBLE_OPTIONS.contains(reader.readUnquotedString()))
//                return reader.readString();
//            else
//                throw EXCEPTION.create();
//        }
//    }
}
