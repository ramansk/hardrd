package com.crux.hardrd.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.crux.hardrd.models.RawModel;

public class OBJLoader {
	public static RawModel loadObjModel(String fileName, Loader loader)
	{
		FileReader fr = null;
		try {
			fr = new FileReader(new File("res/"+fileName+".obj"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(fr);
		String line;
		List<Vector3f> vertices = new ArrayList<Vector3f>();
		List<Vector2f> textures = new ArrayList<Vector2f>();
		List<Vector3f> normals = new ArrayList<Vector3f>();
		List<Integer> indices = new ArrayList<Integer>();
		float [] va = null;
		float [] ta = null;
		float [] na = null;
		int [] ia = null;
		try {
			while(true)
			{
				line = reader.readLine();
				String [] currentLine = line.split(" ");
				if(line.startsWith("v "))
				{
					Vector3f vertex = new Vector3f(
							Float.parseFloat(currentLine[1]),
							Float.parseFloat(currentLine[2]),
							Float.parseFloat(currentLine[3]));
					vertices.add(vertex);
				}else if(line.startsWith("vt "))
				{
					Vector2f texture = new Vector2f(
							Float.parseFloat(currentLine[1]),
							Float.parseFloat(currentLine[2]));
					textures.add(texture);
				}
				else if(line.startsWith("vn "))
				{
					Vector3f normalVector = new Vector3f(
							Float.parseFloat(currentLine[1]),
							Float.parseFloat(currentLine[2]),
							Float.parseFloat(currentLine[3]));
					normals.add(normalVector);
				}
				else if(line.startsWith("f "))
				{
					ta = new float[vertices.size()*2];
					na = new float[vertices.size()*3];
					break;
				}
				
			}
			
			while(line!=null)
			{
				if(!line.startsWith("f ")) {
					line = reader.readLine();
					continue;
				}
				String[] currentLine = line.split(" ");
				String[] vertex1 = currentLine[1].split("/");
				String[] vertex2 = currentLine[2].split("/");
				String[] vertex3 = currentLine[3].split("/");
				
				processVertex(vertex1, indices, textures, normals, ta, na);
				processVertex(vertex2, indices, textures, normals, ta, na);
				processVertex(vertex3, indices, textures, normals, ta, na);
				
				line = reader.readLine();
			}
			reader.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		va = new float[vertices.size()*3];
		ia = new int[indices.size()];
		
		int vertexPointer = 0;
		for(Vector3f vertex: vertices)
		{
			va[vertexPointer++] = vertex.x;
			va[vertexPointer++] = vertex.y;
			va[vertexPointer++] = vertex.z;
		}
		
		for (int i = 0; i < indices.size(); i++) {
			ia[i] = indices.get(i);
		}
		return loader.loadToVAO(va, ta, na, ia);
	}
	
	private static void processVertex(String[] vertexData, List<Integer> indices, List<Vector2f> textures,
			List<Vector3f> normals, float[] ta, float[] na) {
		int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
		indices.add(currentVertexPointer);
		Vector2f currentTex = textures.get(Integer.parseInt(vertexData[1])-1);
		ta[currentVertexPointer*2] = currentTex.x;
		ta[currentVertexPointer*2+1] = 1 - currentTex.y;
		Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2])-1);
		na[currentVertexPointer*3] = currentNorm.x;
		na[currentVertexPointer*3+1] = currentNorm.y;
		na[currentVertexPointer*3+2] = currentNorm.z;
		
	}
}
