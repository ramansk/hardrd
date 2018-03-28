package com.crux.hardrd;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.lwjgl.util.vector.Vector3f;

import com.crux.hardrd.controller.ApplicationController;
import com.crux.hardrd.controller.PlayerResource;
import com.crux.hardrd.entities.DynamicEntity;
import com.crux.hardrd.entities.Entity;
import com.crux.hardrd.entities.Player;

public class OtherPlayersUpdateJob {
	private Map<String, DynamicEntity> othPlayers;
	private List<PlayerResource> playersToAdd;
	private boolean running = true;
	ExecutorService es;
	private ApplicationController controller;
	private static final int WORKERS_COUNT = 1;

	public OtherPlayersUpdateJob(Map<String, DynamicEntity> players, ApplicationController controller, List<PlayerResource> playersToAdd) {
		this.othPlayers = players;
		this.playersToAdd = playersToAdd;
		this.controller = controller;
		es = Executors.newFixedThreadPool(WORKERS_COUNT);
		for (int i = 0; i < WORKERS_COUNT; i++) {
			es.submit(new Worker());
		}
	}

	private class Worker implements Runnable {
		
		//TODO: create domain model of player
		@Override
		public void run() {
			while (running) {
				try {
				for(PlayerResource p : controller.getPlayers())
				{
					DynamicEntity currentPlayer = othPlayers.get(p.getName());
					if (currentPlayer != null) {
						currentPlayer
								.setPosition(
										new Vector3f(
												p.getPosX(),
												p.getPosY(),
												p.getPosZ()));
						currentPlayer.setRotX(p.getRotX());
						currentPlayer.setRotY(p.getRotY());
						currentPlayer.setRotZ(p.getRotZ());
						currentPlayer.setCurrentSpeed(p.getCurrentSpeed());
					}
					else
					{
						if(!playersToAdd.contains(p))
						{playersToAdd.add( p);
							
						}
						
						//Entity player = controller.createEntity(controller.getLoader(), controller.getTerrain(), p.getPosX(),p.getPosZ(), "lowPolyTree", "lowPolyTree");
					//	othPlayers.put(p.getName(), player);					
					}
				}
				
					Thread.sleep(500l);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
