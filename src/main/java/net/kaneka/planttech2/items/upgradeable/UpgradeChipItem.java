package net.kaneka.planttech2.items.upgradeable;


import net.kaneka.planttech2.items.BaseItem;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.item.Item;

public class UpgradeChipItem extends BaseItem
{
	
	private int energyCost = 0, increaseCapacity = 0, energyProduction = 0, increaseHarvestlevel = 0, increaseArmor = 0;
	
	private float increaseAttack = 0, increaseAttackSpeed = 0, increaseBreakdownRate = 0, increaseToughness = 0; 
	
	private boolean unlockShovelFeat = false, unlockAxeFeat = false, unlockHoeFeat = false, unlockShearFeat = false; 
	

	public UpgradeChipItem(String name)
	{
		super(name, new Item.Properties().group(ModCreativeTabs.groupchips));
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
	
	

}
