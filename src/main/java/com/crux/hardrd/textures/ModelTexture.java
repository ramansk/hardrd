package com.crux.hardrd.textures;

public class ModelTexture {
	private int textureID;
	private float shineDamper;
	private float reflectivity;
	public float getShineDamper() {
		return shineDamper;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public float getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}

	public ModelTexture(int id) {
		this.textureID = id;
	}

	public int getTextureID() {
		return textureID;
	}
}
