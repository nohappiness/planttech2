package net.kaneka.planttech2.blocks.baseclasses;

import net.kaneka.planttech2.items.PlantObtainer;
import net.kaneka.planttech2.registries.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.HashSet;

public abstract class NaturalPlants extends BaseBlock
{
    public NaturalPlants(Properties property, String name, ItemGroup group, boolean hasItem)
    {
        super(property, name, group, hasItem);
    }

    /**
     * returns whether it is possible to set the given position in the given world with this block
     */
    public boolean canPlaceAt(World world, BlockPos pos, @Nullable BlockItemUseContext context)
    {
        if (getValidGrounds().contains(world.getBlockState(pos.down()).getBlock()))
        {
            return context == null ? world.isAirBlock(pos) : world.getBlockState(pos).isReplaceable(context);
        }
        return false;
    }

    /**
     * returns a HashSet of valid blocks (ground)
     */
    public HashSet<Block> getValidGrounds()
    {
        HashSet<Block> list = new HashSet<>();
        list.add(Blocks.DIRT);
        list.add(Blocks.GRASS_BLOCK);
        list.add(ModBlocks.PLANTIUM_BLOCK);
        return list;
    }

    /**
     * returns whether this block can be obtained by Plant Obtainer
     * {@link PlantObtainer#onItemUse(ItemUseContext)}
     */
    public abstract boolean canBeObtained();
}
