package net.kaneka.planttech2.items;


import net.kaneka.planttech2.container.TeleporterContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraftforge.fml.network.NetworkHooks;

public class TeleporterItem extends EnergyStorageItem
{

	public TeleporterItem(String name, Properties property, int basecapacity)
	{
		super(name, property, basecapacity);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
	{
		ItemStack stack = player.getHeldItem(hand);
		if(Minecraft.getInstance().gameSettings.keyBindSneak.isKeyDown())
		{
			
		}
		else
		{
			if (!world.isRemote && player instanceof ServerPlayerEntity) 
			{
    			NetworkHooks.openGui((ServerPlayerEntity) player, new NamedContainerProvider(stack), buffer -> buffer.writeItemStack(stack));
			}
		}
		return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
	}

	

	public static boolean addPoint(ItemStack stack, Dimension dim, BlockPos pos)
	{
		boolean retval = false; 
		CompoundNBT stacknbt = stack.getTag();
		if (stacknbt != null)
		{
			stacknbt = new CompoundNBT();
		}
		
		int amount = 0; 
		if(stacknbt.contains("amount"))
		{
			amount = stacknbt.getInt("amount"); 
		}
		
		if(amount < 8)
		{
    		CompoundNBT newnbt = new CompoundNBT();
    		newnbt.putString("dim", dim.getType().getRegistryName().toString());
    		newnbt.putInt("x", pos.getX());
    		newnbt.putInt("y", pos.getY());
    		newnbt.putInt("z", pos.getZ());
    		
    		for(int i = 0; i <= amount; i++)
    		{
    			if(!stacknbt.contains("point_" + i))
    			{
    				stacknbt.put("point_" + i, newnbt); 
    				stacknbt.putInt("amount", amount + 1);
    				retval = true; 
    			}
    		}
		}
		
		return retval; 

	}
	
	public static class NamedContainerProvider implements INamedContainerProvider
	{
		
		private final ItemStack stack; 
		
		public NamedContainerProvider(ItemStack stack)
		{
			this.stack = stack; 
		}
		

		@Override
		public Container createMenu(int id, PlayerInventory inv, PlayerEntity entity)
		{
			return new TeleporterContainer(id, inv, stack);
		}

		@Override
		public ITextComponent getDisplayName()
		{
			return new StringTextComponent("container.teleporter");
		}
		
	}

}
