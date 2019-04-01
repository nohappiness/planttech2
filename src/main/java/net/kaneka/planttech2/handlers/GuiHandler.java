package net.kaneka.planttech2.handlers;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.container.ContainerCompressor;
import net.kaneka.planttech2.container.ContainerDNACleaner;
import net.kaneka.planttech2.container.ContainerDNACombiner;
import net.kaneka.planttech2.container.ContainerDNAExtractor;
import net.kaneka.planttech2.container.ContainerDNARemover;
import net.kaneka.planttech2.container.ContainerIdentifier;
import net.kaneka.planttech2.container.ContainerMegaFurnace;
import net.kaneka.planttech2.container.ContainerPlantFarm;
import net.kaneka.planttech2.container.ContainerSeedSqueezer;
import net.kaneka.planttech2.container.ContainerSeedconstructor;
import net.kaneka.planttech2.container.ContainerSolarGenerator;
import net.kaneka.planttech2.gui.GuiGuideOverview;
import net.kaneka.planttech2.gui.GUICompressor;
import net.kaneka.planttech2.gui.GUIDNACleaner;
import net.kaneka.planttech2.gui.GUIDNACombiner;
import net.kaneka.planttech2.gui.GUIDNAExtractor;
import net.kaneka.planttech2.gui.GUIDNARemover;
import net.kaneka.planttech2.gui.GUIIdentifier;
import net.kaneka.planttech2.gui.GUIMegaFurnace;
import net.kaneka.planttech2.gui.GuiGuidePlants;
import net.kaneka.planttech2.gui.GUIPlantFarm;
import net.kaneka.planttech2.gui.GUIReferences;
import net.kaneka.planttech2.gui.GUISeedSqueezer;
import net.kaneka.planttech2.gui.GUISeedconstructor;
import net.kaneka.planttech2.gui.GUISolarGenerator;
import net.kaneka.planttech2.gui.GuiGuideGeneticEngineering;
import net.kaneka.planttech2.proxy.ClientProxy;
import net.kaneka.planttech2.tileentity.machine.TileEntityCompressor;
import net.kaneka.planttech2.tileentity.machine.TileEntityDNACleaner;
import net.kaneka.planttech2.tileentity.machine.TileEntityDNACombiner;
import net.kaneka.planttech2.tileentity.machine.TileEntityDNAExtractor;
import net.kaneka.planttech2.tileentity.machine.TileEntityDNARemover;
import net.kaneka.planttech2.tileentity.machine.TileEntityIdentifier;
import net.kaneka.planttech2.tileentity.machine.TileEntityMegaFurnace;
import net.kaneka.planttech2.tileentity.machine.TileEntityPlantFarm;
import net.kaneka.planttech2.tileentity.machine.TileEntitySeedSqueezer;
import net.kaneka.planttech2.tileentity.machine.TileEntitySeedconstructor;
import net.kaneka.planttech2.tileentity.machine.TileEntitySolarGenerator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.network.FMLPlayMessages;

