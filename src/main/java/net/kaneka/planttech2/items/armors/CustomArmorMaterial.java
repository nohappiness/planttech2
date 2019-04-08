package net.kaneka.planttech2.items.armors;

import java.util.function.Supplier;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyLoadBase;
import net.minecraft.util.SoundEvent;
import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.init.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public enum CustomArmorMaterial implements IArmorMaterial
{
    KANEKIUM("kanekium", 33, new int[] { 3, 6, 8, 3 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, 
	    () -> {return Ingredient.fromItems(ModItems.KANEKIUM_INGOT);}), 
    DANCIUM("dancium", 33, new int[] { 3, 6, 8, 3 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, 
	    () -> {return Ingredient.fromItems(ModItems.DANCIUM_INGOT);}), 
    LENTHURIUM("lenthurium", 33, new int[] { 3, 6, 8, 3 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, 
	    () -> {return Ingredient.fromItems(ModItems.LENTHURIUM_INGOT);}), 
    KINNOIUM("kinnoium", 33, new int[] { 3, 6, 8, 3 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, 
	    () -> {return Ingredient.fromItems(ModItems.KINNOIUM_INGOT);});
    private static final int[] MAX_DAMAGE_ARRAY = new int[]{ 13, 15, 16, 11 };
    private final String name;
    private final int maxDamageFactor;
    private final int[] damageReductionAmountArray;
    private final int enchantability;
    private final SoundEvent soundEvent;
    private final float toughness;
    private final LazyLoadBase<Ingredient> repairMaterial;

    private CustomArmorMaterial(String nameIn, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability, SoundEvent soundEvent, float toughness, Supplier<Ingredient> repairMaterial)
    {
	this.name = nameIn;
	this.maxDamageFactor = maxDamageFactor;
	this.damageReductionAmountArray = damageReductionAmountArray;
	this.enchantability = enchantability;
	this.soundEvent = soundEvent;
	this.toughness = toughness;
	this.repairMaterial = new LazyLoadBase<>(repairMaterial);
    }

    public int getDurability(EntityEquipmentSlot slotIn)
    {
	return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
    }

    public int getDamageReductionAmount(EntityEquipmentSlot slotIn)
    {
	return this.damageReductionAmountArray[slotIn.getIndex()];
    }

    public int getEnchantability()
    {
	return this.enchantability;
    }

    public SoundEvent getSoundEvent()
    {
	return this.soundEvent;
    }

    public Ingredient getRepairMaterial()
    {
	return this.repairMaterial.getValue();
    }

    @OnlyIn(Dist.CLIENT)
    public String getName()
    {
	return this.name;
    }

    public float getToughness()
    {
	return this.toughness;
    }
}
