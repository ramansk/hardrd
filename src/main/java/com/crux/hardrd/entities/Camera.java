package com.crux.hardrd.entities;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	private float distanceFromPlayer = 50;
	private float angleAroundPlayer = 0;
	
	
	private Vector3f position = new Vector3f(0, 3,0);
	private float pitch;
	private float yaw = 180;
	private float roll;
	private Player player;
	
	private static final float cameraMoveSpeed = 2;
	
	public Camera(Player player) {
		this.player = player;
	}
	
	public void move()
	{	
		calculateZoom();
		calculatePitch();
		calculateAngleAroundPlayer();
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		
		calculateCameraPosition(horizontalDistance, verticalDistance);
		this.yaw = 180 - (player.getRotY()+angleAroundPlayer);
	/*	if(Keyboard.isKeyDown(Keyboard.KEY_LEFT))
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
        }*/
	}
	
	private void calculateCameraPosition(float horD, float verD)
	{
		float theta = player.getRotY() + angleAroundPlayer;
		float offsetX = (float) (horD + Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horD + Math.cos(Math.toRadians(theta)));
		position.x = player.getPosition().x - offsetX;
		position.z = player.getPosition().z - offsetZ;
		position.y = player.getPosition().y + calculateVerticalDistance();
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
	
	
	private void calculateZoom()
	{
		float zoomLevel = Mouse.getDWheel() * 0.1f;
		distanceFromPlayer -= zoomLevel;
	}
	
	private void calculatePitch()
	{
		if(Mouse.isButtonDown(1))
		{
			float pitchChange = Mouse.getDY() * 0.1f;
			pitch -= pitchChange;
		}
	}
	
	private void calculateAngleAroundPlayer() {
		if(Mouse.isButtonDown(0))
		{
			float angleChange  = Mouse.getDX() * 0.3f;
			angleAroundPlayer -= angleChange;
		}
	}
	
	private float calculateHorizontalDistance()
	{
		return (float) (Math.cos(Math.toRadians(pitch)) * distanceFromPlayer);
	}
	
	private float calculateVerticalDistance()
	{
		return (float) (Math.sin(Math.toRadians(pitch)) * distanceFromPlayer);
	}
	
}
