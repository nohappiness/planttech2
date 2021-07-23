package net.kaneka.planttech2.items.upgradeable;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAction;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.network.NetworkHooks;

import net.minecraft.world.item.Item.Properties;

public class RangedWeaponItem extends UpgradeableHandItem
{
	public RangedWeaponItem(Properties property, int basecapacity, int maxInvSize)
	{
		super(property, basecapacity, maxInvSize, 0, 2.4F, UpgradeChipItem.RANGED_WEAPON);
	}

	protected ItemStack findAmmo(PlayerEntity player)
	{
		if (this.isArrow(player.getItemInHand(Hand.OFF_HAND)))
		{
			return player.getItemInHand(Hand.OFF_HAND);
		} 
		else if (this.isArrow(player.getItemInHand(Hand.MAIN_HAND)))
		{
			return player.getItemInHand(Hand.MAIN_HAND);
		} 
		else
		{
			for (int i = 0; i < player.inventory.getContainerSize(); ++i)
			{
				ItemStack itemstack = player.inventory.getItem(i);
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
	public void releaseUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft)
	{
		
		if (entityLiving instanceof PlayerEntity)
		{
			PlayerEntity PlayerEntity = (PlayerEntity) entityLiving;
			boolean flag = PlayerEntity.abilities.instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0;
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
					boolean flag1 = PlayerEntity.abilities.instabuild
					        || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem) itemstack.getItem()).isInfinite(itemstack, stack, PlayerEntity));
					if (!worldIn.isClientSide)
					{
						ArrowItem itemarrow = (ArrowItem) (itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
						AbstractArrowEntity entityarrow = itemarrow.createArrow(worldIn, itemstack, PlayerEntity);
						entityarrow = customizeArrow(entityarrow);
						entityarrow.shoot(PlayerEntity.xRot, PlayerEntity.yRot, 0.0F, f * 3.0F, 1.0F);
						if (f == 1.0F)
						{
							entityarrow.setCritArrow(true);
						}

						int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
						if (j > 0)
						{
							entityarrow.setBaseDamage(entityarrow.getBaseDamage() + (double) j * 0.5D + 0.5D);
						}

						int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack);
						if (k > 0)
						{
							entityarrow.setKnockback(k);
						}

						if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, stack) > 0)
						{
							entityarrow.setSecondsOnFire(100);
						}

						if(!PlayerEntity.isCreative())extractEnergy(stack, getEnergyCost(stack), false);
						if (flag1 || PlayerEntity.abilities.instabuild && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW))
						{
							entityarrow.pickup = ArrowEntity.PickupStatus.CREATIVE_ONLY;
						}

						worldIn.addFreshEntity(entityarrow);
						extractEnergy(stack, getEnergyCost(stack), false);
					}

					worldIn.playSound((PlayerEntity) null, PlayerEntity.getX(), PlayerEntity.getY(), PlayerEntity.getZ(), SoundEvents.ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F,
					        1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
					if (!flag1 && !PlayerEntity.abilities.instabuild)
					{
						itemstack.shrink(1);
						if (itemstack.isEmpty())
						{
							PlayerEntity.inventory.removeItem(itemstack);
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
	public UseAction getUseAnimation(ItemStack stack)
	{
		return UseAction.BOW;
	}

	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity player, Hand hand)
	{
		ItemStack stack = player.getItemInHand(hand);
		if(!player.isCrouching())
		{
			System.out.println(getEnergyCost(stack));
    		if(extractEnergy(stack, getEnergyCost(stack), true) >= getEnergyCost(stack))
    		{
        		
        		boolean flag = !this.findAmmo(player).isEmpty();
        
        		ActionResult<ItemStack> ret = ForgeEventFactory.onArrowNock(stack, worldIn, player, hand, flag);
        		if (ret != null)
        			return ret;
        
        		if (!player.abilities.instabuild && !flag)
        		{
        			return flag ? new ActionResult<>(ActionResultType.PASS, stack) : new ActionResult<>(ActionResultType.FAIL, stack);
        		} else
        		{
        			player.startUsingItem(hand);
        			return new ActionResult<>(ActionResultType.SUCCESS, stack);
        		}
    		}
		}
		else
		{
			if (!worldIn.isClientSide && player instanceof ServerPlayerEntity) 
			{
    			NetworkHooks.openGui((ServerPlayerEntity) player, new NamedContainerProvider(stack, player.inventory.selected), buffer -> buffer.writeItem(stack));
			}
		}
		return new ActionResult<>(ActionResultType.FAIL, stack);
	}

	public AbstractArrowEntity customizeArrow(AbstractArrowEntity arrow)
	{
		return arrow;
	}

}
