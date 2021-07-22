package net.kaneka.planttech2.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.level.Level;

public class RadiationMetreItem extends Item
{
    public RadiationMetreItem(Properties property)
    {
        super(property);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player player, InteractionHand handIn)
    {
//        if (player instanceof ServerPlayerEntity)
//        {
//            PlantTech2PacketHandler.sendTo(new SyncRadiationLevelMessage(player.getCapability(RadiationEffect.RADIATION_CAPABILITY).orElseThrow(() -> new NullPointerException("getting capability")).getLevel()), (ServerPlayerEntity) player);
//        }
//        if (worldIn.isRemote)
//        {
//            float level = player.getCapability(RadiationEffect.RADIATION_CAPABILITY).orElseThrow(() -> new NullPointerException(" ")).getLevel();
//            player.sendMessage(new StringTextComponent("Your Radiation Level: " + new StringTextComponent(new DecimalFormat("###.########").format(level)).mergeStyle(Style.EMPTY.setColor(Color.fromTextFormatting(level <= 0.20F ? TextFormatting.BLUE : level <= 0.40F ? TextFormatting.AQUA : level <= 0.60F ? TextFormatting.GREEN : level <= 0.80F ? TextFormatting.YELLOW : TextFormatting.RED)))), player.getUniqueID());
//            Biome biome = worldIn.getBiome(player.getPosition());
//            player.sendMessage(new StringTextComponent("Biome Radiation Level: " + BiomeRadiation.getText(biome instanceof PlantTopiaBaseBiome ? ((PlantTopiaBaseBiome) biome).getRadiationLevel() : BiomeRadiation.NONE, true).getFormattedText()), player.getUniqueID());
//        }
        return InteractionResultHolder.success(player.getItemInHand(handIn));
    }
}
