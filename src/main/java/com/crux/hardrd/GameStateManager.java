package com.crux.hardrd;

import com.crux.hardrd.controller.ApplicationController;

public class GameStateManager {
	private State currentState;
	private ApplicationController controller;
	public GameStateManager(ApplicationController controller)
	{
		this.controller = controller;
		loadState("menu");
	}

	public void setCurrentState(String state) {
		currentState.cleanUp();
		loadState(state);
	}
	
	private void loadState(String state) {
		switch (state) {
		case "menu":
			currentState = new MenuState(controller);
			break;
		case "test":
			currentState = new MultiplayerGameState(controller);
			break;
		}
	}
	
	public State getCurrentState()
	{
		return currentState;
	}
}
