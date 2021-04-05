package net.kaneka.planttech2.items.upgradeable;

import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class UpgradeChipItem extends Item
{
	public static int HELMET = 0, CHEST = 1, LEGGINGS = 2, BOOTS = 3, RANGED_WEAPON = 4, MELEE_WEAPON = 5, TOOL = 6;  

	private int energyCost = 0, increaseCapacity = 0, energyProduction = 0, increaseHarvestlevel = 0, increaseArmor = 0;

	private float increaseAttack = 0, increaseAttackSpeed = 0, increaseBreakdownRate = 0, increaseToughness = 0;

	private boolean unlockShovelFeat = false, unlockAxeFeat = false, unlockHoeFeat = false, unlockShearFeat = false;
	
	private Enchantment enchantment;
	
	private final List<Integer> restrictions = new ArrayList<>();

	public UpgradeChipItem(String name)
	{
		super(new Item.Properties().group(ModCreativeTabs.CHIPS));
	}


	public int getEnergyCost()
	{
		return energyCost;
	}


	public UpgradeChipItem setEnergyCost(int energyCost)
	{
		this.energyCost = energyCost;
		return this;
	}

	public int getIncreaseCapacity()
	{
		return increaseCapacity;
	}


	public UpgradeChipItem setIncreaseCapacity(int increaseCapacity)
	{
		this.increaseCapacity = increaseCapacity;
		return this;
	}

	public static int getCapacityMax()
	{
		return 50000; 
	}

	public int getEnergyProduction()
	{
		return energyProduction;
	}

	public UpgradeChipItem setEnergyProduction(int energyProduction)
	{
		this.energyProduction = energyProduction;
		return this;
	}

	public int getIncreaseHarvestlevel()
	{
		return increaseHarvestlevel;
	}

	public UpgradeChipItem setIncreaseHarvestlevel(int increaseHarvestlevel)
	{
		this.increaseHarvestlevel = increaseHarvestlevel;
		return this;
	}

	public float getIncreaseAttack()
	{
		return increaseAttack;
	}

	public UpgradeChipItem setIncreaseAttack(float increaseAttack)
	{
		this.increaseAttack = increaseAttack;
		return this;
	}


	public static float getAttackMax()
	{
		return 20;
	}

	public float getIncreaseAttackSpeed()
	{
		return increaseAttackSpeed;
	}


	public UpgradeChipItem setIncreaseAttackSpeed(float increaseAttackSpeed)
	{
		this.increaseAttackSpeed = increaseAttackSpeed;
		return this;
	}

	public static float getAttackSpeedMax()
	{
		return 2F;
	}

	public float getIncreaseBreakdownRate()
	{
		return increaseBreakdownRate;
	}

	public UpgradeChipItem setIncreaseBreakdownRate(float increaseBreakdownRate)
	{
		this.increaseBreakdownRate = increaseBreakdownRate;
		return this;
	}

	public static float getBreakdownRateMax()
	{
		return 10;
	}

	public UpgradeChipItem setIncreaseArmor(int increaseArmor)
	{
		this.increaseArmor = increaseArmor;
		return this;
	}

	public int getIncreaseArmor()
	{
		return increaseArmor;
	}

	public UpgradeChipItem setIncreaseToughness(float increaseToughness)
	{
		this.increaseToughness = increaseToughness;
		return this;
	}

	public float getIncreaseToughness()
	{
		return increaseToughness;
	}

	public static float getToughnessMax()
	{
		return 6;
	}

	public boolean isUnlockShovelFeat()
	{
		return unlockShovelFeat;
	}


	public UpgradeChipItem setUnlockShovelFeat()
	{
		this.unlockShovelFeat = true;
		return this;
	}


	public boolean isUnlockAxeFeat()
	{
		return unlockAxeFeat;
	}


	public UpgradeChipItem setUnlockAxeFeat()
	{
		this.unlockAxeFeat = true;
		return this;
	}


	public boolean isUnlockHoeFeat()
	{
		return unlockHoeFeat;
	}


	public UpgradeChipItem setUnlockHoeFeat()
	{
		this.unlockHoeFeat = true;
		return this;
	}


	public boolean isUnlockShearsFeat()
	{
		return unlockShearFeat;
	}


	public UpgradeChipItem setUnlockShearsFeat()
	{
		this.unlockShearFeat = true;
		return this;
	}
	
	public UpgradeChipItem setEnchantment(Enchantment ench)
	{
		this.enchantment = ench; 
		return this; 
	}
	
	public Enchantment getEnchantment()
	{
		return enchantment; 
	}

	public UpgradeChipItem addRestriction(int... ids)
	{
		for (int id : ids)
		{
			restrictions.add(id);
		}
		return this;
	}
	
	public boolean isAllowed(int id)
	{
		if(restrictions.isEmpty())
		{
			return true; 
		}

		return restrictions.contains(id);
	}


	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		if (increaseCapacity > 0)
		{
			tooltip.add(new TranslationTextComponent("info.upgradechip.increasecapacity", increaseCapacity));
			tooltip.add(new TranslationTextComponent("info.upgradechip.increasecapacitymax", getCapacityMax()));
		}
		if (energyProduction > 0)
		{
			tooltip.add(new TranslationTextComponent("info.upgradechip.energyproduction", energyProduction));
		}
		if (increaseHarvestlevel > 0)
		{
			tooltip.add(new TranslationTextComponent("info.upgradechip.increaseharvestlevel", increaseHarvestlevel));
		}
		if (increaseArmor > 0)
		{
			tooltip.add(new TranslationTextComponent("info.upgradechip.increasearmor", increaseArmor));
		}

		if (increaseAttack > 0)
		{
			tooltip.add(new TranslationTextComponent("info.upgradechip.increaseattack", increaseAttack));
			tooltip.add(new TranslationTextComponent("info.upgradechip.increaseattackmax", getAttackMax()));
		}
		
		if (increaseAttackSpeed > 0)
		{
			tooltip.add(new TranslationTextComponent("info.upgradechip.increaseattackspeed", increaseAttackSpeed));
			tooltip.add(new TranslationTextComponent("info.upgradechip.increaseattackspeedmax", getAttackSpeedMax()));
		}
		
		if (increaseBreakdownRate > 0)
		{
			tooltip.add(new TranslationTextComponent("info.upgradechip.increasebreakdownrate", increaseBreakdownRate));
			tooltip.add(new TranslationTextComponent("info.upgradechip.increasebreakdownratemax", getBreakdownRateMax()));
		}
		
		if (increaseToughness > 0)
		{
			tooltip.add(new TranslationTextComponent("info.upgradechip.increasetougness", increaseToughness));
			tooltip.add(new TranslationTextComponent("info.upgradechip.increasetougnessmax", getToughnessMax()));
		}
		
		if(unlockAxeFeat)
		{
			tooltip.add(new TranslationTextComponent("info.upgradechip.unlockaxefeat"));
		}
		if(unlockShovelFeat)
		{
			tooltip.add(new TranslationTextComponent("info.upgradechip.unlockshovelfeat"));
		}
		if(unlockShearFeat)
		{
			tooltip.add(new TranslationTextComponent("info.upgradechip.unlockshearsfeat"));
		}
		if(unlockHoeFeat)
		{
			tooltip.add(new TranslationTextComponent("info.upgradechip.unlockhoefeat"));
		}
		
		if(enchantment != null)
		{
			tooltip.add(new TranslationTextComponent("info.upgradechip.add").appendString(" ").appendSibling(enchantment.getDisplayName(0)));
			tooltip.add(new TranslationTextComponent("info.upgradechip.stackable"));
		}
		
		if (energyCost > 0)
		{
			tooltip.add(new TranslationTextComponent("info.upgradechip.energycosts", energyCost));
		}

		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

}
