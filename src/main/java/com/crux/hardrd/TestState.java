package com.crux.hardrd;

import org.lwjgl.util.vector.Vector3f;

import com.crux.hardrd.entities.Camera;
import com.crux.hardrd.entities.Entity;
import com.crux.hardrd.models.RawModel;
import com.crux.hardrd.models.TexturedModel;
import com.crux.hardrd.terrains.Terrain;
import com.crux.hardrd.test.Loader;
import com.crux.hardrd.test.MasterRenderer;
import com.crux.hardrd.test.OBJLoader;
import com.crux.hardrd.textures.ModelTexture;

public class TestState extends State {
	Loader loader = new Loader();
	

	RawModel model = OBJLoader.loadObjModel("stall", loader);
	ModelTexture mt = new ModelTexture(loader.loadTexture("stallTexture"));
	TexturedModel tm = new TexturedModel(model, mt);
	
	Entity entity = new Entity(tm, new Vector3f(30,0,30), 0, 90, 0, 1);
	ModelTexture mt21213 =  new ModelTexture(loader.loadTexture("grass"));
	Terrain terrain = new Terrain(-100,-100, loader, mt21213);
	//Terrain terrain2 = new Terrain(1,0, loader, new ModelTexture(loader.loadTexture("grass")));
	Light light = new Light(new Vector3f(20000,20000,2000), new Vector3f(1,1,1));
    Camera camera = new Camera();
    
    MasterRenderer masterRenderer = new MasterRenderer();
    
    
    
	public TestState(GameStateManager gsm) {
		super(gsm);
		mt.setShineDamper(10000);
		mt.setReflectivity(0);
		mt21213.setShineDamper(10000);
		
	}

	@Override
	public void update() {
		//entity.increasePosition(0, 0, 0);
		//entity.increaseRotation(0, 1, 0);
		camera.move();
	 }

	@Override
	public void draw(long window) {
		masterRenderer.processEntity(terrain);
	    //masterRenderer.processEntity(terrain2);
		masterRenderer.processEntity(entity);
		masterRenderer.render(light, camera);
	}

}
