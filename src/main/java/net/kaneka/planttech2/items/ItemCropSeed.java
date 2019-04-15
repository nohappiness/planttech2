package net.kaneka.planttech2.items;

import java.util.List;
import javax.annotation.Nullable;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.enums.EnumTemperature;
import net.minecraft.client.renderer.color.IItemColor;
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

public class ItemCropSeed extends ItemBase
{
    private String entryName; 
    public ItemCropSeed(String entryName)
    {
	super(entryName + "_seeds",new  Item.Properties().group(PlantTechMain.groupseeds));
	this.entryName = entryName; 
    }
    
    public String getEntryName()
    {
	return entryName; 
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
	NBTTagCompound nbt = stack.getTag();
	if (nbt != null)
	{
	    if (nbt.getBoolean("analysed"))
	    {
		tooltip.add(new TextComponentString(new TextComponentTranslation("info.type").getUnformattedComponentText() + ": " + nbt.getString("type")));
		tooltip.add(new TextComponentString(new TextComponentTranslation("info.soil").getUnformattedComponentText() + ": " + this.getSoilString(nbt.getString("type"))));
		tooltip.add(new TextComponentString(new TextComponentTranslation("info.growspeed").getUnformattedComponentText() + ": " + nbt.getInt("growspeed")));
		tooltip.add(new TextComponentString(new TextComponentTranslation("info.sensitivity").getUnformattedComponentText() + ": " + nbt.getInt("sensitivity")));
		tooltip.add(new TextComponentString(new TextComponentTranslation("info.needed_lightlevel").getUnformattedComponentText() + ": " + (14 - nbt.getInt("lightsensitivity"))));
		tooltip.add(new TextComponentString(new TextComponentTranslation("info.waterrange").getUnformattedComponentText() + ": " + (1 + nbt.getInt("watersensitivity"))));
		tooltip.add(new TextComponentString(new TextComponentTranslation("info.temperature").getUnformattedComponentText() + ": " + temperatureString(nbt.getString("type"), nbt.getInt("temperaturetolerance"))));
		tooltip.add(new TextComponentString(new TextComponentTranslation("info.productivity").getUnformattedComponentText() + ": " + nbt.getInt("productivity")));
		tooltip.add(new TextComponentString(new TextComponentTranslation("info.fertility").getUnformattedComponentText() + ": " + nbt.getInt("fertility")));
		tooltip.add(new TextComponentString(new TextComponentTranslation("info.spreedingspeed").getUnformattedComponentText() + ": " + nbt.getInt("spreedingspeed")));
		tooltip.add(new TextComponentString(new TextComponentTranslation("info.genestrength").getUnformattedComponentText() + ": " + nbt.getInt("genestrength")));
		tooltip.add(new TextComponentString(new TextComponentTranslation("info.energyvalue").getUnformattedComponentText() + ": " + nbt.getInt("energyvalue") * 20));

	    }
	    else
	    {
		tooltip.add(new TextComponentString(new TextComponentTranslation("info.type").getUnformattedComponentText() + ": " + new TextComponentTranslation("info.unknown").getUnformattedComponentText()));
		tooltip.add(new TextComponentString(new TextComponentTranslation("info.soil").getUnformattedComponentText() + ": " + new TextComponentTranslation("info.unknown").getUnformattedComponentText()));
		tooltip.add(new TextComponentString(new TextComponentTranslation("info.growspeed").getUnformattedComponentText() + ": " + new TextComponentTranslation("info.unknown").getUnformattedComponentText()));
		tooltip.add(new TextComponentString(new TextComponentTranslation("info.sensitivity").getUnformattedComponentText() + ": " + new TextComponentTranslation("info.unknown").getUnformattedComponentText()));
		tooltip.add(new TextComponentString(new TextComponentTranslation("info.needed_lightlevel").getUnformattedComponentText() + ": " + new TextComponentTranslation("info.unknown").getUnformattedComponentText()));
		tooltip.add(new TextComponentString(new TextComponentTranslation("info.waterrange").getUnformattedComponentText() + ": " + new TextComponentTranslation("info.unknown").getUnformattedComponentText()));
		tooltip.add(new TextComponentString(new TextComponentTranslation("info.temperaturetolerance").getUnformattedComponentText() + ": " + new TextComponentTranslation("info.unknown").getUnformattedComponentText()));
		tooltip.add(new TextComponentString(new TextComponentTranslation("info.productivity").getUnformattedComponentText() + ": " + new TextComponentTranslation("info.unknown").getUnformattedComponentText()));
		tooltip.add(new TextComponentString(new TextComponentTranslation("info.fertility").getUnformattedComponentText() + ": " + new TextComponentTranslation("info.unknown").getUnformattedComponentText()));
		tooltip.add(new TextComponentString(new TextComponentTranslation("info.spreedingspeed").getUnformattedComponentText() + ": " + new TextComponentTranslation("info.unknown").getUnformattedComponentText()));
		tooltip.add(new TextComponentString(new TextComponentTranslation("info.genestrength").getUnformattedComponentText() + ": " + new TextComponentTranslation("info.unknown").getUnformattedComponentText()));
		tooltip.add(new TextComponentString(new TextComponentTranslation("info.energyvalue").getUnformattedComponentText() + ": " + new TextComponentTranslation("info.unknown").getUnformattedComponentText()));
	    }
	}
	else
	{
	    tooltip.add(new TextComponentString(new TextComponentTranslation("info.type").getUnformattedComponentText() + ": " + entryName));
	    tooltip.add(new TextComponentString(new TextComponentTranslation("info.soil").getUnformattedComponentText() + ": " + this.getSoilString(entryName)));
	    tooltip.add(new TextComponentString(new TextComponentTranslation("info.growspeed").getUnformattedComponentText() + ": " + 0));
	    tooltip.add(new TextComponentString(new TextComponentTranslation("info.sensitivity").getUnformattedComponentText() + ": " + 0));
	    tooltip.add(new TextComponentString(new TextComponentTranslation("info.needed_lightlevel").getUnformattedComponentText() + ": " + 14));
	    tooltip.add(new TextComponentString(new TextComponentTranslation("info.waterrange").getUnformattedComponentText() + ": " + 1));
	    tooltip.add(new TextComponentString(new TextComponentTranslation("info.temperaturetolerance").getUnformattedComponentText() + ": " + temperatureString(entryName, 0)));
	    tooltip.add(new TextComponentString(new TextComponentTranslation("info.productivity").getUnformattedComponentText() + ": " + 0));
	    tooltip.add(new TextComponentString(new TextComponentTranslation("info.fertility").getUnformattedComponentText() + ": " + 0));
	    tooltip.add(new TextComponentString(new TextComponentTranslation("info.spreedingspeed").getUnformattedComponentText() + ": " + 0));
	    tooltip.add(new TextComponentString(new TextComponentTranslation("info.genestrength").getUnformattedComponentText() + ": " + 0));
	    tooltip.add(new TextComponentString(new TextComponentTranslation("info.energyvalue").getUnformattedComponentText() + ": " + 20));
	}
    }

    private String temperatureString(String type, int tolerance)
    {
	if (tolerance == 0)
	{
	    return PlantTechMain.croplist.getEntryByName(type).getTemperature().getDisplayString();
	}
	int id = PlantTechMain.croplist.getEntryByName(type).getTemperature().getId();
	int min = id - tolerance;
	int max = id + tolerance;
	if (min < 0)
	{
	    min = 0;
	}
	if (max > 4)
	{
	    max = 4;
	}
	return EnumTemperature.byId(min).getDisplayString() + " - " + EnumTemperature.byId(max).getDisplayString();
    }

    private String getSoilString(String type)
    {
	ItemStack soil = PlantTechMain.croplist.getEntryByName(type).getSoil();
	if (soil.isEmpty())
	{
	    return " / ";
	}
	else
	{
	    return soil.getDisplayName().getUnformattedComponentText();
	}
    }

    public static class ColorHandler implements IItemColor
    {
	@Override
	public int getColor(ItemStack stack, int color)
	{
	    return PlantTechMain.croplist.getEntryByName(((ItemCropSeed) stack.getItem()).getEntryName()).getSeedColor();
	}
    }
}
