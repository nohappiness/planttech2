package net.kaneka.planttech2.items.upgradeable;


import net.kaneka.planttech2.items.ItemBase;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.item.Item;

public class ItemUpgradeChip extends ItemBase
{
	
	private int energyCost = 0, increaseCapacity = 0, energyProduction = 0, increaseHarvestlevel = 0, increaseArmor = 0;
	
	private float increaseAttack = 0, increaseAttackSpeed = 0, increaseBreakdownRate = 0, increaseToughness = 0; 
	
	private boolean unlockShovelFeat = false, unlockAxeFeat = false, unlockHoeFeat = false, unlockShearFeat = false; 
	

	public ItemUpgradeChip(String name)
	{
		super(name, new Item.Properties().group(ModCreativeTabs.groupchips));
	}


	public int getEnergyCost()
	{
		return energyCost;
	}


	public ItemUpgradeChip setEnergyCost(int energyCost)
	{
		this.energyCost = energyCost;
		return this;
	}


	public int getIncreaseCapacity()
	{
		return increaseCapacity;
	}


	public ItemUpgradeChip setIncreaseCapacity(int increaseCapacity)
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


	public ItemUpgradeChip setEnergyProduction(int energyProduction)
	{
		this.energyProduction = energyProduction;
		return this;
	}


	public int getIncreaseHarvestlevel()
	{
		return increaseHarvestlevel;
	}


	public ItemUpgradeChip setIncreaseHarvestlevel(int increaseHarvestlevel)
	{
		this.increaseHarvestlevel = increaseHarvestlevel;
		return this;
	}


	public float getIncreaseAttack()
	{
		return increaseAttack;
	}


	public ItemUpgradeChip setIncreaseAttack(float increaseAttack)
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


	public ItemUpgradeChip setIncreaseAttackSpeed(float increaseAttackSpeed)
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


	public ItemUpgradeChip setIncreaseBreakdownRate(float increaseBreakdownRate)
	{
		this.increaseBreakdownRate = increaseBreakdownRate;
		return this;
	}
	
	public static float getBreakdownRateMax()
	{
		return 10; 
	}
	
	public ItemUpgradeChip setIncreaseArmor(int increaseArmor)
	{
		this.increaseArmor = increaseArmor; 
		return this; 
	}
	
	public int getIncreaseArmor()
	{
		return increaseArmor; 
	}
	
	public ItemUpgradeChip setIncreaseToughness(float increaseToughness)
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


	public ItemUpgradeChip setUnlockShovelFeat()
	{
		this.unlockShovelFeat = true;
		return this;
	}


	public boolean isUnlockAxeFeat()
	{
		return unlockAxeFeat;
	}


	public ItemUpgradeChip setUnlockAxeFeat()
	{
		this.unlockAxeFeat = true;
		return this;
	}


	public boolean isUnlockHoeFeat()
	{
		return unlockHoeFeat;
	}


	public ItemUpgradeChip setUnlockHoeFeat()
	{
		this.unlockHoeFeat = true;
		return this;
	}


	public boolean isUnlockShearsFeat()
	{
		return unlockShearFeat;
	}


	public ItemUpgradeChip setUnlockShearsFeat()
	{
		this.unlockShearFeat = true;
		return this;
	}
	
	

}
