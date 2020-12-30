package net.kaneka.planttech2.tileentity.machine.baseclasses;

import net.kaneka.planttech2.fluids.capability.BiomassFluidEnergy;
import net.kaneka.planttech2.fluids.capability.IBiomassFluidEnergy;
import net.kaneka.planttech2.items.BiomassContainerItem;
import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntityType;

import static net.kaneka.planttech2.items.TierItem.ItemType.SPEED_UPGRADE;

public abstract class EnergyInventoryFluidTileEntity extends EnergyInventoryTileEntity
{
	protected final IBiomassFluidEnergy biomassCap = BiomassFluidEnergy.getTECap(this);

    public EnergyInventoryFluidTileEntity(TileEntityType<?> type, int energyStorage, int invSize, int maxBiomassStorage, int tier)
    {
		super(type, energyStorage, invSize, tier);
		biomassCap.setMaxStorage(maxBiomassStorage);
		biomassCap.setCurrentStorage(0);
    }

	@Override
	public void doUpdate()
	{
		super.doUpdate();
		doFluidLoop();
	}

	public void doFluidLoop()
    {
    	boolean changesMade = false;
		ItemStack stack = itemhandler.getStackInSlot(getFluidInSlot());
		ItemStack stack2 = itemhandler.getStackInSlot(getFluidOutSlot());
		if (biomassCap.getCurrentStorage() < biomassCap.getMaxStorage())
		{
			if (stack.getItem() instanceof BiomassContainerItem)
			{
//				BIOMASS_CAP.changeCurrentStorage(BiomassFluidEnergy.getItemStackCap(stack).extractBiomass(1));
				biomassCap.changeCurrentStorage(BiomassContainerItem.extractBiomass(stack, 1));
				changesMade = true;
			}
			else if (stack.getItem() == ModItems.BIOMASS_BUCKET)
			{
				stack.shrink(1);
				itemhandler.setStackInSlot(getFluidInSlot(), new ItemStack(Items.BUCKET));
				biomassCap.recieveBiomass(3000);
				changesMade = true;
			}
		}
		if(stack2.getItem() instanceof BiomassContainerItem)
		{
//			IBiomassFluidEnergy capability = BiomassFluidEnergy.getItemStackCap(stack2);
			for (int i=4;i>1;i--)
			{
//				if (capability.getMaxStorage() - capability.getCurrentStorage() >= i && BIOMASS_CAP.getCurrentStorage() >= i)
				if (BiomassContainerItem.getCapacity() - BiomassContainerItem.getCurrentStorage(stack2) >= i && biomassCap.getCurrentStorage() >= i)
				{
//					capability.recieveBiomass(BIOMASS_CAP.extractBiomass(i));
					BiomassContainerItem.receiveBiomass(stack2, biomassCap.extractBiomass(i));
					break;
				}
			}
			changesMade = true;
		}
		else if (stack2.getItem() == Items.BUCKET)
		{
			if (biomassCap.getCurrentStorage() >= 3000)
			{
				stack2.shrink(1);
				itemhandler.setStackInSlot(getFluidOutSlot(), new ItemStack(ModItems.BIOMASS_BUCKET));
				biomassCap.extractBiomass(3000);
				changesMade = true;
			}
		}
		if (changesMade)
			markDirty();
    }

	public int fluidPerTick()
	{
		return 5 + getUpgradeTier(SPEED_UPGRADE) * 3;
	}

	protected abstract int getFluidInSlot();
    
    protected abstract int getFluidOutSlot();

}
