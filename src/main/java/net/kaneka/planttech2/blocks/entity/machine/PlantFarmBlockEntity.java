package net.kaneka.planttech2.blocks.entity.machine;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.blocks.entity.machine.baseclasses.EnergyInventoryFluidBlockEntity;
import net.kaneka.planttech2.hashmaps.HashMapCropTraits;
import net.kaneka.planttech2.inventory.PlantFarmMenu;
import net.kaneka.planttech2.items.CropSeedItem;
import net.kaneka.planttech2.items.TierItem;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.Level;

import static net.kaneka.planttech2.items.TierItem.ItemType.RANGE_UPGRADE;
import static net.kaneka.planttech2.items.TierItem.ItemType.SPEED_UPGRADE;

public class PlantFarmBlockEntity extends EnergyInventoryFluidBlockEntity
{
	private int[] progress = new int[5];
	
	protected final ContainerData data = new ContainerData()
	{
		public int get(int index)
		{
			return switch (index)
					{
						case 0 -> PlantFarmBlockEntity.this.energystorage.getEnergyStored();
						case 1 -> PlantFarmBlockEntity.this.energystorage.getMaxEnergyStored();
						case 2 -> PlantFarmBlockEntity.this.biomassCap.getCurrentStorage();
						case 3 -> PlantFarmBlockEntity.this.biomassCap.getMaxStorage();
						case 4 -> PlantFarmBlockEntity.this.progress[0];
						case 5 -> PlantFarmBlockEntity.this.progress[1];
						case 6 -> PlantFarmBlockEntity.this.progress[2];
						case 7 -> PlantFarmBlockEntity.this.progress[3];
						case 8 -> PlantFarmBlockEntity.this.progress[4];
						default -> 0;
					};
		}

		public void set(int index, int value)
		{
			switch (index)
			{
				case 0 -> PlantFarmBlockEntity.this.energystorage.setEnergyStored(value);
				case 1 -> PlantFarmBlockEntity.this.energystorage.setEnergyMaxStored(value);
				case 2 -> PlantFarmBlockEntity.this.biomassCap.setCurrentStorage(value);
				case 3 -> PlantFarmBlockEntity.this.biomassCap.setMaxStorage(value);
				case 4 -> PlantFarmBlockEntity.this.progress[0] = value;
				case 5 -> PlantFarmBlockEntity.this.progress[1] = value;
				case 6 -> PlantFarmBlockEntity.this.progress[2] = value;
				case 7 -> PlantFarmBlockEntity.this.progress[3] = value;
				case 8 -> PlantFarmBlockEntity.this.progress[4] = value;
			}

		}

		public int getCount()
		{
			return 9;
		}
	};

	public PlantFarmBlockEntity()
	{
		this(BlockPos.ZERO, ModBlocks.PLANTFARM.defaultBlockState());
	}

	public PlantFarmBlockEntity(BlockPos pos, BlockState state)
	{
		super(ModTileEntities.PLANTFARM_TE, pos, state, 1000, 18, 5000, PlantTechConstants.MACHINETIER_PLANTFARM);
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
								spawnAsEntity(level, worldPosition.above(), stack);
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
		if (item instanceof BlockItem bi)
		{
			Block block = bi.getBlock();
			return block instanceof CropBlock;
		}
		return false;
	}
	
	@Override
	public ContainerData getContainerData()
	{
		return data;
	}
	
	private int getTicks(ItemStack stack)
	{
		if (!stack.isEmpty())
		{
			if (stack.getItem() instanceof CropSeedItem)
			{
				CompoundTag nbt = stack.getOrCreateTag();
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
		if(level != null)
		{
			Item item = stack.getItem(); 
			if (item instanceof CropSeedItem)
			{
				HashMapCropTraits traits = new HashMapCropTraits();
				traits.fromStack(stack);
				PlantTechMain.getCropList().getByName(traits.getType()).calculateDropsReduced(drops, traits, 7, level.random);
				return drops; 
			}
			if (item instanceof BlockItem)
			{
				Block block = ((BlockItem) item).getBlock();
				if (block instanceof CropBlock)
					if (level instanceof ServerLevel sl)
						drops.addAll(Block.getDrops(block.defaultBlockState().setValue(CropBlock.AGE, 7), sl, worldPosition, null));
			}
		}
		return drops; 
	}

	private int getRange()
	{
		ItemStack stack = itemhandler.getStackInSlot(12);
		if (!stack.isEmpty())
		{
			if (stack.getItem() instanceof TierItem ti)
			{
				if (ti.getItemType() == RANGE_UPGRADE)
					return ti.getTier();
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
	public CompoundTag save(CompoundTag compound)
	{
		compound.putIntArray("progress", progress);
		return super.save(compound);
	}

	@Override
	public void load(CompoundTag compound)
	{
//		if (level != null && level.isClientSide)
//			throw new NullPointerException();
		super.load(compound);
		progress = compound.getIntArray("progress");
	}

	@Override
	public String getNameString()
	{
		return "plantfarm";
	}

	@Override
	public AbstractContainerMenu createMenu(int id, Inventory inv, Player player)
	{
		return new PlantFarmMenu(id, inv, this);
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
