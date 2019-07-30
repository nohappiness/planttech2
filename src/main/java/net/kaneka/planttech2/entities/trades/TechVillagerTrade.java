package net.kaneka.planttech2.entities.trades;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

public class TechVillagerTrade
{
	private final List<ItemStack> inputs; 
	private final List<ItemStack> outputs; 
	private final int credits; 
	private final int neededTrustLevel; 
	
	public TechVillagerTrade(List<ItemStack> inputs, List<ItemStack> outputs, int credits, int neededTrustLevel )
	{
		this.inputs = inputs; 
		this.outputs = outputs; 
		this.credits = credits; 
		this.neededTrustLevel = neededTrustLevel; 
	}
	
	public List<ItemStack> getInputs()
	{
		return inputs; 
	}

	public List<ItemStack> getOutputs()
	{
		return outputs;
	}

	public int getCredits()
	{
		return credits;
	}

	public int getNeededLevel()
	{
		return neededTrustLevel;
	}
	
	public CompoundNBT toNBT()
	{
		CompoundNBT nbt = new CompoundNBT();
		nbt.putInt("length_inputs", inputs.size());
		for(int i = 0; i < inputs.size(); i++)
		{
			nbt.put("input_" + i, inputs.get(i).serializeNBT()); 
		}
		
		nbt.putInt("length_outputs", outputs.size());
		for(int i = 0; i < outputs.size(); i++)
		{
			nbt.put("output_" + i, outputs.get(i).serializeNBT()); 
		}
		nbt.putInt("credits", credits);
		nbt.putInt("neededtrustlevel", neededTrustLevel);
		return nbt; 
	}
	
	public static TechVillagerTrade fromNBT(CompoundNBT nbt)
	{
		List<ItemStack> inputs = new ArrayList<>(), outputs = new ArrayList<>(); 
		for(int i = 0; i < nbt.getInt("length_inputs"); i++)
		{
			inputs.add(ItemStack.read(nbt.getCompound("input_" + i)));
		}
		
		for(int i = 0; i < nbt.getInt("length_outputs"); i++)
		{
			outputs.add(ItemStack.read(nbt.getCompound("output_" + i)));
		}
		
		return new TechVillagerTrade(inputs, outputs, nbt.getInt("credits") , nbt.getInt("neededtrustlevel"));
	}
	
	public PacketBuffer toBuffer(PacketBuffer buf)
	{
		buf.writeInt(inputs.size()); 
		for(int i = 0; i < inputs.size(); i++)
		{
			buf.writeItemStack(inputs.get(i)); 
		}
		
		buf.writeInt(outputs.size()); 
		for(int i = 0; i < outputs.size(); i++)
		{
			buf.writeItemStack(outputs.get(i)); 
		}
		buf.writeInt(credits); 
		buf.writeInt(neededTrustLevel); 
		return buf; 
	}
	
	public static TechVillagerTrade fromBuffer(PacketBuffer buf)
	{
		List<ItemStack> inputs = new ArrayList<>(), outputs = new ArrayList<>(); 
		for(int i = 0; i < buf.readInt(); i++)
		{
			inputs.add(buf.readItemStack());
		}
		
		for(int i = 0; i < buf.readInt(); i++)
		{
			outputs.add(buf.readItemStack());
		}
		return new TechVillagerTrade(inputs, outputs, buf.readInt() , buf.readInt());
	}
}
