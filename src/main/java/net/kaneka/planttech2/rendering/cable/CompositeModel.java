package net.kaneka.planttech2.rendering.cable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.data.IModelData;

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
    public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, Random rand, IModelData extraData)
    {
    
	List<BakedQuad> quadsList = new ArrayList<BakedQuad>();

	quadsList.addAll(modelCore.getQuads(state, side, rand, extraData));


	int up = 1, down = 1, east = 1, west = 1, north = 1, south = 1;
	if (extraData.getData(CableModel.DATA_UP) != null)
	{
	    up = extraData.getData(CableModel.DATA_UP);
	}
	if (0 < up && up < 6)
	{
	    quadsList.addAll(modelUp[up - 1].getQuads(state, side, rand, extraData));
	}

	if (extraData.getData(CableModel.DATA_DOWN) != null)
	{
	    down = extraData.getData(CableModel.DATA_DOWN);
	}
	if (0 < down && down < 6)
	{
	    quadsList.addAll(modelDown[down - 1].getQuads(state, side, rand, extraData));
	}

	if (extraData.getData(CableModel.DATA_EAST) != null)
	{
	    east = extraData.getData(CableModel.DATA_EAST);
	}
	if (0 < east && east < 6)
	{
	    quadsList.addAll(modelEast[east - 1].getQuads(state, side, rand, extraData));
	}

	if (extraData.getData(CableModel.DATA_WEST) != null)
	{
	    west = extraData.getData(CableModel.DATA_WEST);
	}
	if (0 < west && west < 6)
	{
	    quadsList.addAll(modelWest[west - 1].getQuads(state, side, rand, extraData));
	}

	if (extraData.getData(CableModel.DATA_NORTH) != null)
	{
	    north = extraData.getData(CableModel.DATA_NORTH);
	}
	if (0 < north && north < 6)
	{
	    quadsList.addAll(modelNorth[north - 1].getQuads(state, side, rand, extraData));
	}

	if (extraData.getData(CableModel.DATA_SOUTH) != null)
	{
	    south = extraData.getData(CableModel.DATA_SOUTH);
	}
	if (0 < south && south < 6)
	{
	    quadsList.addAll(modelSouth[south - 1].getQuads(state, side, rand, extraData));
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

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, Random rand)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
