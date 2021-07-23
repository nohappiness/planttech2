package net.kaneka.planttech2.items.upgradeable;


import net.kaneka.planttech2.configuration.PlantTech2Configuration;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
		super(new Item.Properties().tab(ModCreativeTabs.CHIPS));
	}


	public int getEnergyCost()
	{
		return (int) (((float)energyCost) * PlantTech2Configuration.MULTIPLIER_ENERGYCOSTS.get());
	}


	public UpgradeChipItem setEnergyCost(int energyCost)
	{
		this.energyCost = energyCost;
		return this;
	}

	public int getIncreaseCapacity()
	{
		return (int) (((float)increaseCapacity) * PlantTech2Configuration.MULTIPLIER_CAPACITY.get());
	}


	public UpgradeChipItem setIncreaseCapacity(int increaseCapacity)
	{
		this.increaseCapacity = increaseCapacity;
		return this;
	}

	public static int getCapacityMax()
	{
		return (int) ((float)50000 * PlantTech2Configuration.MULTIPLIER_CAPACITY_MAX.get()); 
	}

	public int getEnergyProduction()
	{
		return (int) ((float)energyProduction * PlantTech2Configuration.MULTIPLIER_ENERGYPRODUCTION.get());
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
		return (float) (increaseAttack * PlantTech2Configuration.MULTIPLIER_ATTACK.get());
	}

	public UpgradeChipItem setIncreaseAttack(float increaseAttack)
	{
		this.increaseAttack = increaseAttack;
		return this;
	}


	public static float getAttackMax()
	{
		return (float) (20F * PlantTech2Configuration.MULTIPLIER_ATTACK_MAX.get());
	}

	public float getIncreaseAttackSpeed()
	{
		return (float) (increaseAttackSpeed * PlantTech2Configuration.MULTIPLIER_ATTACKSPEED.get());
	}


	public UpgradeChipItem setIncreaseAttackSpeed(float increaseAttackSpeed)
	{
		this.increaseAttackSpeed = increaseAttackSpeed;
		return this;
	}

	public static float getAttackSpeedMax()
	{
		return (float) (2F * PlantTech2Configuration.MULTIPLIER_ATTACKSPEED_MAX.get());
	}

	public float getIncreaseBreakdownRate()
	{
		return (float) (increaseBreakdownRate * PlantTech2Configuration.MULTIPLIER_BREAKDOWNRATE.get());
	}

	public UpgradeChipItem setIncreaseBreakdownRate(float increaseBreakdownRate)
	{
		this.increaseBreakdownRate = increaseBreakdownRate;
		return this;
	}

	public static float getBreakdownRateMax()
	{
		return (float) (10F * PlantTech2Configuration.MULTIPLIER_BREAKDOWNRATE_MAX.get());
	}

	public UpgradeChipItem setIncreaseArmor(int increaseArmor)
	{
		this.increaseArmor = increaseArmor;
		return this;
	}

	public int getIncreaseArmor()
	{
		return (int) ((float)increaseArmor * PlantTech2Configuration.MULTIPLIER_ARMOR.get());
	}

	public UpgradeChipItem setIncreaseToughness(float increaseToughness)
	{
		this.increaseToughness = increaseToughness;
		return this;
	}

	public float getIncreaseToughness()
	{
		return (float) (increaseToughness * PlantTech2Configuration.MULTIPLIER_THOUGHNESS.get());
	}

	public static float getToughnessMax()
	{
		return (float) (6 * PlantTech2Configuration.MULTIPLIER_THOUGHNESS_MAX.get());
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
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		if (increaseCapacity > 0)
		{
			tooltip.add(new TranslationTextComponent("info.upgradechip.increasecapacity", getIncreaseCapacity()));
			tooltip.add(new TranslationTextComponent("info.upgradechip.increasecapacitymax", getCapacityMax()));
		}
		if (energyProduction > 0)
		{
			tooltip.add(new TranslationTextComponent("info.upgradechip.energyproduction", getEnergyProduction()));
		}
		if (increaseHarvestlevel > 0)
		{
			tooltip.add(new TranslationTextComponent("info.upgradechip.increaseharvestlevel", increaseHarvestlevel));
		}
		if (increaseArmor > 0)
		{
			tooltip.add(new TranslationTextComponent("info.upgradechip.increasearmor", getIncreaseArmor()));
		}

		if (increaseAttack > 0)
		{
			tooltip.add(new TranslationTextComponent("info.upgradechip.increaseattack", getIncreaseAttack()));
			tooltip.add(new TranslationTextComponent("info.upgradechip.increaseattackmax", getAttackMax()));
		}
		
		if (increaseAttackSpeed > 0)
		{
			tooltip.add(new TranslationTextComponent("info.upgradechip.increaseattackspeed", getIncreaseAttackSpeed()));
			tooltip.add(new TranslationTextComponent("info.upgradechip.increaseattackspeedmax", getAttackSpeedMax()));
		}
		
		if (increaseBreakdownRate > 0)
		{
			tooltip.add(new TranslationTextComponent("info.upgradechip.increasebreakdownrate", getIncreaseBreakdownRate()));
			tooltip.add(new TranslationTextComponent("info.upgradechip.increasebreakdownratemax", getBreakdownRateMax()));
		}
		
		if (increaseToughness > 0)
		{
			tooltip.add(new TranslationTextComponent("info.upgradechip.increasetougness", getIncreaseToughness()));
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
			tooltip.add(new TranslationTextComponent("info.upgradechip.add").append(" ").append(enchantment.getFullname(0)));
			tooltip.add(new TranslationTextComponent("info.upgradechip.stackable"));
		}
		
		if (energyCost > 0)
		{
			tooltip.add(new TranslationTextComponent("info.upgradechip.energycosts", getEnergyCost()));
		}

		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}

}
