package net.kaneka.planttech2.tileentity.machine.baseclasses;

import net.kaneka.planttech2.fluids.capability.BiomassFluidEnergy;
import net.kaneka.planttech2.fluids.capability.IBiomassFluidEnergy;
import net.kaneka.planttech2.items.BiomassContainerItem;
import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;

public abstract class EnergyInventoryFluidTileEntity extends EnergyInventoryTileEntity
{
	protected final IBiomassFluidEnergy BIOMASS_CAP = BiomassFluidEnergy.getTECap(this);

    public EnergyInventoryFluidTileEntity(TileEntityType<?> type, int energyStorage, int invSize, int maxBiomassStorage, int tier)
    {
		super(type, energyStorage, invSize, tier);
		BIOMASS_CAP.setMaxStorage(maxBiomassStorage);
		BIOMASS_CAP.setCurrentStorage(0);
    }

    public void doFluidLoop()
    {
    	boolean changesMade = false;
		ItemStack stack = itemhandler.getStackInSlot(getFluidInSlot());
		ItemStack stack2 = itemhandler.getStackInSlot(getFluidOutSlot());
		if (BIOMASS_CAP.getCurrentStorage() < BIOMASS_CAP.getMaxStorage())
		{
			if (stack.getItem() instanceof BiomassContainerItem)
			{
//				BIOMASS_CAP.changeCurrentStorage(BiomassFluidEnergy.getItemStackCap(stack).extractBiomass(1));
				BIOMASS_CAP.changeCurrentStorage(BiomassContainerItem.extractBiomass(stack, 1));
				changesMade = true;
			}
			else if (stack.getItem() == ModItems.BIOMASS_BUCKET)
			{
				stack.shrink(1);
				itemhandler.setStackInSlot(getFluidInSlot(), new ItemStack(Items.BUCKET));
				BIOMASS_CAP.recieveBiomass(3000);
				changesMade = true;
			}
		}
		if(stack2.getItem() instanceof BiomassContainerItem)
		{
//			IBiomassFluidEnergy capability = BiomassFluidEnergy.getItemStackCap(stack2);
			for (int i=4;i>1;i--)
			{
//				if (capability.getMaxStorage() - capability.getCurrentStorage() >= i && BIOMASS_CAP.getCurrentStorage() >= i)
				if (BiomassContainerItem.getCapacity() - BiomassContainerItem.getCurrentStorage(stack2) >= i && BIOMASS_CAP.getCurrentStorage() >= i)
				{
//					capability.recieveBiomass(BIOMASS_CAP.extractBiomass(i));
					BiomassContainerItem.receiveBiomass(stack2, BIOMASS_CAP.extractBiomass(i));
					break;
				}
			}
			changesMade = true;
		}
		else if (stack2.getItem() == Items.BUCKET)
		{
			if (BIOMASS_CAP.getCurrentStorage() >= 3000)
			{
				stack2.shrink(1);
				itemhandler.setStackInSlot(getFluidOutSlot(), new ItemStack(ModItems.BIOMASS_BUCKET));
				BIOMASS_CAP.extractBiomass(3000);
				changesMade = true;
			}
		}
		if (changesMade)
			markDirty();
    }
    
    protected abstract int getFluidInSlot();
    
    protected abstract int getFluidOutSlot();

}
