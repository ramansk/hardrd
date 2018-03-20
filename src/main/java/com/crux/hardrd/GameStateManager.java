package com.crux.hardrd;

import java.util.ArrayList;
import java.util.List;

public class GameStateManager {
	private State currentState;
	private List<State> states;
	
	public GameStateManager()
	{
		states = new ArrayList<>();
		State tst = new TestState(this);
		states.add(tst);
		setCurrentState(tst);
	}

	public void setStates(List<State> states) {
		this.states = states;
	}

	public State getCurrentState() {
		return currentState;
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}

	public List<State> getStates() {
		return states;
	}
}