public class GuiHandler
{
    public static GuiScreen openGui(FMLPlayMessages.OpenContainer msg)
    {
	EntityPlayer player = Minecraft.getInstance().player;
	String path = msg.getId().getPath();
	if (path.equals(GUIReferences.GUI_COMPRESSOR))
	{
	    BlockPos pos = msg.getAdditionalData().readBlockPos();
	    TileEntity te = Minecraft.getInstance().world.getTileEntity(pos);
	    if(te instanceof TileEntityCompressor)
	    {
		return new GUICompressor(player.inventory, (TileEntityCompressor) te);
	    }
	}
	else if (path.equals(GUIReferences.GUI_DNA_CLEANER))
	{
	    BlockPos pos = msg.getAdditionalData().readBlockPos();
	    TileEntity te = Minecraft.getInstance().world.getTileEntity(pos);
	    if(te instanceof TileEntityDNACleaner)
	    {
		return new GUIDNACleaner(player.inventory, (TileEntityDNACleaner) te);
	    }
	}
	else if (path.equals(GUIReferences.GUI_DNA_COMBINER))
	{
	    BlockPos pos = msg.getAdditionalData().readBlockPos();
	    TileEntity te = Minecraft.getInstance().world.getTileEntity(pos);
	    if(te instanceof TileEntityDNACombiner)
	    {
		return new GUIDNACombiner(player.inventory, (TileEntityDNACombiner) te);
	    }
	}
	else if (path.equals(GUIReferences.GUI_DNA_EXTRACTOR))
	{
	    BlockPos pos = msg.getAdditionalData().readBlockPos();
	    TileEntity te = Minecraft.getInstance().world.getTileEntity(pos);
	    if(te instanceof TileEntityDNAExtractor)
	    {
		return new GUIDNAExtractor(player.inventory, (TileEntityDNAExtractor) te);
	    }
	}
	else if (path.equals(GUIReferences.GUI_DNA_REMOVER))
	{
	    BlockPos pos = msg.getAdditionalData().readBlockPos();
	    TileEntity te = Minecraft.getInstance().world.getTileEntity(pos);
	    if(te instanceof TileEntityDNARemover)
	    {
		return new GUIDNARemover(player.inventory, (TileEntityDNARemover) te);
	    }
	}
	else if (path.equals(GUIReferences.GUI_IDENTIFIER))
	{
	    BlockPos pos = msg.getAdditionalData().readBlockPos();
	    TileEntity te = Minecraft.getInstance().world.getTileEntity(pos);
	    if(te instanceof TileEntityIdentifier)
	    {
		return new GUIIdentifier(player.inventory, (TileEntityIdentifier) te);
	    }
	}
	else if (path.equals(GUIReferences.GUI_MEGA_FURNACE))
	{
	    BlockPos pos = msg.getAdditionalData().readBlockPos();
	    TileEntity te = Minecraft.getInstance().world.getTileEntity(pos);
	    if(te instanceof TileEntityMegaFurnace)
	    {
		return new GUIMegaFurnace(player.inventory, (TileEntityMegaFurnace) te);
	    }
	}
	else if (path.equals(GUIReferences.GUI_PLANTFARM))
	{
	    BlockPos pos = msg.getAdditionalData().readBlockPos();
	    TileEntity te = Minecraft.getInstance().world.getTileEntity(pos);
	    if(te instanceof TileEntityPlantFarm)
	    {
		return new GUIPlantFarm(player.inventory, (TileEntityPlantFarm) te);
	    }
	}
	else if (path.equals(GUIReferences.GUI_SEEDCONSTRUCTOR))
	{
	    BlockPos pos = msg.getAdditionalData().readBlockPos();
	    TileEntity te = Minecraft.getInstance().world.getTileEntity(pos);
	    if(te instanceof TileEntitySeedconstructor)
	    {
		return new GUISeedconstructor(player.inventory, (TileEntitySeedconstructor) te);
	    }
	}
	else if (path.equals(GUIReferences.GUI_SEEDSQUEEZER))
	{
	    BlockPos pos = msg.getAdditionalData().readBlockPos();
	    TileEntity te = Minecraft.getInstance().world.getTileEntity(pos);
	    if(te instanceof TileEntitySeedSqueezer)
	    {
		return new GUISeedSqueezer(player.inventory, (TileEntitySeedSqueezer) te);
	    }
	}
	else if (path.equals(GUIReferences.GUI_SOLARGENERATOR))
	{
	    BlockPos pos = msg.getAdditionalData().readBlockPos();
	    TileEntity te = Minecraft.getInstance().world.getTileEntity(pos);
	    if(te instanceof TileEntitySolarGenerator)
	    {
		return new GUISolarGenerator(player.inventory, (TileEntitySolarGenerator) te);
	    }
	}
	else 
	{
	    PlantTechMain.LOGGER.info("Unknown guiID:" + path);
	}
	
	
	
	return null;
    }
}
