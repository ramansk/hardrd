package com.crux.hardrd.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	private Vector3f position = new Vector3f(0, 3,0);
	private float pitch;
	private float yaw = 180;
	private float roll;
	
	
	private static final float cameraMoveSpeed = 2;
	
	public Camera() {
		
	}
	
	public void move()
	{	
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT))
		{
			yaw-=cameraMoveSpeed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
		{
			yaw+=cameraMoveSpeed;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			position.z-=cameraMoveSpeed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			position.x+=cameraMoveSpeed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			position.x-=cameraMoveSpeed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			position.z+=cameraMoveSpeed;
		}
        if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
            position.y+=cameraMoveSpeed;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
            position.y-=cameraMoveSpeed;
        }
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public float getRoll() {
		return roll;
	}

	public void setRoll(float roll) {
		this.roll = roll;
	}
	
	
}
