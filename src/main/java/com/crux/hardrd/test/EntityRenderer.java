package com.crux.hardrd.test;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import com.crux.hardrd.entities.Entity;
import com.crux.hardrd.models.RawModel;
import com.crux.hardrd.models.TexturedModel;
import com.crux.hardrd.shaders.StaticShader;
import com.crux.hardrd.textures.ModelTexture;
import com.crux.hardrd.toolbox.Maths;

public class EntityRenderer {
	private StaticShader shader;

	public EntityRenderer(StaticShader shader, Matrix4f projectionMatrix)
	{
		this.shader = shader;
		shader.start();
		shader.loadPrMatrix(projectionMatrix);
		shader.stop();
	}

	public void render(Map<TexturedModel, List<Entity>> entities)
	{
		for(Entry<TexturedModel, List<Entity>> model : entities.entrySet())
		{
			prepareTexturedModel(model.getKey());
			List<Entity> batch = model.getValue();
			for(Entity entity: batch)
			{
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getKey().getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
				
			}
			unbindTexturedModel();
			
		}
	}
	
	private void prepareTexturedModel(TexturedModel tm)
	{
		RawModel model = tm.getRawModel();
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		ModelTexture texture = tm.getTexture();
		if(texture.isHasTransparency())
		{
			MasterRenderer.disableCulling();
		}
		shader.loadFakeLightingVariable(texture.isUseFakeLighting());
		shader.loadReflectivity(texture.getReflectivity());
		shader.loadShineDamper(texture.getShineDamper());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		
	
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, tm.getTexture().getTextureID());
	}
	
	private void unbindTexturedModel()
	{
		MasterRenderer.enableCulling();
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	private void prepareInstance(Entity entity)
	{
		Matrix4f trM = Maths.createTrMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		shader.loadTrMatrix(trM);

	}
	
	

}
