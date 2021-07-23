package net.kaneka.planttech2.entities.capabilities.techvillagertrust;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.ImmutableList;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.util.LazyOptional;

public class TechVillagerTrust implements ITechVillagerTrust, ICapabilitySerializable<CompoundNBT>
{
	@CapabilityInject(ITechVillagerTrust.class)
	public static Capability<ITechVillagerTrust> INSTANCE = null;
	
	private final List<String> keys = ImmutableList.of("scientist", "headhunter", "botanist", "engineer"); 
	private Map<String, Integer> trust = new HashMap<String, Integer>(); 
	private int[] levelcondition = {10, 50, 100, 200, 400, 800, 1600, 3200, 6400, 12800} ; 
	
	private final LazyOptional<ITechVillagerTrust> lazyoption = LazyOptional.of(() -> this);

	public TechVillagerTrust() 
	{
		for(String key: keys)
		{
			trust.put(key, 0); 
		}
	}
	
	@Override
	public void increaseTrust(String key, int trust, int maxLevel) 
	{
		if(this.trust.containsKey(key))
		{ 
			int actualTrust = this.trust.get(key); 
			int maxTrust = levelcondition[maxLevel-1];
			if(actualTrust < maxTrust)
			{
				int newValue = Math.min(actualTrust + trust, maxTrust); 
				this.trust.put(key, newValue); 
			}
		}

	}
	
	@Override
	public void decreaseTrust(String key, int trust, int minLevel) 
	{
		if(this.trust.containsKey(key))
		{
			int actualTrust = this.trust.get(key); 
			int minTrust = levelcondition[minLevel-1];
			if(actualTrust > minTrust)
			{
				int newValue = Math.max(actualTrust - trust, minTrust); 
				this.trust.put(key, newValue); 
			}
		}
	}

	@Override
	public void setTrust(String key, int trust) 
	{	
		if(this.trust.containsKey(key))
		{
			this.trust.put(key, trust); 
		}
	}
	
	@Override
	public int getTrust(String key) 
	{
		if(this.trust.containsKey(key))
		{ 
			int intvalue = this.trust.get(key);  
			return intvalue; 
		}
		return 0; 
	}
	
	@Override
	public int getMaxLevel()
	{
		return levelcondition.length - 1; 
	}
	
	@Override
	public Map<String, Integer> getTrustsMap() 
	{
		return this.trust; 
	}
	
	public int getLevel(String key)
	{
		if (this.trust.containsKey(key))
		{
			int level = 0;
			int trust = this.getTrust(key);
			for(int i = 0; i < this.levelcondition.length; i++)
			{
				if(trust >= this.levelcondition[i])
				{
					trust-= this.levelcondition[i]; 
					level++;
				}
				else
				{
					break; 
				}
			}
			return level;
		} 
		return 0; 
	}
	
	public int getLevelTrust(int level)
	{
		int trust = 0; 
		for(int i = 0; i < level; i++)
		{
			trust += this.levelcondition[i]; 
		}
		
		return trust;
	}

	@Override
	public List<String> getKeys()
	{
		return keys;
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side)
	{
		return INSTANCE.orEmpty(cap, lazyoption);
	}

	@Override
	public CompoundNBT serializeNBT()
	{
		Map<String, Integer> map = getTrustsMap(); 
		CompoundNBT compound = new CompoundNBT();
		for(Entry<String, Integer> entry: map.entrySet())
		{
			compound.putInt(entry.getKey(), entry.getValue());
		}
		return compound;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt)
	{
		CompoundNBT nbt2 = (CompoundNBT) nbt; 
		for(String key: getKeys())
		{
			if(nbt2.contains(key))
			{
				setTrust(key, nbt2.getInt(key));
			}
		}
	}
	
	public static boolean isAllowedForEntity(ICapabilityProvider entity)
	{
		if(entity instanceof PlayerEntity)
		{
			if (!entity.getCapability(INSTANCE).isPresent()) 
			{
                return true;
            }
		}
		return false; 
	}
	
	public static class TechVillagerTrustStorage implements IStorage<ITechVillagerTrust>
	{

		@Override
		public INBT writeNBT(Capability<ITechVillagerTrust> capability, ITechVillagerTrust instance, Direction side)
		{
			if(instance instanceof TechVillagerTrust)
			{
				return ((TechVillagerTrust) instance).serializeNBT(); 
			}
			 return new CompoundNBT();
		}

		@Override
		public void readNBT(Capability<ITechVillagerTrust> capability, ITechVillagerTrust instance, Direction side, INBT nbt)
		{
			if(instance instanceof TechVillagerTrust)
			{
				((TechVillagerTrust) instance).deserializeNBT((CompoundNBT) nbt);
			}
		}
	}
}
