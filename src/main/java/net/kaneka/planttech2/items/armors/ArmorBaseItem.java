package net.kaneka.planttech2.items.armors;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

public class ArmorBaseItem extends ArmorItem
{
    private final String resString;

    public ArmorBaseItem(String resString, ArmorMaterial mat, EquipmentSlot equipmentSlotIn, Properties properties)
    {
	super(mat, equipmentSlotIn, properties);
	this.resString = resString;
    }


    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type)
    {
	if (slot == EquipmentSlot.HEAD || slot == EquipmentSlot.CHEST || slot == EquipmentSlot.FEET)
	{
	    return "planttech2:textures/models/armor/" + resString + "_layer_1.png";
	}
	else if (slot == EquipmentSlot.LEGS)
	{
	    return "planttech2:textures/models/armor/" + resString + "_layer_2.png";
	}
	else
	{
	    return null;
	}
    }
}
