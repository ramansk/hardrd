package com.crux.hardrd;

import java.util.HashMap;
import java.util.Map;

import com.crux.hardrd.controller.ApplicationController;

public class GameStateManager {
	private State currentState;
	private Map<String, State> states;
	
	public GameStateManager(ApplicationController controller)
	{
		states = new HashMap<>();
		State tst = new TestState(controller);
		states.put("main", tst);
		setCurrentState(tst);
	}

	public void setStates(Map<String, State> states) {
		this.states = states;
	}

	public State getCurrentState() {
		return currentState;
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}
	
	public State getState(String key)
	{
		return states.get(key);
	}
}
