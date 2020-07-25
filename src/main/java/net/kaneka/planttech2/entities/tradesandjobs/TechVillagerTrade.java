package net.kaneka.planttech2.entities.tradesandjobs;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.TranslationTextComponent;

public class TechVillagerTrade
{
	private final String name; 
	private final List<ItemStack> inputs; 
	private final List<ItemStack> outputs; 
	private final int creditsBuy, creditsSell, neededTrustLevel; 
	
	public TechVillagerTrade(String name, List<ItemStack> inputs, List<ItemStack> outputs, int creditsBuy, int creditsSell, int neededTrustLevel )
	{
		this.name = name; 
		this.inputs = inputs; 
		this.outputs = outputs; 
		this.creditsBuy = creditsBuy;
		this.creditsSell = creditsSell; 
		this.neededTrustLevel = neededTrustLevel; 
	}
	
	public String getName()
	{
		return new TranslationTextComponent(name).getString(); 
	}
	
	public List<ItemStack> getInputs()
	{
		return inputs; 
	}

	public List<ItemStack> getOutputs()
	{
		return outputs;
	}
	
	public int getCreditsBuy()
	{
		return creditsBuy;
	}

	public int getCreditsSell()
	{
		return creditsSell;
	}

	public int getNeededLevel()
	{
		return neededTrustLevel;
	}
	
	public CompoundNBT toNBT()
	{
		CompoundNBT nbt = new CompoundNBT();
		nbt.putString("name", name);
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
		nbt.putInt("creditsbuy", creditsBuy);
		nbt.putInt("creditssell", creditsSell);
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
		
		return new TechVillagerTrade(nbt.getString("name"), inputs, outputs, nbt.getInt("creditsbuy") , nbt.getInt("creditssell") , nbt.getInt("neededtrustlevel"));
	}
	
	public PacketBuffer toBuffer(PacketBuffer buf)
	{
		buf.writeString(name);
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
		buf.writeInt(creditsBuy); 
		buf.writeInt(creditsSell); 
		buf.writeInt(neededTrustLevel); 
		return buf; 
	}
	
	public static TechVillagerTrade fromBuffer(PacketBuffer buf)
	{
		List<ItemStack> inputs = new ArrayList<>(), outputs = new ArrayList<>(); 
		String name = buf.readString();
		int size = buf.readInt();
		for(int i = 0; i < size; i++)
		{
			inputs.add(buf.readItemStack());
		}
		
		size = buf.readInt();
		for(int i = 0; i < size; i++)
		{
			outputs.add(buf.readItemStack());
		}
		return new TechVillagerTrade(name, inputs, outputs, buf.readInt(), buf.readInt(), buf.readInt());
	}
}
