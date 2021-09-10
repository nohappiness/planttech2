package net.kaneka.planttech2.items.upgradeable;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.kaneka.planttech2.energy.BioEnergyStorage;
import net.kaneka.planttech2.energy.IItemChargeable;
import net.kaneka.planttech2.inventory.ItemUpgradeableContainer;
import net.kaneka.planttech2.utilities.NBTHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fmllegacy.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public abstract class BaseUpgradeableItem extends Item implements IItemChargeable, IUpgradeable
{
	private final int basecapacity, maxInvSize, slotId;
	private final float baseAttack, baseAttackSpeed;
	
	public BaseUpgradeableItem(Properties property, int basecapacity, int maxInvSize, float baseAttack, float baseAttackSpeed, int slotId)
	{
		super(property.stacksTo(1));
		this.basecapacity = basecapacity;
		this.maxInvSize = maxInvSize; 
		this.baseAttack = baseAttack; 
		this.baseAttackSpeed = baseAttackSpeed; 
		this.slotId = slotId; 
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt)
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
		return getEnergyCap(stack, IEnergyStorage::getMaxEnergyStored, 0);
	}

	@Override
	public int currentEnergy(ItemStack stack)
	{
		return getEnergyCap(stack, IEnergyStorage::getEnergyStored, 0);
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker)
	{
		if (!((Player) attacker).getAbilities().instabuild)
			extractEnergy(stack, getEnergyCost(stack), false);
		return true;
	}

	@Override
	public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entityLiving)
	{
		if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0F && !((Player) entityLiving).getAbilities().instabuild)
		{
			extractEnergy(stack, getEnergyCost(stack), false);
			updateEnergy(stack);
		}
		return true;
	}

	protected static int getEnergyCost(ItemStack stack)
	{
		return 20 + NBTHelper.getInt(stack, "energycost", 0);
	}

	private double getAttackSpeed(ItemStack stack)
	{
		return Math.min(baseAttackSpeed + NBTHelper.getFloat(stack, "attackspeed", 0), -0.4D);
	}

	private double getAttDamage(ItemStack stack)
	{ 
		return Math.min(baseAttack + NBTHelper.getFloat(stack, "attack", 0), UpgradeChipItem.getAttackMax());
	}

	public static <T> T getEnergyCap(ItemStack stack, Function<IEnergyStorage, T> getter, T defau1t)
	{
		IEnergyStorage cap = getEnergyCap(stack);
		if (cap != null)
			return getter.apply(cap);
		return defau1t;
	}

	@Nullable
	public static IEnergyStorage getEnergyCap(ItemStack stack)
	{
		return getCap(stack, CapabilityEnergy.ENERGY);
	}

	@Nullable
	public static IItemHandler getInvCap(ItemStack stack)
	{
		return getCap(stack, CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
	}

	@Nullable
	private static <C> C getCap(ItemStack stack, Capability<C> cap)
	{
		return stack.getCapability(cap).orElse(null);
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack)
	{
		Multimap<Attribute, AttributeModifier> multimap = HashMultimap.create();
		if (equipmentSlot == EquipmentSlot.MAINHAND)
		{
			multimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", getAttDamage(stack), AttributeModifier.Operation.ADDITION));
			multimap.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", getAttackSpeed(stack), AttributeModifier.Operation.ADDITION));
		}

		return multimap;
	}

	protected void updateEnergy(ItemStack stack)
	{
		CompoundTag tag = stack.getOrCreateTag();
		IEnergyStorage storage = getEnergyCap(stack);
		if (storage instanceof BioEnergyStorage)
		{
			tag.putInt("current_energy", storage.getEnergyStored());
			tag.putInt("max_energy", storage.getMaxEnergyStored());
		}
		stack.setTag(tag);
	}

	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flagIn)
	{
		CompoundTag tag = stack.getOrCreateTag();
		tooltip.add(new TranslatableComponent("info.energy", tag.getInt("current_energy") + "/" + tag.getInt("max_energy")));
		tooltip.add(new TranslatableComponent("info.energycosts", getEnergyCost(stack)));
		tooltip.add(new TranslatableComponent("info.openwithshift"));
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack)
	{
		return !(getDurabilityForDisplay(stack) >= 1);
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack)
	{
		CompoundTag tag = stack.getTag();
		if (tag != null)
			return 1D - ((double) tag.getInt("current_energy") / (double) tag.getInt("max_energy"));
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
		if (handler != null)
			return handler.getSlots();
		return 0;
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand)
	{
		ItemStack stack = player.getItemInHand(hand);
		if (player.isCrouching())
		{
			if (!world.isClientSide && player instanceof ServerPlayer sp)
    			NetworkHooks.openGui(sp, new NamedContainerProvider(stack, player.getInventory().selected), (buffer) -> {
    				buffer.writeItem(stack);
    				buffer.writeInt(player.getInventory().selected);
				});
		}
		return new InteractionResultHolder<ItemStack>(InteractionResult.SUCCESS, stack);
	}

	@Override
	public void updateNBTValues(ItemStack stack)
	{
		IItemHandler inv = getInvCap(stack); 
		if (inv != null)
		{
			int energyCost = 0, increaseCapacity = 0, energyProduction = 0, increaseHarvestlevel = 0;
			float increaseAttack = 0, increaseAttackSpeed = 0, increaseBreakdownRate = 0; 
			boolean unlockShovelFeat = false, unlockAxeFeat = false, unlockHoeFeat = false, unlockShearsFeat = false; 
			HashMap<Enchantment, Integer> enchantments = new HashMap<Enchantment, Integer>();
			
			ItemStack slot; 
			for (int i = 0; i < inv.getSlots(); i++)
			{
				slot = inv.getStackInSlot(i); 
				if (!slot.isEmpty())
				{
					if (slot.getItem() instanceof UpgradeChipItem uci)
					{
						energyCost += uci.getEnergyCost();
						increaseCapacity += uci.getIncreaseCapacity();
						energyProduction += uci.getEnergyProduction();
						increaseHarvestlevel += uci.getIncreaseHarvestlevel();
						increaseAttack += uci.getIncreaseAttack();
						increaseAttackSpeed += uci.getIncreaseAttackSpeed();
						increaseBreakdownRate += uci.getIncreaseBreakdownRate();
						if (uci.isUnlockShovelFeat()) unlockShovelFeat = true;
						if (uci.isUnlockAxeFeat()) unlockAxeFeat = true;
						if (uci.isUnlockHoeFeat()) unlockHoeFeat = true;
						if (uci.isUnlockShearsFeat()) unlockShearsFeat = true;
						Enchantment ench = uci.getEnchantment();
						if (uci.isAllowed(getSlotId()))
						{
    						if (ench != null)
    						{
    							if (enchantments.containsKey(ench))
    							{
    								int nextlevel = enchantments.get(ench) + 1; 
    								enchantments.put(ench, nextlevel); 
    							}
    							else
    								enchantments.put(ench, 1);
    						}
						}
					}
				}
			}
			stack.getEnchantmentTags().clear();
			for (Enchantment ench: enchantments.keySet())
			{
				int level = enchantments.get(ench);
				stack.enchant(ench, Math.min(ench.getMaxLevel(), level));
			}
			CompoundTag nbt = stack.getOrCreateTag();
			nbt.putInt("energycost", energyCost);
			nbt.putInt("energyproduction", energyProduction);
			nbt.putInt("harvestlevel", increaseHarvestlevel);
			nbt.putFloat("attack", increaseAttack);
			nbt.putFloat("attackspeed", increaseAttackSpeed);
			nbt.putFloat("breakdownrate", 0.5F + increaseBreakdownRate);
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
	public void inventoryTick(ItemStack stack, Level world, Entity entityIn, int itemSlot, boolean isSelected)
	{
		if (!world.isClientSide)
			if (!stack.isEmpty())
				if (world.getGameTime() % 200L == 0)
					receiveEnergy(stack, NBTHelper.getInt(stack, "energyproduction", 0), false);
	}
	
	@Override
	public int getSlotId()
	{
		return slotId;
	}

	public static class NamedContainerProvider implements MenuProvider
	{
		private final int slot;
		private final ItemStack stack; 
		
		public NamedContainerProvider(ItemStack stack, int slot)
		{
			this.stack = stack;
			this.slot = slot;
		}

		@Override
		public AbstractContainerMenu createMenu(int id, Inventory inv, Player entity)
		{
			return new ItemUpgradeableContainer(id, inv, stack, this.slot);
		}

		@Override
		public Component getDisplayName()
		{
			return new TranslatableComponent("container.upgradeableitem");
		}
	}
}
