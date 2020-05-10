package net.kaneka.planttech2.items;

import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.enums.EnumTemperature;
import net.kaneka.planttech2.enums.EnumTraitsInt;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.client.renderer.color.IItemColor;
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

public class CropSeedItem extends BaseItem {
    private String entryName;
    private int TRAIT_MIN=0;
    private int TRAIT_MAX=1;

    public CropSeedItem(String entryName) {
	    super(entryName + "_seeds",new  Item.Properties().group(ModCreativeTabs.groupseeds));
	    this.entryName = entryName;
    }
    
    public String getEntryName()
    {
	return entryName; 
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
	    CompoundNBT nbt = stack.getTag();
	    if (nbt != null) {
	        if (nbt.getBoolean("analysed")) {
		        tooltip.add(new StringTextComponent(new TranslationTextComponent("info.type").getUnformattedComponentText() + ": " + nbt.getString("type")));
		        tooltip.add(new StringTextComponent(new TranslationTextComponent("info.soil").getUnformattedComponentText() + ": " + getSoilString(nbt.getString("type"))));
		        tooltip.add(new StringTextComponent(new TranslationTextComponent("info.temperature").getUnformattedComponentText() + ": " + temperatureString(nbt.getString("type"), nbt.getInt("temperaturetolerance"))));
		        tooltip.add(new StringTextComponent(getTraitColor(nbt, "growspeed") + new TranslationTextComponent("info.growspeed").getUnformattedComponentText() + ": " + nbt.getInt("growspeed")));
		        tooltip.add(new StringTextComponent(getTraitColor(nbt, "sensitivity") + new TranslationTextComponent("info.sensitivity").getUnformattedComponentText() + ": " + nbt.getInt("sensitivity")));
		        tooltip.add(new StringTextComponent(getTraitColor(nbt, "lightsensitivity") + new TranslationTextComponent("info.needed_lightlevel").getUnformattedComponentText() + ": " + (14 - nbt.getInt("lightsensitivity"))));
		        tooltip.add(new StringTextComponent(getTraitColor(nbt, "watersensitivity") + new TranslationTextComponent("info.waterrange").getUnformattedComponentText() + ": " + (1 + nbt.getInt("watersensitivity"))));
		        tooltip.add(new StringTextComponent(getTraitColor(nbt, "productivity") + new TranslationTextComponent("info.productivity").getUnformattedComponentText() + ": " + nbt.getInt("productivity")));
		        tooltip.add(new StringTextComponent(getTraitColor(nbt, "fertility") + new TranslationTextComponent("info.fertility").getUnformattedComponentText() + ": " + nbt.getInt("fertility")));
		        tooltip.add(new StringTextComponent(getTraitColor(nbt, "spreedingspeed") + new TranslationTextComponent("info.spreedingspeed").getUnformattedComponentText() + ": " + nbt.getInt("spreedingspeed")));
		        tooltip.add(new StringTextComponent(getTraitColor(nbt, "genestrenght") + new TranslationTextComponent("info.genestrength").getUnformattedComponentText() + ": " + nbt.getInt("genestrenght")));
		        tooltip.add(new StringTextComponent(getTraitColor(nbt, "energyvalue") + new TranslationTextComponent("info.energyvalue").getUnformattedComponentText() + ": " + nbt.getInt("energyvalue") * 20));
	        }
	        else {
		        tooltip.add(new StringTextComponent(new TranslationTextComponent("info.type").getUnformattedComponentText() + ": " + new TranslationTextComponent("info.unknown").getUnformattedComponentText()));
		        tooltip.add(new StringTextComponent(new TranslationTextComponent("info.soil").getUnformattedComponentText() + ": " + new TranslationTextComponent("info.unknown").getUnformattedComponentText()));
		        tooltip.add(new StringTextComponent(new TranslationTextComponent("info.temperaturetolerance").getUnformattedComponentText() + ": " + new TranslationTextComponent("info.unknown").getUnformattedComponentText()));
		        tooltip.add(new StringTextComponent(new TranslationTextComponent("info.growspeed").getUnformattedComponentText() + ": " + new TranslationTextComponent("info.unknown").getUnformattedComponentText()));
		        tooltip.add(new StringTextComponent(new TranslationTextComponent("info.sensitivity").getUnformattedComponentText() + ": " + new TranslationTextComponent("info.unknown").getUnformattedComponentText()));
		        tooltip.add(new StringTextComponent(new TranslationTextComponent("info.needed_lightlevel").getUnformattedComponentText() + ": " + new TranslationTextComponent("info.unknown").getUnformattedComponentText()));
		        tooltip.add(new StringTextComponent(new TranslationTextComponent("info.waterrange").getUnformattedComponentText() + ": " + new TranslationTextComponent("info.unknown").getUnformattedComponentText()));
		        tooltip.add(new StringTextComponent(new TranslationTextComponent("info.productivity").getUnformattedComponentText() + ": " + new TranslationTextComponent("info.unknown").getUnformattedComponentText()));
		        tooltip.add(new StringTextComponent(new TranslationTextComponent("info.fertility").getUnformattedComponentText() + ": " + new TranslationTextComponent("info.unknown").getUnformattedComponentText()));
		        tooltip.add(new StringTextComponent(new TranslationTextComponent("info.spreedingspeed").getUnformattedComponentText() + ": " + new TranslationTextComponent("info.unknown").getUnformattedComponentText()));
		        tooltip.add(new StringTextComponent(new TranslationTextComponent("info.genestrength").getUnformattedComponentText() + ": " + new TranslationTextComponent("info.unknown").getUnformattedComponentText()));
		        tooltip.add(new StringTextComponent(new TranslationTextComponent("info.energyvalue").getUnformattedComponentText() + ": " + new TranslationTextComponent("info.unknown").getUnformattedComponentText()));
	        }
	    }
	    else {
	        tooltip.add(new StringTextComponent(new TranslationTextComponent("info.type").getUnformattedComponentText() + ": " + entryName));
	        tooltip.add(new StringTextComponent(new TranslationTextComponent("info.soil").getUnformattedComponentText() + ": " + getSoilString(entryName)));
	        tooltip.add(new StringTextComponent(getTraitColor(TRAIT_MIN) + new TranslationTextComponent("info.growspeed").getUnformattedComponentText() + ": " + 0));
	        tooltip.add(new StringTextComponent(getTraitColor(TRAIT_MIN) + new TranslationTextComponent("info.sensitivity").getUnformattedComponentText() + ": " + 0));
	        tooltip.add(new StringTextComponent(getTraitColor(TRAIT_MIN) + new TranslationTextComponent("info.needed_lightlevel").getUnformattedComponentText() + ": " + 14));
	        tooltip.add(new StringTextComponent(getTraitColor(TRAIT_MIN) + new TranslationTextComponent("info.waterrange").getUnformattedComponentText() + ": " + 1));
	        tooltip.add(new StringTextComponent(new TranslationTextComponent("info.temperaturetolerance").getUnformattedComponentText() + ": " + temperatureString(entryName, 0)));
	        tooltip.add(new StringTextComponent(getTraitColor(TRAIT_MIN) + new TranslationTextComponent("info.productivity").getUnformattedComponentText() + ": " + 0));
	        tooltip.add(new StringTextComponent(getTraitColor(TRAIT_MIN) + new TranslationTextComponent("info.fertility").getUnformattedComponentText() + ": " + 0));
	        tooltip.add(new StringTextComponent(getTraitColor(TRAIT_MIN) + new TranslationTextComponent("info.spreedingspeed").getUnformattedComponentText() + ": " + 0));
	        tooltip.add(new StringTextComponent(getTraitColor(TRAIT_MIN) + new TranslationTextComponent("info.genestrength").getUnformattedComponentText() + ": " + 0));
	        tooltip.add(new StringTextComponent(getTraitColor(TRAIT_MIN) + new TranslationTextComponent("info.energyvalue").getUnformattedComponentText() + ": " + 20));
	    }
    }

