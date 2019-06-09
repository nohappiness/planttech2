package net.kaneka.planttech2.items.armors;

import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;

public class ItemArmorBase extends ArmorItem
{
    private String resString;

    public ItemArmorBase(String name, String resString, IArmorMaterial mat, EquipmentSlotType equipmentSlotIn, Properties properties)
    {
	super(mat, equipmentSlotIn, properties);
	this.resString = resString; 
	setRegistryName(name);
	
	ModItems.ITEMSARMOR.add(this);
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
