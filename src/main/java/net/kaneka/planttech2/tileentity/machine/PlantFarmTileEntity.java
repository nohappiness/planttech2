package net.kaneka.planttech2.tileentity.machine;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.container.PlantFarmContainer;
import net.kaneka.planttech2.hashmaps.HashMapCropTraits;
import net.kaneka.planttech2.items.CropSeedItem;
import net.kaneka.planttech2.items.TierItem;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.tileentity.machine.baseclasses.ConvertEnergyInventoryFluidTileEntity;
import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyInventoryFluidTileEntity;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
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

import static net.kaneka.planttech2.items.TierItem.ItemType.RANGE_UPGRADE;
import static net.kaneka.planttech2.items.TierItem.ItemType.SPEED_UPGRADE;

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
			    return PlantFarmTileEntity.this.biomassCap.getCurrentStorage();
			case 3:
			    return PlantFarmTileEntity.this.biomassCap.getMaxStorage();
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
				PlantFarmTileEntity.this.biomassCap.setCurrentStorage(value);
			    break; 
			case 3: 
				PlantFarmTileEntity.this.biomassCap.setMaxStorage(value);
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
		super.doUpdate();
		ItemStack seed = itemhandler.getStackInSlot(0);
		if (isSeed(seed))
		{
			for (int i = 0 ; i <= getRange(); i++)
			{
				if (progress[i] < getTicks(seed))
					progress[i] += getUpgradeTier(SPEED_UPGRADE) * 5 + 1;
				else if (energystorage.getEnergyStored() > energyPerAction())
				{
					NonNullList<ItemStack> drops = getDrops(seed);
					if (!drops.isEmpty())
					{
						for (ItemStack stack : drops)
						{
							for (int k = 0; k < 15; k++)
							{
								if (!stack.isEmpty())
									stack = itemhandler.insertItem(k, stack, false);
							}
							if (!stack.isEmpty())
								spawnAsEntity(world, pos.up(), stack);
						}
						energystorage.extractEnergy(energyPerAction(), false);
						progress[i] = 0;
						addKnowledge();
					}
				}
				else
				{
					resetProgress(false);
					break;
				}
			}
		}
		else
			resetProgress(false);
	}

	@Override
	protected void resetProgress(boolean forced)
	{
		for (int i = 0; i <= getRange(); i++)
			progress[i] = 0;
	}

	private boolean isSeed(ItemStack stack)
	{
		Item item = stack.getItem();
		if (item instanceof CropSeedItem)
			return true;
		if (item instanceof BlockItem)
		{
			Block block = ((BlockItem) item).getBlock();
			return block instanceof CropsBlock;
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
		if (!stack.isEmpty())
		{
			Item item = stack.getItem(); 
			if (item instanceof CropSeedItem)
			{
				CompoundNBT nbt = stack.getOrCreateTag();
				if(nbt.contains("growspeed"))
					return (90 - nbt.getInt("growspeed") * 6) * 20 * 7;
			}
		}
		return 90 * 20 * 7; 
	}
	
	public int getTicks()
	{
		return getTicks(itemhandler.getStackInSlot(0));
	}

	private NonNullList<ItemStack> getDrops(ItemStack stack)
	{
		NonNullList<ItemStack> drops = NonNullList.create();
		if(world != null)
		{
			Item item = stack.getItem(); 
			if (item instanceof CropSeedItem)
			{
				HashMapCropTraits traits = new HashMapCropTraits();
				traits.fromStack(stack);
				PlantTechMain.getCropList().getByName(traits.getType()).calculateDropsReduced(drops, traits, 7, world.rand);
				return drops; 
			}
			if (item instanceof BlockItem)
			{
				Block block = ((BlockItem) item).getBlock();
				if (block instanceof CropsBlock)
					if (world instanceof ServerWorld)
						drops.addAll(Block.getDrops(block.getDefaultState().with(CropsBlock.AGE, 7), (ServerWorld)world, pos, null));
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
				if (item.getItemType() == RANGE_UPGRADE)
					return item.getTier();
			}
		}
		return 0;
	}

	@Override
	public int getUpgradeSlot()
	{
		return 11;
	}

	@Override
	public int energyPerAction()
	{
		return 400;
	}

	@Override
	public CompoundNBT write(CompoundNBT compound)
	{
		compound.putIntArray("progress", progress);
		return super.write(compound);
	}

	@Override
	public void read(BlockState state, CompoundNBT compound)
	{
		super.read(state, compound);
		progress = compound.getIntArray("progress");
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
