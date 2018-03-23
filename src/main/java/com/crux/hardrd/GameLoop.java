package com.crux.hardrd;

import org.lwjgl.opengl.Display;

import com.crux.hardrd.GameStateManager;

public class GameLoop {
	private long window;
	private GameStateManager gsm;

	public void run()
	{
		init();
		loop();
	}

	private void init()
	{	
		DisplayManager.createDisplay();
		gsm = new GameStateManager();
	}

	private void loop() {
		while(!Display.isCloseRequested())
		{
			update();
			draw();
		}
		DisplayManager.close();
	}

	private void update()
	{
		gsm.getCurrentState().update();
		DisplayManager.update();		
	}

	private void draw()
	{
		gsm.getCurrentState().draw(window);
	}
}
