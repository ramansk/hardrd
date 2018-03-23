package com.crux.hardrd;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import com.crux.hardrd.textures.TerrainTexture;
import com.crux.hardrd.textures.TerrainTexturePack;

public class TestState extends State {
	List<Entity> entities = new ArrayList<Entity>();
	Loader loader = new Loader();

	TerrainTexture bt = new TerrainTexture(loader.loadTexture("grass"));
	TerrainTexture r = new TerrainTexture(loader.loadTexture("dirt"));
	TerrainTexture g = new TerrainTexture(loader.loadTexture("pinkFlowers"));
	TerrainTexture b = new TerrainTexture(loader.loadTexture("path"));
	TerrainTexturePack ttp = new TerrainTexturePack(bt, r, g, b);
	TerrainTexture bm = new TerrainTexture(loader.loadTexture("blendMap"));
	Terrain terrain = new Terrain(-100,-100, loader, ttp, bm);
	
	
	Light light = new Light(new Vector3f(20000,20000,2000), new Vector3f(1,1,1));
    Camera camera = new Camera();
    
    MasterRenderer masterRenderer = new MasterRenderer();

    private Entity createEntity(float x, float z, String modelFileName, String textureFileName)
    {
    	RawModel model = OBJLoader.loadObjModel(modelFileName, loader);
    	ModelTexture mt = new ModelTexture(loader.loadTexture(textureFileName));
		mt.setShineDamper(10000);
		mt.setReflectivity(0);
    	TexturedModel tm = new TexturedModel(model, mt);
    	
    	return new Entity(tm, new Vector3f(x,0,z), 0, 90, 0, 1);
    	
    }
	public TestState(GameStateManager gsm) {
		super(gsm);
		//mt21213.setShineDamper(10000);
		Random r = new Random();
		for(int i = 0; i< 100; i++)
		{
			Entity grass = createEntity(r.nextFloat()*500,r.nextFloat()*500,"grassModel", "grassTexture");
			grass.getModel().getTexture().setHasTransparency(true);
			grass.getModel().getTexture().setUseFakeLighting(true);
			entities.add(grass);
			entities.add(createEntity(r.nextFloat()*500,r.nextFloat()*500,"lowPolyTree", "lowPolyTree"));
			///entities.add(createEntity(r.nextFloat()*500,r.nextFloat()*500,"grassModel", "flower"));
			//entities.add(createEntity(r.nextFloat()*500,r.nextFloat()*500,"grassModel", "fern"));
			//entities.add(createEntity(r.nextFloat()*500,r.nextFloat()*500,"grassModel", "grassy"));
		}

		entities.add(createEntity(30,30,"stall", "stallTexture"));	
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
		for(Entity entity : entities)
		{
			masterRenderer.processEntity(entity);
		}
		masterRenderer.render(light, camera);
	}

}
