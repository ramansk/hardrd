package com.crux.hardrd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.util.vector.Vector3f;

import com.crux.hardrd.controller.ApplicationController;
import com.crux.hardrd.controller.MapResource;
import com.crux.hardrd.controller.PlayerResource;
import com.crux.hardrd.controller.ServerUpdateJob;
import com.crux.hardrd.controller.UpdateEventsQueue;
import com.crux.hardrd.entities.Camera;
import com.crux.hardrd.entities.DynamicEntity;
import com.crux.hardrd.entities.Entity;
import com.crux.hardrd.entities.Player;
import com.crux.hardrd.fontRendering.TextMaster;
import com.crux.hardrd.models.RawModel;
import com.crux.hardrd.models.TexturedModel;
import com.crux.hardrd.terrains.Terrain;
import com.crux.hardrd.test.Loader;
import com.crux.hardrd.test.MasterRenderer;
import com.crux.hardrd.test.OBJLoader;
import com.crux.hardrd.textures.ModelTexture;

public class MultiplayerGameState extends State {
	
	private List<Entity> entities = new ArrayList<>();
	private Player player;
	private Camera camera;
	private Light light;
	private Map<TerrainKey, Terrain> terrains = new ConcurrentHashMap<>();
    private MasterRenderer masterRenderer;
    
    private Map<String, DynamicEntity> otherPlayers = new ConcurrentHashMap<>();
    private List<PlayerResource> playersToAdd = new CopyOnWriteArrayList<>();
    private List<MapResource> mapsToAdd = new CopyOnWriteArrayList<>();
    
    
    private ServerUpdateJob job;
    private OtherPlayersUpdateJob opuJob;

	public MultiplayerGameState(ApplicationController controller) {
		super(controller);
		job = new ServerUpdateJob(controller);
		opuJob = new OtherPlayersUpdateJob(otherPlayers, controller, playersToAdd, mapsToAdd);
		light = new Light(new Vector3f(20000,20000,2000), new Vector3f(1,1,1));
		masterRenderer = new MasterRenderer();
	
		PlayerResource pr  = controller.getPlayer(controller.getCurrentPlayerId());
	
	
	
		//x 2700, z 2700
		//TODO: Add availability to sent global map row, col during player creation
		//TODO: Add availability to change model
		Player player = createPlayerEntity(controller.getLoader(), pr.getPosX(),pr.getPosZ(), "tree", "tree");
		player.setName(pr.getName());
		player.setGlobalMapColNum(1);
		player.setGlobalMapRowNum(1);
		Terrain terrain = new Terrain(1800, 1800, controller.getLoader(), controller.getTerrainTexturePack(), controller.getBlendMap(), controller.getMap(1,1));

		
		
		//generate entities
		Random rand = new Random();
		for(int i = 0; i< 100; i++)
		{
			Entity grass = createEntity(controller.getLoader(), terrain, rand.nextFloat()*500,rand.nextFloat()*500,"grassModel", "grassTexture");
			grass.getModel().getTexture().setHasTransparency(true);
			grass.getModel().getTexture().setUseFakeLighting(true);
			entities.add(grass);
			entities.add(createEntity(controller.getLoader(), terrain, rand.nextFloat()*500,rand.nextFloat()*500,"lowPolyTree", "lowPolyTree"));
			///entities.add(createEntity(r.nextFloat()*500,r.nextFloat()*500,"grassModel", "flower"));
			//entities.add(createEntity(r.nextFloat()*500,r.nextFloat()*500,"grassModel", "fern"));
			//entities.add(createEntity(r.nextFloat()*500,r.nextFloat()*500,"grassModel", "grassy"));
		}

		Entity cube = createEntity(controller.getLoader(), terrain, 10,10,"test2", "wall");
		entities.add(cube);
		entities.add(createEntity(controller.getLoader(), terrain, 30,30,"stall", "stallTexture"));


		applyPlayer(player);
		applyTerrain(terrain);
		//gsm.getState("main").applyStaticEntities(entities);
	}

	@Override
	public void applyStaticEntities(List<Entity> entity) {
		this.entities = entity;		
	}

