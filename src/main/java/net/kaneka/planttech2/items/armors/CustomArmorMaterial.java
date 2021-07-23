package net.kaneka.planttech2.items.armors;

import java.util.function.Supplier;

import net.minecraft.world.item.IArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.kaneka.planttech2.registries.ModItems;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.util.SoundEvents; 
import net.minecraft.inventory.EquipmentSlotType;

public enum CustomArmorMaterial implements IArmorMaterial
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
	private final LazyValue<Ingredient> repairMaterial;

	CustomArmorMaterial(String nameIn, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability, SoundEvent soundEvent, float toughness,
	        Supplier<Ingredient> repairMaterial)
	{
		this.name = nameIn;
		this.maxDamageFactor = maxDamageFactor;
		this.damageReductionAmountArray = damageReductionAmountArray;
		this.enchantability = enchantability;
		this.soundEvent = soundEvent;
		this.toughness = toughness;
		this.repairMaterial = new LazyValue<>(repairMaterial);
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
	public int getDurabilityForSlot(EquipmentSlotType slotIn)
	{
		return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
	}

	@Override
	public int getDefenseForSlot(EquipmentSlotType slotIn)
	{
		return this.damageReductionAmountArray[slotIn.getIndex()];
	}

	@Override
	public float getKnockbackResistance()
	{
		return 0;
	}
}
