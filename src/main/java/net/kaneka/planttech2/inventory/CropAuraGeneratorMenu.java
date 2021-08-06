package net.kaneka.planttech2.inventory;

import net.kaneka.planttech2.blocks.entity.machine.CropAuraGeneratorBlockEntity;
import net.kaneka.planttech2.items.AuraCoreItem;
import net.kaneka.planttech2.items.CropSeedItem;
import net.kaneka.planttech2.registries.ModContainers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public class CropAuraGeneratorMenu extends BaseMenu
{
    public CropAuraGeneratorMenu(int id, Inventory inv)
    {
        this(id, inv, new CropAuraGeneratorBlockEntity());
    }

    public CropAuraGeneratorMenu(int id, Inventory player, CropAuraGeneratorBlockEntity tileentity)
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
}