    public static String temperatureString(String type, int tolerance) {
	    if (tolerance == 0)	{
	        return PlantTechMain.croplist.getEntryByName(type).getTemperature().getDisplayString(true);
	    }
	    int id = PlantTechMain.croplist.getEntryByName(type).getTemperature().getId();
	    int min = id - tolerance;
	    int max = id + tolerance;
	    if (min < 0) {min = 0;}
	    if (max > 4) {max = 4;}
	    return EnumTemperature.byId(min).getDisplayString(true) + " - " + EnumTemperature.byId(max).getDisplayString(true);
    }

    public static String getSoilString(String type) {
	    ItemStack soil = PlantTechMain.croplist.getEntryByName(type).getSoil();
	    if (soil.isEmpty()) {
	        return " / ";
	    }
	    else {
	        return soil.getDisplayName().getUnformattedComponentText();
	    }
    }

    public static class ColorHandler implements IItemColor {
		@Override
		public int getColor(ItemStack stack, int color) {
			return PlantTechMain.croplist.getEntryByName(((CropSeedItem) stack.getItem()).getEntryName()).getSeedColor();
		}
	}

	private TextFormatting getTraitColor (CompoundNBT nbt, String trait) {
		if (nbt.getInt(trait) == Objects.requireNonNull(EnumTraitsInt.getByName(trait)).getMax()) {
			return getTraitColor(TRAIT_MAX);
		}
		if (nbt.getInt(trait) == Objects.requireNonNull(EnumTraitsInt.getByName(trait)).getMin()) {
			return getTraitColor(TRAIT_MIN);
		}
		return TextFormatting.RESET;
	}
	private TextFormatting getTraitColor (int level) {
    	if (level == TRAIT_MIN) {return TextFormatting.GRAY;}
    	if (level == TRAIT_MAX) {return TextFormatting.GREEN;}
		return TextFormatting.RESET;
	}
}
