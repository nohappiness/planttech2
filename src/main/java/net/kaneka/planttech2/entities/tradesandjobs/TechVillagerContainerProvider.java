package net.kaneka.planttech2.entities.tradesandjobs;

import java.util.List;

import net.kaneka.planttech2.container.entities.TechVillagerContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TechVillagerContainerProvider implements INamedContainerProvider
{
	
	private final List<TechVillagerTrade> list; 
	private final int profession; 
	
	public TechVillagerContainerProvider(List<TechVillagerTrade> list, int profession)
	{
		this.list = list;
		this.profession = profession; 
	}
	

	@Override
	public Container createMenu(int id, PlayerInventory inv, PlayerEntity entity)
	{
		return new TechVillagerContainer(id, inv, list, profession);
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return new TranslationTextComponent("container.techvillager");
	}
	
}
