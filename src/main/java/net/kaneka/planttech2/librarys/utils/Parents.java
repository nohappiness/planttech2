package net.kaneka.planttech2.librarys.utils;

public class Parents
{
	private String parent1; 
	private String parent2; 
	
		
	public Parents(String parent1, String parent2)
	{
		this.parent1 = parent1;
		this.parent2 = parent2; 
	}
	
	public boolean isMatching(String parent1, String parent2)
	{
		if((this.parent1.equals(parent1) && this.parent2.equals(parent2)) || (this.parent1.equals(parent2) && this.parent2.equals(parent2)))
		{
			return true; 
		}
		return false; 
	}
	
	public String getParent(int id)
	{
		switch(id)
		{
			case 0: return this.parent1;
			case 1: return this.parent2;
		}
		return "";
	}

}
