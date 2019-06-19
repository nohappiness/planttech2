package net.kaneka.planttech2.handlers;


public class GuiHandler
{
	/*
	public static Screen openGui(FMLPlayMessages.OpenContainer msg)
	{
		PlayerEntity player = Minecraft.getInstance().player;
		ContainerType<?> container = msg.getType();
		int windowId = msg.getWindowId();
		
		if (container.equals(GUIReferences.GUI_COMPRESSOR))
		{
			BlockPos pos = msg.getAdditionalData().readBlockPos();
			TileEntity te = Minecraft.getInstance().world.getTileEntity(pos);
			if (te instanceof TileEntityCompressor)
			{
				return new GUICompressor(windowId, player.inventory, (TileEntityCompressor) te);
			}
		} else if (container.equals(GUIReferences.GUI_DNA_CLEANER))
		{
			BlockPos pos = msg.getAdditionalData().readBlockPos();
			TileEntity te = Minecraft.getInstance().world.getTileEntity(pos);
			if (te instanceof TileEntityDNACleaner)
			{
				return new GUIDNACleaner(windowId, player.inventory, (TileEntityDNACleaner) te);
			}
		} else if (container.equals(GUIReferences.GUI_DNA_COMBINER))
		{
			BlockPos pos = msg.getAdditionalData().readBlockPos();
			TileEntity te = Minecraft.getInstance().world.getTileEntity(pos);
			if (te instanceof TileEntityDNACombiner)
			{
				return new GUIDNACombiner(windowId, player.inventory, (TileEntityDNACombiner) te);
			}
		} else if (container.equals(GUIReferences.GUI_DNA_EXTRACTOR))
		{
			BlockPos pos = msg.getAdditionalData().readBlockPos();
			TileEntity te = Minecraft.getInstance().world.getTileEntity(pos);
			if (te instanceof TileEntityDNAExtractor)
			{
				return new GUIDNAExtractor(windowId, player.inventory, (TileEntityDNAExtractor) te);
			}
		} else if (container.equals(GUIReferences.GUI_DNA_REMOVER))
		{
			BlockPos pos = msg.getAdditionalData().readBlockPos();
			TileEntity te = Minecraft.getInstance().world.getTileEntity(pos);
			if (te instanceof TileEntityDNARemover)
			{
				return new GUIDNARemover(windowId, player.inventory, (TileEntityDNARemover) te);
			}
		} else if (container.equals(GUIReferences.GUI_IDENTIFIER))
		{
			BlockPos pos = msg.getAdditionalData().readBlockPos();
			TileEntity te = Minecraft.getInstance().world.getTileEntity(pos);
			if (te instanceof TileEntityIdentifier)
			{
				return new GUIIdentifier(windowId, player.inventory, (TileEntityIdentifier) te);
			}
		} else if (container.equals(GUIReferences.GUI_MEGA_FURNACE))
		{
			BlockPos pos = msg.getAdditionalData().readBlockPos();
			TileEntity te = Minecraft.getInstance().world.getTileEntity(pos);
			if (te instanceof TileEntityMegaFurnace)
			{
				return new GUIMegaFurnace(windowId, player.inventory, (TileEntityMegaFurnace) te);
			}
		} else if (container.equals(GUIReferences.GUI_PLANTFARM))
		{
			BlockPos pos = msg.getAdditionalData().readBlockPos();
			TileEntity te = Minecraft.getInstance().world.getTileEntity(pos);
			if (te instanceof TileEntityPlantFarm)
			{
				return new GUIPlantFarm(windowId, player.inventory, (TileEntityPlantFarm) te);
			}
		} else if (container.equals(GUIReferences.GUI_SEEDCONSTRUCTOR))
		{
			BlockPos pos = msg.getAdditionalData().readBlockPos();
			TileEntity te = Minecraft.getInstance().world.getTileEntity(pos);
			if (te instanceof TileEntitySeedconstructor)
			{
				return new GUISeedconstructor(windowId, player.inventory, (TileEntitySeedconstructor) te);
			}
		} else if (container.equals(GUIReferences.GUI_SEEDSQUEEZER))
		{
			BlockPos pos = msg.getAdditionalData().readBlockPos();
			TileEntity te = Minecraft.getInstance().world.getTileEntity(pos);
			if (te instanceof TileEntitySeedSqueezer)
			{
				return new GUISeedSqueezer(windowId, player.inventory, (TileEntitySeedSqueezer) te);
			}
		} else if (container.equals(GUIReferences.GUI_SOLARGENERATOR))
		{
			BlockPos pos = msg.getAdditionalData().readBlockPos();
			TileEntity te = Minecraft.getInstance().world.getTileEntity(pos);
			if (te instanceof TileEntitySolarGenerator)
			{
				return new GUISolarGenerator(windowId, player.inventory, (TileEntitySolarGenerator) te);
			}
		} else if (container.equals(GUIReferences.GUI_ENERGYSTORAGE))
		{
			BlockPos pos = msg.getAdditionalData().readBlockPos();
			TileEntity te = Minecraft.getInstance().world.getTileEntity(pos);
			if (te instanceof TileEntityEnergyStorage)
			{
				return new GUIEnergyStorage(windowId, player.inventory, (TileEntityEnergyStorage) te);
			}
		} else if (container.equals(GUIReferences.GUI_INFUSER))
		{
			BlockPos pos = msg.getAdditionalData().readBlockPos();
			TileEntity te = Minecraft.getInstance().world.getTileEntity(pos);
			if (te instanceof TileEntityInfuser)
			{
				return new GUIInfuser(windowId, player.inventory, (TileEntityInfuser) te);
			}
		} else if (container.equals(GUIReferences.GUI_ITEMUPGRADEABLE))
		{
			ItemStack stack = msg.getAdditionalData().readItemStack();
			if (stack != null)
			{
				if (stack.getItem() instanceof IUpgradeable)
				{
					return new GuiItemUpgradeable(windowId, player.inventory, stack);
				}
			}
		} else
		{
			PlantTechMain.LOGGER.info("Unknown guiID:" + container);
		}

		return null;
	}
	*/
}
