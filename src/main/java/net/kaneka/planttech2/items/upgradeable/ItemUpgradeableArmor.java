package net.kaneka.planttech2.items.upgradeable;

import java.util.List;
import java.util.UUID;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.kaneka.planttech2.energy.BioEnergyStorage;
import net.kaneka.planttech2.energy.IItemChargeable;
import net.kaneka.planttech2.items.armors.CustomArmorMaterial;
import net.kaneka.planttech2.items.armors.ItemArmorBase;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.kaneka.planttech2.utilities.NBTHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ItemUpgradeableArmor extends ItemArmorBase implements IItemChargeable, IUpgradeable
{
	private static final UUID[] ARMOR_MODIFIERS = new UUID[] { UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"),
	        UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150") };

	private int basecapacity, maxInvSize, baseDamageReduction;
	private float baseToughness; 

	public ItemUpgradeableArmor(String name, String resname, EquipmentSlotType slot, int basecapacity, int maxInvSize, int baseDamageReduction, float baseToughness)
	{
		super(name, resname, CustomArmorMaterial.UNNECESSARY, slot, new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor));
		this.basecapacity = basecapacity;
		this.maxInvSize = maxInvSize;
		this.baseDamageReduction = baseDamageReduction; 
		this.baseToughness = baseToughness; 
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
		return 20 + NBTHelper.getIntSave(stack, "energycost", 0);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		CompoundNBT tag = stack.getTag();
		if (tag != null)
		{
			tooltip.add(new StringTextComponent(tag.getInt("current_energy") + "/" + tag.getInt("max_energy")));
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
		if (handler != null)
		{
			return handler.getSlots();
		}
		return 0;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
	{
		ItemStack stack = player.getHeldItem(hand);
		if (Minecraft.getInstance().gameSettings.keyBindSneak.isKeyDown())
		{
			if (!world.isRemote && player instanceof ServerPlayerEntity)
			{
				NetworkHooks.openGui((ServerPlayerEntity) player, new ItemBaseUpgradeable.NamedContainerProvider(stack), buffer -> buffer.writeItemStack(stack));
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

			ItemStack slot;
			for (int i = 0; i < inv.getSlots(); i++)
			{
				slot = inv.getStackInSlot(i);
				if (!slot.isEmpty())
				{
					if (slot.getItem() instanceof ItemUpgradeChip)
					{
						ItemUpgradeChip item = (ItemUpgradeChip) slot.getItem();
						energyCost += item.getEnergyCost();
						increaseCapacity += item.getIncreaseCapacity();
						energyProduction += item.getEnergyProduction();
						increaseArmor += item.getIncreaseArmor(); 
						increaseToughness += item.getIncreaseToughness(); 
					}
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
	public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot, ItemStack stack)
	{
		Multimap<String, AttributeModifier> multimap = HashMultimap.create();
		if (equipmentSlot == this.armorType)
		{
			multimap.put(SharedMonsterAttributes.ARMOR.getName(), new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Armor modifier", (double)getDamageReduceAmount(stack), AttributeModifier.Operation.ADDITION));
			multimap.put(SharedMonsterAttributes.ARMOR_TOUGHNESS.getName(), new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Armor toughness", (double)getToughness(stack), AttributeModifier.Operation.ADDITION));
		}

		return multimap;
	}
	
	public int getDamageReduceAmount(ItemStack stack)
	{
		return baseDamageReduction + NBTHelper.getIntSave(stack, "increasearmor", 0);
	}
	
	public float getToughness(ItemStack stack)
	{
		return baseToughness + NBTHelper.getFloatSave(stack, "increasetoughness", 0);
	}
	
	@Override
	public boolean isDamageable()
	{
		return false;
	}
	
	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player)
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

}
