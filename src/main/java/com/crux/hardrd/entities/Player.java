package com.crux.hardrd.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import com.crux.hardrd.DisplayManager;
import com.crux.hardrd.models.TexturedModel;
import com.crux.hardrd.terrains.Terrain;

public class Player extends Entity {
	private int globalMapRowNum;
	private int globalMapColNum;


	private static final float RUN_SPEED = 10;
	private static final float TURN_SPEED = 40;
	private static final float GRAVITY = -5;
	private static final float JUMP_POWER = 3;
	private static final float terrainHeight = 0;
	private float currentSpeed = 0;
	public float getCurrentSpeed() {
		return currentSpeed;
	}

	private float currentTurnSpeed = 0;
	private float upwardsSpeed = 0;
	private boolean isInAir;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGlobalMapRowNum() {
		return globalMapRowNum;
	}

	public void setGlobalMapRowNum(int globalMapRowNum) {
		this.globalMapRowNum = globalMapRowNum;
	}

	public int getGlobalMapColNum() {
		return globalMapColNum;
	}

	public void setGlobalMapColNum(int globalMapColNum) {
		this.globalMapColNum = globalMapColNum;
	}

	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
	}

	public void move(Terrain terrain) {
		checkInputs();
		super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx, 0, dz);
		upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
		super.increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		
		float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
		
		if (super.getPosition().y < terrainHeight) {
			upwardsSpeed = 0;
			isInAir = false;
			super.getPosition().y = terrainHeight;
		}
	}

	private void jump() {
		if (!isInAir) {
			this.upwardsSpeed = JUMP_POWER;
			isInAir = true;
		}
	}

	private void checkInputs() {
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			currentSpeed = RUN_SPEED;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			currentSpeed = -RUN_SPEED;
		} else {
			currentSpeed = 0;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			currentTurnSpeed = -TURN_SPEED;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			currentTurnSpeed = TURN_SPEED;
		} else {
			currentTurnSpeed = 0;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			jump();
		}
	}
}
