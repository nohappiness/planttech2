package net.kaneka.planttech2.tileentity.machine;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.container.PlantFarmContainer;
import net.kaneka.planttech2.hashmaps.HashMapCropTraits;
import net.kaneka.planttech2.items.CropSeedItem;
import net.kaneka.planttech2.items.TierItem;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyInventoryFluidTileEntity;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.block.Block;
import net.minecraft.block.CropsBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.world.server.ServerWorld;

public class PlantFarmTileEntity extends EnergyInventoryFluidTileEntity
{
	private int[] progress = new int[5]; 
	
	protected final IIntArray field_array = new IIntArray()
	{
		public int get(int index)
		{
			switch (index)
			{
			case 0:
				return PlantFarmTileEntity.this.energystorage.getEnergyStored();
			case 1:
				return PlantFarmTileEntity.this.energystorage.getMaxEnergyStored();
			case 2:
			    return PlantFarmTileEntity.this.fluidtank.getBiomass();
			case 3:
			    return PlantFarmTileEntity.this.fluidtank.getCapacity(); 
			case 4: 
				return PlantFarmTileEntity.this.progress[0]; 
			case 5: 
				return PlantFarmTileEntity.this.progress[1]; 
			case 6: 
				return PlantFarmTileEntity.this.progress[2]; 
			case 7: 
				return PlantFarmTileEntity.this.progress[3]; 
			case 8: 
				return PlantFarmTileEntity.this.progress[4]; 	
			default:
				return 0;
			}
		}

		public void set(int index, int value)
		{
			switch (index)
			{
			case 0:
				PlantFarmTileEntity.this.energystorage.setEnergyStored(value);
				break;
			case 1:
				PlantFarmTileEntity.this.energystorage.setEnergyMaxStored(value);
				break;
			case 2:
				PlantFarmTileEntity.this.fluidtank.setBiomass(value);
			    break; 
			case 3: 
				PlantFarmTileEntity.this.fluidtank.setCapacity(value);
				break;
			case 4: 
				PlantFarmTileEntity.this.progress[0] = value; 
				break; 
			case 5: 
				PlantFarmTileEntity.this.progress[1] = value; 
				break; 
			case 6: 
				PlantFarmTileEntity.this.progress[2] = value; 
				break; 
			case 7: 
				PlantFarmTileEntity.this.progress[3] = value; 
				break; 
			case 8: 
				PlantFarmTileEntity.this.progress[4] = value; 
				break;
			}

		}

		public int size()
		{
			return 9;
		}
	};

	public PlantFarmTileEntity()
	{
		super(ModTileEntities.PLANTFARM_TE, 1000, 18, 5000, PlantTechConstants.MACHINETIER_PLANTFARM);
	}

	@Override
	public void doUpdate()
	{
		int range = getRange(); 
		ItemStack seed = itemhandler.getStackInSlot(0);
		if(!seed.isEmpty())
		{
			if(isSeed(seed))
			{
				for(int i = 0 ; i <= range; i++)
	    		{
	    			if(progress[i] < getTicks(seed))
	    			{
	    				progress[i]++;
	    			}
	    			else
	    			{
	    				if(energystorage.getEnergyStored() > getEnergyPerAction())
	    				{
    	    				NonNullList<ItemStack> drops = getDrops(seed);
    	    				if(!drops.isEmpty())
    	    				{
        	    				for (ItemStack stack : drops)
        	    				{
        	    					for (int k = 0; k < 15; k++)
        	    					{
        	    						if (!stack.isEmpty())
        	    						{
        	    							stack = itemhandler.insertItem(k, stack, false);
        	    						}
        	    					}
        	    					if (!stack.isEmpty())
        	    					{
        	    						spawnAsEntity(world, pos.up(), stack);
        	    					}
        	    				}
        	    				energystorage.extractEnergy(getEnergyPerAction(), false);
        	    				progress[i] = 0; 
        	    				addKnowledge();
    	    				}
	    				}
	    			}
	    		}
			}
		}
		
		doEnergyLoop();
	}
	
