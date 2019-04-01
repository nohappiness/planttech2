package net.kaneka.planttech2.items;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.gui.GUIReferences;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemWrench extends ItemBase
{

	public ItemWrench()
	{
		super("wrench", new Item.Properties().maxStackSize(1).group(PlantTechMain.groupmain));
	}
}
