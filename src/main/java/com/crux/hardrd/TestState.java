package com.crux.hardrd;

import com.crux.hardrd.models.RawModel;
import com.crux.hardrd.models.TexturedModel;
import com.crux.hardrd.shaders.StaticShader;
import com.crux.hardrd.test.Loader;
import com.crux.hardrd.test.Renderer;
import com.crux.hardrd.textures.ModelTexture;

public class TestState extends State {
	Loader loader = new Loader();
	Renderer renderer = new Renderer();
	float [] vertices = {
			-0.5f, 0.5f, 0f,
			-0.5f, -0.5f, 0f,
			0.5f, -0.5f, 0f,
			0.5f, 0.5f, 0f,
	};
	int[] indices = {
			0,1,3,
			3,1,2
	};
	
	float[] textureCoords = {
			0,0,
			0,1,
			1,1,
			1,0
	};
	RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
	ModelTexture mt = new ModelTexture(loader.loadTexture("Untitled"));
	TexturedModel tm = new TexturedModel(model, mt);
	StaticShader shader = new StaticShader();
	public TestState(GameStateManager gsm) {
		super(gsm);
		
	}

	@Override
	public void update() {

	 }

	@Override
	public void draw(long window) {
		renderer.prepare();
		shader.start();
		renderer.render(tm);
		shader.stop();
	}

}
