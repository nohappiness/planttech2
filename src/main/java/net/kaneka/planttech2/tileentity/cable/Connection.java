package net.kaneka.planttech2.tileentity.cable;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

public class Connection
{
	private BlockPos pos; 
	private Direction facing; 
	
	public Connection()
	{
		pos = new BlockPos(0,0,0); 
		facing = Direction.UP; 
	}
	
	public Connection(BlockPos pos, Direction facing)
	{
		this.pos = pos; 
		this.facing = facing; 
	}
	
	public BlockPos getCablePos()
	{
		return this.pos;
	}
	
	public Direction getFacing()
	{
		return this.facing;
	}
	
	public BlockPos getConnectedPos()
	{
		return this.pos.relative(this.facing);
	}
	
	public boolean areEqual(Connection con2)
	{
		if(this.pos.equals(con2.pos) && this.facing.equals(con2.facing))
		{
			return true; 
		}
		return false;
	}
	
	public boolean areEqual(BlockPos pos, Direction facing)
	{
		if(this.pos.equals(pos) && this.facing.equals(facing))
		{
			return true; 
		}
		return false;
	}
	
	public CompoundNBT serializeConnection()
	{
		CompoundNBT nbt = new CompoundNBT();
		nbt.putInt("posx", this.pos.getX());
		nbt.putInt("posy", this.pos.getY());
		nbt.putInt("posz", this.pos.getZ());
		nbt.putInt("facing", this.facing.get3DDataValue());
		return nbt; 
	}
	
	public Connection deserializeConnection(CompoundNBT nbt)
	{
		this.pos = new BlockPos(nbt.getInt("posx"),nbt.getInt("posy"), nbt.getInt("posz"));
		this.facing = Direction.from3DDataValue(nbt.getInt("facing")); 
		return this; 
	}
}
