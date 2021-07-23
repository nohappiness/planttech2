package net.kaneka.planttech2.inventory;

import net.kaneka.planttech2.blocks.entity.machine.CropAuraGeneratorBlockEntity;
import net.kaneka.planttech2.items.AuraCoreItem;
import net.kaneka.planttech2.items.CropSeedItem;
import net.kaneka.planttech2.registries.ModContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Slot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public class CropAuraGeneratorContainer extends BaseContainer
{
    public CropAuraGeneratorContainer(int id, Inventory inv)
    {
        this(id, inv, new CropAuraGeneratorBlockEntity());
    }

    public CropAuraGeneratorContainer(int id, Inventory player, CropAuraGeneratorBlockEntity tileentity)
    {
        super(id, ModContainers.CROPAURAGENERATOR, player, tileentity, 6);
        IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new);
        int index = 0;
        AtomicInteger x = new AtomicInteger(29);
        index += createSlot(handler, index, x, AuraCoreItem::doesModifyTemperature);
        index += createSlot(handler, index, x, AuraCoreItem::doesModifyLightValue);
        index += createSlot(handler, index, x, AuraCoreItem::doesModifyWaterRange);
        index += createSlot(handler, index, x, AuraCoreItem::doesModifySoil);
        index += createSlot(handler, index, x, AuraCoreItem::doesModifyFertility);
        index += createSlot(handler, index, x, AuraCoreItem::doesModifyProductivity);
        addSlot(createKnowledgeChipSlot(handler, 12, 9));
        addSlot(createEnergyInSlot(handler, 167, 38));
        addSlot(createEnergyOutSlot(handler,167, 57));
    }

    private int createSlot(IItemHandler handler, int index, AtomicInteger x, Predicate<ItemStack> fitCore)
    {
        addSlot(new LimitedItemInfoSlot(handler, index, x.getAndAdd(18), 60, "slot.crop_aura_generator.chipinput_" + index).setConditions((stack) -> stack.getItem() instanceof AuraCoreItem && fitCore.test(stack)).setShouldListen());
        return 1;
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index)
    {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = (Slot)this.slots.get(index);
        if(slot != null && slot.hasItem())
        {
            ItemStack stack1 = slot.getItem();
            stack = stack1.copy();

            if(index > 35)
            {
                if (!this.moveItemStackTo(stack1, 0, 34, true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(index < 36)
            {
                if(!this.moveItemStackTo(stack1, 36, 37, false) && stack1.getItem() instanceof CropSeedItem)
                {
                    return ItemStack.EMPTY;
                }
                else if(index >= 0 && index < 27)
                {
                    if(!this.moveItemStackTo(stack1, 27, 35, false)) return ItemStack.EMPTY;
                }
                else if(index >= 27 && index < 36 && !this.moveItemStackTo(stack1, 0, 26, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(!this.moveItemStackTo(stack1, 0, 35, false))
            {
                return ItemStack.EMPTY;
            }


            if(stack1.isEmpty())
            {
                slot.set(ItemStack.EMPTY);
            }
            else
            {
                slot.setChanged();

            }
            if(stack1.getCount() == stack.getCount()) return ItemStack.EMPTY;
            slot.onTake(playerIn, stack1);
        }
        return stack;
    }
}
