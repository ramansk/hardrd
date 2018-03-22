package com.crux.hardrd.shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import com.crux.hardrd.Light;
import com.crux.hardrd.entities.Camera;
import com.crux.hardrd.toolbox.Maths;

public class TerrainShader extends ShaderProgram {
	private static final String VS_FILE = "terrainVertexShader.txt";
	private static final String FS_FILE = "terrainFragmentShader.txt";
	private int loc_tm;
	private int loc_pm;
	private int loc_vm;
	private int loc_lightPosition;
	private int loc_lightColour;
	private int loc_shineDamper;
	private int loc_reflectivity;
	private int loc_skyColour;
	public TerrainShader() {
		super(VS_FILE, FS_FILE);
	}

	@Override
	public void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
	}

	@Override
	public void getAllUniformLocations() {
		loc_tm = super.getUniformLocation("transformationMatrix");
		loc_pm = super.getUniformLocation("projectionMatrix");
		loc_vm = super.getUniformLocation("viewMatrix");
		loc_lightPosition = super.getUniformLocation("lightPosition");
		loc_lightColour = super.getUniformLocation("lightColour");
		loc_shineDamper = super.getUniformLocation("shineDamper");
		loc_reflectivity = super.getUniformLocation("reflectivity");
		loc_skyColour = super.getUniformLocation("skyColour");
		
	}
	
	public void loadSkyColour(Vector3f rgb)
	{
		super.loadVector(loc_skyColour, rgb);
	}

	public void loadShineDamper(float sd)
	{
		super.loadF(loc_shineDamper, sd);
	}
	
	public void loadReflectivity(float rty)
	{
		super.loadF(loc_reflectivity, rty);
	}
	
	public void loadLightColour(Light light)
	{
		super.loadVector(loc_lightPosition, light.getPosition());
		
		super.loadVector(loc_lightColour, light.getColor());
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
