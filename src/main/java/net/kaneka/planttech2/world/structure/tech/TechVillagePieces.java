package net.kaneka.planttech2.world.structure.tech;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;

import net.kaneka.planttech2.registries.ModReferences;
import net.kaneka.planttech2.registries.ModStructurePieceTypes;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.jigsaw.SingleJigsawPiece;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;


@SuppressWarnings("deprecation")
public class TechVillagePieces
{
	public static void init(ChunkGenerator<?> chunkGenerator, TemplateManager templateManager, BlockPos pos, List<StructurePiece> piecesList, SharedSeedRandom seed) 
	{
		JigsawManager.func_214889_a(new ResourceLocation(ModReferences.MODID,"village/tech/starts"), 7, TechVillagePieces.TechVillage::new, chunkGenerator, templateManager, pos, piecesList, seed);
	}
	
	static {
		
		JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation(ModReferences.MODID,"village/tech/starts" ), new ResourceLocation("empty"), ImmutableList.of(Pair.of(new SingleJigsawPiece(ModReferences.MODID + ":village/tech/starts/street_cross"   ), 1), Pair.of(new SingleJigsawPiece(ModReferences.MODID + ":village/tech/starts/street_cross2" ), 1)), JigsawPattern.PlacementBehaviour.RIGID));  
		JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation(ModReferences.MODID,"village/tech/streets"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(new SingleJigsawPiece(ModReferences.MODID + ":village/tech/streets/street_straight"), 1), Pair.of(new SingleJigsawPiece(ModReferences.MODID + ":village/tech/streets/street_threeway"), 1), Pair.of(new SingleJigsawPiece(ModReferences.MODID + ":village/tech/streets/street_turn"), 1)), JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING));
	   }
	
	public static class TechVillage extends AbstractVillagePiece {
	      public TechVillage(TemplateManager tempManager, JigsawPiece piece, BlockPos pos, int groundLevelDelta, Rotation rotation, MutableBoundingBox mutableBB) {
	         super(ModStructurePieceTypes.TECHVILLAGE, tempManager, piece, pos, groundLevelDelta, rotation, mutableBB);
	      }

	      public TechVillage(TemplateManager tempManager, CompoundNBT nbt) {
	         super(tempManager, nbt, ModStructurePieceTypes.TECHVILLAGE);
	      }
	   }
}
