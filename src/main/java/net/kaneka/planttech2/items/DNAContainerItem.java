package net.kaneka.planttech2.items;

import java.util.List;

import javax.annotation.Nullable;

import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
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
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
	CompoundNBT nbt = stack.getTag();
	if (nbt != null)
	{
	    if (nbt.contains("type"))
	    {
		tooltip.add(new StringTextComponent(new TranslationTextComponent("info.type").getUnformattedComponentText() + ": " + nbt.getString("type")));
	    }
	    if (nbt.contains("growspeed"))
	    {
		tooltip.add(new StringTextComponent(new TranslationTextComponent("info.growspeed").getUnformattedComponentText() + ": " + nbt.getInt("growspeed")));
	    }
	    if (nbt.contains("sensitivity"))
	    {
		tooltip.add(new StringTextComponent(new TranslationTextComponent("info.sensitivity").getUnformattedComponentText() + ": " + nbt.getInt("sensitivity")));
	    }
	    if (nbt.contains("lightsensitivity"))
	    {
		tooltip.add(new StringTextComponent(new TranslationTextComponent("info.needed_lightlevel").getUnformattedComponentText() + ": " + (14 - nbt.getInt("lightsensitivity"))));
	    }
	    if (nbt.contains("watersensitivity"))
	    {
		tooltip.add(new StringTextComponent(new TranslationTextComponent("info.waterrange").getUnformattedComponentText() + ": " + (1 + nbt.getInt("watersensitivity"))));
	    }
	    if (nbt.contains("temperaturetolerance"))
	    {
		tooltip.add(new StringTextComponent(new TranslationTextComponent("info.temperaturetolerance").getUnformattedComponentText() + ": " + nbt.getInt("temperaturetolerance")));
	    }
	    if (nbt.contains("productivity"))
	    {
		tooltip.add(new StringTextComponent(new TranslationTextComponent("info.productivity").getUnformattedComponentText() + ": " + nbt.getInt("productivity")));
	    }
	    if (nbt.contains("fertility"))
	    {
		tooltip.add(new StringTextComponent(new TranslationTextComponent("info.fertility").getUnformattedComponentText() + ": " + nbt.getInt("fertility")));
	    }
	    if (nbt.contains("spreedingspeed"))
	    {
		tooltip.add(new StringTextComponent(new TranslationTextComponent("info.spreedingspeed").getUnformattedComponentText() + ": " + nbt.getInt("spreedingspeed")));
	    }
	    if (nbt.contains("genestrength"))
	    {
		tooltip.add(new StringTextComponent(new TranslationTextComponent("info.genestrength").getUnformattedComponentText() + ": " + nbt.getInt("genestrength")));
	    }
	    if (nbt.contains("energyvalue"))
	    {
		tooltip.add(new StringTextComponent(new TranslationTextComponent("info.energyvalue").getUnformattedComponentText() + ": " + nbt.getInt("energyvalue") * 20));
	    }
	}
    }
}
