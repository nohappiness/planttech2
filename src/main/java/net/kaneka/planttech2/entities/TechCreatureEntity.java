package net.kaneka.planttech2.entities;

import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.UUID;

public abstract class TechCreatureEntity extends CreatureEntity
{
    protected int passiveCooldown;
    protected static final UUID MOVEMENT_SPEED_MODIFIER = UUID.fromString("8FA5B460-E908-42ED-8203-573535F1912A");

    public TechCreatureEntity(EntityType<? extends TechCreatureEntity> type, World world)
    {
        super(type, world);
    }

    @Override
    public void tick()
    {
        super.tick();
        if (this.passiveCooldown > 0)
            this.passiveCooldown--;
        int cooldown = this.getMaxPassiveCooldown() - this.getPassiveCooldown();
        if (cooldown == this.getPassiveDuration())
            this.onPassiveEnds();
        else if (this.isPassiveActive())
            this.onPassiveActive();
        if (!world.isRemote())
        {
            if (this.matchPassiveCriteria())
                if (this.onPassiveActivate())
                    this.passiveCooldown = this.getMaxPassiveCooldown();
            ModifiableAttributeInstance attribute = this.getAttribute(Attributes.MOVEMENT_SPEED);
            AttributeModifier modifier = attribute.getModifier(MOVEMENT_SPEED_MODIFIER);
            float amount = (this.getMaxHealth() - this.getHealth()) / this.getMaxHealth() * 0.03F;
            if (modifier != null && (this.isPassiveActive() || modifier.getAmount() != amount))
                attribute.removeModifier(MOVEMENT_SPEED_MODIFIER);
            else if (modifier == null)
                attribute.applyModifier(new AttributeModifier(MOVEMENT_SPEED_MODIFIER, "Speed Modifier", -amount, AttributeModifier.Operation.ADDITION));
        }
        this.idleTime++;
        if (this.getHealth() < this.getMaxHealth() && this.idleTime > 300 && this.idleTime % 12 == 0)
        {
            this.heal(Math.max(0.5F, (this.getMaxHealth() - this.getHealth()) / 30));
            if (world.isRemote())
                for (int p=0;p<7;p++)
                    this.spawnParticles(ParticleTypes.HAPPY_VILLAGER);
        }
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.getItem() == ModItems.BIOMASS)
            if (this.getHealth() < this.getMaxHealth())
            {
                this.heal(this.getMaxHealth() / 4);
                if (!player.abilities.isCreativeMode)
                    stack.shrink(1);
                for (int p=0;p<5;p++)
                    this.spawnParticles(ParticleTypes.HAPPY_VILLAGER);
                return true;
            }
        return false;
    }

    protected boolean matchPassiveCriteria()
    {
        return this.passiveCooldown <= 0 && !isPassiveActive() && this.getHealth() <= this.getMaxHealth() / 5;
    }

    protected boolean onPassiveActivate()
    {
        for (int p=0;p<7;p++)
            this.spawnParticles(ParticleTypes.EXPLOSION);
        this.addPotionEffect(new EffectInstance(Effects.SPEED, 60));
        this.addPotionEffect(new EffectInstance(Effects.REGENERATION, 100));
        return true;
    }

    protected void onPassiveActive()
    {
        this.spawnParticles(ParticleTypes.EXPLOSION);
    }

    protected void onPassiveEnds()
    {
        for (int p=0;p<7;p++)
            this.spawnParticles(ParticleTypes.LARGE_SMOKE);
    }

    protected void spawnParticles(IParticleData particle)
    {
        world.addParticle(particle, this.getPosX() - this.getWidth() / 2 + this.rand.nextFloat() * this.getWidth(), this.getPosY() + this.rand.nextFloat() * this.getWidth(), this.getPosZ() - this.getWidth() / 2 + this.rand.nextFloat() * this.getWidth(), 0.0F, 0.0F, 0.0F);
    }

    @Override
    public void writeAdditional(CompoundNBT compound)
    {
        compound.putInt("passivecooldown", this.passiveCooldown);
        super.writeAdditional(compound);
    }

    @Override
    public void readAdditional(CompoundNBT compound)
    {
        this.passiveCooldown = compound.getInt("passivecooldown");
        super.readAdditional(compound);
    }

    protected int getPassiveDuration()
    {
        return 100;
    }

    public boolean isPassiveActive()
    {
        return this.getMaxPassiveCooldown() - this.getPassiveCooldown() < this.getPassiveDuration();
    }

    protected int getMaxPassiveCooldown()
    {
        return 1200;
    }

    protected int getPassiveCooldown()
    {
        return this.passiveCooldown;
    }
}
