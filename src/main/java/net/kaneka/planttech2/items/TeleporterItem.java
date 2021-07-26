package net.kaneka.planttech2.items;


import net.kaneka.planttech2.inventory.TeleporterContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class TeleporterItem extends EnergyStorageItem
{

	public TeleporterItem(Properties property, int basecapacity)
	{
		super(property, basecapacity);
	}

	/*
	@Override
	public InteractionResultHolder<ItemStack> onItemRightClick(Level world, PlayerEntity player, Hand hand)
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
		return new InteractionResultHolder<ItemStack>(InteractionResultHolderType.SUCCESS, stack);
	}

	*/
    /*
	public static boolean addPoint(ItemStack stack, Dimension dim, BlockPos pos)
	{
		boolean retval = false; 
		CompoundTag stacknbt = stack.getTag();
		if (stacknbt != null)
		{
			stacknbt = new CompoundTag();
		}
		
		int amount = 0; 
		if(stacknbt.contains("amount"))
		{
			amount = stacknbt.getInt("amount"); 
		}
		
		if(amount < 8)
		{
    		CompoundTag newnbt = new CompoundTag();
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
	*/
	public static class NamedMenuProvider implements MenuProvider
	{
		
		private final ItemStack stack; 
		
		public NamedMenuProvider(ItemStack stack)
		{
			this.stack = stack; 
		}

		@Override
		public AbstractContainerMenu createMenu(int id, Inventory inv, Player entity)
		{
			return new TeleporterContainer(id, inv, stack);
		}

		@Override
		public Component getDisplayName()
		{
			return new TextComponent("container.teleporter");
		}
		
	}

}
