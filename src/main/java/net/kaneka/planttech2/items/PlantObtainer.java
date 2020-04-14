package net.kaneka.planttech2.items;

import net.kaneka.planttech2.blocks.baseclasses.NaturalPlants;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;

public class PlantObtainer extends BaseItem
{
    public PlantObtainer(String name, Properties property)
    {
        super(name, property.maxStackSize(1));
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context)
    {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        BlockState state = world.getBlockState(pos);
        PlayerEntity player = context.getPlayer();
        if (player == null)
        {
            return ActionResultType.FAIL;
        }
        ItemStack stack = player.getHeldItemMainhand();
        iniTags(stack);
        if (state.getBlock() instanceof NaturalPlants)
        {
            if (!isFilled(stack) && ((NaturalPlants) state.getBlock()).canBeObtained())
            {
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
                setBlockFilled(stack, state);
                world.playSound(player, pos, SoundEvents.BLOCK_PORTAL_TRAVEL, SoundCategory.BLOCKS, 0.5F, 10.0F);
                return ActionResultType.SUCCESS;
            }
        }
        else if (isFilled(stack))
        {
            Block block = getBlockFilled(stack);
            if (block != null && ((NaturalPlants) block).canPlaceAt(world, pos, null))
            {
                BlockPos pos2 = pos.offset(context.getFace());
                if (world.isAirBlock(pos2))
                {
                    world.setBlockState(pos2, block.getDefaultState());
                    setFilled(stack, false);
                    setBlockFilled(stack, null);
                    world.playSound(player, pos, SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.BLOCKS, 1.0F, 10.0F);
                    return ActionResultType.SUCCESS;
                }
            }
        }
        return ActionResultType.PASS;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(new StringTextComponent("Right click on an unique plants found planttopia to obtain and store it in the void"));
        tooltip.add(new StringTextComponent("Right click again to get it back"));
        iniTags(stack);
        if (isFilled(stack))
        {
            tooltip.add(new StringTextComponent("Plant Obtained: " + getBlockFilled(stack).toString()).setStyle(new Style().setColor(TextFormatting.GREEN).setBold(true)));
        }
        else
        {
            tooltip.add(new StringTextComponent("Empty").setStyle(new Style().setColor(TextFormatting.GREEN).setBold(true)));
        }
    }

    @Override
    public boolean hasEffect(ItemStack stack)
    {
        return isFilled(stack);
    }

    private ItemStack setBlockFilled(ItemStack stack, BlockState state)
    {
        CompoundNBT compound = stack.getTag();
        if (state != null)
        {
            compound.putString("block", String.valueOf(state.getBlock().getRegistryName()));
            setFilled(stack, true);
        }
        else
        {
            setFilled(stack, false);
        }
        stack.setTag(compound);
        return stack;
    }

    private Block getBlockFilled(ItemStack stack)
    {
        String block = stack.getTag().getString("block");
        if (block.equals(""))
        {
            return null;
        }
        setFilled(stack, true);
        return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(block));
    }

    private ItemStack setFilled(ItemStack stack, boolean filled)
    {
        CompoundNBT compound = stack.hasTag() ? stack.getTag() : new CompoundNBT();
        compound.putBoolean("filled", filled);
        stack.setTag(compound);
        return stack;
    }

    private boolean isFilled(ItemStack stack)
    {
        return stack.getTag().getBoolean("filled") && getBlockFilled(stack) != null;
    }

    private ItemStack iniTags(ItemStack stack)
    {
        CompoundNBT compound = stack.hasTag() ? stack.getTag() : new CompoundNBT();
        if (!compound.contains("filled") && !compound.contains("block"))
        {
            compound.putBoolean("filled", false);
            compound.putString("block", "");
            stack.setTag(compound);
        }
        return stack;
    }
}
