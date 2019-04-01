package net.kaneka.planttech2.items.armors;

import net.kaneka.planttech2.PlantTechMain;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemArmorBase extends ItemArmor
{
    private String name;
    private String resString;

    public ItemArmorBase(String name, String resString, IArmorMaterial mat, EntityEquipmentSlot equipmentSlotIn, Properties properties)
    {
	super(mat, equipmentSlotIn, properties);
	this.name = name;
	this.resString = resString; 
	setRegistryName(name);
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
