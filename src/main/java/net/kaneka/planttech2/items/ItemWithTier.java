package net.kaneka.planttech2.items;

public class ItemWithTier extends ItemBase
{
    private int tier; 
    private int itemtype; 

    public ItemWithTier(String name, Properties property, int tier, int itemtype)
    {
	super(name, property);
	this.tier = tier; 
	this.itemtype = itemtype; 
    }

    public int getTier()
    {
	return tier; 
    }
    
    public int getItemType()
    {
	return itemtype; 
    }
}