	@Override
	public void applyPlayer(Player player) {
		this.player = player;
		opuJob.setCurPlayer(player);
		camera = new Camera(player);
		
	}

	@Override
	public void applyTerrain(Terrain terrain) {
		//this.terrain = terrain;
		terrains.put(TerrainKey.create(terrain.getCol(), terrain.getRow()), terrain);
	}
	private long lastUpdateTime;
	@Override
	public void update() {
		if(!mapsToAdd.isEmpty())
		{
			for(MapResource mr: mapsToAdd)
			{
				System.out.println("!!!!!!!!" + mr.getCol() + " " + mr.getRow() + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				System.out.println("!!!!!!!!" + mr.getCol()*Terrain.SIZE + " " + (mr.getRow())*Terrain.SIZE + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

				terrains.put(TerrainKey.create(mr.getCol(), mr.getRow()),new Terrain((mr.getCol())*Terrain.SIZE, (mr.getRow())*Terrain.SIZE, controller.getLoader(), controller.getTerrainTexturePack(), controller.getBlendMap(), mr));
				mapsToAdd.remove(mr);
			}
		}
		if(!playersToAdd.isEmpty())
		{
			for(PlayerResource p: playersToAdd)
			{
				DynamicEntity pe = createDynamicEntity(controller.getLoader(), terrains.get(TerrainKey.create(player.getGlobalMapColNum(), player.getGlobalMapRowNum())), p.getPosX(),p.getPosZ(),"lowPolyTree", "lowPolyTree", p.getCurrentSpeed());

				otherPlayers.put(p.getName(), pe);
				playersToAdd.remove(p);
			}
			
		}
		
		for(DynamicEntity dynamicPlayerEntity : otherPlayers.values())
		{
			if(dynamicPlayerEntity.getCurrentSpeed() != 0)
			{
				dynamicPlayerEntity.increasePosition(60, 2);
			}
				

			
		}
		long start = System.nanoTime();
		//entity.increasePosition(0, 0, 0);
		//entity.increaseRotation(0, 1, 0);
		camera.move();
		player.move(terrains.get(TerrainKey.create(player.getGlobalMapColNum(), player.getGlobalMapRowNum())));
		
		
		if(System.currentTimeMillis() > (lastUpdateTime + 500l) && player.getCurrentSpeed() != 0)
		{
			System.out.println(player.getPosition().x + " " + player.getPosition().z);
			//controller.retrieveDynamicDataUpdates();
			UpdateEventsQueue.put(new Updates(
					player.getName(),
					player.getPosition().x,
					player.getPosition().y,
					player.getPosition().z,
					player.getRotX(),
					player.getRotY(),
					player.getRotZ(),
					player.getCurrentSpeed()
					));
			//System.out.println(UpdateEventsQueue.size());
			lastUpdateTime = System.currentTimeMillis();
			stopSignalSent = false;
		} else if(System.currentTimeMillis() > (lastUpdateTime + 500l) &&  !stopSignalSent)
		{
			UpdateEventsQueue.put(new Updates(
					player.getName(),
					player.getPosition().x,
					player.getPosition().y,
					player.getPosition().z,
					player.getRotX(),
					player.getRotY(),
					player.getRotZ(),
					0
					));
			//System.out.println(UpdateEventsQueue.size());
			lastUpdateTime = System.currentTimeMillis();
			stopSignalSent = true;
		}
	//	System.out.println(start - System.nanoTime());
		
	 }
	boolean stopSignalSent = false;
	@Override
	public void draw() {
		Random rand = new Random();
		//entities.add(controller.createEntity(controller.getLoader(), terrain, rand.nextFloat()*500,rand.nextFloat()*500,"lowPolyTree", "lowPolyTree"));
		for(Terrain terrain : terrains.values())
		{
			masterRenderer.processEntity(terrain);
		}
		
		for(Entity entity : entities)
		{
			masterRenderer.processEntity(entity);
		}
		for(Entity p : otherPlayers.values())
		{
			masterRenderer.processEntity(p);
		}
		masterRenderer.processEntity(player);
		masterRenderer.render(light, camera);
		TextMaster.render();
	}

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub
		
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
