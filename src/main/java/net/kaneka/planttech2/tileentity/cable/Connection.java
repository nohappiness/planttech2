package net.kaneka.planttech2.tileentity.cable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class Connection
{
	private BlockPos pos; 
	private EnumFacing facing; 
	
	public Connection()
	{
		this.pos = new BlockPos(0,0,0); 
		this.facing = EnumFacing.UP; 
	}
	
	public Connection(BlockPos pos, EnumFacing facing)
	{
		this.pos = pos; 
		this.facing = facing; 
	}
	
	public BlockPos getCablePos()
	{
		return this.pos;
	}
	
	public EnumFacing getFacing()
	{
		return this.facing;
	}
	
	public BlockPos getConnectedPos()
	{
		return this.pos.offset(this.facing);
	}
	
	public boolean areEqual(Connection con2)
	{
		if(this.pos.equals(con2.pos) && this.facing.equals(con2.facing))
		{
			return true; 
		}
		return false;
	}
	
	public boolean areEqual(BlockPos pos, EnumFacing facing)
	{
		if(this.pos.equals(pos) && this.facing.equals(facing))
		{
			return true; 
		}
		return false;
	}
	
	public NBTTagCompound serializeConnection()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInt("posx", this.pos.getX());
		nbt.setInt("posy", this.pos.getY());
		nbt.setInt("posz", this.pos.getZ());
		nbt.setInt("facing", this.facing.getIndex());
		return nbt; 
	}
	
	public Connection deserializeConnection(NBTTagCompound nbt)
	{
		this.pos = new BlockPos(nbt.getInt("posx"),nbt.getInt("posy"), nbt.getInt("posz"));
		this.facing = EnumFacing.byIndex(nbt.getInt("facing")); 
		return this; 
	}
}
