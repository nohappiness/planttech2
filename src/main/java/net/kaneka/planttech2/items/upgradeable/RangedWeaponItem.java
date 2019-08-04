package net.kaneka.planttech2.items.upgradeable;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.network.NetworkHooks;

public class RangedWeaponItem extends UpgradeableHandItem
{

	public RangedWeaponItem(String name, Properties property, int basecapacity, int maxInvSize)
	{
		super(name, property, basecapacity, maxInvSize, 0, 2.4F);
		this.addPropertyOverride(new ResourceLocation("pull"), (stack, world, entity) -> {
			if (entity == null)
			{
				return 0.0F;
			} else
			{
				return !(entity.getActiveItemStack().getItem() instanceof RangedWeaponItem) ? 0.0F : (float) (stack.getUseDuration() - entity.getItemInUseCount()) / 20.0F;
			}
		});
		this.addPropertyOverride(new ResourceLocation("pulling"), (stack, world, entity) -> {
			return entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack ? 1.0F : 0.0F;
		});
	}

	protected ItemStack findAmmo(PlayerEntity player)
	{
		if (this.isArrow(player.getHeldItem(Hand.OFF_HAND)))
		{
			return player.getHeldItem(Hand.OFF_HAND);
		} 
		else if (this.isArrow(player.getHeldItem(Hand.MAIN_HAND)))
		{
			return player.getHeldItem(Hand.MAIN_HAND);
		} 
		else
		{
			for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
			{
				ItemStack itemstack = player.inventory.getStackInSlot(i);
				if (this.isArrow(itemstack))
				{
					return itemstack;
				}
			}

			return ItemStack.EMPTY;
		}
	}

	protected boolean isArrow(ItemStack stack)
	{
		return stack.getItem() instanceof ArrowItem;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft)
	{
		
		if (entityLiving instanceof PlayerEntity)
		{
			PlayerEntity PlayerEntity = (PlayerEntity) entityLiving;
			boolean flag = PlayerEntity.abilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
			ItemStack itemstack = this.findAmmo(PlayerEntity);

			int i = this.getUseDuration(stack) - timeLeft;
			i = ForgeEventFactory.onArrowLoose(stack, worldIn, PlayerEntity, i, !itemstack.isEmpty() || flag);
			if (i < 0)
				return;

			if (!itemstack.isEmpty() || flag)
			{
				if (itemstack.isEmpty())
				{
					itemstack = new ItemStack(Items.ARROW);
				}

				float f = getArrowVelocity(i);
				if (!((double) f < 0.1D))
				{
					boolean flag1 = PlayerEntity.abilities.isCreativeMode
					        || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem) itemstack.getItem()).isInfinite(itemstack, stack, PlayerEntity));
					if (!worldIn.isRemote)
					{
						ArrowItem itemarrow = (ArrowItem) (itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
						AbstractArrowEntity entityarrow = itemarrow.createArrow(worldIn, itemstack, PlayerEntity);
						entityarrow = customizeArrow(entityarrow);
						entityarrow.shoot(PlayerEntity, PlayerEntity.rotationPitch, PlayerEntity.rotationYaw, 0.0F, f * 3.0F, 1.0F);
						if (f == 1.0F)
						{
							entityarrow.setIsCritical(true);
						}

						int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
						if (j > 0)
						{
							entityarrow.setDamage(entityarrow.getDamage() + (double) j * 0.5D + 0.5D);
						}

						int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
						if (k > 0)
						{
							entityarrow.setKnockbackStrength(k);
						}

						if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0)
						{
							entityarrow.setFire(100);
						}

						if(!PlayerEntity.isCreative())extractEnergy(stack, getEnergyCost(stack), false);
						if (flag1 || PlayerEntity.abilities.isCreativeMode && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW))
						{
							entityarrow.pickupStatus = ArrowEntity.PickupStatus.CREATIVE_ONLY;
						}

						worldIn.addEntity(entityarrow);
						extractEnergy(stack, getEnergyCost(stack), false);
					}

					worldIn.playSound((PlayerEntity) null, PlayerEntity.posX, PlayerEntity.posY, PlayerEntity.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F,
					        1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
					if (!flag1 && !PlayerEntity.abilities.isCreativeMode)
					{
						itemstack.shrink(1);
						if (itemstack.isEmpty())
						{
							PlayerEntity.inventory.deleteStack(itemstack);
						}
					}
				}
			}
		}
	}

	public static float getArrowVelocity(int charge)
	{
		float f = (float) charge / 20.0F;
		f = (f * f + f * 2.0F) / 3.0F;
		if (f > 1.0F)
		{
			f = 1.0F;
		}

		return f;
	}


	public int getUseDuration(ItemStack stack)
	{
		return 72000;
	}

	@Override
	public UseAction getUseAction(ItemStack stack)
	{
		return UseAction.BOW;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity player, Hand hand)
	{
		ItemStack stack = player.getHeldItem(hand);
		if(!player.isSneaking())
		{
			System.out.println(getEnergyCost(stack));
    		if(extractEnergy(stack, getEnergyCost(stack), true) >= getEnergyCost(stack))
    		{
        		
        		boolean flag = !this.findAmmo(player).isEmpty();
        
        		ActionResult<ItemStack> ret = ForgeEventFactory.onArrowNock(stack, worldIn, player, hand, flag);
        		if (ret != null)
        			return ret;
        
        		if (!player.abilities.isCreativeMode && !flag)
        		{
        			return flag ? new ActionResult<>(ActionResultType.PASS, stack) : new ActionResult<>(ActionResultType.FAIL, stack);
        		} else
        		{
        			player.setActiveHand(hand);
        			return new ActionResult<>(ActionResultType.SUCCESS, stack);
        		}
    		}
		}
		else
		{
			if (!worldIn.isRemote && player instanceof ServerPlayerEntity) 
			{
    			NetworkHooks.openGui((ServerPlayerEntity) player, new NamedContainerProvider(stack), buffer -> buffer.writeItemStack(stack));
			}
		}
		return new ActionResult<>(ActionResultType.FAIL, stack);
	}

	public AbstractArrowEntity customizeArrow(AbstractArrowEntity arrow)
	{
		return arrow;
	}

}
