package net.kaneka.planttech2.items.upgradeable;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

import java.util.Random;

public class RangedWeaponItem extends UpgradeableHandItem
{
	public RangedWeaponItem(Properties property, int basecapacity, int maxInvSize)
	{
		super(property, basecapacity, maxInvSize, 0, 2.4F, UpgradeChipItem.RANGED_WEAPON);
	}

	protected ItemStack findAmmo(Player player)
	{
		if (this.isArrow(player.getItemInHand(InteractionHand.OFF_HAND)))
			return player.getItemInHand(InteractionHand.OFF_HAND);
		else if (this.isArrow(player.getItemInHand(InteractionHand.MAIN_HAND)))
			return player.getItemInHand(InteractionHand.MAIN_HAND);
		else
		{
			for (int i = 0; i < player.getInventory().getContainerSize(); ++i)
			{
				ItemStack itemstack = player.getInventory().getItem(i);
				if (this.isArrow(itemstack))
					return itemstack;
			}
			return ItemStack.EMPTY;
		}
	}

	protected boolean isArrow(ItemStack stack)
	{
		return stack.getItem() instanceof ArrowItem;
	}

	@Override
	public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft)
	{
		if (entityLiving instanceof Player player)
		{
			boolean flag = player.getAbilities().instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0;
			ItemStack itemstack = this.findAmmo(player);
			int i = this.getUseDuration(stack) - timeLeft;
			i = ForgeEventFactory.onArrowLoose(stack, level, player, i, !itemstack.isEmpty() || flag);
			if (i < 0)
				return;
			if (!itemstack.isEmpty() || flag)
			{
				if (itemstack.isEmpty())
					itemstack = new ItemStack(Items.ARROW);
				float f = getArrowVelocity(i);
				if (!((double) f < 0.1D))
				{
					boolean flag1 = player.getAbilities().instabuild || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem) itemstack.getItem()).isInfinite(itemstack, stack, player));
					if (!level.isClientSide)
					{
						ArrowItem itemarrow = (ArrowItem) (itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
						AbstractArrow entityarrow = itemarrow.createArrow(level, itemstack, player);
						entityarrow = customizeArrow(entityarrow);
						entityarrow.shoot(player.xRotO, player.yRotO, 0.0F, f * 3.0F, 1.0F);
						if (f == 1.0F)
							entityarrow.setCritArrow(true);
						int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
						if (j > 0)
							entityarrow.setBaseDamage(entityarrow.getBaseDamage() + (double) j * 0.5D + 0.5D);
						int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack);
						if (k > 0)
							entityarrow.setKnockback(k);
						if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, stack) > 0)
							entityarrow.setSecondsOnFire(100);
						if(!player.isCreative())extractEnergy(stack, getEnergyCost(stack), false);
						if (flag1 || player.getAbilities().instabuild && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW))
							entityarrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
						level.addFreshEntity(entityarrow);
						extractEnergy(stack, getEnergyCost(stack), false);
					}

					Random rand = new Random();
					level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F,
					        1.0F / (rand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
					if (!flag1 && !player.getAbilities().instabuild)
					{
						itemstack.shrink(1);
						if (itemstack.isEmpty())
							player.getInventory().removeItem(itemstack);
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
			f = 1.0F;
		return f;
	}

	public int getUseDuration(ItemStack stack)
	{
		return 72000;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack p_41452_) {
		return UseAnim.BOW;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
	{
		ItemStack stack = player.getItemInHand(hand);
		if (!player.isCrouching())
		{
    		if(extractEnergy(stack, getEnergyCost(stack), true) >= getEnergyCost(stack))
    		{
        		boolean flag = !this.findAmmo(player).isEmpty();
        		InteractionResultHolder<ItemStack> ret = ForgeEventFactory.onArrowNock(stack, level, player, hand, flag);
        		if (ret != null)
        			return ret;
        		if (!player.getAbilities().instabuild && !flag)
        			return new InteractionResultHolder<>(InteractionResult.FAIL, stack);
        		else
        		{
        			player.startUsingItem(hand);
        			return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
        		}
    		}
		}
		else if (!level.isClientSide && player instanceof ServerPlayer)
    			NetworkHooks.openGui((ServerPlayer) player, new NamedContainerProvider(stack, player.getInventory().selected), buffer -> buffer.writeItem(stack));
		return new InteractionResultHolder<>(InteractionResult.FAIL, stack);
	}

	public AbstractArrow customizeArrow(AbstractArrow arrow)
	{
		return arrow;
	}
}
