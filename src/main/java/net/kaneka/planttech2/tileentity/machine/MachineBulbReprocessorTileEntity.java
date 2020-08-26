package net.kaneka.planttech2.tileentity.machine;

import net.kaneka.planttech2.container.MachineBulbReprocessorContainer;
import net.kaneka.planttech2.items.CropSeedItem;
import net.kaneka.planttech2.items.KnowledgeChip;
import net.kaneka.planttech2.items.MachineBulbItem;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyInventoryFluidTileEntity;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.Tags.Items;

public class MachineBulbReprocessorTileEntity extends EnergyInventoryFluidTileEntity
{
	private int ticksPassed = 0;
	private int selectedId = 0; 
	private int actualTier = 0; 
	protected final IIntArray field_array = new IIntArray()
	{
		public int get(int index)
		{
			switch (index)
			{
			case 0:
				return MachineBulbReprocessorTileEntity.this.energystorage.getEnergyStored();
			case 1:
				return MachineBulbReprocessorTileEntity.this.energystorage.getMaxEnergyStored();
			case 2:
			    return MachineBulbReprocessorTileEntity.this.BIOMASS_CAP.getCurrentStorage();
			case 3:
			    return MachineBulbReprocessorTileEntity.this.BIOMASS_CAP.getMaxStorage();
			case 4: 
				return MachineBulbReprocessorTileEntity.this.ticksPassed; 
			case 5: 
				return MachineBulbReprocessorTileEntity.this.selectedId; 
			case 6: 
				return MachineBulbReprocessorTileEntity.this.actualTier; 
			case 7: 
				return MachineBulbReprocessorTileEntity.this.pos.getX(); 
			case 8: 
				return MachineBulbReprocessorTileEntity.this.pos.getY(); 
			case 9: 
				return MachineBulbReprocessorTileEntity.this.pos.getZ(); 
			default:
				return 0;
			}
		}

		public void set(int index, int value)
		{
			switch (index)
			{
			case 0:
				MachineBulbReprocessorTileEntity.this.energystorage.setEnergyStored(value);
				break;
			case 1:
				MachineBulbReprocessorTileEntity.this.energystorage.setEnergyMaxStored(value);
				break;
			case 2:
				MachineBulbReprocessorTileEntity.this.BIOMASS_CAP.setCurrentStorage(value);
			    break; 
			case 3: 
				MachineBulbReprocessorTileEntity.this.BIOMASS_CAP.setMaxStorage(value);
				break;
			case 4: 
				MachineBulbReprocessorTileEntity.this.ticksPassed = value; 
				break; 
			case 5: 
				MachineBulbReprocessorTileEntity.this.selectedId = value; 
				break; 
			case 6: 
				MachineBulbReprocessorTileEntity.this.actualTier = value; 
				break;
			case 7:
				BlockPos newPos = new BlockPos(value, MachineBulbReprocessorTileEntity.this.pos.getY(), MachineBulbReprocessorTileEntity.this.pos.getZ()); 
				MachineBulbReprocessorTileEntity.this.pos = newPos;
				break;
			case 8:
				BlockPos newPos2 = new BlockPos(MachineBulbReprocessorTileEntity.this.pos.getX(), value, MachineBulbReprocessorTileEntity.this.pos.getZ()); 
				MachineBulbReprocessorTileEntity.this.pos = newPos2;
				break;
			case 9:
				BlockPos newPos3 = new BlockPos(MachineBulbReprocessorTileEntity.this.pos.getX(), MachineBulbReprocessorTileEntity.this.pos.getY(), value); 
				MachineBulbReprocessorTileEntity.this.pos = newPos3;
				break;
			}

		}

		public int size()
		{
			return 10;
		}
	};

	public MachineBulbReprocessorTileEntity()
	{
		super(ModTileEntities.MACHINEBULBREPROCESSOR_TE, 5000, 8, 5000, PlantTechConstants.MACHINETIER_MACHINEBULBREPROCESSOR);
	}

	@Override
	public void doUpdate()
	{
		if(selectedId > 0 && selectedId <= ModItems.MACHINEBULBS.size())
		{

			MachineBulbItem bulb = ModItems.MACHINEBULBS.get(selectedId-1);
    		if (energystorage.getEnergyStored() >= energyPerItem() && bulb.getTier() <= actualTier && BIOMASS_CAP.getCurrentStorage() >= bulb.getNeededBiomass())
    		{
    			ItemStack input = itemhandler.getStackInSlot(0);
    			ItemStack output = itemhandler.getStackInSlot(1);
    			if(!input.isEmpty() && output.isEmpty())
    			{
    				if(Items.SEEDS.contains(input.getItem()) || input.getItem() instanceof CropSeedItem)
    				{
    					if(ticksPassed < ticksPerItem())
    					{
    						ticksPassed++;
    					}
    					else
    					{
    						energystorage.extractEnergy(energyPerItem());
							BIOMASS_CAP.extractBiomass(bulb.getNeededBiomass());
    						input.shrink(1);
    						ticksPassed = 0;
    						itemhandler.setStackInSlot(1, new ItemStack(bulb));
    						addKnowledge();
    					}
    				}
    			}

    		}
		}
		checkTier();
		doEnergyLoop();
		doFluidLoop();
	}
	
	private void checkTier()
	{
		ItemStack stack = itemhandler.getStackInSlot(getKnowledgeChipSlot()); 
		if(!stack.isEmpty())
		{
			Item item = stack.getItem(); 
			if(item != null)
			{
				if(item instanceof KnowledgeChip)
				{
					actualTier = ((KnowledgeChip) item).getTier();
					return; 
				}
			}
		}
		actualTier = 0; 
	}
	
	@Override
	public IIntArray getIntArray()
	{
		return field_array;
	}

	

	public int energyPerItem()
	{
		return 1000;
	}

	public int ticksPerItem()
	{
		return 300;
	}

	@Override
	public String getNameString()
	{
		return "machinebulbreprocessor";
	}

	@Override
	public CompoundNBT write(CompoundNBT compound)
	{
		compound.putInt("tickspassed", ticksPassed);
		super.write(compound);
		return compound;
	}

	@Override
	public void read(BlockState state, CompoundNBT compound)
	{
		this.ticksPassed = compound.getInt("tickspassed");
		super.read(state, compound);
	}

	@Override
	protected int getFluidInSlot()
	{
		return 2;
	}

	@Override
	protected int getFluidOutSlot()
	{
		return 3;
	}

	@Override
	public int getEnergyInSlot()
	{
		return 4;
	}

	@Override
	public int getEnergyOutSlot()
	{
		return 5;
	}
	
	public int getKnowledgeChipSlot()
	{
		return 6; 
	}
	
	@Override
	public Container createMenu(int id, PlayerInventory inv, PlayerEntity player)
	{
		return new MachineBulbReprocessorContainer(id, inv, this);
	}

	public void setSelectedId(int buttonId)
	{
		selectedId = buttonId; 
	}

	@Override
	public int getKnowledgePerAction()
	{
		return 10;
	}

}
