package com.crux.hardrd.test;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import com.crux.hardrd.models.RawModel;
import com.crux.hardrd.shaders.TerrainShader;
import com.crux.hardrd.terrains.Terrain;
import com.crux.hardrd.textures.TerrainTexturePack;
import com.crux.hardrd.toolbox.Maths;

public class TerrainRenderer {
	private TerrainShader shader;
	public TerrainRenderer(TerrainShader shader, Matrix4f projectionMatrix)
	{
		this.shader = shader;
		shader.start();
		shader.loadPrMatrix(projectionMatrix);
		shader.connectTextureUnits();
		shader.stop();
	}
	
	public void render(List<Terrain> terrains)
	{
		for(Terrain t : terrains)
		{
			prepareTerrain(t);
			loadModelMatrix(t);
			GL11.glDrawElements(GL11.GL_TRIANGLES, t.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);

			unbindTexturedModel();
		}
	}
	
	private void prepareTerrain(Terrain terrain)
	{
		RawModel model = terrain.getModel();
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		bindTextures(terrain);
		

		//ModelTexture texture = terrain.getTexture();
		shader.loadReflectivity(0);
		shader.loadShineDamper(10000);
		//shader.loadReflectivity(texture.getReflectivity());
		//shader.loadShineDamper(texture.getShineDamper());
	}
	
	
	private void bindTextures(Terrain terrain) {
		TerrainTexturePack ttp = terrain.getTexturePack();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, ttp.getBackgroundTexture().getTextureID());
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, ttp.getrTexture().getTextureID());
		GL13.glActiveTexture(GL13.GL_TEXTURE2);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, ttp.getgTexture().getTextureID());
		GL13.glActiveTexture(GL13.GL_TEXTURE3);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, ttp.getbTexture().getTextureID());
		GL13.glActiveTexture(GL13.GL_TEXTURE4);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, terrain.getBlendMap().getTextureID());

	}
	private void unbindTexturedModel()
	{
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	private void loadModelMatrix(Terrain terrain)
	{
		Matrix4f trM = Maths.createTrMatrix(new Vector3f(terrain.getX(), 0, terrain.getZ()), 0, 0, 0, 1);
		shader.loadTrMatrix(trM);

	}
}
