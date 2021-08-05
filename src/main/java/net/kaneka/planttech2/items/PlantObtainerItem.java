package net.kaneka.planttech2.items;

import net.kaneka.planttech2.blocks.baseclasses.NaturalPlants;
import net.kaneka.planttech2.blocks.interfaces.IObtainable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class PlantObtainerItem extends Item
{
    public PlantObtainerItem(Properties property)
    {
        super(property.stacksTo(1));
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        Player player = context.getPlayer();
        if (player == null)
        {
            return InteractionResult.FAIL;
        }
        ItemStack stack = player.getMainHandItem();
        initTags(stack);
        if (state.getBlock() instanceof IObtainable block)
        {
            if (!isFilled(stack) && block.isObtainable(context))
            {
                setBlockFilled(stack, block.getObtainedBlockState(state));
                block.onObtained(level, player, stack, pos);
//                world.playSound(player, pos, SoundEvents.BLOCK_PORTAL_TRAVEL, SoundCategory.BLOCKS, 0.15F, 10.0F);
                return InteractionResult.SUCCESS;
            }
        }
        else if (isFilled(stack))
        {
            IObtainable block = (IObtainable) getBlockStateFilled(stack).getBlock();
            if (((NaturalPlants) block).canPlaceAt(level, pos.relative(context.getClickedFace())))
            {
                block.onReleased(context, getBlockStateFilled(stack));
                setBlockFilled(stack, null);
                setFilled(stack, false);
//                world.playSound(player, pos, SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.BLOCKS, 1.0F, 10.0F);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    public static ItemStack setBlockFilled(ItemStack stack, BlockState state)
    {
        CompoundTag compound = stack.hasTag() ? stack.getTag() : initTags(stack).getTag();
        if (state == Blocks.AIR.defaultBlockState() || state != null)
        {
            compound.merge(NbtUtils.writeBlockState(state));
            setFilled(stack, true);
        }
        else
        {
            NbtUtils.writeBlockState(Blocks.AIR.defaultBlockState());
            setFilled(stack, false);
        }
        stack.setTag(compound);
        return stack;
    }

    public static BlockState getBlockStateFilled(ItemStack stack)
    {
        CompoundTag compound = stack.hasTag() ? stack.getTag() : initTags(stack).getTag();
        BlockState state = NbtUtils.readBlockState(compound);
        if (state.getBlock() == Blocks.AIR)
        {
            return null;
        }
        setFilled(stack, true);
        return state;
    }

    public static ItemStack setFilled(ItemStack stack, boolean filled)
    {
        CompoundTag compound = stack.hasTag() ? stack.getTag() : initTags(stack).getTag();
        if (!filled)
        {
            compound.merge(NbtUtils.writeBlockState(Blocks.AIR.defaultBlockState()));
        }
        compound.putBoolean("filled", filled);
        stack.setTag(compound);
        return stack;
    }

    public static boolean isFilled(ItemStack stack)
    {
        return stack.getTag() != null && stack.getTag().getBoolean("filled") && getBlockStateFilled(stack) != null;
    }

    public static ItemStack initTags(ItemStack stack)
    {
        CompoundTag compound = stack.hasTag() ? stack.getTag() : new CompoundTag();
        stack.setTag(compound);
        return stack;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flagIn)
    {
        tooltip.add(new TextComponent("Right click on an unique plants found planttopia to obtain and store it in the void"));
        tooltip.add(new TextComponent("Right click again to get it back"));
        initTags(stack);
        if (isFilled(stack))
            tooltip.add(new TextComponent("Plant Obtained: ").append(getBlockStateFilled(stack).getBlock().getName().setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN).withBold(true))));
        else
            tooltip.add(new TextComponent("Empty").setStyle(Style.EMPTY.withColor(TextColor.parseColor("BLUE")).withBold(true)));
    }
}
