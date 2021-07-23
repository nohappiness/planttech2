package net.kaneka.planttech2.items.upgradeable;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.kaneka.planttech2.energy.BioEnergyStorage;
import net.kaneka.planttech2.energy.IItemChargeable;
import net.kaneka.planttech2.items.armors.CustomArmorMaterial;
import net.kaneka.planttech2.items.upgradeable.BaseUpgradeableItem.NamedContainerProvider;
import net.kaneka.planttech2.items.armors.ArmorBaseItem;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.kaneka.planttech2.utilities.NBTHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
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

public class UpgradeableArmorItem extends ArmorBaseItem implements IItemChargeable, IUpgradeable
{
	private static final UUID[] ARMOR_MODIFIERS = new UUID[] { UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"),
	        UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150") };

	private int basecapacity, maxInvSize, baseDamageReduction, slotId;
	private float baseToughness; 

	public UpgradeableArmorItem(String resname, EquipmentSlotType slot, int basecapacity, int maxInvSize, int baseDamageReduction, float baseToughness, int slotId)
	{
		super(resname, CustomArmorMaterial.UNNECESSARY, slot, new Item.Properties().tab(ModCreativeTabs.TOOLS_AND_ARMOR));
		this.basecapacity = basecapacity;
		this.maxInvSize = maxInvSize;
		this.baseDamageReduction = baseDamageReduction; 
		this.baseToughness = baseToughness; 
		this.slotId = slotId; 
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt)
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
		if (handler != null)
		{
			return handler.getSlots();
		}
		return 0;
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
	{
		ItemStack stack = player.getItemInHand(hand);

		if(player.isCrouching())
		{
			if (!world.isClientSide && player instanceof ServerPlayerEntity) 
			{
    			NetworkHooks.openGui((ServerPlayerEntity) player, new NamedContainerProvider(stack, player.inventory.selected), buffer -> buffer.writeItem(stack));
			}
		}
		return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
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

			CompoundNBT nbt = stack.getTag();
			if (nbt == null)
			{
				nbt = new CompoundNBT();
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
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot, ItemStack stack)
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
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player)
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
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		CompoundNBT tag = stack.getTag();
		if (tag != null)
		{
			tooltip.add(new TranslationTextComponent("info.energy", tag.getInt("current_energy") + "/" + tag.getInt("max_energy")));
			tooltip.add(new TranslationTextComponent("info.energycosts", getEnergyCost(stack))); 
			tooltip.add(new TranslationTextComponent("info.openwithshift")); 
		}

		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public int getSlotId()
	{
		return slotId;
	}

}
