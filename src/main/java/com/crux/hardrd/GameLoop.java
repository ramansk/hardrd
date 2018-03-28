package com.crux.hardrd;

import org.lwjgl.opengl.Display;

import com.crux.hardrd.controller.ApplicationController;

public class GameLoop {
	private ApplicationController applicatonController;

	public void run() {
		init();
		loop();
	}

	private void init() {
		DisplayManager.createDisplay();
		applicatonController = new ApplicationController();
	}

	private void loop() {
		applicatonController.loadResources();
		while (!Display.isCloseRequested()) {
			applicatonController.updateState();
		}
		DisplayManager.close();
	}
}
