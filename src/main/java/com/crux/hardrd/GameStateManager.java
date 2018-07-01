package com.crux.hardrd;

import com.crux.hardrd.controller.ApplicationController;
import com.crux.hardrd.controller.Client;
import com.crux.hardrd.controller.JacksonRestClient;

public class GameStateManager {
	Client restClient = new JacksonRestClient("http://localhost:8080");
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
				currentState = new MenuState(controller, restClient);
				break;
			case "test":
				currentState = new MultiplayerGameState(controller, restClient);
				break;
			case "singlePlayer":
				currentState = new SinglePlayerGameState(controller);
		}
	}
	
	public State getCurrentState()
	{
		return currentState;
	}
}
