package com.crux.hardrd.entities;

import org.lwjgl.util.vector.Vector3f;

import com.crux.hardrd.DisplayManager;
import com.crux.hardrd.models.TexturedModel;

public class DynamicEntity extends Entity{
	public Float getCurrentSpeed() {
		return currentSpeed;
	}

	public void setCurrentSpeed(Float currentSpeed) {
		this.currentSpeed = currentSpeed;
	}

	private Vector3f curPosition;
	private Vector3f prevPosition;
	private Vector3f nextPosition;
	private Float currentSpeed;

	private float prevRotX, prevRotY, prevRotZ;

	public DynamicEntity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, float currentSpeed) {
		super(model, position, rotX, rotY, rotZ, scale);
		this.currentSpeed = currentSpeed;
		nextPosition = position;
		curPosition = position;
		prevPosition = position;
		prevRotX = rotX;
		prevRotY = rotY;
		prevRotZ = rotZ;		
	}
	
	public void increasePosition(float fps, float numTicksPerSecond)
	{
		
		float numFramesPerTick = fps/numTicksPerSecond;
		
		
		
		
		float distance = 10*0.5f;
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		
		

		if(currentSpeed != 0)
		{
			super.position.x += dx / numFramesPerTick;
		}

		if(currentSpeed != 0)
		{
			super.position.z += dz / numFramesPerTick;
		}
	}
	
	public void increaseRotation(int fps, int numTicksPerSecond)
	{
		float numFramesPerTick = fps/numTicksPerSecond;
		float rotxDelta = super.rotX - prevRotX;
		float rotyDelta = super.rotY - prevRotY;
		float rotzDelta = super.rotZ - prevRotZ;
		
		//super.rotX += rotxDelta/numFramesPerTick;
		//super.rotY += rotyDelta/numFramesPerTick;
		//super.rotZ += rotzDelta/numFramesPerTick;
	}

	@Override
	public void setPosition(Vector3f position) {
		super.position = position;
		prevPosition = curPosition;
		curPosition = position;
		if(currentSpeed != 0)
		{
			float distance = 10*0.5f;
			float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
			float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
			nextPosition = new Vector3f(position.x + dx, position.y, position.z + dz);	
		}
	}

	@Override
	public void setRotX(float rotX) {
		prevRotX = super.rotX;
		super.rotX = rotX;
	}

	@Override
	public void setRotY(float rotY) {
		prevRotY = super.rotY;
		super.rotY = rotY;
	}

	@Override
	public void setRotZ(float rotZ) {
		prevRotZ = super.rotZ;
		super.rotZ = rotZ;
	}
}
