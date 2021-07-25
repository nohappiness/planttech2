package net.kaneka.planttech2.items.armors;

import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public enum CustomArmorMaterial implements ArmorMaterial
{
	UNNECESSARY("unnecessary", 0, new int[] { 1, 1, 1, 1 }, 0, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.0F, () -> {
		return Ingredient.of(ModItems.PLANTIUM_INGOT);
	});

	private static final int[] MAX_DAMAGE_ARRAY = new int[] { 13, 15, 16, 11 };
	private final String name;
	private final int maxDamageFactor;
	private final int[] damageReductionAmountArray;
	private final int enchantability;
	private final SoundEvent soundEvent;
	private final float toughness;
	private final LazyLoadedValue<Ingredient> repairMaterial;

	CustomArmorMaterial(String nameIn, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability, SoundEvent soundEvent, float toughness,
	        Supplier<Ingredient> repairMaterial)
	{
		this.name = nameIn;
		this.maxDamageFactor = maxDamageFactor;
		this.damageReductionAmountArray = damageReductionAmountArray;
		this.enchantability = enchantability;
		this.soundEvent = soundEvent;
		this.toughness = toughness;
		this.repairMaterial = new LazyLoadedValue<>(repairMaterial);
	}

	public int getEnchantmentValue()
	{
		return this.enchantability;
	}

	public SoundEvent getEquipSound()
	{
		return this.soundEvent;
	}

	public Ingredient getRepairIngredient()
	{
		return this.repairMaterial.get();
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

	@Override
	public int getDurabilityForSlot(EquipmentSlot slotIn)
	{
		return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
	}

	@Override
	public int getDefenseForSlot(EquipmentSlot slotIn)
	{
		return this.damageReductionAmountArray[slotIn.getIndex()];
	}

	@Override
	public float getKnockbackResistance()
	{
		return 0;
	}
}
