package net.kaneka.planttech2.items.upgradeable;

<<<<<<< .merge_file_a05600

import net.kaneka.planttech2.items.BaseItem;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.item.Item;

public class UpgradeChipItem extends BaseItem
{
	
	private int energyCost = 0, increaseCapacity = 0, energyProduction = 0, increaseHarvestlevel = 0, increaseArmor = 0;
	
	private float increaseAttack = 0, increaseAttackSpeed = 0, increaseBreakdownRate = 0, increaseToughness = 0; 
	
	private boolean unlockShovelFeat = false, unlockAxeFeat = false, unlockHoeFeat = false, unlockShearFeat = false; 
	
=======
import java.util.List;

import net.kaneka.planttech2.items.BaseItem;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class UpgradeChipItem extends BaseItem
{

	private int energyCost = 0, increaseCapacity = 0, energyProduction = 0, increaseHarvestlevel = 0, increaseArmor = 0;

	private float increaseAttack = 0, increaseAttackSpeed = 0, increaseBreakdownRate = 0, increaseToughness = 0;

	private boolean unlockShovelFeat = false, unlockAxeFeat = false, unlockHoeFeat = false, unlockShearFeat = false;
>>>>>>> .merge_file_a11128

	public UpgradeChipItem(String name)
	{
		super(name, new Item.Properties().group(ModCreativeTabs.groupchips));
	}

<<<<<<< .merge_file_a05600

=======
>>>>>>> .merge_file_a11128
	public int getEnergyCost()
	{
		return energyCost;
	}

<<<<<<< .merge_file_a05600

=======
>>>>>>> .merge_file_a11128
	public UpgradeChipItem setEnergyCost(int energyCost)
	{
		this.energyCost = energyCost;
		return this;
	}

<<<<<<< .merge_file_a05600

=======
>>>>>>> .merge_file_a11128
	public int getIncreaseCapacity()
	{
		return increaseCapacity;
	}

<<<<<<< .merge_file_a05600

=======
>>>>>>> .merge_file_a11128
	public UpgradeChipItem setIncreaseCapacity(int increaseCapacity)
	{
		this.increaseCapacity = increaseCapacity;
		return this;
	}
<<<<<<< .merge_file_a05600
	
	public static int getCapacityMax()
	{
		return 50000; 
	}


=======

	public static int getCapacityMax()
	{
		return 50000;
	}

>>>>>>> .merge_file_a11128
	public int getEnergyProduction()
	{
		return energyProduction;
	}

<<<<<<< .merge_file_a05600

=======
>>>>>>> .merge_file_a11128
	public UpgradeChipItem setEnergyProduction(int energyProduction)
	{
		this.energyProduction = energyProduction;
		return this;
	}

<<<<<<< .merge_file_a05600

=======
>>>>>>> .merge_file_a11128
	public int getIncreaseHarvestlevel()
	{
		return increaseHarvestlevel;
	}

<<<<<<< .merge_file_a05600

=======
>>>>>>> .merge_file_a11128
	public UpgradeChipItem setIncreaseHarvestlevel(int increaseHarvestlevel)
	{
		this.increaseHarvestlevel = increaseHarvestlevel;
		return this;
	}

<<<<<<< .merge_file_a05600

=======
>>>>>>> .merge_file_a11128
	public float getIncreaseAttack()
	{
		return increaseAttack;
	}

<<<<<<< .merge_file_a05600

=======
>>>>>>> .merge_file_a11128
	public UpgradeChipItem setIncreaseAttack(float increaseAttack)
	{
		this.increaseAttack = increaseAttack;
		return this;
	}
<<<<<<< .merge_file_a05600
	
	public static float getAttackMax()
	{
		return 20; 
	}


=======

	public static float getAttackMax()
	{
		return 20;
	}

>>>>>>> .merge_file_a11128
	public float getIncreaseAttackSpeed()
	{
		return increaseAttackSpeed;
	}

<<<<<<< .merge_file_a05600

=======
>>>>>>> .merge_file_a11128
	public UpgradeChipItem setIncreaseAttackSpeed(float increaseAttackSpeed)
	{
		this.increaseAttackSpeed = increaseAttackSpeed;
		return this;
	}
<<<<<<< .merge_file_a05600
	
	public static float getAttackSpeedMax()
	{
		return 2F; 
	}


=======

	public static float getAttackSpeedMax()
	{
		return 2F;
	}

>>>>>>> .merge_file_a11128
	public float getIncreaseBreakdownRate()
	{
		return increaseBreakdownRate;
	}

<<<<<<< .merge_file_a05600

=======
>>>>>>> .merge_file_a11128
	public UpgradeChipItem setIncreaseBreakdownRate(float increaseBreakdownRate)
	{
		this.increaseBreakdownRate = increaseBreakdownRate;
		return this;
	}
<<<<<<< .merge_file_a05600
	
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


=======

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

>>>>>>> .merge_file_a11128
	public boolean isUnlockShovelFeat()
	{
		return unlockShovelFeat;
	}

<<<<<<< .merge_file_a05600

=======
>>>>>>> .merge_file_a11128
	public UpgradeChipItem setUnlockShovelFeat()
	{
		this.unlockShovelFeat = true;
		return this;
	}

<<<<<<< .merge_file_a05600

=======
>>>>>>> .merge_file_a11128
	public boolean isUnlockAxeFeat()
	{
		return unlockAxeFeat;
	}

<<<<<<< .merge_file_a05600

=======
>>>>>>> .merge_file_a11128
	public UpgradeChipItem setUnlockAxeFeat()
	{
		this.unlockAxeFeat = true;
		return this;
	}

<<<<<<< .merge_file_a05600

=======
>>>>>>> .merge_file_a11128
	public boolean isUnlockHoeFeat()
	{
		return unlockHoeFeat;
	}

<<<<<<< .merge_file_a05600

=======
>>>>>>> .merge_file_a11128
	public UpgradeChipItem setUnlockHoeFeat()
	{
		this.unlockHoeFeat = true;
		return this;
	}

<<<<<<< .merge_file_a05600

=======
>>>>>>> .merge_file_a11128
	public boolean isUnlockShearsFeat()
	{
		return unlockShearFeat;
	}

<<<<<<< .merge_file_a05600

=======
>>>>>>> .merge_file_a11128
	public UpgradeChipItem setUnlockShearsFeat()
	{
		this.unlockShearFeat = true;
		return this;
	}
<<<<<<< .merge_file_a05600
	
	
=======

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
		
		if (energyCost > 0)
		{
			tooltip.add(new TranslationTextComponent("info.upgradechip.energycosts", energyCost));
		}

		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
>>>>>>> .merge_file_a11128

}
