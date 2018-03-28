package com.crux.hardrd;

import java.util.List;

import com.crux.hardrd.controller.ApplicationController;
import com.crux.hardrd.entities.Entity;
import com.crux.hardrd.entities.Player;
import com.crux.hardrd.terrains.Terrain;

public abstract class State {
	protected ApplicationController controller;
	public State(ApplicationController controller)
	{
		this.controller = controller;
	}
	public abstract void update();

	public abstract void draw();
	
	public abstract void applyStaticEntities(List<Entity> entity);
	public abstract void applyPlayer(Player player);
	public abstract void applyTerrain(Terrain terrain);
}
