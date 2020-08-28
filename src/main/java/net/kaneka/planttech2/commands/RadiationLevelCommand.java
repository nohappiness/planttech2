package net.kaneka.planttech2.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.kaneka.planttech2.entities.capabilities.player.RadiationEffect;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.StringTextComponent;

import static com.mojang.brigadier.arguments.FloatArgumentType.floatArg;
import static com.mojang.brigadier.arguments.FloatArgumentType.getFloat;
import static net.minecraft.command.Commands.argument;
import static net.minecraft.command.Commands.literal;

public class RadiationLevelCommand
{
	public RadiationLevelCommand() {}

	public static LiteralArgumentBuilder<CommandSource> register()
	{
		// @formatter:off
		return literal("radiationlevel")
			.then(
				literal("add")
					.then(
						argument("amount", floatArg(-2, 2))
							.executes((context) -> add(context, getFloat(context, "amount")))
					)
			).then(
				literal("set")
					.then(
						argument("value", floatArg(0, 2))
							.executes((context) -> set(context, getFloat(context, "value")))
					)
			).then(
				literal("clear")
					.executes((context) -> set(context, 0))
			).then(
				literal("full")
					.executes((context) -> set(context, 2))
			);
		// @formatter:on
	}

	private static int add(CommandContext<CommandSource> context, float amount) throws CommandSyntaxException
	{
		RadiationEffect.getCap(context.getSource().asPlayer()).changeLevel(amount);
		context.getSource().sendFeedback(new StringTextComponent((amount >= 0 ? "added " : "removed ") + amount + " radiation level on ").append(context.getSource().getDisplayName()), false);
		return (int) amount;
	}

	private static int set(CommandContext<CommandSource> context, float amount) throws CommandSyntaxException
	{
		RadiationEffect.getCap(context.getSource().asPlayer()).setLevel(amount);
		context.getSource().sendFeedback(new StringTextComponent("set ").append(context.getSource().getDisplayName()).appendString("'s radiation to " + amount), false);
		return (int) amount;
	}
}
