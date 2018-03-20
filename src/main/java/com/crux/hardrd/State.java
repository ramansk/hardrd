package com.crux.hardrd;

public abstract class State {
	protected GameStateManager gsm;
	public State(GameStateManager gsm)
	{
		this.gsm = gsm;
	}
	public abstract void update();

	public abstract void draw(long window);
}
