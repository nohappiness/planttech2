package net.kaneka.planttech2.entities.passive;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.kaneka.planttech2.entities.tradesandjobs.TechVillagerContainerProvider;
import net.kaneka.planttech2.entities.tradesandjobs.TechVillagerTrade;
import net.kaneka.planttech2.entities.tradesandjobs.TechVillagerTradePool;
import net.kaneka.planttech2.entities.tradesandjobs.TechVillagerTradePools;
import net.kaneka.planttech2.registries.ModEntityTypes;
import net.kaneka.planttech2.registries.ModReferences;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class TechVillagerEntity extends AgeableEntity
{
	public static final int SCIENTIST = 0; 
	public static final int BOTANIST = 1; 
	public static final int HEADHUNTER = 2; 
	public static final int ENGINEER = 3; 
	
	private static final DataParameter<Integer> PROFESSION = EntityDataManager.createKey(TechVillagerEntity.class, DataSerializers.VARINT);

	private List<TechVillagerTrade> offers;

	public TechVillagerEntity(EntityType<? extends TechVillagerEntity> type, World worldIn)
	{
		super(type, worldIn);
	}

	@Nullable
	@Override
	public AgeableEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageableEntity)
	{
		return new TechVillagerEntity((EntityType<? extends TechVillagerEntity>) ModEntityTypes.TECHVILLAGERENTITY, this.world);

	}

	@Override
	protected void registerData()
	{
		super.registerData();
		this.dataManager.register(PROFESSION, 0);
	}
	
	public int getProfession()
	{
		return dataManager.get(PROFESSION); 
	}
	
	public static String getProfessionString(int profession)
	{
		switch(profession)
		{
			case 0: return "scientist"; 
			case 1: return "botanist";
			case 2: return "headhunter"; 
			case 3: return "engineer"; 
			default: return "scientist"; 
		}
	}

	private List<TechVillagerTrade> getOffers()
	{
		if (offers == null)
		{
			offers = new ArrayList<TechVillagerTrade>();
			populateTrades();
		}
		else if(offers.size() == 0)
		{
			populateTrades();
		}
		return offers;
	}

	private void populateTrades()
	{
		List<TechVillagerTradePool> pool = TechVillagerTradePools.getSCIENTISTS();
		List<Integer> integers = new ArrayList<Integer>();
		for (int i = 0; i < pool.size(); i++)
		{
			integers.add(i);
		}

		Random rand = new Random();
		for (int i = 0; i < Math.min(8, pool.size()); i++)
		{
			int randomint = rand.nextInt(integers.size());
			offers.add(pool.get(integers.get(randomint)).generateTechVillagerTrade());
			integers.remove(randomint);
		}

	}
	
	@Override
	public void readAdditional(CompoundNBT compound)
	{
		super.readAdditional(compound);
		offers = new ArrayList<TechVillagerTrade>();
		for (int i = 0; i < compound.getInt("length_trades"); i++)
		{
			offers.add(TechVillagerTrade.fromNBT(compound.getCompound("offer_" + i)));
		}
	}

	
	@Override
	public void writeAdditional(CompoundNBT compound)
	{
		super.writeAdditional(compound);
		if(offers != null)
		{
    		compound.putInt("length_trades", offers.size());
    		for (int i = 0; i < offers.size(); i++)
    		{
    			compound.put("offer_" + i, offers.get(i).toNBT());
    		}
		}
		else
		{
			compound.putInt("length_trades", 0);
		}
	}

	@Override
	protected ActionResultType func_230254_b_(PlayerEntity player, Hand hand)
	{
		if (hand == Hand.MAIN_HAND && !world.isRemote)
		{
			if (player instanceof ServerPlayerEntity)
			{
				NetworkHooks.openGui((ServerPlayerEntity) player, new TechVillagerContainerProvider(getOffers(), getProfession()), buffer ->
				{
					buffer.writeInt(getProfession());
					buffer.writeInt(offers.size());
					for(TechVillagerTrade trade:offers)
					{
						trade.toBuffer(buffer);
					}
				});
				return ActionResultType.SUCCESS;
			}
		}
		return super.func_230254_b_(player, hand);
	}

	@Override
	public boolean canDespawn(double distanceToClosestPlayer)
	{
		return false;
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public AgeableEntity createChild(AgeableEntity ageable)
//	{
//		return new TechVillagerEntity((EntityType<? extends TechVillagerEntity>) ModEntityTypes.TECHVILLAGERENTITY, this.world);
//	}

}
