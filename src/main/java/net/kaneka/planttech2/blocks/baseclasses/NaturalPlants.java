package net.kaneka.planttech2.blocks.baseclasses;

import net.kaneka.planttech2.registries.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.HashSet;

public class NaturalPlants extends Block
{
	//    private float width;
	//    private float height;
	protected static VoxelShape BASE_SHAPE;

	public NaturalPlants(float width, float height)
	{
		super(Block.Properties.create(Material.PLANTS).notSolid().sound(SoundType.PLANT));
		//        this.width = width;
		//        this.height = height;
		float halfWidth = width / 2;
		BASE_SHAPE = Block.makeCuboidShape(8 - halfWidth, 0, 8 - halfWidth, 8 + halfWidth, height, 8 + halfWidth);
	}

	public NaturalPlants()
	{
		//default size
		this(6, 10);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		Vector3d vec3d = state.getOffset(worldIn, pos);
		return BASE_SHAPE.withOffset(vec3d.x, vec3d.y, vec3d.z);
	}

	@Override
	public OffsetType getOffsetType()
	{
		return Block.OffsetType.XZ;
	}

	//    /**
	//     * set both x and y -1 for the centre position
	//     */
    /*private VoxelShape getShape(float x, float z)
    {
        if ((x == -1 && z == -1) || (x < 0 && z < 0))
        {
            float halfWidth = this.width / 2;
            return Block.makeCuboidShape(8 - halfWidth, 0, 16 - halfWidth, 8 - halfWidth, this.height, 16 - halfWidth);
        }
        x = Math.min(16 - this.width, x);
        z = Math.min(16 - this.width, z);
        return Block.makeCuboidShape(x, 0, z, x + this.width, this.height, this.width);
    }*/

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return VoxelShapes.empty();
	}

	public boolean canPlaceAt(World world, BlockPos pos)
	{
		if (getValidGrounds().contains(world.getBlockState(pos.down()).getBlock())) {
			return world.isAirBlock(pos);
		}
		return false;
	}

	public HashSet<Block> getValidGrounds()
	{
		HashSet<Block> list = new HashSet<>();
		list.add(Blocks.DIRT);
		list.add(Blocks.GRASS_BLOCK);
		list.add(ModBlocks.PLANTIUM_BLOCK);
		list.add(Blocks.COARSE_DIRT);
		list.add(Blocks.FARMLAND);
		list.add(Blocks.PODZOL);
		return list;
	}

	//    /**
	//     * returns whether this block can be obtained by Plant Obtainer
	//     * {@link PlantObtainer#onItemUse(ItemUseContext)}
	//     */
	//    public abstract boolean canBeObtained(ItemUseContext context);
}
