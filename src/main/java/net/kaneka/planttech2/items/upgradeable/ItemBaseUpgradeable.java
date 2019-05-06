package net.kaneka.planttech2.items.upgradeable;

import java.util.List;

import com.google.common.collect.Multimap;

import net.kaneka.planttech2.energy.BioEnergyStorage;
import net.kaneka.planttech2.energy.IItemChargeable;
import net.kaneka.planttech2.items.ItemBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ItemBaseUpgradeable extends ItemBase implements IItemChargeable
{
	public ItemBaseUpgradeable(String name, Properties property)
	{
		super(name, property);
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt)
	{
		return new InventoryEnergyProvider(10000, 10);
	}

	@Override
	public int extractEnergy(ItemStack stack, int amount, boolean simulate)
	{
		IEnergyStorage storage = getEnergyCap(stack);
		if (storage != null)
		{
			return storage.extractEnergy(amount, simulate);
		}
		return 0;
	}
	
	@Override
	public int receiveEnergy(ItemStack stack, int amount, boolean simulate)
	{
		IEnergyStorage storage = getEnergyCap(stack);
		if (storage != null)
		{
			return storage.receiveEnergy(amount, simulate);
		}
		return 0;
	}

	@Override
	public int maxEnergy(ItemStack stack)
	{
		IEnergyStorage storage = getEnergyCap(stack);
		if (storage != null)
		{
			return storage.getMaxEnergyStored();
		}
		return 0;
	}

	@Override
	public int currentEnergy(ItemStack stack)
	{
		IEnergyStorage storage = getEnergyCap(stack);
		if (storage != null)
		{
			return storage.getEnergyStored();
		}
		return 0;
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		if(!((EntityPlayer)attacker).abilities.isCreativeMode)
		{
			extractEnergy(stack, getEnergyCostHit(stack), false);
			updateTag(stack);
		}
		return true;
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
	{
		if (!worldIn.isRemote && state.getBlockHardness(worldIn, pos) != 0.0F && !((EntityPlayer)entityLiving).abilities.isCreativeMode) 
		{
			extractEnergy(stack, getEnergyCostBreak(stack), false); 
			updateTag(stack);
		}
		return true;
	}
	
	private int getEnergyCostHit(ItemStack stack)
	{
		return 50; 
	}
	
	private int getEnergyCostBreak(ItemStack stack)
	{
		return 50; 
	}
	
	private double getAttackSpeed()
	{
		return 5.0D;
	}
	
	private double getDamage()
	{
		return 2.0D; 
	}
	

	protected IEnergyStorage getEnergyCap(ItemStack stack)
	{
		LazyOptional<IEnergyStorage> provider = stack.getCapability(CapabilityEnergy.ENERGY);
		if (provider != null)
		{
			return provider.orElse(null);
		}
		return null;
	}
	
	protected IItemHandler getInvCap(ItemStack stack)
	{
		LazyOptional<IItemHandler> provider = stack.getCapability( CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
		if (provider != null)
		{
			return provider.orElse(null);
		}
		return null;
	}
	
	@Override 
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
	      @SuppressWarnings("deprecation")
		Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(equipmentSlot);
	      if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
	         multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", getDamage(), 0));
	         multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", getAttackSpeed(), 0));
	      }

	      return multimap;
	   }
	
	protected void updateTag(ItemStack stack)
	{
		NBTTagCompound tag = stack.getTag();
		if(tag == null)
		{
			tag = new NBTTagCompound(); 
		}
		IEnergyStorage storage = getEnergyCap(stack);
		if(storage instanceof BioEnergyStorage)
		{
			System.out.println("test"); 
			tag.setInt("current_energy", ((BioEnergyStorage) storage).getEnergyStored());
			tag.setInt("max_energy", ((BioEnergyStorage) storage).getMaxEnergyStored());
		}
		stack.setTag(tag);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		NBTTagCompound tag = stack.getTag(); 
		if (tag != null)
		{
			tooltip.add(new TextComponentString(tag.getInt("current_energy") + "/" + tag.getInt("max_energy")));
		}

		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack)
	{
		if(getDurabilityForDisplay(stack) >= 1)
		{
			return false; 
		}
		return true;
	}
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack)
	{
		NBTTagCompound tag = stack.getTag(); 
		if (tag != null)
		{
			return 1D - ((double) tag.getInt("current_energy") / (double) tag.getInt("max_energy"));
		}
		
		return 1D;
	}
	
	@Override
	public int getRGBDurabilityForDisplay(ItemStack stack)
	{
		return Integer.parseInt("06bc00", 16);
	}
}
