package net.kaneka.planttech2.datagen;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.blocks.Hedge;
import net.kaneka.planttech2.blocks.baseclasses.CustomFenceBlock;
import net.kaneka.planttech2.registries.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockModelGenerator extends BlockStateProvider
{

	public BlockModelGenerator(DataGenerator gen, ExistingFileHelper exFileHelper)
	{
		super(gen, PlantTechMain.MODID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels()
	{
		addHedges();
		
	}
	
	private void addHedges()
	{
		BlockModelBuilder hedge_base = models().getBuilder("block/hedge/base/hedge_base");
		hedge_base.parent(models().getExistingFile(mcLoc("block/block")));
		createCube(hedge_base, 5,5,5,11,16,11, "#leaves", 0);
		createCube(hedge_base, 4,0,4,5,4,5, "#wood");
		createCube(hedge_base, 4,0,11,5,4,12, "#wood");
		createCube(hedge_base, 11,0,11,12,4,12, "#wood");
		createCube(hedge_base, 11,0,4,12,4,5, "#wood");
		createCube(hedge_base, 5,0,5,11,4,11, "#soil", 1);
		
		BlockModelBuilder hedge_adding = models().getBuilder("block/hedge/base/hedge_adding");
		createCube(hedge_adding, 11,5,5,16,16,11, "#leaves", 0);
		createCube(hedge_adding, 12,0,11,16,4,12, "#wood");
		createCube(hedge_adding, 12,0,4,16,4,5, "#wood");
		createCube(hedge_adding, 11,0,5,16,4,11, "#soil", 1);
		
		BlockModelBuilder hedge_none = models().getBuilder("block/hedge/base/hedge_none");
		createCube(hedge_none, 11,0,5,12,4,11, "#wood");
		
		
		String[] types = new String[] {"oak", "spruce", "birch", "jungle", "acacia", "dark_oak"}; 
		
		String[] soils = new String[] {"dirt", "grass_block_top", "podzol_top"}; 
		
		BlockModelBuilder temp; 
		String hedge, woodType, soilType;
		ResourceLocation leavesTexture, woodsTexture, soilTexture;
		
		
		for(String type_1: types)
		{
			for(String type_2: types)
			{
				
				hedge = "block/hedge/";
				woodType = type_1 + "_" + type_2;
				leavesTexture = mcLoc("block/" + type_1 + "_leaves");
				woodsTexture = mcLoc("block/" + type_2 + "_log");
				for(String soil: soils)
				{
					soilType = soil.replace("_block", "").replace("_top", "");
					soilTexture = mcLoc("block/" + soil); 
    				temp = models().getBuilder(hedge + woodType + "_" + soilType + "_base");
    				temp.parent(hedge_base).texture("leaves", leavesTexture)
    										.texture("wood", woodsTexture)
    										.texture("soil", soilTexture)
    										.texture("particle", woodsTexture);
    				
    				temp = models().getBuilder(hedge + woodType + "_" + soil.replace("_block", "").replace("_top", "") + "_adding");
    				temp.parent(hedge_adding).texture("leaves", leavesTexture)
    										.texture("wood", woodsTexture)
    										.texture("soil", soilTexture)
    										.texture("particle", woodsTexture);
				}

				temp = models().getBuilder(hedge + woodType + "_none");
				temp.parent(hedge_none).texture("wood", woodsTexture)
										.texture("particle", woodsTexture);
			}
		}

		
		MultiPartBlockStateBuilder builder; 
		
		for(Hedge b: ModBlocks.HEDGE_BLOCKS)
		{
			builder = getMultipartBuilder(b);
			String s = b.getRegistryName().toString().replace("planttech2:hedge_", "block/hedge/");
			builder.part().modelFile(models().getExistingFile(modLoc(s + "_base"))).addModel(); 
			ModelFile adding = models().getExistingFile(modLoc(s + "_adding"));
			ModelFile none = models().getExistingFile(modLoc(s.replace("_dirt", "").replace("_grass", "").replace("_podzol", "") + "_none"));
			builder.part().modelFile(adding).rotationY(270).addModel().condition(CustomFenceBlock.NORTH, true);
			builder.part().modelFile(none).rotationY(270).addModel().condition(CustomFenceBlock.NORTH, false);
			
			builder.part().modelFile(adding).addModel().condition(CustomFenceBlock.EAST, true);
			builder.part().modelFile(none).addModel().condition(CustomFenceBlock.EAST, false);
			
			builder.part().modelFile(adding).rotationY(90).addModel().condition(CustomFenceBlock.SOUTH, true);
			builder.part().modelFile(none).rotationY(90).addModel().condition(CustomFenceBlock.SOUTH, false);
			
			builder.part().modelFile(adding).rotationY(180).addModel().condition(CustomFenceBlock.WEST, true);
			builder.part().modelFile(none).rotationY(180).addModel().condition(CustomFenceBlock.WEST, false);
		}
		
		
	}
	
	private void createCube(BlockModelBuilder builder, float fx, float fy, float fz, float tx, float ty, float tz, String texture) 
	{
        builder.element().from(fx, fy, fz).to(tx, ty, tz).textureAll(texture).end();
    }
	
	private void createCube(BlockModelBuilder builder, float fx, float fy, float fz, float tx, float ty, float tz, String texture, int tint) 
	{
        builder.element().from(fx, fy, fz).to(tx, ty, tz).allFaces((dir, faceB) -> faceB.texture(texture).tintindex(tint)).end();
    }
	
	/*
	private void createCube(BlockModelBuilder builder, String name ,float fx, float fy, float fz, float tx, float ty, float tz, String tn, String te, String ts, String tw, String tu, String td) 
	{
        ElementBuilder eb = builder.element().from(fx, fy, fz).to(tx, ty, tz);
        eb.face(Direction.NORTH).texture(tn);
        eb.face(Direction.EAST).texture(te);
        eb.face(Direction.SOUTH).texture(ts);
        eb.face(Direction.WEST).texture(tw);
        eb.face(Direction.UP).texture(tu);
        eb.face(Direction.DOWN).texture(td);
        eb.end();
    }
    */
	
	@Override
    public String getName() {
        return " planttech2 block model generator";
    }
	
}
