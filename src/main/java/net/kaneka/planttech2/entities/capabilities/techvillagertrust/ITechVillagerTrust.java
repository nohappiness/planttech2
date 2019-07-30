package net.kaneka.planttech2.entities.capabilities.techvillagertrust;

import java.util.List;
import java.util.Map;

public interface ITechVillagerTrust
{
	public void increaseTrust(String profession, int trust, int maxLevel);

	public void decreaseTrust(String profession, int trust, int minLevel);

	public void setTrust(String profession, int trust);

	public int getTrust(String profession);

	public int getLevel(String profession);

	public Map<String, Integer> getTrustsMap();

	public int getLevelTrust(int level);
	
	public List<String> getKeys(); 
}
