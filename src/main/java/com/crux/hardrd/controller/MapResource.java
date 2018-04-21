package com.crux.hardrd.controller;

public class MapResource {
	private Integer id;
	private float [][] heights;
	public float[][] getHeights() {
		return heights;
	}

	public void setHeights(float[][] heights) {
		this.heights = heights;
	}

	private Float[] vertices;

	private Float[] textureCoords;

	private Float[] normals;

	private Integer[] indices;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float[] getVertices() {
		return vertices;
	}

	public void setVertices(Float[] vertices) {
		this.vertices = vertices;
	}

	public Float[] getTextureCoords() {
		return textureCoords;
	}

	public void setTextureCoords(Float[] textureCoords) {
		this.textureCoords = textureCoords;
	}

	public Float[] getNormals() {
		return normals;
	}

	public void setNormals(Float[] normals) {
		this.normals = normals;
	}

	public Integer[] getIndices() {
		return indices;
	}

	public void setIndices(Integer[] indices) {
		this.indices = indices;
	}
}
