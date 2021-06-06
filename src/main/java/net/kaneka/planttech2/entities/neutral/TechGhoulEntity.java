package net.kaneka.planttech2.entities.neutral;

import net.kaneka.planttech2.entities.TechCreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.WitchEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class TechGhoulEntity extends TechCreatureEntity
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

		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new AttackNearbyKillerGoal<>(MobEntity.class));
	}

	/*@Override
	protected void registerAttributes()
	{
		super.registerAttributes();
		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(45.0D);
		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.25D);
		this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5D);
		this.getAttributes().registerAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(5.0D);
		this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(50.0D);
	}*/

//	/**
//	 * Not being called before a good place to call it is found
//	 * {@link net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes#put(EntityType, AttributeModifierMap)}
//	 */
	//Was called CommonSetupEvent atm
	

	@Override
	protected boolean onPassiveActivate()
	{
		this.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 60));
		return super.onPassiveActivate();
	}

    @Override
    protected void onPassiveActive()
    {
        super.onPassiveActive();
    }

    @Override
    protected void onPassiveEnds()
    {
        this.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 60, 1));
        super.onPassiveEnds();
    }

    class AttackNearbyKillerGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T>
	{
		public AttackNearbyKillerGoal(Class<T> targetClassIn)
		{
			super(TechGhoulEntity.this, targetClassIn, 30, true, true, null);
		}

		@Override
		public boolean canUse()
		{
			this.findTarget();
//			System.out.println("target: " + nearestTarget);
			return this.target != null;
		}

		@Override
		protected void findTarget()
		{
			if (mob.getTarget() != null)
			{
				return;
			}
			for (Entity entity : mob.getCommandSenderWorld().getEntitiesOfClass(LivingEntity.class, mob.getBoundingBox().inflate(20.0D, 20.0D, 20.0D)))
			{
				if (!(entity instanceof IMob || entity instanceof PlayerEntity))
				{
					target = null;
					continue;
				}
				else if (entity instanceof SkeletonEntity || entity instanceof CreeperEntity || entity instanceof WitchEntity || entity instanceof TechGhoulEntity)
				{
					target = null;
					continue;
				}
				if (entity instanceof LivingEntity)
				{
					LivingEntity living = (LivingEntity) entity;
					if(living.getLastHurtMob() != null && living.tickCount - living.getLastHurtMobTimestamp() <= 40 && !living.getLastHurtMob().isAlive())
					{
//						System.out.println(living);
						target = living;
						break;
					}
				}
				target = null;
			}
		}
	}
}
