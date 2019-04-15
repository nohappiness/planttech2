package net.kaneka.planttech2.items.armors;

import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemArmorBase extends ItemArmor
{
    private String resString;

    public ItemArmorBase(String name, String resString, IArmorMaterial mat, EntityEquipmentSlot equipmentSlotIn, Properties properties)
    {
	super(mat, equipmentSlotIn, properties);
	this.resString = resString; 
	setRegistryName(name);
	
	ModItems.ITEMSARMOR.add(this);
    }


    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
    {
	if (slot == EntityEquipmentSlot.HEAD || slot == EntityEquipmentSlot.CHEST || slot == EntityEquipmentSlot.FEET)
	{
	    return "planttech2:textures/models/armor/" + resString + "_layer_1.png";
	}
	else if (slot == EntityEquipmentSlot.LEGS)
	{
	    return "planttech2:textures/models/armor/" + resString + "_layer_2.png";
	}
	else
	{
	    return null;
	}
    }
}
