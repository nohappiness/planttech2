package net.kaneka.planttech2.entities.passive;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.kaneka.planttech2.entities.trades.TechVillagerTrade;
import net.kaneka.planttech2.entities.trades.TechVillagerTradePool;
import net.kaneka.planttech2.entities.trades.TechVillagerTradePools;
import net.kaneka.planttech2.registries.ModReferences;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

public class TechVillagerEntity extends AgeableEntity
{
	private static final DataParameter<Integer> PROFESSION = EntityDataManager.createKey(TechVillagerEntity.class, DataSerializers.VARINT);

	private List<TechVillagerTrade> offers;

	public TechVillagerEntity(EntityType<TechVillagerEntity> type, World worldIn)
	{
		super(type, worldIn);
	}

	@Override
	protected void registerData()
	{
		super.registerData();
		this.dataManager.register(PROFESSION, 1);
	}

	private List<TechVillagerTrade> getOffers()
	{
		if (offers == null)
		{
			offers = new ArrayList<TechVillagerTrade>();
			populateTrades();
		}
		return offers;
	}

	private void populateTrades()
	{
		System.out.println("populate"); 
		List<TechVillagerTradePool> pool = TechVillagerTradePools.getSCIENTISTS();
		List<Integer> integers = new ArrayList<Integer>();
		for (int i = 0; i < pool.size(); i++)
		{
			integers.add(i);
		}

		Random rand = new Random();
		for (int i = 0; i < 6; i++)
		{
			int randomint = rand.nextInt(integers.size());
			offers.add(pool.get(integers.get(randomint)).generateTechVillagerTrade());
			integers.remove(randomint);
		}

	}
	
	@Override
	public void read(CompoundNBT compound)
	{
		super.read(compound);
		 
		List<TechVillagerTrade> offers = new ArrayList<TechVillagerTrade>();
		System.out.println(compound.getInt("length_trades"));
		for (int i = 0; i < compound.getInt("length_trades"); i++)
		{
			offers.add(TechVillagerTrade.fromNBT(compound.getCompound("offer_" + i)));
		}
	}

	
	@Override
	public CompoundNBT writeWithoutTypeId(CompoundNBT compound)
	{
		super.writeWithoutTypeId(compound);
		System.out.println("writeAdd"); 
		System.out.println(this.offers); 
		List<TechVillagerTrade> offers = getOffers();
		compound.putInt("length_trades", offers.size());
		for (int i = 0; i < offers.size(); i++)
		{
			compound.put("offer_" + i, offers.get(i).toNBT());
		}
		return compound; 
	}
	
	
	
	@Override
	public boolean processInteract(PlayerEntity player, Hand hand)
	{
		if (hand == Hand.MAIN_HAND && !world.isRemote)
		{
			List<TechVillagerTrade> offers = getOffers();
			for (TechVillagerTrade offer : offers)
			{
				player.sendMessage(new StringTextComponent(offer.getInputs() + "für" + offer.getOutputs() + ".Trustlevel:" + offer.getNeededLevel()));
			}
		}
		return true;
	}

	@Override
	public boolean canDespawn(double distanceToClosestPlayer)
	{
		return false;
	}

	public EntityType<?> getEntityType()
	{
		return ForgeRegistries.ENTITIES.getValue(new ResourceLocation(ModReferences.TECHVILLAGER));
	}

	@SuppressWarnings("unchecked")
	@Override
	public AgeableEntity createChild(AgeableEntity ageable)
	{
		return new TechVillagerEntity((EntityType<TechVillagerEntity>) getEntityType(), this.world);
	}

}
