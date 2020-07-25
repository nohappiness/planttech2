package net.kaneka.planttech2.items;

import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

import net.kaneka.planttech2.enums.EnumTraitsInt;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DNAContainerItem extends BaseItem
{

	public DNAContainerItem()
	{
		super("dna_container",new Item.Properties().group(ModCreativeTabs.groupmain));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		CompoundNBT nbt = stack.getTag();
		if (nbt != null) {
			if (nbt.contains("type")) {
				tooltip.add(new StringTextComponent(new TranslationTextComponent("info.type").getString() + ": " + nbt.getString("type")));
			}
			if (nbt.contains("temperaturetolerance")) {
				tooltip.add(new StringTextComponent(getTraitColor(nbt, "temperaturetolerance") + new TranslationTextComponent("info.temperaturetolerance").getString() + ": " + nbt.getInt("temperaturetolerance")));
			}
			if (nbt.contains("growspeed")) {
				tooltip.add(new StringTextComponent(getTraitColor(nbt, "growspeed") + new TranslationTextComponent("info.growspeed").getString() + ": " + nbt.getInt("growspeed")));
			}
			if (nbt.contains("sensitivity")) {
				tooltip.add(new StringTextComponent(getTraitColor(nbt, "sensitivity") + new TranslationTextComponent("info.sensitivity").getString() + ": " + nbt.getInt("sensitivity")));
			}
			if (nbt.contains("lightsensitivity")) {
				tooltip.add(new StringTextComponent(getTraitColor(nbt, "lightsensitivity") + new TranslationTextComponent("info.needed_lightlevel").getString() + ": " + (14 - nbt.getInt("lightsensitivity"))));
			}
			if (nbt.contains("watersensitivity")) {
				tooltip.add(new StringTextComponent(getTraitColor(nbt, "watersensitivity") + new TranslationTextComponent("info.waterrange").getString() + ": " + (1 + nbt.getInt("watersensitivity"))));
			}
			if (nbt.contains("productivity")) {
				tooltip.add(new StringTextComponent(getTraitColor(nbt, "productivity") + new TranslationTextComponent("info.productivity").getString() + ": " + nbt.getInt("productivity")));
			}
			if (nbt.contains("fertility")) {
				tooltip.add(new StringTextComponent(getTraitColor(nbt, "fertility") + new TranslationTextComponent("info.fertility").getString() + ": " + nbt.getInt("fertility")));
			}
			if (nbt.contains("spreedingspeed")) {
				tooltip.add(new StringTextComponent(getTraitColor(nbt, "spreedingspeed") + new TranslationTextComponent("info.spreedingspeed").getString() + ": " + nbt.getInt("spreedingspeed")));
			}
			if (nbt.contains("genestrenght")) {
				tooltip.add(new StringTextComponent(getTraitColor(nbt, "genestrenght") + new TranslationTextComponent("info.genestrength").getString() + ": " + nbt.getInt("genestrenght")));
			}
			if (nbt.contains("energyvalue")) {
				tooltip.add(new StringTextComponent(getTraitColor(nbt, "energyvalue") + new TranslationTextComponent("info.energyvalue").getString() + ": " + nbt.getInt("energyvalue") * 20));
			}
		}
	}
	private TextFormatting getTraitColor (CompoundNBT nbt, String trait) {
		if (nbt.getInt(trait) == Objects.requireNonNull(EnumTraitsInt.getByName(trait)).getMax()) {
			return TextFormatting.GREEN;
		}
		if (nbt.getInt(trait) == Objects.requireNonNull(EnumTraitsInt.getByName(trait)).getMin()) {
			return TextFormatting.GRAY;
		}
		return TextFormatting.RESET;
	}
}
