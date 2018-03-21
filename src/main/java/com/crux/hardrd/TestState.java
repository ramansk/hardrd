package com.crux.hardrd;

import org.lwjgl.util.vector.Vector3f;

import com.crux.hardrd.entities.Camera;
import com.crux.hardrd.entities.Entity;
import com.crux.hardrd.models.RawModel;
import com.crux.hardrd.models.TexturedModel;
import com.crux.hardrd.shaders.StaticShader;
import com.crux.hardrd.test.Loader;
import com.crux.hardrd.test.OBJLoader;
import com.crux.hardrd.test.Renderer;
import com.crux.hardrd.textures.ModelTexture;

public class TestState extends State {
	Loader loader = new Loader();
	

	RawModel model = OBJLoader.loadObjModel("stall", loader);
	ModelTexture mt = new ModelTexture(loader.loadTexture("stallTexture"));
	TexturedModel tm = new TexturedModel(model, mt);
	Entity entity = new Entity(tm, new Vector3f(0,0,-30), 0, 90, 0, 1);
    Camera camera = new Camera();
	StaticShader shader = new StaticShader();
	Renderer renderer = new Renderer(shader);
	public TestState(GameStateManager gsm) {
		super(gsm);
		
	}

	@Override
	public void update() {
		//entity.increasePosition(0, 0, 0);
		entity.increaseRotation(0, 1, 0);
		camera.move();
	 }

	@Override
	public void draw(long window) {
		renderer.prepare();
		shader.start();
		shader.loadViewMatrix(camera);
		renderer.render(entity, shader);

		shader.stop();
	}

}
