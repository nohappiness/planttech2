package net.kaneka.planttech2.librarys;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import net.kaneka.planttech2.enums.EnumTemperature;
import net.kaneka.planttech2.enums.EnumTraitsInt;
import net.kaneka.planttech2.hashmaps.HashMapCropTraits;
import net.kaneka.planttech2.librarys.utils.Drop;
import net.kaneka.planttech2.librarys.utils.Parents;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class CropListEntry implements Comparable<CropListEntry>
{

    private int ID;

    private String name;

    private Set<ItemStack> seeds = new HashSet<ItemStack>();

    private ItemStack mainSeed = new ItemStack(Items.WHEAT_SEEDS);

    private Drop mainSeedDrop = new Drop(mainSeed, 1, 4);

    private int seedColor = 16777215;

    private Set<Drop> drops = null;

    private Set<Parents> parents = null;

    private float chance = 0;

    private ItemStack soil = ItemStack.EMPTY;

    private EnumTemperature temp = EnumTemperature.NORMAL;

    private boolean hasParticle = false;

    private boolean isBlacklisted = false;

    public CropListEntry(int id, String name, int seedColor, boolean hasParticle)
    {
	this.ID = id;
	this.name = name;
	this.seedColor = seedColor;
	this.hasParticle = hasParticle;
    }

    public int getID()
    {
	return this.ID;
    }

    public String getString()
    {
	return this.name;
    }

    public String getDisplayNameUnformated()
    {
	return this.getDisplayName().getUnformattedComponentText();
    }

    public ITextComponent getDisplayName()
    {
	return new TextComponentTranslation("crop." + this.name);
    }

    public int getSeedColor()
    {
	return this.seedColor;
    }

    public EnumTemperature getTemperature()
    {
	return this.temp;
    }

    public CropListEntry addSeeds(ItemStack... seeds)
    {
	for (ItemStack seed : seeds)
	{
	    this.seeds.add(seed);
	}
	return this;
    }

    public CropListEntry setChance(float chance)
    {
	this.chance = chance;
	return this;
    }

    public ItemStack getSoil()
    {
	return this.soil;
    }

    public CropListEntry setSoil(ItemStack block)
    {
	this.soil = block;
	return this;
    }

    public float getChance()
    {
	return this.chance;
    }

    public ItemStack getMainSeed()
    {
	return this.mainSeed;
    }

    public Drop getMainSeedDrop()
    {
	return this.mainSeedDrop;
    }

    public Set<ItemStack> getSeeds()
    {
	return seeds;
    }

    public CropListEntry setBlacklisted(boolean isBlacklisted)
    {
	this.isBlacklisted = isBlacklisted;
	return this;
    }

    public boolean isBlacklisted()
    {
	return isBlacklisted;
    }

    public CropListEntry addDrop(ItemStack item, int min, int max)
    {
	if (drops == null)
	{
	    this.drops = new HashSet<Drop>();
	}
	drops.add(new Drop(item, min, max));
	return this;
    }

    public CropListEntry addParents(String parent1, String parent2, float chance)
    {
	if (!isBlacklisted)
	{
	    if (this.parents == null)
	    {
		this.parents = new HashSet<Parents>();
	    }
	    this.parents.add(new Parents(parent1, parent2));
	    this.chance = chance;
	}
	return this;
    }

    public CropListEntry setMainSeed(ItemStack item, int min, int max)
    {
	this.mainSeed = item;
	this.mainSeedDrop = new Drop(item, min, max);
	this.seeds.add(item);
	return this;

    }

    public boolean isSeed(ItemStack item)
    {
	for (ItemStack stack : seeds)
	{
	    if (stack.isItemEqual(item))
	    {
		return true;
	    }
	}
	return false;
    }

    public Set<Drop> getDrops()
    {
	return this.drops;
    }

    public NonNullList<ItemStack> calculateDrops(NonNullList<ItemStack> returndrops, HashMapCropTraits traits, int growstate)
    {
	Random rand = new Random();
	ItemStack seeddrop = this.mainSeedDrop.getDroppedStack(traits.getTrait(EnumTraitsInt.FERTILITY), EnumTraitsInt.FERTILITY.getMax(), rand);
	if (seeddrop != null)
	{
	    if (growstate < 7)
	    {
		seeddrop.setCount(1);
	    }
	    returndrops.add(traits.addToItemStack(seeddrop));
	}

	if (this.drops != null && growstate > 6)
	{
	    for (Drop drop : this.drops)
	    {
		ItemStack returndrop = drop.getDroppedStack(traits.getTrait(EnumTraitsInt.PRODUCTIVITY), EnumTraitsInt.PRODUCTIVITY.getMax(), rand);
		if (returndrop != null)
		{
		    returndrops.add(returndrop);
		}
	    }
	}
	return returndrops;
    }

    public NonNullList<ItemStack> calculateDropsReduced(NonNullList<ItemStack> returndrops, HashMapCropTraits traits, int growstate)
    {
	Random rand = new Random();
	ItemStack seeddrop = this.mainSeedDrop.getDroppedStack(traits.getTrait(EnumTraitsInt.FERTILITY), EnumTraitsInt.FERTILITY.getMax(), rand);
	if (seeddrop != null)
	{
	    if (growstate > 6)
	    {
		if (seeddrop.getCount() > 1)
		{
		    seeddrop.shrink(1);
		    returndrops.add(traits.addToItemStack(seeddrop));
		}
	    }

	}

	if (this.drops != null && growstate > 6)
	{
	    for (Drop drop : this.drops)
	    {
		ItemStack returndrop = drop.getDroppedStack(traits.getTrait(EnumTraitsInt.PRODUCTIVITY), EnumTraitsInt.PRODUCTIVITY.getMax(), rand);
		if (returndrop != null)
		{
		    returndrops.add(returndrop);
		}
	    }
	}
	return returndrops;
    }

    public boolean isChild(String parent1, String parent2)
    {
	Boolean bool = false;
	if (this.parents != null)
	{
	    for (Parents singleParent : this.parents)
	    {
		if (singleParent.isMatching(parent1, parent2))
		{
		    bool = true;
		}
	    }
	}
	return bool;
    }

    public Set<Parents> getParents()
    {
	return parents;
    }

    public boolean hasParents()
    {
	if (parents == null)
	{
	    return false;
	}
	return true;
    }

    public CropListEntry alternateTemperature(EnumTemperature temp)
    {
	this.temp = temp;
	return this;
    }

    @Override
    public int compareTo(CropListEntry o)
    {
	return o.getID() - this.ID;
    }

    public boolean hasParticle()
    {
	return this.hasParticle;
    }
}
