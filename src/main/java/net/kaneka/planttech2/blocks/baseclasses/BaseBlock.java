package net.kaneka.planttech2.blocks.baseclasses;


import net.kaneka.planttech2.registries.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

public class BaseBlock extends Block
{
	private String name;
	private ItemGroup group;

	public BaseBlock(Properties property, String name, ItemGroup group, boolean hasItem)
	{
		this(property, name, group, hasItem, false);
	}

	public BaseBlock(Properties property, String name, ItemGroup group, boolean hasItem, boolean requireSpecialRender)
	{
		super(property);
		this.group = group;
		this.name = name;
		setRegistryName(name);

		if (requireSpecialRender)
		{
			ModBlocks.SPECIAL_RENDER_BLOCKS.add(this);
		}
		ModBlocks.BLOCKS.add(this);
		if (hasItem)
		{
			ModBlocks.BLOCKITEMS.add(this);
		}
	}

	public Item createItemBlock()
	{
		return new BlockItem(this, new Item.Properties().group(group)).setRegistryName(name);
	}
	
	protected VoxelShape[] makeShapes(float nodeWidth, float extensionWidth, float p_196408_3_, float p_196408_4_, float p_196408_5_) {
	      float f = 8.0F - nodeWidth;
	      float f1 = 8.0F + nodeWidth;
	      float f2 = 8.0F - extensionWidth;
	      float f3 = 8.0F + extensionWidth;
	      VoxelShape voxelshape = Block.makeCuboidShape((double)f, 0.0D, (double)f, (double)f1, (double)p_196408_3_, (double)f1);
	      VoxelShape voxelshape1 = Block.makeCuboidShape((double)f2, (double)p_196408_4_, 0.0D, (double)f3, (double)p_196408_5_, (double)f3);
	      VoxelShape voxelshape2 = Block.makeCuboidShape((double)f2, (double)p_196408_4_, (double)f2, (double)f3, (double)p_196408_5_, 16.0D);
	      VoxelShape voxelshape3 = Block.makeCuboidShape(0.0D, (double)p_196408_4_, (double)f2, (double)f3, (double)p_196408_5_, (double)f3);
	      VoxelShape voxelshape4 = Block.makeCuboidShape((double)f2, (double)p_196408_4_, (double)f2, 16.0D, (double)p_196408_5_, (double)f3);
	      VoxelShape voxelshape5 = VoxelShapes.or(voxelshape1, voxelshape4);
	      VoxelShape voxelshape6 = VoxelShapes.or(voxelshape2, voxelshape3);
	      VoxelShape[] avoxelshape = new VoxelShape[]{VoxelShapes.empty(), voxelshape2, voxelshape3, voxelshape6, voxelshape1, VoxelShapes.or(voxelshape2, voxelshape1), VoxelShapes.or(voxelshape3, voxelshape1), VoxelShapes.or(voxelshape6, voxelshape1), voxelshape4, VoxelShapes.or(voxelshape2, voxelshape4), VoxelShapes.or(voxelshape3, voxelshape4), VoxelShapes.or(voxelshape6, voxelshape4), voxelshape5, VoxelShapes.or(voxelshape2, voxelshape5), VoxelShapes.or(voxelshape3, voxelshape5), VoxelShapes.or(voxelshape6, voxelshape5)};

	      for(int i = 0; i < 16; ++i) {
	         avoxelshape[i] = VoxelShapes.or(voxelshape, avoxelshape[i]);
	      }

	      return avoxelshape;
	   }
}