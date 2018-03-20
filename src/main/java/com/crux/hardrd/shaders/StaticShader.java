package com.crux.hardrd.shaders;

import org.lwjgl.util.vector.Matrix4f;

public class StaticShader extends ShaderProgram {
	private static final String VS_FILE = "vertexShader.txt";
	private static final String FS_FILE = "fragmentShader.txt";
	private int loc_tm;
	public StaticShader() {
		super(VS_FILE, FS_FILE);
	}

	@Override
	public void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}

	@Override
	public void getAllUniformLocations() {
		loc_tm = super.getUniformLocation("transformationMatrix");
		
	}
	
	public void loadTrMatrix(Matrix4f matrix)
	{
		super.loadMatrix(loc_tm, matrix);
	}
}
