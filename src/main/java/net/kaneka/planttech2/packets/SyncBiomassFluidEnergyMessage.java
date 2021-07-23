package net.kaneka.planttech2.packets;

import net.kaneka.planttech2.fluids.capability.BiomassFluidEnergy;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncBiomassFluidEnergyMessage
{
    private final ItemStack stack;

    public SyncBiomassFluidEnergyMessage(ItemStack stack)
    {
        this.stack = stack;
    }

    public static void encode(SyncBiomassFluidEnergyMessage message, FriendlyByteBuf buffer)
    {
        buffer.writeItem(message.stack);
    }

    public static SyncBiomassFluidEnergyMessage decode(FriendlyByteBuf buffer)
    {
        return new SyncBiomassFluidEnergyMessage(buffer.readItem());
    }

    public static void handle(SyncBiomassFluidEnergyMessage message, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> {
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.isAlive())
            {
                BiomassFluidEnergy.getItemStackCap(message.stack).setCurrentStorage(BiomassFluidEnergy.getItemStackCap(message.stack).getCurrentStorage());
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
