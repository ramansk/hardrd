package com.crux.hardrd;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.crux.hardrd.controller.Client;
import org.lwjgl.util.vector.Vector3f;

import com.crux.hardrd.controller.ApplicationController;
import com.crux.hardrd.controller.MapResource;
import com.crux.hardrd.controller.PlayerResource;
import com.crux.hardrd.entities.DynamicEntity;
import com.crux.hardrd.entities.Player;
import com.crux.hardrd.terrains.Terrain;

public class OtherPlayersUpdateJob {
	private Client client;
	private Map<String, DynamicEntity> othPlayers;
	private List<PlayerResource> playersToAdd;
	private List<MapResource> loadedMaps;
	private Player curPlayer;
	private boolean running = true;
	ExecutorService es;
	private ApplicationController controller;
	private static final int WORKERS_COUNT = 1;

	public OtherPlayersUpdateJob(Map<String, DynamicEntity> players, ApplicationController controller,
			List<PlayerResource> playersToAdd, List<MapResource> loadedMaps) {
		this.othPlayers = players;
		this.playersToAdd = playersToAdd;

		this.controller = controller;
		this.loadedMaps = loadedMaps;
		es = Executors.newFixedThreadPool(WORKERS_COUNT);
		for (int i = 0; i < WORKERS_COUNT; i++) {
			es.submit(new Worker());
		}
	}

	public Player getCurPlayer() {
		return curPlayer;
	}

	public void setCurPlayer(Player curPlayer) {
		this.curPlayer = curPlayer;
	}

	private class Worker implements Runnable {

		// TODO: create domain model of player
		@Override
		public void run() {
			boolean cloaded = false;
			boolean rloaded = false;
			boolean ncloaded = false;
			boolean nrloaded = false;
			while (running) {
				try {
					if (curPlayer != null) {
						if (curPlayer.getCurrentSpeed() > 0) {

							if (curPlayer.getPosition().x > Terrain.SIZE * (curPlayer.getGlobalMapColNum() + 1)
									- Terrain.SIZE / 5 && !cloaded) {
								loadedMaps.add(client.getMap(curPlayer.getGlobalMapColNum() + 1,
										curPlayer.getGlobalMapRowNum()));
								cloaded = true;
							}
							if (curPlayer.getPosition().z > Terrain.SIZE * (curPlayer.getGlobalMapRowNum() + 1)
									- Terrain.SIZE / 5 && !rloaded) {
								loadedMaps.add(client.getMap(curPlayer.getGlobalMapColNum(),
										curPlayer.getGlobalMapRowNum() + 1));
								rloaded = true;

							}

							if (curPlayer.getPosition().x < Terrain.SIZE * (curPlayer.getGlobalMapColNum())
									+ Terrain.SIZE / 5 && !ncloaded) {
								loadedMaps.add(client.getMap(curPlayer.getGlobalMapColNum() - 1,
										curPlayer.getGlobalMapRowNum()));
								ncloaded = true;
							}
							if (curPlayer.getPosition().z < Terrain.SIZE * (curPlayer.getGlobalMapRowNum())
									+ Terrain.SIZE / 5 && !nrloaded) {
								loadedMaps.add(client.getMap(curPlayer.getGlobalMapColNum(),
										curPlayer.getGlobalMapRowNum() - 1));
								nrloaded = true;
							}

							if (curPlayer.getPosition().x > Terrain.SIZE * (curPlayer.getGlobalMapColNum() + 1)) {
								curPlayer.setGlobalMapColNum(curPlayer.getGlobalMapColNum() + 1);
								cloaded = false;
								rloaded = false;
								cloaded = false;
								rloaded = false;
							}
							if (curPlayer.getPosition().z > Terrain.SIZE * (curPlayer.getGlobalMapRowNum() + 1)) {
								curPlayer.setGlobalMapRowNum(curPlayer.getGlobalMapRowNum() + 1);
								nrloaded = false;
								ncloaded = false;
								cloaded = false;
								rloaded = false;
							}

							if (curPlayer.getPosition().x < Terrain.SIZE * (curPlayer.getGlobalMapColNum())) {
								curPlayer.setGlobalMapColNum(curPlayer.getGlobalMapColNum() - 1);
								ncloaded = false;
								nrloaded = false;
								cloaded = false;
								rloaded = false;
							}
							if (curPlayer.getPosition().z < Terrain.SIZE * (curPlayer.getGlobalMapRowNum())) {
								curPlayer.setGlobalMapRowNum(curPlayer.getGlobalMapRowNum() - 1);
								nrloaded = false;
								ncloaded = false;
								cloaded = false;
								rloaded = false;
							}

						}
					}

					List<PlayerResource> playersList = client.getPlayersFromServer();
					if (playersList != null) {
						for (PlayerResource p : playersList) {
							DynamicEntity currentPlayer = othPlayers.get(p.getName());
							if (currentPlayer != null) {
								currentPlayer.setPosition(new Vector3f(p.getPosX(), p.getPosY(), p.getPosZ()));
								currentPlayer.setRotX(p.getRotX());
								currentPlayer.setRotY(p.getRotY());
								currentPlayer.setRotZ(p.getRotZ());
								currentPlayer.setCurrentSpeed(p.getCurrentSpeed());
							} else {
								if (!playersToAdd.contains(p)) {
									playersToAdd.add(p);

								}
							}
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
