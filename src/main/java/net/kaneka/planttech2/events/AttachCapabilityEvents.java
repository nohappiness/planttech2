package net.kaneka.planttech2.events;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.blocks.entity.machine.baseclasses.EnergyInventoryFluidBlockEntity;
import net.kaneka.planttech2.fluids.capability.BiomassFluidEnergy;
import net.kaneka.planttech2.items.BiomassContainerItem;
import net.kaneka.planttech2.registries.ModReferences;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PlantTechMain.MODID)
public class AttachCapabilityEvents
{
//    @SubscribeEvent
//    public static void attachEntityCapability(final AttachCapabilitiesEvent<Entity> event)
//    {
//        if(event.getObject() instanceof PlayerEntity)
//        {
//
//            if (!event.getCapabilities().containsKey(ModReferences.TECHVILLAGERTRUSTCAP))
//            {
//                event.addCapability(ModReferences.TECHVILLAGERTRUSTCAP, new TechVillagerTrust());
//            }
//            if (!event.getCapabilities().containsKey(ModReferences.RADIATIONEFFECTCAP))
//            {
//                event.addCapability(ModReferences.RADIATIONEFFECTCAP, new RadiationEffect());
//            }
//            if (!event.getCapabilities().containsKey(ModReferences.PLAYERRENDERRGBCAP))
//            {
//                event.addCapability(ModReferences.PLAYERRENDERRGBCAP, new PlayerRenderRGB());
//            }
//        }
//    }

    @SubscribeEvent
    public static void attachItemStackCapability(final AttachCapabilitiesEvent<ItemStack> event)
    {
//        add a checking here because somehow it passes a server Level at the end of the event
//      [00:39:00] [Server thread/INFO] [STDOUT/]: [net.kaneka.planttech2.events.AttachCapabilityEvents:attachItemStackCapability:45]: 1 carrot
//      [00:39:11] [Server thread/INFO] [STDOUT/]: [net.kaneka.planttech2.events.AttachCapabilityEvents:attachItemStackCapability:45]: net.minecraft.world.server.ServerWorld@38766f34
        if(event.getObject() instanceof ItemStack)
            if (event.getObject().getItem() instanceof BiomassContainerItem)
                if (!event.getCapabilities().containsKey(ModReferences.BIOMASSFLUIDENERGYCAP))
                    event.addCapability(ModReferences.BIOMASSFLUIDENERGYCAP, new BiomassFluidEnergy());
    }

    @SubscribeEvent
    public static void attachTileEntityCapability(final AttachCapabilitiesEvent<BlockEntity> event)
    {
        if (event.getObject() instanceof EnergyInventoryFluidBlockEntity)
            if (!event.getCapabilities().containsKey(ModReferences.BIOMASSFLUIDENERGYCAP))
                event.addCapability(ModReferences.BIOMASSFLUIDENERGYCAP, new BiomassFluidEnergy());
    }
}
