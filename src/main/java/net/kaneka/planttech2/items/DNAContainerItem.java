package net.kaneka.planttech2.items;

import net.kaneka.planttech2.enums.EnumTraitsInt;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class DNAContainerItem extends Item
{

	public DNAContainerItem()
	{
		super(new Item.Properties().tab(ModCreativeTabs.MAIN));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flagIn) {
		CompoundTag nbt = stack.getTag();
		if (nbt != null) {
			if (nbt.contains("type")) {
				tooltip.add(new TextComponent(new TranslatableComponent("info.type").getString() + ": " + nbt.getString("type")));
			}
			if (nbt.contains("temperaturetolerance")) {
				tooltip.add(new TextComponent(getTraitColor(nbt, "temperaturetolerance") + new TranslatableComponent("info.temperaturetolerance").getString() + ": " + nbt.getInt("temperaturetolerance")));
			}
			if (nbt.contains("growspeed")) {
				tooltip.add(new TextComponent(getTraitColor(nbt, "growspeed") + new TranslatableComponent("info.growspeed").getString() + ": " + nbt.getInt("growspeed")));
			}
			if (nbt.contains("sensitivity")) {
				tooltip.add(new TextComponent(getTraitColor(nbt, "sensitivity") + new TranslatableComponent("info.sensitivity").getString() + ": " + nbt.getInt("sensitivity")));
			}
			if (nbt.contains("lightsensitivity")) {
				tooltip.add(new TextComponent(getTraitColor(nbt, "lightsensitivity") + new TranslatableComponent("info.needed_lightlevel").getString() + ": " + (14 - nbt.getInt("lightsensitivity"))));
			}
			if (nbt.contains("watersensitivity")) {
				tooltip.add(new TextComponent(getTraitColor(nbt, "watersensitivity") + new TranslatableComponent("info.waterrange").getString() + ": " + (1 + nbt.getInt("watersensitivity"))));
			}
			if (nbt.contains("productivity")) {
				tooltip.add(new TextComponent(getTraitColor(nbt, "productivity") + new TranslatableComponent("info.productivity").getString() + ": " + nbt.getInt("productivity")));
			}
			if (nbt.contains("fertility")) {
				tooltip.add(new TextComponent(getTraitColor(nbt, "fertility") + new TranslatableComponent("info.fertility").getString() + ": " + nbt.getInt("fertility")));
			}
			if (nbt.contains("spreedingspeed")) {
				tooltip.add(new TextComponent(getTraitColor(nbt, "spreedingspeed") + new TranslatableComponent("info.spreedingspeed").getString() + ": " + nbt.getInt("spreedingspeed")));
			}
			if (nbt.contains("genestrenght")) {
				tooltip.add(new TextComponent(getTraitColor(nbt, "genestrenght") + new TranslatableComponent("info.genestrength").getString() + ": " + nbt.getInt("genestrenght")));
			}
			if (nbt.contains("energyvalue")) {
				tooltip.add(new TextComponent(getTraitColor(nbt, "energyvalue") + new TranslatableComponent("info.energyvalue").getString() + ": " + nbt.getInt("energyvalue") * 20));
			}
		}
	}
	private ChatFormatting getTraitColor (CompoundTag nbt, String trait) {
		if (nbt.getInt(trait) == Objects.requireNonNull(EnumTraitsInt.getByName(trait)).getMax()) {
			return ChatFormatting.GREEN;
		}
		if (nbt.getInt(trait) == Objects.requireNonNull(EnumTraitsInt.getByName(trait)).getMin()) {
			return ChatFormatting.GRAY;
		}
		return ChatFormatting.RESET;
	}
}
