package net.kaneka.planttech2.blocks.entity.machine.baseclasses;

import net.kaneka.planttech2.fluids.capability.BiomassFluidEnergy;
import net.kaneka.planttech2.fluids.capability.IBiomassFluidEnergy;
import net.kaneka.planttech2.items.BiomassContainerItem;
import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class EnergyInventoryFluidBlockEntity extends EnergyInventoryBlockEntity
{
	protected final IBiomassFluidEnergy biomassCap = BiomassFluidEnergy.getTECap(this);

    public EnergyInventoryFluidBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int energyStorage, int invSize, int maxBiomassStorage, int tier)
    {
		super(type,pos, state, energyStorage, invSize, tier);
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
