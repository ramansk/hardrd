package com.crux.hardrd.terrains;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.crux.hardrd.controller.MapResource;
import com.crux.hardrd.models.RawModel;
import com.crux.hardrd.test.Loader;
import com.crux.hardrd.textures.TerrainTexture;
import com.crux.hardrd.textures.TerrainTexturePack;
import com.crux.hardrd.toolbox.Maths;

public class Terrain {
	public static final float SIZE = 1800;
	private static final float MAX_HEIGHT = 40;
	private static final float MAX_PIXEL_COLOUR = 256*256*256;
	
	private int row;
	private int col;
	
	public int getRow() {
		return row;
	}


	public void setRow(int row) {
		this.row = row;
	}


	public int getCol() {
		return col;
	}


	public void setCol(int col) {
		this.col = col;
	}

	private float x;
	private float z;
	private RawModel model;
	private TerrainTexturePack texturePack;
	private TerrainTexture blendMap;
	private float [][] hights = null;
	public Terrain(float x, float z, Loader loader, TerrainTexturePack texture, TerrainTexture blendMap, String imageName) {
		this.texturePack = texture;
		this.blendMap = blendMap;
		this.x = x;
		this.z = z;
		this.model = generateTerrain(loader, imageName);
	}
	
	
	public Terrain(float x, float z, Loader loader, TerrainTexturePack texture, TerrainTexture blendMap,
			MapResource map) {
		this.col = map.getCol();
		this.row = map.getRow();
		this.texturePack = texture;
		this.blendMap = blendMap;
		this.x = x;
		this.z = z;
		this.hights = map.getHeights();
		this.model = loader.loadToVAO(toPrimitiveFloat(map.getVertices()), toPrimitiveFloat(map.getTextureCoords()),
				toPrimitiveFloat(map.getNormals()), toPrimitiveInt(map.getIndices()));
	}
	
	float[] toPrimitiveFloat(Float[] f) {
		float[] fr = new float[f.length];
		for(int i = 0; i <f.length; i++)
		{
			fr[i] = f[i];
		}

	    return fr;
	}
	
	int[] toPrimitiveInt(Integer[] intArray) {
		int[] fr = new int[intArray.length];
		for(int i = 0; i <intArray.length; i++)
		{
			fr[i] = intArray[i];
		}

	    return fr;
	}
	
	public TerrainTexturePack getTexturePack() {
		return texturePack;
	}

	public void setTexturePack(TerrainTexturePack texturePack) {
		this.texturePack = texturePack;
	}

	public TerrainTexture getBlendMap() {
		return blendMap;
	}

	public void setBlendMap(TerrainTexture blendMap) {
		this.blendMap = blendMap;
	}
	
	public float getHeightOfTerrain(float worldX, float worldZ)
	{
		float terrainX = worldX - this.x;
		float terrainZ = worldZ - this.z;
		float gridSquareSize = SIZE / ((float)hights.length - 1);
		
		int gridX = (int) Math.floor(terrainX / gridSquareSize);
		int gridZ = (int) Math.floor(terrainZ / gridSquareSize);
		
		if(gridX >= hights.length -1 || gridZ >= hights.length -1 || gridX < 0|| gridZ <0)
		{
			return 0;
		}
		
		float xCoord = (terrainX % gridSquareSize)/gridSquareSize;
		float zCoord = (terrainZ % gridSquareSize)/gridSquareSize;
		float answer;
		if (xCoord <= (1-zCoord)) {
			answer = Maths
					.barryCentric(new Vector3f(0, hights[gridX][gridZ], 0), new Vector3f(1,
							hights[gridX + 1][gridZ], 0), new Vector3f(0,
							hights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
		} else {
			answer = Maths
					.barryCentric(new Vector3f(1, hights[gridX + 1][gridZ], 0), new Vector3f(1,
							hights[gridX + 1][gridZ + 1], 1), new Vector3f(0,
							hights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
		}
		
		return answer;
	}
	
	private RawModel generateTerrain(Loader loader, String heightMap){
		
		BufferedImage image = null;
		
		
		try {
			image = ImageIO.read(new File("res/" + heightMap + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		int VERTEX_COUNT = image.getHeight();
		hights = new float[VERTEX_COUNT][VERTEX_COUNT];
		int count = VERTEX_COUNT * VERTEX_COUNT;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count*2];
		int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT-1)];
		int vertexPointer = 0;
		for(int i=0;i<VERTEX_COUNT;i++){
			for(int j=0;j<VERTEX_COUNT;j++){
				vertices[vertexPointer*3] = (float)j/((float)VERTEX_COUNT - 1) * SIZE;
				float height = getHeight(j,i,image);
				hights[j][i] = height;
				vertices[vertexPointer*3+1] = height;
				vertices[vertexPointer*3+2] = (float)i/((float)VERTEX_COUNT - 1) * SIZE;
				normals[vertexPointer*3] = 0;
				normals[vertexPointer*3+1] = 1;
				normals[vertexPointer*3+2] = 0;
				textureCoords[vertexPointer*2] = (float)j/((float)VERTEX_COUNT - 1);
				textureCoords[vertexPointer*2+1] = (float)i/((float)VERTEX_COUNT - 1);
				vertexPointer++;
			}
		}
		int pointer = 0;
		for(int gz=0;gz<VERTEX_COUNT-1;gz++){
			for(int gx=0;gx<VERTEX_COUNT-1;gx++){
				int topLeft = (gz*VERTEX_COUNT)+gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz+1)*VERTEX_COUNT)+gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		return loader.loadToVAO(vertices, textureCoords, normals, indices);
	}

	public float getX() {
		return x;
	}

	public float getZ() {
		return z;
	}

	public RawModel getModel() {
		return model;
	}
	
	private float getHeight(int x, int z, BufferedImage image) {
		if (x < 0 || x >= image.getHeight() || z < 0 || z >= image.getWidth()) {
			return 0;
		}
		
		float height = image.getRGB(x, z);
		height += MAX_PIXEL_COLOUR/2f;
		height /= MAX_PIXEL_COLOUR/2f;
		height *= MAX_HEIGHT;

		return height;		
	}
}
