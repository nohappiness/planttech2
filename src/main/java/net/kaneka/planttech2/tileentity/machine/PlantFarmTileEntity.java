package net.kaneka.planttech2.tileentity.machine;

import net.kaneka.planttech2.blocks.CropBaseBlock;
import net.kaneka.planttech2.container.PlantFarmContainer;
import net.kaneka.planttech2.items.TierItem;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.tileentity.CropsTileEntity;
import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyInventoryTileEntity;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;

public class PlantFarmTileEntity extends EnergyInventoryTileEntity
{
	private int actualX = 0, actualY = 0;
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
			}

		}

		public int size()
		{
			return 2;
		}
	};

	public PlantFarmTileEntity()
	{
		super(ModTileEntities.PLANTFARM_TE, 100000, 17);
	}

	@Override
	public void doUpdate()
	{
		if (world.getGameTime() % (400F - getSpeedReduction() * 45F) == 0F)
		{
			actualX++;
			int range = getRange();
			int diameter = range * 2 + 1;
			if (actualX >= diameter)
			{
				actualX = 0;
				actualY++;
				if (actualY >= diameter)
				{
					actualY = 0;
				}
			}

			if (energystorage.getEnergyStored() >= getEnergyPerAction() || true)
			{

				BlockPos actualPos = this.pos.add(actualX - range, 0, actualY - range);

				BlockState state = world.getBlockState(actualPos);
				if (world.getBlockState(actualPos).getBlock() instanceof CropBaseBlock)
				{
					if (state.get(CropBaseBlock.GROWSTATE) > 6)
					{
						NonNullList<ItemStack> drops = NonNullList.create();
						TileEntity te = world.getTileEntity(actualPos);
						if (te instanceof CropsTileEntity)
						{
							((CropsTileEntity) te).dropsRemoveOneSeed(drops, 7);
							for (ItemStack stack : drops)
							{
								for (int i = 0; i < 15; i++)
								{
									if (!stack.isEmpty())
									{
										stack = itemhandler.insertItem(i, stack, false);
									}
								}
								if (!stack.isEmpty())
								{
									spawnAsEntity(world, pos.up(), stack);
								}
							}
							world.setBlockState(actualPos, state.with(CropBaseBlock.GROWSTATE, 0));
							// energystorage.extractEnergy(getEnergyPerAction());
						}
					}
				}
			}
		}
	}
	
	@Override
	public IIntArray getIntArray()
	{
		return field_array;
	}

	private int getEnergyPerAction()
	{
		return 20 + 40 * (getSpeedReduction() + getRange());
	}

	private int getSpeedReduction()
	{

		ItemStack stack = itemhandler.getStackInSlot(16);
		if (!stack.isEmpty())
		{
			if (stack.getItem() instanceof TierItem)
			{
				TierItem item = (TierItem) stack.getItem();
				if (item.getItemType() == PlantTechConstants.SPEEDUPGRADE_TYPE)
				{
					return item.getTier();
				}
			}
		}
		return 0;
	}

	private int getRange()
	{
		ItemStack stack = itemhandler.getStackInSlot(15);
		if (!stack.isEmpty())
		{
			if (stack.getItem() instanceof TierItem)
			{
				TierItem item = (TierItem) stack.getItem();
				if (item.getItemType() == PlantTechConstants.RANGEUPGRADE_TYPE)
				{
					return item.getTier() + 1;
				}
			}
		}
		return 1;
	}

	@Override
	public CompoundNBT write(CompoundNBT compound)
	{
		compound.putInt("actualx", actualX);
		compound.putInt("actualY", actualY);
		return super.write(compound);
	}

	@Override
	public void read(CompoundNBT compound)
	{
		actualX = compound.getInt("actualx");
		actualY = compound.getInt("actualy");
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
}
