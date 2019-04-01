package net.kaneka.planttech2.rendering.cable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.blocks.machines.BlockCable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.property.IExtendedBlockState;

public class CompositeModel implements IBakedModel
{
    private IBakedModel modelCore;
    private IBakedModel[] modelUp, modelDown, modelWest, modelEast, modelNorth, modelSouth;

    public CompositeModel(IBakedModel modelCore, IBakedModel[] modelUp, IBakedModel[] modelDown, IBakedModel[] modelWest, IBakedModel[] modelEast, IBakedModel[] modelNorth, IBakedModel[] modelSouth)
    {
	this.modelCore = modelCore;
	this.modelUp = modelUp;
	this.modelDown = modelDown;
	this.modelWest = modelWest;
	this.modelEast = modelEast;
	this.modelNorth = modelNorth;
	this.modelSouth = modelSouth;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, Random rand)
    {
	List<BakedQuad> quadsList = new ArrayList<BakedQuad>();

	quadsList.addAll(modelCore.getQuads(state, side, rand));

	if (!(state instanceof IExtendedBlockState))
	{
	    return quadsList;
	}

	IExtendedBlockState extState = (IExtendedBlockState) state;
	int up = 1, down = 1, east = 1, west = 1, north = 1, south = 1;
	if (extState.getValue(BlockCable.UP) != null)
	{
	    up = extState.getValue(BlockCable.UP);
	}
	if (0 < up && up < 6)
	{
	    quadsList.addAll(modelUp[up - 1].getQuads(extState, side, rand));
	}

	if (extState.getValue(BlockCable.DOWN) != null)
	{
	    down = extState.getValue(BlockCable.DOWN);
	}
	if (0 < down && down < 6)
	{
	    quadsList.addAll(modelDown[down - 1].getQuads(extState, side, rand));
	}

	if (extState.getValue(BlockCable.EAST) != null)
	{
	    east = extState.getValue(BlockCable.EAST);
	}
	if (0 < east && east < 6)
	{
	    quadsList.addAll(modelEast[east - 1].getQuads(extState, side, rand));
	}

	if (extState.getValue(BlockCable.WEST) != null)
	{
	    west = extState.getValue(BlockCable.WEST);
	}
	if (0 < west && west < 6)
	{
	    quadsList.addAll(modelWest[west - 1].getQuads(extState, side, rand));
	}

	if (extState.getValue(BlockCable.NORTH) != null)
	{
	    north = extState.getValue(BlockCable.NORTH);
	}
	if (0 < north && north < 6)
	{
	    quadsList.addAll(modelNorth[north - 1].getQuads(extState, side, rand));
	}

	if (extState.getValue(BlockCable.SOUTH) != null)
	{
	    south = extState.getValue(BlockCable.SOUTH);
	}
	if (0 < south && south < 6)
	{
	    quadsList.addAll(modelSouth[south - 1].getQuads(extState, side, rand));
	}

	return quadsList;
    }

    @Override
    public boolean isAmbientOcclusion()
    {
	return modelCore.isAmbientOcclusion();
    }

    @Override
    public boolean isGui3d()
    {
	return modelCore.isGui3d();
    }

    @Override
    public boolean isBuiltInRenderer()
    {
	return false;
    }

    @Override
    public TextureAtlasSprite getParticleTexture()
    {
	return Minecraft.getInstance().getTextureMap().getAtlasSprite("planttech2:blocks/cables/cable");
    }

    @Override
    public ItemOverrideList getOverrides()
    {
	return null;
    }
}
