package net.kaneka.planttech2.tileentity.machine.baseclasses;

import net.kaneka.planttech2.fluids.capability.BiomassFluidEnergy;
import net.kaneka.planttech2.fluids.capability.IBiomassFluidEnergy;
import net.kaneka.planttech2.items.BiomassContainerItem;
import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
				if (biomassCap.getCurrentStorage() < biomassCap.getMaxStorage())
				{
					biomassCap.changeCurrentStorage(BiomassFluidEnergy.getItemStackCap(stack).extractBiomass(1));
//					BiomassContainerItem.changeCurrentStorage(stack, -1);
					itemhandler.setStackInSlot(getFluidInSlot(), stack);
				}
//				biomassCap.changeCurrentStorage(BiomassContainerItem.extractBiomass(stack, 1));
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
		if (stack2.getItem() instanceof BiomassContainerItem)
		{
			IBiomassFluidEnergy capability = BiomassFluidEnergy.getItemStackCap(stack2);
			int need = capability.getMaxStorage() - capability.getCurrentStorage();
			int have = biomassCap.getCurrentStorage();
			int amount = biomassCap.extractBiomass(Math.min(Math.min(need, have), 4));
			capability.recieveBiomass(amount);
//			BiomassContainerItem.changeCurrentStorage(stack2, amount);
			itemhandler.setStackInSlot(getFluidOutSlot(), stack2);
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
		{
			setChanged();
		}
    }

	public abstract int getFluidInSlot();
    
    public abstract int getFluidOutSlot();

}
