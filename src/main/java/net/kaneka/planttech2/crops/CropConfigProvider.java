package net.kaneka.planttech2.crops;

import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.kaneka.planttech2.PlantTechMain;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class CropConfigProvider implements IDataProvider
{
	private static final Logger LOGGER = LogManager.getLogger();
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
	private final DataGenerator dataGenerator;

	public CropConfigProvider(DataGenerator generator)
	{
		this.dataGenerator = generator;
	}

	public abstract Map<ResourceLocation, CropEntryConfigData> getCropData();

	@Override
	public void run(DirectoryCache cache)
	{
		Path path = this.dataGenerator.getOutputFolder();
		Map<ResourceLocation, CropEntryConfigData> cropEntryData = this.getCropData();
		this.validate(PlantTechMain.getCropList(), cropEntryData);

		cropEntryData.forEach((key, data) -> {
			Path outputFile = getPath(path, key);
			try
			{
				IDataProvider.save(GSON, cache, CropListReloadListener.toJson(data), outputFile);
			} catch (Exception ioexception)
			{
				LOGGER.error("Couldn't save crop entry configuration {}", outputFile, ioexception);
			}
		});
	}

	protected void validate(CropList list, Map<ResourceLocation, CropEntryConfigData> dataMap)
	{
		final AtomicBoolean errored = new AtomicBoolean(false);
		dataMap.forEach((loc, data) -> {
			if (list.getByName(data.getCropEntryName()) == null)
			{
				LOGGER.fatal("Unknown crop entry {} in {}", data.getCropEntryName(), loc);
				errored.set(true);
			}
			data.getParents().forEach(parentPair -> {
				if (list.getByName(parentPair.getFirstParent()) == null)
				{
					LOGGER.fatal("Unknown first parent crop entry {} in {}: {}", parentPair.getFirstParent(), loc, parentPair);
					errored.set(true);
				}
				if (list.getByName(parentPair.getSecondParent()) == null)
				{
					LOGGER.fatal("Unknown second parent crop entry {} in {}: {}", parentPair.getSecondParent(), loc, parentPair);
					errored.set(true);
				}
			});
		});
		if (errored.get())
		{
			throw new RuntimeException("Unknown crop entries found during validation. Check the log for details");
		}
	}

	private static Path getPath(Path pathIn, ResourceLocation id)
	{
		return pathIn.resolve("data/" + id.getNamespace() + "/" + CropListReloadListener.FOLDER + "/" + id.getPath() + ".json");
	}

	@Override
	public String getName()
	{
		return "Crop Entry Configurations";
	}
}
