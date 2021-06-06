package net.kaneka.planttech2.entities.passive;

import net.kaneka.planttech2.entities.tradesandjobs.TechVillagerContainerProvider;
import net.kaneka.planttech2.entities.tradesandjobs.TechVillagerTrade;
import net.kaneka.planttech2.entities.tradesandjobs.TechVillagerTradePool;
import net.kaneka.planttech2.entities.tradesandjobs.TechVillagerTradePools;
import net.kaneka.planttech2.registries.ModEntityTypes;
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
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

public class TechVillagerEntity extends AgeableEntity
{
	public static final int SCIENTIST = 0; 
	public static final int BOTANIST = 1; 
	public static final int HEADHUNTER = 2; 
	public static final int ENGINEER = 3; 
	
	private static final DataParameter<Integer> PROFESSION = EntityDataManager.defineId(TechVillagerEntity.class, DataSerializers.INT);

	private List<TechVillagerTrade> offers;

	public TechVillagerEntity(EntityType<? extends TechVillagerEntity> type, World worldIn)
	{
		super(type, worldIn);
	}

//	@Nullable
//	@Override
//	public AgeableEntity createChild(AgeableEntity ageableEntity)
//	{
//		return new TechVillagerEntity((EntityType<? extends TechVillagerEntity>) ModEntityTypes.TECHVILLAGERENTITY, this.world);
//
//	}

	@Nullable
	@Override
	public AgeableEntity getBreedOffspring(ServerWorld world, AgeableEntity ageableEntity)
	{
		return new TechVillagerEntity(ModEntityTypes.TECHVILLAGERENTITY, this.level);
	}

	@Override
	protected void defineSynchedData()
	{
		super.defineSynchedData();
		this.entityData.define(PROFESSION, 0);
	}
	
	public int getProfession()
	{
		return entityData.get(PROFESSION); 
	}
	
	public static String getProfessionString(int profession)
	{
		switch(profession)
		{
			default:
			case 0: return "scientist";
			case 1: return "botanist";
			case 2: return "headhunter"; 
			case 3: return "engineer";
		}
	}

	private List<TechVillagerTrade> getOffers()
	{
		if (offers == null)
		{
			offers = new ArrayList<>();
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
		List<Integer> integers = new ArrayList<>();
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
	public void readAdditionalSaveData(CompoundNBT compound)
	{
		super.readAdditionalSaveData(compound);
		offers = new ArrayList<>();
		for (int i = 0; i < compound.getInt("length_trades"); i++)
		{
			offers.add(TechVillagerTrade.fromNBT(compound.getCompound("offer_" + i)));
		}
	}

	
	@Override
	public void addAdditionalSaveData(CompoundNBT compound)
	{
		super.addAdditionalSaveData(compound);
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
	protected ActionResultType mobInteract(PlayerEntity player, Hand hand)
	{
		if (hand == Hand.MAIN_HAND && !level.isClientSide)
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
		return super.mobInteract(player, hand);
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer)
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
