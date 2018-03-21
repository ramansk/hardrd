package com.crux.hardrd.shaders;

import org.lwjgl.util.vector.Matrix4f;

import com.crux.hardrd.entities.Camera;
import com.crux.hardrd.toolbox.Maths;

public class StaticShader extends ShaderProgram {
	private static final String VS_FILE = "vertexShader.txt";
	private static final String FS_FILE = "fragmentShader.txt";
	private int loc_tm;
	private int loc_pm;
	private int loc_vm;
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
		loc_pm = super.getUniformLocation("projectionMatrix");
		loc_vm = super.getUniformLocation("viewMatrix");
		
	}
	
	public void loadTrMatrix(Matrix4f matrix)
	{
		super.loadMatrix(loc_tm, matrix);
	}
	
	public void loadPrMatrix(Matrix4f matrix)
	{
		super.loadMatrix(loc_pm, matrix);
	}
	
	public void loadViewMatrix(Camera camera)
	{
		super.loadMatrix(loc_vm, Maths.createViewMatrix(camera));
	}
}