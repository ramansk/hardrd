package com.crux.hardrd.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import com.crux.hardrd.DisplayManager;
import com.crux.hardrd.GameStateManager;
import com.crux.hardrd.Updates;
import com.crux.hardrd.entities.DynamicEntity;
import com.crux.hardrd.entities.Entity;
import com.crux.hardrd.entities.Player;
import com.crux.hardrd.models.RawModel;
import com.crux.hardrd.models.TexturedModel;
import com.crux.hardrd.terrains.Terrain;
import com.crux.hardrd.test.Loader;
import com.crux.hardrd.test.OBJLoader;
import com.crux.hardrd.textures.ModelTexture;
import com.crux.hardrd.textures.TerrainTexture;
import com.crux.hardrd.textures.TerrainTexturePack;

public class ApplicationController {
	Loader loader = new Loader();
	Terrain terrain;
	public Terrain getTerrain() {
		return terrain;
	}


	public void setTerrain(Terrain terrain) {
		this.terrain = terrain;
	}


	public Loader getLoader() {
		return loader;
	}

	private GameStateManager gsm;
	private Client client;

	public ApplicationController()
	{
		gsm = new GameStateManager(this);
		
		client = new JacksonRestClient("http://localhost:8080");
	}
	
	
	//add action response
	public void loadResources()
	{
		//load static resources action
		//load dynamic resources action

		List<Entity> entities = new ArrayList<Entity>();
		
		

		TerrainTexture bt = new TerrainTexture(loader.loadTexture("grass"));
		TerrainTexture r = new TerrainTexture(loader.loadTexture("dirt"));
		TerrainTexture g = new TerrainTexture(loader.loadTexture("pinkFlowers"));
		TerrainTexture b = new TerrainTexture(loader.loadTexture("path"));
		TerrainTexturePack ttp = new TerrainTexturePack(bt, r, g, b);
		TerrainTexture bm = new TerrainTexture(loader.loadTexture("blendMap"));
		Terrain terrain = new Terrain(-100,-100, loader, ttp, bm,"heightmap");
		
		Player player = createPlayerEntity(loader, 5,5, "tree", "tree");
		
		
		
		//generate entities
		Random rand = new Random();
		for(int i = 0; i< 100; i++)
		{
			Entity grass = createEntity(loader, terrain, rand.nextFloat()*500,rand.nextFloat()*500,"grassModel", "grassTexture");
			grass.getModel().getTexture().setHasTransparency(true);
			grass.getModel().getTexture().setUseFakeLighting(true);
			entities.add(grass);
			entities.add(createEntity(loader, terrain, rand.nextFloat()*500,rand.nextFloat()*500,"lowPolyTree", "lowPolyTree"));
			///entities.add(createEntity(r.nextFloat()*500,r.nextFloat()*500,"grassModel", "flower"));
			//entities.add(createEntity(r.nextFloat()*500,r.nextFloat()*500,"grassModel", "fern"));
			//entities.add(createEntity(r.nextFloat()*500,r.nextFloat()*500,"grassModel", "grassy"));
		}

		Entity cube = createEntity(loader, terrain, 10,10,"test2", "wall");
		entities.add(cube);
		entities.add(createEntity(loader, terrain, 30,30,"stall", "stallTexture"));	
		
		
		gsm.getState("main").applyPlayer(player);
		gsm.getState("main").applyTerrain(terrain);
		gsm.getState("main").applyStaticEntities(entities);
		
		
		//ActionResponse response = new ActionResponse();
		//response.setState("main");	
	}
	
	public void updateState()
	{
		gsm.getCurrentState().update();
		DisplayManager.update();
		gsm.getCurrentState().draw();
	}
	
	public void sendUpdatesToServer(Updates updates)
	{
		client.sendUpdatesToServer(updates);
	}
	
	public List<PlayerResource> getPlayers()
	{
		return client.getPlayersFromServer();
	}
	
    public Entity createEntity(Loader loader, Terrain terrain, float x, float z, String modelFileName, String textureFileName)
    {
    	RawModel model = OBJLoader.loadObjModel(modelFileName, loader);
    	ModelTexture mt = new ModelTexture(loader.loadTexture(textureFileName));
		mt.setShineDamper(10000);
		mt.setReflectivity(0);
    	TexturedModel tm = new TexturedModel(model, mt);
    	
    	return new Entity(tm, new Vector3f(x,terrain.getHeightOfTerrain(x, z),z), 0, 90, 0, 1);
    }
    
    public DynamicEntity createDynamicEntity(Loader loader, Terrain terrain, float x, float z, String modelFileName, String textureFileName, float currentSpeed)
    {
    	RawModel model = OBJLoader.loadObjModel(modelFileName, loader);
    	ModelTexture mt = new ModelTexture(loader.loadTexture(textureFileName));
		mt.setShineDamper(10000);
		mt.setReflectivity(0);
    	TexturedModel tm = new TexturedModel(model, mt);
    	
    	return new DynamicEntity(tm, new Vector3f(x,terrain.getHeightOfTerrain(x, z),z), 0, 90, 0, 1, currentSpeed);
    }
    
    public Player createPlayerEntity(Loader loader, float x, float z, String modelFileName, String textureFileName)
    {
    	RawModel model = OBJLoader.loadObjModel(modelFileName, loader);
    	ModelTexture mt = new ModelTexture(loader.loadTexture(textureFileName));
		mt.setShineDamper(10000);
		mt.setReflectivity(0);
    	TexturedModel tm = new TexturedModel(model, mt);
    	
    	return new Player(tm, new Vector3f(x,0,z), 0, 90, 0, 1);
    	
    }
}
