package net.kaneka.planttech2.entities.neutral;

import net.kaneka.planttech2.entities.IAffectPlayerRadiation;
import net.kaneka.planttech2.registries.ModEntityTypes;
import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.WitchEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class TechGhoulEntity extends CreatureEntity implements IAffectPlayerRadiation
{
	public TechGhoulEntity(EntityType<? extends TechGhoulEntity> type, World worldIn)
	{
		super(type, worldIn);
	}

	@Override
	protected void registerGoals()
	{
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.15D, true));
		this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 0.8D, 40.0F));
		this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 12.0F));
		this.goalSelector.addGoal(4, new LookRandomlyGoal(this));

		this.targetSelector.addGoal(1, new AttackNearbyKillerGoal<>(MobEntity.class
//			(target) -> (target instanceof IMob || target instanceof PlayerEntity) && !(target instanceof CreeperEntity) && !(target instanceof SkeletonEntity)
		));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
	}

	@Override
	protected void registerAttributes()
	{
		super.registerAttributes();
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(35.0D);
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
		this.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5D);
		this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
		this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(50.0D);
	}

	@Override
	public boolean processInteract(PlayerEntity player, Hand hand)
	{
		ItemStack stack = player.getHeldItem(hand);
		if (stack.getItem() == ModItems.BIOMASS)
		{
			if (this.getHealth() + 2.0F <= this.getMaxHealth())
			{
				this.heal(2.0F);
				if (!player.abilities.isCreativeMode)
				{
					stack.shrink(1);
				}
			}
		}
		return false;
	}

	public static EntityType<? extends TechGhoulEntity> getEntityType()
	{
		return (EntityType<? extends TechGhoulEntity>) ModEntityTypes.TECHGHOULENTITY;
	}

	@Override
	public float getAmount()
	{
		return (1.0F / 1000.0F);
	}

	class AttackNearbyKillerGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T>
	{
		public AttackNearbyKillerGoal(Class<T> targetClassIn)
		{
			super(TechGhoulEntity.this, targetClassIn, 30, true, true, null);
		}

		@Override
		public boolean shouldExecute()
		{
			this.findNearestTarget();
//			System.out.println("target: " + nearestTarget);
			return this.nearestTarget != null;
		}

		@Override
		protected void findNearestTarget()
		{
			if (goalOwner.getAttackTarget() != null)
			{
				return;
			}
			for (Entity entity : goalOwner.getEntityWorld().getEntitiesWithinAABB(LivingEntity.class, goalOwner.getBoundingBox().grow(20.0D, 20.0D, 20.0D)))
			{
				if (!(entity instanceof IMob || entity instanceof PlayerEntity))
				{
					nearestTarget = null;
					continue;
				}
				else if (entity instanceof SkeletonEntity || entity instanceof CreeperEntity || entity instanceof WitchEntity || entity instanceof TechGhoulEntity)
				{
					nearestTarget = null;
					continue;
				}
				if (entity instanceof LivingEntity)
				{
					LivingEntity living = (LivingEntity) entity;
					if(living.getLastAttackedEntity() != null && living.ticksExisted - living.getLastAttackedEntityTime() <= 40 && !living.getLastAttackedEntity().isAlive())
					{
//						System.out.println(living);
						nearestTarget = living;
						break;
					}
				}
				nearestTarget = null;
			}
		}
	}
}
