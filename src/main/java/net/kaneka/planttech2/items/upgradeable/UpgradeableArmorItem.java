package net.kaneka.planttech2.items.upgradeable;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.kaneka.planttech2.energy.BioEnergyStorage;
import net.kaneka.planttech2.energy.IItemChargeable;
import net.kaneka.planttech2.items.armors.ArmorBaseItem;
import net.kaneka.planttech2.items.armors.CustomArmorMaterial;
import net.kaneka.planttech2.items.upgradeable.BaseUpgradeableItem.NamedContainerProvider;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.kaneka.planttech2.utilities.NBTHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fmllegacy.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class UpgradeableArmorItem extends ArmorBaseItem implements IItemChargeable, IUpgradeable
{
	private static final UUID[] ARMOR_MODIFIERS = new UUID[] { UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"),
	        UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150") };

	private int basecapacity, maxInvSize, baseDamageReduction, slotId;
	private float baseToughness; 

	public UpgradeableArmorItem(String resname, EquipmentSlot slot, int basecapacity, int maxInvSize, int baseDamageReduction, float baseToughness, int slotId)
	{
		super(resname, CustomArmorMaterial.UNNECESSARY, slot, new Item.Properties().tab(ModCreativeTabs.TOOLS_AND_ARMOR));
		this.basecapacity = basecapacity;
		this.maxInvSize = maxInvSize;
		this.baseDamageReduction = baseDamageReduction; 
		this.baseToughness = baseToughness; 
		this.slotId = slotId; 
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt)
	{
		return new InventoryEnergyProvider(basecapacity, maxInvSize);
	}


	/*
	@Override
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default)
	{
		if (!itemStack.isEmpty())
		{
			if (itemStack.getItem() instanceof ItemUpgradeableArmor)
			{
				TestModel model = new TestModel();

				model.bipedLeftArm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
				model.bipedRightArm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
				model.bipedBody.showModel = armorSlot == EntityEquipmentSlot.CHEST;

				model.isChild = _default.isChild;
				model.isRiding = _default.isRiding;
				model.isSneak = _default.isSneak;
				model.rightArmPose = _default.rightArmPose;
				model.leftArmPose = _default.leftArmPose;

				return model;
			}
		}
		return null;
	}
	*/

	public void updateEnergy(ItemStack stack)
	{
		CompoundTag tag = stack.getTag();
		if (tag == null)
		{
			tag = new CompoundTag();
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

	public static IEnergyStorage getEnergyCap(ItemStack stack)
	{
		LazyOptional<IEnergyStorage> provider = stack.getCapability(CapabilityEnergy.ENERGY);
		if (provider != null)
		{
			return provider.orElse(null);
		}
		return null;
	}

	public static IItemHandler getInvCap(ItemStack stack)
	{
		LazyOptional<IItemHandler> provider = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
		if (provider != null)
		{
			return provider.orElse(null);
		}
		return null;
	}

	public static int getEnergyCost(ItemStack stack)
	{
		return 20 + NBTHelper.getInt(stack, "energycost", 0);
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
		CompoundTag tag = stack.getTag();
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
		if (handler != null)
		{
			return handler.getSlots();
		}
		return 0;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand)
	{
		ItemStack stack = player.getItemInHand(hand);

		if(player.isCrouching())
		{
			if (!world.isClientSide && player instanceof ServerPlayer)
			{
    			NetworkHooks.openGui((ServerPlayer) player, new NamedContainerProvider(stack, player.getInventory().selected), buffer -> buffer.writeItem(stack));
			}
		}
		return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
	}

	@Override
	public void updateNBTValues(ItemStack stack)
	{
		IItemHandler inv = getInvCap(stack);
		if (inv != null)
		{
			int energyCost = 0, increaseCapacity = 0, energyProduction = 0, increaseArmor = 0;
			float increaseToughness = 0; 
			HashMap<Enchantment, Integer> enchantments = new HashMap<Enchantment, Integer>();

			ItemStack slot;
			for (int i = 0; i < inv.getSlots(); i++)
			{
				slot = inv.getStackInSlot(i);
				if (!slot.isEmpty())
				{
					if (slot.getItem() instanceof UpgradeChipItem)
					{
						UpgradeChipItem item = (UpgradeChipItem) slot.getItem();
						energyCost += item.getEnergyCost();
						increaseCapacity += item.getIncreaseCapacity();
						energyProduction += item.getEnergyProduction();
						increaseArmor += item.getIncreaseArmor(); 
						increaseToughness += item.getIncreaseToughness(); 
						Enchantment ench = item.getEnchantment(); 
						if(item.isAllowed(getSlotId()))
						{
    						if(ench != null)
    						{
    							if(enchantments.containsKey(ench))
    							{
    								int nextlevel = enchantments.get(ench) + 1; 
    								enchantments.put(ench, nextlevel); 
    							}
    							else
    							{
    								enchantments.put(ench, 1);
    							}
    						}
						}
					}
				}
			}
			
			stack.getEnchantmentTags().clear();
			for (Enchantment ench: enchantments.keySet())
			{
				int level = enchantments.get(ench);  
				if(ench.getMaxLevel() < level)
				{
					stack.enchant(ench, ench.getMaxLevel());
				}
				else
				{
					stack.enchant(ench, level);
				}
			}

			CompoundTag nbt = stack.getTag();
			if (nbt == null)
			{
				nbt = new CompoundTag();
			}

			nbt.putInt("energycost", energyCost);
			nbt.putInt("energyproduction", energyProduction);
			nbt.putInt("increasearmor", increaseArmor);
			nbt.putFloat("increasetoughness", increaseToughness);

			stack.setTag(nbt);

			IEnergyStorage energy = getEnergyCap(stack);
			if (energy != null)
			{
				if (energy instanceof BioEnergyStorage)
				{
					((BioEnergyStorage) energy).setEnergyMaxStored(basecapacity + increaseCapacity);
				}
			}
		}
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack)
	{
		Multimap<Attribute, AttributeModifier> multimap = HashMultimap.create();
		if (equipmentSlot == slot)
		{
			multimap.put(Attributes.ARMOR, new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Armor modifier", (double)getDamageReduceAmount(stack), AttributeModifier.Operation.ADDITION));
			multimap.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Armor toughness", (double)getToughness(stack), AttributeModifier.Operation.ADDITION));
		}

		return multimap;
	}
	
	public int getDamageReduceAmount(ItemStack stack)
	{
		return baseDamageReduction + NBTHelper.getInt(stack, "increasearmor", 0);
	}
	
	public float getToughness(ItemStack stack)
	{
		return baseToughness + NBTHelper.getFloat(stack, "increasetoughness", 0);
	}
	
	@Override
	public boolean canBeDepleted()
	{
		return false;
	}
	
	@Override
	public void onArmorTick(ItemStack stack, Level world, Player player)
	{
		if(!world.isClientSide)
		{
			if(!stack.isEmpty())
			{
				if(world.getGameTime() % 200L == 0)
				{
					receiveEnergy(stack, NBTHelper.getInt(stack, "energyproduction", 0), false);
				}
			}
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flagIn)
	{
		CompoundTag tag = stack.getTag();
		if (tag != null)
		{
			tooltip.add(new TranslatableComponent("info.energy", tag.getInt("current_energy") + "/" + tag.getInt("max_energy")));
			tooltip.add(new TranslatableComponent("info.energycosts", getEnergyCost(stack)));
			tooltip.add(new TranslatableComponent("info.openwithshift"));
		}

		super.appendHoverText(stack, level, tooltip, flagIn);
	}

	@Override
	public int getSlotId()
	{
		return slotId;
	}

}
