package net.kaneka.planttech2.packets;

import net.kaneka.planttech2.fluids.capability.BiomassFluidEnergy;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncBiomassFluidEnergyMessage
{
    private final ItemStack stack;

    public SyncBiomassFluidEnergyMessage(ItemStack stack)
    {
        this.stack = stack;
    }

    public static void encode(SyncBiomassFluidEnergyMessage message, PacketBuffer buffer)
    {
        buffer.writeItemStack(message.stack);
    }

    public static SyncBiomassFluidEnergyMessage decode(PacketBuffer buffer)
    {
        return new SyncBiomassFluidEnergyMessage(buffer.readItemStack());
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
