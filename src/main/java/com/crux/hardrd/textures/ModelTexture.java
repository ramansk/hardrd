package com.crux.hardrd.textures;

public class ModelTexture {
	private int textureID;
	private float shineDamper;
	private float reflectivity;
	private boolean hasTransparency;
	private boolean useFakeLighting;
	public float getShineDamper() {
		return shineDamper;
	}

	public boolean isUseFakeLighting() {
		return useFakeLighting;
	}

	public void setUseFakeLighting(boolean useFakeLighting) {
		this.useFakeLighting = useFakeLighting;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public boolean isHasTransparency() {
		return hasTransparency;
	}

	public void setHasTransparency(boolean hasTransparency) {
		this.hasTransparency = hasTransparency;
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
