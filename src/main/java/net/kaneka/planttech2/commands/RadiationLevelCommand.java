package net.kaneka.planttech2.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.kaneka.planttech2.entities.capabilities.player.RadiationEffect;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TextComponent;

import static com.mojang.brigadier.arguments.FloatArgumentType.floatArg;
import static com.mojang.brigadier.arguments.FloatArgumentType.getFloat;
import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class RadiationLevelCommand
{
	public RadiationLevelCommand() {}

	public static LiteralArgumentBuilder<CommandSourceStack> register()
	{
		return literal("radiationlevel")
				.then(literal("add")
				.then(argument("amount", floatArg(-2, 2))
						.executes((context) -> add(context, getFloat(context, "amount")))))
				.then(literal("set")
				.then(argument("value", floatArg(0, 2))
						.executes((context) -> set(context, getFloat(context, "value")))))
				.then(literal("clear")
						.executes((context) -> set(context, 0)))
				.then(literal("full")
						.executes((context) -> set(context, 2)));
	}

	private static int add(CommandContext<CommandSourceStack> context, float amount) throws CommandSyntaxException
	{
		RadiationEffect.getCap(context.getSource().getPlayerOrException()).changeLevel(amount);
		context.getSource().sendSuccess(new TextComponent((amount >= 0 ? "added " : "removed ") + amount + " radiation level on ").append(context.getSource().getDisplayName()), false);
		return (int) amount;
	}

	private static int set(CommandContext<CommandSourceStack> context, float amount) throws CommandSyntaxException
	{
		RadiationEffect.getCap(context.getSource().getPlayerOrException()).setLevel(amount);
		context.getSource().sendSuccess(new TextComponent("set ").append(context.getSource().getDisplayName()).append("'s radiation to " + amount), false);
		return (int) amount;
	}
}
