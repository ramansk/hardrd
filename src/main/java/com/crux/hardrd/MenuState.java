package com.crux.hardrd;

import java.util.List;

import com.crux.hardrd.controller.ApplicationController;
import com.crux.hardrd.entities.Entity;
import com.crux.hardrd.entities.Player;
import com.crux.hardrd.terrains.Terrain;

public class MenuState extends State{

	public MenuState(ApplicationController controller) {
		super(controller);
	}

	@Override
	public void update() {
	}

	@Override
	public void draw() {
	}

	@Override
	public void applyStaticEntities(List<Entity> entity) {
	}

	@Override
	public void applyPlayer(Player player) {
	}

	@Override
	public void applyTerrain(Terrain terrain) {
	}

}
