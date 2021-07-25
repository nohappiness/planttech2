package net.kaneka.planttech2.blocks.entity;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.blocks.CropBaseBlock;
import net.kaneka.planttech2.enums.EnumTraitsInt;
import net.kaneka.planttech2.hashmaps.HashMapCropTraits;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class CropsBlockEntity extends BlockEntity
{
	private long startTick = 0;
	private HashMapCropTraits traits = new HashMapCropTraits();

	public CropsBlockEntity(BlockPos pos, BlockState state)
	{
		super(ModTileEntities.CROPS_TE, pos, state);
	}


	public static void tick(Level level, BlockPos pos, BlockState state, BlockEntity be)
	{
		if (level != null && !level.isClientSide) {
			if(be instanceof  CropsBlockEntity cbe) {
				if ((level.getGameTime() - cbe.startTick) % ((90L - cbe.traits.getTrait(EnumTraitsInt.GROWSPEED) * 6L) * 20L) == 0L) {
					Block block = level.getBlockState(cbe.worldPosition).getBlock();
					if (block instanceof CropBaseBlock)
						((CropBaseBlock) block).updateCrop(cbe.level, cbe.worldPosition, cbe.traits);
				}
			}
		}

	}

	public void setTraits(HashMapCropTraits traits)
	{
		this.traits = traits;
	}
	
	public boolean isAnalysed()
	{
		return traits.isAnalysed(); 
	}
	
	public HashMapCropTraits setAnalysedAndGetTraits()
	{
		traits.setAnalysed(true);
		return getTraits(); 
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
		this.startTick = level.getGameTime();
	}

	public List<ItemStack> addDrops(List<ItemStack> drops, int growstate)
	{
		PlantTechMain.getCropList().getByName(this.traits.getType()).calculateDrops(drops, this.traits, growstate, level.random);
		return drops;
	}

	public void dropsRemoveOneSeed(NonNullList<ItemStack> drops, int growstate)
	{
		PlantTechMain.getCropList().getByName(this.traits.getType()).calculateDropsReduced(drops, this.traits, growstate, level.random);
	}


	@Override
	public CompoundTag getUpdateTag()
	{
		CompoundTag nbtTagCompound = new CompoundTag();
		save(nbtTagCompound);
		return nbtTagCompound;
	}

	@Override
	public CompoundTag save(CompoundTag compound)
	{
		super.save(compound);
		compound.putLong("starttick", this.startTick);
		compound = traits.addToNBT(compound);
		return compound;
	}
	
	@Override
	public void load(CompoundTag compound)
	{
		super.load(compound);
		this.startTick = compound.getLong("starttick");
		this.traits.fromNBT(compound);
	}
}
