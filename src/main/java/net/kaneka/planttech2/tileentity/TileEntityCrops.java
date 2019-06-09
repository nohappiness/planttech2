package net.kaneka.planttech2.tileentity;

import javax.annotation.Nullable;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.blocks.BlockCropBase;
import net.kaneka.planttech2.enums.EnumTraitsInt;
import net.kaneka.planttech2.hashmaps.HashMapCropTraits;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;

public class TileEntityCrops extends TileEntity implements ITickableTileEntity
{
	private long startTick = 0;
	private HashMapCropTraits traits = new HashMapCropTraits();

	public TileEntityCrops()
	{
		super(ModTileEntities.CROPS_TE);
	}

	@Override
	public void tick()
	{
		if (this.world != null && !this.world.isRemote)
		{

			if ((world.getGameTime() - this.startTick) % ((90L - traits.getTrait(EnumTraitsInt.GROWSPEED) * 6L) * 20L) == 0L)
			{
				Block block = world.getBlockState(pos).getBlock();
				if (block instanceof BlockCropBase)
				{
					((BlockCropBase) block).updateCrop(this.world, this.pos, this.traits);
				}
			}
		}

	}

	public void setTraits(HashMapCropTraits traits)
	{
		this.traits = traits;
	}

	public HashMapCropTraits getTraits()
	{
		return this.traits;
	}

	public String getCropType()
	{
		return this.traits.getType();
	}

	public void setStartTick()
	{
		this.startTick = world.getGameTime();
	}

	public ItemStack getSeedDrop()
	{
		// TODO
		/*
		 * System.out.println(this.traits.getType()); ItemStack stack =
		 * this.traits.createItemStackwithTraits(this.traits.getType().getSeed());
		 * System.out.println(stack);
		 */
		return new ItemStack(Items.CARROT);

	}

	public NonNullList<ItemStack> addDrops(NonNullList<ItemStack> drops, int growstate)
	{
		PlantTechMain.croplist.getEntryByName(this.traits.getType()).calculateDrops(drops, this.traits, growstate);
		return drops;
	}

	public void dropsRemoveOneSeed(NonNullList<ItemStack> drops, int growstate)
	{
		PlantTechMain.croplist.getEntryByName(this.traits.getType()).calculateDropsReduced(drops, this.traits, growstate);
	}

	@Override
	@Nullable
	public SUpdateTileEntityPacket getUpdatePacket()
	{
		CompoundNBT nbtTagCompound = new CompoundNBT();
		write(nbtTagCompound);
		return new SUpdateTileEntityPacket(this.pos, 1, nbtTagCompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet)
	{
		read(packet.getNbtCompound());
	}

	@Override
	public CompoundNBT getUpdateTag()
	{
		CompoundNBT nbtTagCompound = new CompoundNBT();
		write(nbtTagCompound);
		return nbtTagCompound;
	}

	@Override
	public void handleUpdateTag(CompoundNBT tag)
	{
		this.read(tag);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound)
	{
		super.write(compound);
		compound.putLong("starttick", this.startTick);
		compound = traits.addToNBT(compound);
		return compound;
	}

	@Override
	public void read(CompoundNBT compound)
	{
		super.read(compound);
		this.startTick = compound.getLong("starttick");
		this.traits.fromNBT(compound);
	}
}
