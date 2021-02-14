package net.kaneka.planttech2.container;

import net.kaneka.planttech2.items.CropSeedItem;
import net.kaneka.planttech2.registries.ModContainers;
import net.kaneka.planttech2.tileentity.machine.CropAuraGeneratorTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.concurrent.atomic.AtomicInteger;

public class CropAuraGeneratorContainer extends BaseContainer
{
    public CropAuraGeneratorContainer(int id, PlayerInventory inv)
    {
        this(id, inv, new CropAuraGeneratorTileEntity());
    }

    public CropAuraGeneratorContainer(int id, PlayerInventory player, CropAuraGeneratorTileEntity tileentity)
    {
        super(id, ModContainers.CROPAURAGENERATOR, player, tileentity, 6);
        IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new);
        AtomicInteger index = new AtomicInteger(0);
        AtomicInteger x = new AtomicInteger(50);
        int y = 56;
        addSlot(new LimitedItemInfoSlot(handler, index.getAndIncrement(), x.getAndAdd(18), y, "slot.crop_aura_generator.chipinput_0"));
        addSlot(new LimitedItemInfoSlot(handler, index.getAndIncrement(), x.getAndAdd(18), y, "slot.crop_aura_generator.chipinput_1"));
        addSlot(new LimitedItemInfoSlot(handler, index.getAndIncrement(), x.getAndAdd(18), y, "slot.crop_aura_generator.chipinput_2"));
        addSlot(new LimitedItemInfoSlot(handler, index.getAndIncrement(), x.getAndAdd(18), y, "slot.crop_aura_generator.chipinput_3"));
        addSlot(new LimitedItemInfoSlot(handler, index.getAndIncrement(), x.getAndAdd(18), y, "slot.crop_aura_generator.chipinput_4"));
        addSlot(new LimitedItemInfoSlot(handler, index.getAndIncrement(), x.getAndAdd(18), y, "slot.crop_aura_generator.chipinput_5"));
        addSlot(createKnowledgeChipSlot(handler, 12, 19));
        addSlot(createEnergyInSlot(handler, 167, 38));
        addSlot(createEnergyOutSlot(handler,167, 57));
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index)
    {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(index);
        if(slot != null && slot.getHasStack())
        {
            ItemStack stack1 = slot.getStack();
            stack = stack1.copy();

            if(index > 35)
            {
                if (!this.mergeItemStack(stack1, 0, 34, true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(index < 36)
            {
                if(!this.mergeItemStack(stack1, 36, 37, false) && stack1.getItem() instanceof CropSeedItem)
                {
                    return ItemStack.EMPTY;
                }
                else if(index >= 0 && index < 27)
                {
                    if(!this.mergeItemStack(stack1, 27, 35, false)) return ItemStack.EMPTY;
                }
                else if(index >= 27 && index < 36 && !this.mergeItemStack(stack1, 0, 26, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(!this.mergeItemStack(stack1, 0, 35, false))
            {
                return ItemStack.EMPTY;
            }


            if(stack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();

            }
            if(stack1.getCount() == stack.getCount()) return ItemStack.EMPTY;
            slot.onTake(playerIn, stack1);
        }
        return stack;
    }
}
