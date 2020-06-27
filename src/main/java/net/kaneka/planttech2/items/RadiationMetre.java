package net.kaneka.planttech2.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class RadiationMetre extends BaseItem
{
    public RadiationMetre(String name, Properties property)
    {
        super(name, property);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity player, Hand handIn)
    {
    	return ActionResult.resultSuccess(player.getHeldItem(handIn));
    	/*
        if (player instanceof ServerPlayerEntity)
        {
            PlantTech2PacketHandler.sendTo(new SyncRadiationLevelMessage(player.getCapability(RadiationEffect.RADIATION_CAPABILITY).orElseThrow(() -> new NullPointerException("getting capability")).getLevel()), (ServerPlayerEntity) player);
        }
        if (worldIn.isRemote)
        {
            float level = player.getCapability(RadiationEffect.RADIATION_CAPABILITY).orElseThrow(() -> new NullPointerException(" ")).getLevel();
            player.sendMessage(new StringTextComponent("Your Radiation Level: " + new StringTextComponent(new DecimalFormat("###.########").format(level)).setStyle(new Style().setColor(level <= 0.20F ? TextFormatting.BLUE : level <= 0.40F ? TextFormatting.AQUA : level <= 0.60F ? TextFormatting.GREEN : level <= 0.80F ? TextFormatting.YELLOW : TextFormatting.RED)).getFormattedText()), player.getUniqueID());
            Biome biome = worldIn.getBiome(player.getPosition());
            player.sendMessage(new StringTextComponent("Biome Radiation Level: " + BiomeRadiation.getText(biome instanceof PlantTopiaBaseBiome ? ((PlantTopiaBaseBiome) biome).getRadiationLevel() : BiomeRadiation.NONE, true).getFormattedText()), player.getUniqueID());
        }
        return ActionResult.resultSuccess(player.getHeldItem(handIn));
        */
    }
}
