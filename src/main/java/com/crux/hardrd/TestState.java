package com.crux.hardrd;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.util.vector.Vector3f;

import com.crux.hardrd.controller.ApplicationController;
import com.crux.hardrd.controller.PlayerResource;
import com.crux.hardrd.controller.ServerUpdateJob;
import com.crux.hardrd.controller.UpdateEventsQueue;
import com.crux.hardrd.entities.Camera;
import com.crux.hardrd.entities.DynamicEntity;
import com.crux.hardrd.entities.Entity;
import com.crux.hardrd.entities.Player;
import com.crux.hardrd.terrains.Terrain;
import com.crux.hardrd.test.MasterRenderer;

public class TestState extends State {
	
	private List<Entity> entities;
	private Player player;
	private Camera camera;
	private Light light;
	private Terrain terrain;
    private MasterRenderer masterRenderer;
    
    private Map<String, DynamicEntity> otherPlayers = new ConcurrentHashMap<>();
    private List<PlayerResource> playersToAdd = new CopyOnWriteArrayList<>();
    
    
    private ServerUpdateJob job;
    private OtherPlayersUpdateJob opuJob;

	public TestState(ApplicationController controller) {
		super(controller);
		job = new ServerUpdateJob(controller);
		opuJob = new OtherPlayersUpdateJob(otherPlayers, controller, playersToAdd);
		light = new Light(new Vector3f(20000,20000,2000), new Vector3f(1,1,1));
		masterRenderer = new MasterRenderer();
	}

	@Override
	public void applyStaticEntities(List<Entity> entity) {
		this.entities = entity;		
	}

	@Override
	public void applyPlayer(Player player) {
		this.player = player;
		camera = new Camera(player);
		
	}

	@Override
	public void applyTerrain(Terrain terrain) {
		this.terrain = terrain;
	}
	private long lastUpdateTime;
	@Override
	public void update() {
		if(!playersToAdd.isEmpty())
		{
			for(PlayerResource player: playersToAdd)
			{
				System.out.println(player.getCurrentSpeed());
				DynamicEntity pe = controller.createDynamicEntity(controller.getLoader(), terrain, player.getPosX(),player.getPosZ(),"lowPolyTree", "lowPolyTree", player.getCurrentSpeed());

				otherPlayers.put(player.getName(), pe);
				playersToAdd.remove(player);
			}
		}
		for(DynamicEntity dynamicPlayerEntity : otherPlayers.values())
		{
			dynamicPlayerEntity.increasePosition(60, 2);
		}
		//entity.increasePosition(0, 0, 0);
		//entity.increaseRotation(0, 1, 0);
		camera.move();
		player.move(terrain);
		
		if(System.currentTimeMillis() > (lastUpdateTime + 500l))
		{
			//controller.retrieveDynamicDataUpdates();
			UpdateEventsQueue.put(new Updates(
					"test2", 
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
		}
	 }

	@Override
	public void draw() {
		Random rand = new Random();
		//entities.add(controller.createEntity(controller.getLoader(), terrain, rand.nextFloat()*500,rand.nextFloat()*500,"lowPolyTree", "lowPolyTree"));
		masterRenderer.processEntity(terrain);
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
	}
}
