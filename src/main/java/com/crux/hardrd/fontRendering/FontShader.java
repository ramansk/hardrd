package com.crux.hardrd.fontRendering;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.crux.hardrd.shaders.ShaderProgram;


public class FontShader extends ShaderProgram{

	private static final String VERTEX_FILE =   "src/main/java/com/crux/hardrd/fontRendering/fontVertex.txt";
	private static final String FRAGMENT_FILE = "src/main/java/com/crux/hardrd/fontRendering/fontFragment.txt";
	
	private int location_colour;
	private int location_translation;
	
	public FontShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	public void getAllUniformLocations() {
		location_colour = super.getUniformLocation("colour");
		location_translation = super.getUniformLocation("translation");
	}

	@Override
	public void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}
	
	protected void loadColour(Vector3f colour){
		super.loadVector(location_colour, colour);
	}
	
	public void loadTranslation(Vector2f translation){
		super.load2DVector(location_translation, translation);
	}


}
