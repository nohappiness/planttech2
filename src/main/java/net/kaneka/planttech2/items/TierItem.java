package net.kaneka.planttech2.items;

public class TierItem extends BaseItem
{
    private int tier; 
    private int itemtype; 

    public TierItem(String name, Properties property, int tier, int itemtype)
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
