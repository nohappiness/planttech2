package net.kaneka.planttech2.items.armors;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.IArmorMaterial;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.item.Item.Properties;

public class ArmorBaseItem extends ArmorItem
{
    private final String resString;

    public ArmorBaseItem(String resString, IArmorMaterial mat, EquipmentSlotType equipmentSlotIn, Properties properties)
    {
	super(mat, equipmentSlotIn, properties);
	this.resString = resString;
    }


    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type)
    {
	if (slot == EquipmentSlotType.HEAD || slot == EquipmentSlotType.CHEST || slot == EquipmentSlotType.FEET)
	{
	    return "planttech2:textures/models/armor/" + resString + "_layer_1.png";
	}
	else if (slot == EquipmentSlotType.LEGS)
	{
	    return "planttech2:textures/models/armor/" + resString + "_layer_2.png";
	}
	else
	{
	    return null;
	}
    }
}
