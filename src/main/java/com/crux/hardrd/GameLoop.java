package com.crux.hardrd;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

import com.crux.hardrd.GameStateManager;

public class GameLoop {
	// The window handle
	private long window;
	private GameStateManager gsm;

	public void run()
	{
		init();
		loop();
	}
	
	private void init()
	{
		ContextAttribs attribs = new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true);
		
		try {
			Display.setDisplayMode(new DisplayMode(300,300));
			Display.create(new PixelFormat(), attribs);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		GL11.glViewport(0, 0, 300, 300);
		
		gsm = new GameStateManager();
	
	}
	
	private void loop() {
		while(!Display.isCloseRequested())
		{
			update();
			draw();
		}
	}
	

	
	private void update()
	{
		gsm.getCurrentState().update();
		Display.sync(60);
		Display.update();
		
	}
	
	private void draw()
	{
		gsm.getCurrentState().draw(window);
	}
}
