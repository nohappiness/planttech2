package net.kaneka.planttech2.items;

import net.kaneka.planttech2.Effects.RadiationSickness;
import net.kaneka.planttech2.dimensions.planttopia.biomes.BiomeRadiation;
import net.kaneka.planttech2.dimensions.planttopia.biomes.PlantTopiaBaseBiome;
import net.kaneka.planttech2.entities.capabilities.player.RadiationEffect;
import net.kaneka.planttech2.packets.PlantTech2PacketHandler;
import net.kaneka.planttech2.packets.SyncRadiationLevelMessage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.text.DecimalFormat;

public class RadiationMetre extends BaseItem
{
    public RadiationMetre(String name, Properties property)
    {
        super(name, property);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        if (playerIn instanceof ServerPlayerEntity)
        {
            PlantTech2PacketHandler.sendTo(new SyncRadiationLevelMessage(playerIn.getCapability(RadiationEffect.RADIATION_CAPABILITY).orElseThrow(() -> new NullPointerException("getting capability")).getLevel()), (ServerPlayerEntity) playerIn);
        }
        if (worldIn.isRemote)
        {
            float level = playerIn.getCapability(RadiationEffect.RADIATION_CAPABILITY).orElseThrow(() -> new NullPointerException(" ")).getLevel();
            playerIn.sendMessage(new StringTextComponent("Your Radiation Level: " + new StringTextComponent(new DecimalFormat("###.########").format(level)).setStyle(new Style().setColor(level <= 0.20F ? TextFormatting.BLUE : level <= 0.40F ? TextFormatting.AQUA : level <= 0.60F ? TextFormatting.GREEN : level <= 0.80F ? TextFormatting.YELLOW : TextFormatting.RED)).getFormattedText()));
            Biome biome = worldIn.getBiome(playerIn.getPosition());
            playerIn.sendMessage(new StringTextComponent("Biome Radiation Level: " + BiomeRadiation.getText(biome instanceof PlantTopiaBaseBiome ? ((PlantTopiaBaseBiome) biome).getRadiationLevel() : BiomeRadiation.NONE, true).getFormattedText()));
        }
        return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
    }
}
