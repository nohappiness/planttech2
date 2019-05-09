package net.kaneka.planttech2.items;

import java.util.List;

import javax.annotation.Nullable;

import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemDNAContainer extends ItemBase
{

    public ItemDNAContainer()
    {
	super("dna_container",new Item.Properties().group(ModCreativeTabs.groupmain));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
	NBTTagCompound nbt = stack.getTag();
	if (nbt != null)
	{
	    if (nbt.hasKey("type"))
	    {
		tooltip.add(new TextComponentString(new TextComponentTranslation("info.type").getUnformattedComponentText() + ": " + nbt.getString("type")));
	    }
	    if (nbt.hasKey("growspeed"))
	    {
		tooltip.add(new TextComponentString(new TextComponentTranslation("info.growspeed").getUnformattedComponentText() + ": " + nbt.getInt("growspeed")));
	    }
	    if (nbt.hasKey("sensitivity"))
	    {
		tooltip.add(new TextComponentString(new TextComponentTranslation("info.sensitivity").getUnformattedComponentText() + ": " + nbt.getInt("sensitivity")));
	    }
	    if (nbt.hasKey("lightsensitivity"))
	    {
		tooltip.add(new TextComponentString(new TextComponentTranslation("info.needed_lightlevel").getUnformattedComponentText() + ": " + (14 - nbt.getInt("lightsensitivity"))));
	    }
	    if (nbt.hasKey("watersensitivity"))
	    {
		tooltip.add(new TextComponentString(new TextComponentTranslation("info.waterrange").getUnformattedComponentText() + ": " + (1 + nbt.getInt("watersensitivity"))));
	    }
	    if (nbt.hasKey("temperaturetolerance"))
	    {
		tooltip.add(new TextComponentString(new TextComponentTranslation("info.temperaturetolerance").getUnformattedComponentText() + ": " + nbt.getInt("temperaturetolerance")));
	    }
	    if (nbt.hasKey("productivity"))
	    {
		tooltip.add(new TextComponentString(new TextComponentTranslation("info.productivity").getUnformattedComponentText() + ": " + nbt.getInt("productivity")));
	    }
	    if (nbt.hasKey("fertility"))
	    {
		tooltip.add(new TextComponentString(new TextComponentTranslation("info.fertility").getUnformattedComponentText() + ": " + nbt.getInt("fertility")));
	    }
	    if (nbt.hasKey("spreedingspeed"))
	    {
		tooltip.add(new TextComponentString(new TextComponentTranslation("info.spreedingspeed").getUnformattedComponentText() + ": " + nbt.getInt("spreedingspeed")));
	    }
	    if (nbt.hasKey("genestrength"))
	    {
		tooltip.add(new TextComponentString(new TextComponentTranslation("info.genestrength").getUnformattedComponentText() + ": " + nbt.getInt("genestrength")));
	    }
	    if (nbt.hasKey("energyvalue"))
	    {
		tooltip.add(new TextComponentString(new TextComponentTranslation("info.energyvalue").getUnformattedComponentText() + ": " + nbt.getInt("energyvalue") * 20));
	    }
	}
    }
}
