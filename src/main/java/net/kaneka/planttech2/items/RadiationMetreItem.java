package net.kaneka.planttech2.items;

import net.kaneka.planttech2.entities.capabilities.player.RadiationEffect;
import net.kaneka.planttech2.packets.PlantTech2PacketHandler;
import net.kaneka.planttech2.packets.SyncRadiationLevelMessage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.text.DecimalFormat;

public class RadiationMetreItem extends BaseItem
{
    public RadiationMetreItem(String name, Properties property)
    {
        super(name, property);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity player, Hand handIn)
    {
//        if (player instanceof ServerPlayerEntity)
//        {
//            PlantTech2PacketHandler.sendTo(new SyncRadiationLevelMessage(player.getCapability(RadiationEffect.RADIATION_CAPABILITY).orElseThrow(() -> new NullPointerException("getting capability")).getLevel()), (ServerPlayerEntity) player);
//        }
//        if (worldIn.isRemote)
//        {
//            float level = player.getCapability(RadiationEffect.RADIATION_CAPABILITY).orElseThrow(() -> new NullPointerException(" ")).getLevel();
//            player.sendMessage(new StringTextComponent("Your Radiation Level: " + new StringTextComponent(new DecimalFormat("###.########").format(level)).func_240703_c_(Style.EMPTY.setColor(Color.func_240744_a_(level <= 0.20F ? TextFormatting.BLUE : level <= 0.40F ? TextFormatting.AQUA : level <= 0.60F ? TextFormatting.GREEN : level <= 0.80F ? TextFormatting.YELLOW : TextFormatting.RED)))), player.getUniqueID());
//            Biome biome = worldIn.getBiome(player.func_233580_cy_());
//            player.sendMessage(new StringTextComponent("Biome Radiation Level: " + BiomeRadiation.getText(biome instanceof PlantTopiaBaseBiome ? ((PlantTopiaBaseBiome) biome).getRadiationLevel() : BiomeRadiation.NONE, true).getFormattedText()), player.getUniqueID());
//        }
        return ActionResult.resultSuccess(player.getHeldItem(handIn));
    }
}