	private boolean isSeed(ItemStack stack)
	{
		if(!stack.isEmpty())
		{
			Item item = stack.getItem(); 
			if(item instanceof CropSeedItem)
			{
				return true; 
			}
			
			if(item instanceof BlockItem)
			{
				Block block = ((BlockItem) item).getBlock();
				if(block instanceof CropsBlock)
				{
					return true;
				}
			}
		}
		return false; 
	}
	
	@Override
	public IIntArray getIntArray()
	{
		return field_array;
	}
	
	private int getTicks(ItemStack stack)
	{
		if(!stack.isEmpty())
		{
			Item item = stack.getItem(); 
			if(item instanceof CropSeedItem)
			{
				if (stack.hasTag())
				{
					CompoundNBT nbt = stack.getTag();
					if(nbt.contains("growspeed"))
					{
						return ((90 - nbt.getInt("growspeed") * 6) * 20) * 7;
					}
				}
			}
		}
		return 90 * 20 * 7; 
	}
	
	public int getTicks()
	{
		ItemStack stack = itemhandler.getStackInSlot(0);
		if(!stack.isEmpty())
		{
			Item item = stack.getItem(); 
			if(item instanceof CropSeedItem)
			{
				CompoundNBT nbt = stack.getTag();
				if(nbt.contains("growspeed"))
				{
					return ((90 - nbt.getInt("growspeed") * 6) * 20) * 7;
				}
				
			}
		}
		return 90 * 20 * 7; 
	}

	private int getEnergyPerAction()
	{
		return 400;
	}
	
	private NonNullList<ItemStack> getDrops(ItemStack stack)
	{
		NonNullList<ItemStack> drops = NonNullList.create();
		if(!stack.isEmpty())
		{
			Item item = stack.getItem(); 
			if(item instanceof CropSeedItem)
			{
				HashMapCropTraits traits = new HashMapCropTraits();
				traits.fromStack(stack);
				PlantTechMain.croplist.getEntryByName(traits.getType()).calculateDropsReduced(drops, traits, 7);
				return drops; 
			}
			if(item instanceof BlockItem)
			{
				Block block = ((BlockItem) item).getBlock(); 
				if(block != null)
				{
					if(block instanceof CropsBlock)
					{
						if(world instanceof ServerWorld)
						{
							drops.addAll(Block.getDrops(block.getDefaultState().with(CropsBlock.AGE, 7), (ServerWorld)world, pos, null));
						}
					}
				}
			}
		}
		return drops; 
	}

	private int getRange()
	{
		ItemStack stack = itemhandler.getStackInSlot(12);
		if (!stack.isEmpty())
		{
			if (stack.getItem() instanceof TierItem)
			{
				TierItem item = (TierItem) stack.getItem();
				if (item.getItemType() == PlantTechConstants.RANGEUPGRADE_TYPE)
				{
					return item.getTier();
				}
			}
		}
		return 0;
	}

	@Override
	public CompoundNBT write(CompoundNBT compound)
	{
		for(int i = 0; i < progress.length; i++)
		{
			compound.putInt("progress_" + i , progress[i]);
		}
		return super.write(compound);
	}

	@Override
	public void read(CompoundNBT compound)
	{
		for(int i = 0; i < progress.length; i++)
		{
			progress[i] = compound.getInt("progress_" + i);
		}
		super.read(compound);
	}

	@Override
	public String getNameString()
	{
		return "plantfarm";
	}

	@Override
	public Container createMenu(int id, PlayerInventory inv, PlayerEntity player)
	{
		return new PlantFarmContainer(id, inv, this);
	}

	@Override
	public int getEnergyInSlot()
	{
		return 15;
	}

	@Override
	public int getEnergyOutSlot()
	{
		return 16;
	}

	@Override
	public int getFluidInSlot()
	{
		return 13;
	}

	@Override
	public int getFluidOutSlot()
	{
		return 14;
	}

	@Override
	public int getKnowledgeChipSlot()
	{
		return 17;
	}

	@Override
	public int getKnowledgePerAction()
	{
		return 50;
	}
}
