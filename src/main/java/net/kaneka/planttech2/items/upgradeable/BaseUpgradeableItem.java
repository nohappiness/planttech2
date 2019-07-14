package net.kaneka.planttech2.items.upgradeable;

import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.kaneka.planttech2.container.ItemUpgradeableContainer;
import net.kaneka.planttech2.energy.BioEnergyStorage;
import net.kaneka.planttech2.energy.IItemChargeable;
import net.kaneka.planttech2.items.BaseItem;
import net.kaneka.planttech2.utilities.NBTHelper;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BaseUpgradeableItem extends BaseItem implements IItemChargeable, IUpgradeable
{
	private int basecapacity, maxInvSize; 
	private float baseAttack, baseAttackSpeed; 
	
	public BaseUpgradeableItem(String name, Properties property, int basecapacity, int maxInvSize, float baseAttack, float baseAttackSpeed)
	{
		super(name, property);
		this.basecapacity = basecapacity;
		this.maxInvSize = maxInvSize; 
		this.baseAttack = baseAttack; 
		this.baseAttackSpeed = baseAttackSpeed; 
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt)
	{
		return new InventoryEnergyProvider(basecapacity, maxInvSize);
	}

	@Override
	public int extractEnergy(ItemStack stack, int amount, boolean simulate)
	{
		IEnergyStorage storage = getEnergyCap(stack);
		if (storage != null)
		{
			int returnvalue = storage.extractEnergy(amount, simulate);
			updateEnergy(stack); 
			return returnvalue; 
		}
		return 0;
	}

	@Override
	public int receiveEnergy(ItemStack stack, int amount, boolean simulate)
	{
		IEnergyStorage storage = getEnergyCap(stack);
		if (storage != null)
		{
			int returnvalue = storage.receiveEnergy(amount, simulate);
			updateEnergy(stack);
			return returnvalue; 
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
	public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker)
	{
		if (!((PlayerEntity) attacker).abilities.isCreativeMode)
		{
			extractEnergy(stack, getEnergyCost(stack), false);
		}
		return true;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving)
	{
		if (!worldIn.isRemote && state.getBlockHardness(worldIn, pos) != 0.0F && !((PlayerEntity) entityLiving).abilities.isCreativeMode)
		{
			extractEnergy(stack, getEnergyCost(stack), false);
			updateEnergy(stack);
		}
		return true;
	}

	protected static int getEnergyCost(ItemStack stack)
	{
		return 20 + NBTHelper.getIntSave(stack, "energycost", 0);
	}

	private double getAttackSpeed(ItemStack stack)
	{
		return Math.min(baseAttackSpeed + NBTHelper.getFloatSave(stack, "attackspeed", 0), -0.4D);
	}

	private double getAttDamage(ItemStack stack)
	{ 
		return Math.min(baseAttack + NBTHelper.getFloatSave(stack, "attack", 0), UpgradeChipItem.getAttackMax());
	}
	

	public static IEnergyStorage getEnergyCap(ItemStack stack)
	{
		LazyOptional<IEnergyStorage> provider = stack.getCapability(CapabilityEnergy.ENERGY);
		if (provider.isPresent())
		{
			return provider.orElse(null);
		}
		return null;
	}

	public static IItemHandler getInvCap(ItemStack stack)
	{
		LazyOptional<IItemHandler> provider = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
		if (provider.isPresent())
		{
			return provider.orElse(null);
		}
		return null;
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot, ItemStack stack)
	{
		Multimap<String, AttributeModifier> multimap = HashMultimap.create();
		if (equipmentSlot == EquipmentSlotType.MAINHAND)
		{
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", getAttDamage(stack), AttributeModifier.Operation.ADDITION));
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", getAttackSpeed(stack), AttributeModifier.Operation.ADDITION));
		}

		return multimap;
	}

	protected void updateEnergy(ItemStack stack)
	{
		CompoundNBT tag = stack.getTag();
		if (tag == null)
		{
			tag = new CompoundNBT();
		}
		IEnergyStorage storage = getEnergyCap(stack);
		if (storage instanceof BioEnergyStorage)
		{
			tag.putInt("current_energy", ((BioEnergyStorage) storage).getEnergyStored());
			tag.putInt("max_energy", ((BioEnergyStorage) storage).getMaxEnergyStored());
		}
		stack.setTag(tag);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		CompoundNBT tag = stack.getTag();
		if (tag != null)
		{
			tooltip.add(new TranslationTextComponent("info.energy", tag.getInt("current_energy") + "/" + tag.getInt("max_energy")));
			tooltip.add(new TranslationTextComponent("info.energycosts", getEnergyCost(stack))); 
			tooltip.add(new TranslationTextComponent("info.openwithshift")); 
		}

		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack)
	{
		if (getDurabilityForDisplay(stack) >= 1)
		{
			return false;
		}
		return true;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack)
	{
		CompoundNBT tag = stack.getTag();
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
	
	@Override
	public boolean isEnchantable(ItemStack stack)
	{
		return false;
	}
	
	public static int getInventorySize(ItemStack stack)
	{
		IItemHandler handler = getInvCap(stack);
		if(handler != null)
		{
			return handler.getSlots(); 
		}
		return 0; 
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
	{
		ItemStack stack = player.getHeldItem(hand);

		if(player.isSneaking())
		{
			if (!world.isRemote && player instanceof ServerPlayerEntity) 
			{
    			NetworkHooks.openGui((ServerPlayerEntity) player, new NamedContainerProvider(stack), buffer -> buffer.writeItemStack(stack));
			}
		}
		return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
	}
	
	@Override
	public void updateNBTValues(ItemStack stack)
	{
		IItemHandler inv = getInvCap(stack); 
		if(inv != null)
		{
			int energyCost = 0, increaseCapacity = 0, energyProduction = 0, increaseHarvestlevel = 0;
			float increaseAttack = 0, increaseAttackSpeed = 0, increaseBreakdownRate = 0; 
			boolean unlockShovelFeat = false, unlockAxeFeat = false, unlockHoeFeat = false, unlockShearsFeat = false; 
			
			ItemStack slot; 
			for(int i = 0; i < inv.getSlots(); i++)
			{
				slot = inv.getStackInSlot(i); 
				if(!slot.isEmpty())
				{
					if(slot.getItem() instanceof UpgradeChipItem)
					{
						UpgradeChipItem item = (UpgradeChipItem) slot.getItem(); 
						energyCost += item.getEnergyCost(); 
						increaseCapacity += item.getIncreaseCapacity(); 
						energyProduction += item.getEnergyProduction(); 
						increaseHarvestlevel += item.getIncreaseHarvestlevel(); 
						increaseAttack += item.getIncreaseAttack(); 
						increaseAttackSpeed += item.getIncreaseAttackSpeed(); 
						increaseBreakdownRate += item.getIncreaseBreakdownRate(); 
						if(item.isUnlockShovelFeat()) unlockShovelFeat = true; 
						if(item.isUnlockAxeFeat()) unlockAxeFeat = true; 
						if(item.isUnlockHoeFeat()) unlockHoeFeat = true; 
						if(item.isUnlockShearsFeat()) unlockShearsFeat = true; 
					}
				}
			}
			
			CompoundNBT nbt = stack.getTag(); 
			if(nbt == null)
			{
				nbt = new CompoundNBT(); 
			}
			
			nbt.putInt("energycost", energyCost);
			nbt.putInt("energyproduction", energyProduction);
			nbt.putInt("harvestlevel", increaseHarvestlevel);
			nbt.putFloat("attack", increaseAttack);
			nbt.putFloat("attackspeed", increaseAttackSpeed);
			nbt.putFloat("breakdownrate", increaseBreakdownRate);
			nbt.putBoolean("unlockshovel", unlockShovelFeat);
			nbt.putBoolean("unlockaxe", unlockAxeFeat);
			nbt.putBoolean("unlockhoe", unlockHoeFeat);
			nbt.putBoolean("unlockshears", unlockShearsFeat);
			
			stack.setTag(nbt);
			
			IEnergyStorage energy = getEnergyCap(stack); 
			if(energy != null)
			{
				if(energy instanceof BioEnergyStorage)
				{
					((BioEnergyStorage) energy).setEnergyMaxStored(basecapacity + increaseCapacity);
					updateEnergy(stack);
				}
			}
		}
	}
	
	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entityIn, int itemSlot, boolean isSelected)
	{
		if(!world.isRemote)
		{
			if(!stack.isEmpty())
			{
				if(world.getGameTime() % 200L == 0)
				{
					receiveEnergy(stack, NBTHelper.getIntSave(stack, "energyproduction", 0), false); 
				}
			}
		}
	}
	
	
	
	
	public static class NamedContainerProvider implements INamedContainerProvider
	{
		
		private final ItemStack stack; 
		
		public NamedContainerProvider(ItemStack stack)
		{
			this.stack = stack; 
		}
		

		@Override
		public Container createMenu(int id, PlayerInventory inv, PlayerEntity entity)
		{
			return new ItemUpgradeableContainer(id, inv, stack);
		}

		@Override
		public ITextComponent getDisplayName()
		{
			return new TranslationTextComponent("container.upgradeableitem");
		}
		
	}
}
