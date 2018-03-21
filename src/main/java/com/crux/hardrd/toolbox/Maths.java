package com.crux.hardrd.toolbox;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import com.crux.hardrd.entities.Camera;

public class Maths {
	public static Matrix4f createTrMatrix(Vector3f translation, float rx, float ry, float rz, float scale)
	{
		Matrix4f m = new Matrix4f();
		m.setIdentity();
		Matrix4f.translate(translation, m, m);
		Matrix4f.rotate((float)Math.toRadians(rx), new Vector3f(1, 0,0), m,m);
		Matrix4f.rotate((float)Math.toRadians(ry), new Vector3f(0, 1,0), m,m);
		Matrix4f.rotate((float)Math.toRadians(rz), new Vector3f(0, 0,1), m,m);
		Matrix4f.scale(new Vector3f(scale, scale, scale), m, m);
		return m;
		
	}
	
	public static Matrix4f createViewMatrix(Camera camera)
	{
		Matrix4f m = new Matrix4f();
		m.setIdentity();
		Matrix4f.rotate((float)Math.toRadians(camera.getPitch()), new Vector3f(1, 0,0), m,m);
		Matrix4f.rotate((float)Math.toRadians(camera.getYaw()), new Vector3f(0, 1,0), m,m);
		Matrix4f.rotate((float)Math.toRadians(camera.getRoll()), new Vector3f(0, 0,1), m,m);
		Vector3f cameraPos = camera.getPosition();
		Vector3f negativeCameraPos = new Vector3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);
	//	Matrix4f.rotate((float)Math.toRadians(rz), new Vector3f(0, 0,1), m,m);
		Matrix4f.translate(negativeCameraPos, m, m);
		return m;
		
	}
}
