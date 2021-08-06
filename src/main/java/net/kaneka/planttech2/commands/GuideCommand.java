package net.kaneka.planttech2.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import java.util.List;

public class GuideCommand
{
    private static final List<String> POSSIBLE_OPTIONS = List.of("overview","plant");


    public GuideCommand() {}

    public static ArgumentBuilder<CommandSourceStack, LiteralArgumentBuilder<CommandSourceStack>> register(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        // This does nothing, just for auto completion usage
        //todo remove this when the client command is out
        LiteralArgumentBuilder<CommandSourceStack> command = Commands.literal("guide");
        for(String entry : POSSIBLE_OPTIONS)
            command = command.then(Commands.literal(entry));
        return command;
    }

//    @OnlyIn(Dist.CLIENT)
//    private static void openScreen()
//    {
//        Minecraft.getInstance().displayGuiScreen(new GuideScreen());
//    }

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
