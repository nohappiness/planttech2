package net.kaneka.planttech2.entities.neutral;

import net.kaneka.planttech2.entities.IAffectPlayerRadiation;
import net.kaneka.planttech2.entities.TechCreatureEntity;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.WitchEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class TechGhoulEntity extends TechCreatureEntity implements IAffectPlayerRadiation
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

	/**
	 * Not being called before a good place to call it is found
	 * {@link net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes#put(EntityType, AttributeModifierMap)}
	 */
	public static AttributeModifierMap.MutableAttribute getAttributes()
	{
		return MobEntity.func_233666_p_().func_233815_a_(Attributes.MAX_HEALTH, 45.0D)
				.func_233815_a_(Attributes.MOVEMENT_SPEED, 0.25D)
				.func_233815_a_(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
				.func_233815_a_(Attributes.ATTACK_DAMAGE, 5.0F)
				.func_233815_a_(Attributes.FOLLOW_RANGE, 50.0D);
	}

	@Override
	public float getAmount()
	{
		return (1.0F / 1000.0F);
	}

	@Override
	protected boolean onPassiveActivate()
	{
		this.addPotionEffect(new EffectInstance(Effects.STRENGTH, 60));
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
        this.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 60, 1));
        super.onPassiveEnds();
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
