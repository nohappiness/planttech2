package net.kaneka.planttech2.items;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.blocks.baseclasses.NaturalPlants;
import net.kaneka.planttech2.blocks.interfaces.IObtainable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
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

public class PlantObtainerItem extends BaseItem
{
    public PlantObtainerItem(String name, Properties property)
    {
        super(name, property.maxStackSize(1));
        addPropertyOverride(new ResourceLocation(PlantTechMain.MODID, "filled"), (stack, world, entity) ->
                isFilled(initTags(stack)) ? 1.0F : 0.0F);
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
        initTags(stack);
        if (state.getBlock() instanceof IObtainable)
        {
            IObtainable block = (IObtainable) state.getBlock();
            if (!isFilled(stack) && block.isObtainable(context))
            {
                setBlockFilled(stack, state);
                block.onObtained(world, player, stack, pos);
//                world.playSound(player, pos, SoundEvents.BLOCK_PORTAL_TRAVEL, SoundCategory.BLOCKS, 0.15F, 10.0F);
                return ActionResultType.SUCCESS;
            }
        }
        else if (isFilled(stack))
        {
            IObtainable block = (IObtainable) getBlockStateFilled(stack).getBlock();
            if (((NaturalPlants) block).canPlaceAt(world, pos.offset(context.getFace())))
            {
                block.onReleased(context, getBlockStateFilled(stack));
                setBlockFilled(stack, null);
                setFilled(stack, false);
//                world.playSound(player, pos, SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.BLOCKS, 1.0F, 10.0F);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }

    public static ItemStack setBlockFilled(ItemStack stack, BlockState state)
    {
        CompoundNBT compound = stack.hasTag() ? stack.getTag() : initTags(stack).getTag();
        if (state == Blocks.AIR.getDefaultState() || state != null)
        {
            compound.merge(NBTUtil.writeBlockState(state));
            setFilled(stack, true);
        }
        else
        {
            NBTUtil.writeBlockState(Blocks.AIR.getDefaultState());
            setFilled(stack, false);
        }
        stack.setTag(compound);
        return stack;
    }

    public static BlockState getBlockStateFilled(ItemStack stack)
    {
        CompoundNBT compound = stack.hasTag() ? stack.getTag() : initTags(stack).getTag();
        BlockState state = NBTUtil.readBlockState(compound);
        if (state.getBlock() == Blocks.AIR)
        {
            return null;
        }
        setFilled(stack, true);
        return state;
    }

    public static ItemStack setFilled(ItemStack stack, boolean filled)
    {
        CompoundNBT compound = stack.hasTag() ? stack.getTag() : initTags(stack).getTag();
        if (!filled)
        {
            compound.merge(NBTUtil.writeBlockState(Blocks.AIR.getDefaultState()));
        }
        compound.putBoolean("filled", filled);
        stack.setTag(compound);
        return stack;
    }

    public static boolean isFilled(ItemStack stack)
    {
        return stack.getTag().getBoolean("filled") && getBlockStateFilled(stack) != null;
    }

    public static ItemStack initTags(ItemStack stack)
    {
        CompoundNBT compound = stack.hasTag() ? stack.getTag() : new CompoundNBT();
        stack.setTag(compound);
        return stack;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(new StringTextComponent("Right click on an unique plants found planttopia to obtain and store it in the void"));
        tooltip.add(new StringTextComponent("Right click again to get it back"));
        initTags(stack);
        if (isFilled(stack))
        {
            tooltip.add(new StringTextComponent("Plant Obtained: " + getBlockStateFilled(stack).getBlock().getNameTextComponent().setStyle(new Style().setColor(TextFormatting.GREEN).setBold(true)).getFormattedText()));
        }
        else
        {
            tooltip.add(new StringTextComponent("Empty").setStyle(new Style().setColor(TextFormatting.GREEN).setBold(true)));
        }
    }
}
