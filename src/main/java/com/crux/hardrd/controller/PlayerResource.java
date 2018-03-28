package com.crux.hardrd.controller;

public class PlayerResource {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((posX == null) ? 0 : posX.hashCode());
		result = prime * result + ((posY == null) ? 0 : posY.hashCode());
		result = prime * result + ((posZ == null) ? 0 : posZ.hashCode());
		result = prime * result + ((rotX == null) ? 0 : rotX.hashCode());
		result = prime * result + ((rotY == null) ? 0 : rotY.hashCode());
		result = prime * result + ((rotZ == null) ? 0 : rotZ.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayerResource other = (PlayerResource) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (posX == null) {
			if (other.posX != null)
				return false;
		} else if (!posX.equals(other.posX))
			return false;
		if (posY == null) {
			if (other.posY != null)
				return false;
		} else if (!posY.equals(other.posY))
			return false;
		if (posZ == null) {
			if (other.posZ != null)
				return false;
		} else if (!posZ.equals(other.posZ))
			return false;
		if (rotX == null) {
			if (other.rotX != null)
				return false;
		} else if (!rotX.equals(other.rotX))
			return false;
		if (rotY == null) {
			if (other.rotY != null)
				return false;
		} else if (!rotY.equals(other.rotY))
			return false;
		if (rotZ == null) {
			if (other.rotZ != null)
				return false;
		} else if (!rotZ.equals(other.rotZ))
			return false;
		return true;
	}
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private Float posX, posY, posZ;
	public Float getPosX() {
		return posX;
	}
	public void setPosX(Float posX) {
		this.posX = posX;
	}
	public Float getPosY() {
		return posY;
	}
	public void setPosY(Float posY) {
		this.posY = posY;
	}
	public Float getPosZ() {
		return posZ;
	}
	public void setPosZ(Float posZ) {
		this.posZ = posZ;
	}
	public Float getRotX() {
		return rotX;
	}
	public void setRotX(Float rotX) {
		this.rotX = rotX;
	}
	public Float getRotY() {
		return rotY;
	}
	public void setRotY(Float rotY) {
		this.rotY = rotY;
	}
	public Float getRotZ() {
		return rotZ;
	}
	public void setRotZ(Float rotZ) {
		this.rotZ = rotZ;
	}
	private Float rotX, rotY, rotZ;
	private Float currentSpeed;
	public Float getCurrentSpeed() {
		if(currentSpeed == null)
		{
			return 0f;
		}
		else
		{
			return currentSpeed;
		}
		
	}
	public void setCurrentSpeed(Float currentSpeed) {
		if(currentSpeed == null)
		{
			this.currentSpeed = 0f;
		} else {
			System.out.println(currentSpeed);
			this.currentSpeed = currentSpeed;
		}
	}
}
