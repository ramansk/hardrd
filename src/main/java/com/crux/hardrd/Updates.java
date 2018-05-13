package com.crux.hardrd;

public class Updates {
	private String playerId;
	private float playerPosX;
	private float playerPosY;
	private float playerPosZ;
	private float playerRotX;
	private float playerRotY;
	private float playerRotZ;
	private float currentSpeed;
	private String username;

	public float getCurrentSpeed() {
		return currentSpeed;
	}

	public Updates(String playerId, float playerPosX, float playerPosY, float playerPosZ, float playerRotX,
			float playerRotY, float playerRotZ, float currentSpeed) {
		super();
		this.playerId = playerId;
		this.playerPosX = playerPosX;
		this.playerPosY = playerPosY;
		this.playerPosZ = playerPosZ;
		this.playerRotX = playerRotX;
		this.playerRotY = playerRotY;
		this.playerRotZ = playerRotZ;
		this.currentSpeed = currentSpeed;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPlayerId() {
		return playerId;
	}

	public float getPlayerPosX() {
		return playerPosX;
	}

	public float getPlayerPosY() {
		return playerPosY;
	}

	public float getPlayerPosZ() {
		return playerPosZ;
	}

	public float getPlayerRotX() {
		return playerRotX;
	}

	public float getPlayerRotY() {
		return playerRotY;
	}

	public float getPlayerRotZ() {
		return playerRotZ;
	}
}
