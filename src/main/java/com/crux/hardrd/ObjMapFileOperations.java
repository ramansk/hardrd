package com.crux.hardrd;

import com.crux.hardrd.entities.Entity;
import com.crux.hardrd.models.RawModel;
import com.crux.hardrd.models.TexturedModel;
import com.crux.hardrd.terrains.Terrain;
import com.crux.hardrd.test.Loader;
import com.crux.hardrd.test.OBJLoader;
import com.crux.hardrd.textures.ModelTexture;
import org.lwjgl.util.vector.Vector3f;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ObjMapFileOperations {
    public static  void save(float x, float z, int col, int row, String modelFileName, String textureFileName, float rotX, float rotY, float rotZ, int scale) {

        StringBuilder sb = new StringBuilder();
        sb.append(x).append(" ")
                .append(z).append(" ")
                .append(col).append(" ")//col
                .append(row).append(" ")//row
                .append(modelFileName).append(" ")
                .append(textureFileName).append(" ")
                .append(rotX).append(" ")
                .append(rotY).append(" ")
                .append(rotZ).append(" ")
                .append(scale);
        try (FileWriter fw = new FileWriter("objMap.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
            //exception handling left as an exercise for the reader
        }
    }

    public static List<Entity> read(String filename, Loader loader, Terrain terrain) throws IOException {
        List<Entity> entities = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
               String[] ideoms = line.split(" ");
               if (terrain.getCol() == Integer.valueOf(ideoms[2]) && terrain.getRow() == Integer.valueOf(ideoms[3]))
               {
                   Entity entity = createEntity(loader,terrain,Float.valueOf(ideoms[0]),Float.valueOf(ideoms[1]),ideoms[4],ideoms[5] );
                   entity.setRotX(Float.valueOf(ideoms[6]));
                   entity.setRotY(Float.valueOf(ideoms[7]));
                   entity.setRotZ(Float.valueOf(ideoms[8]));
                   entity.setScale(Float.valueOf(ideoms[9]));
                   entities.add(entity);
               }

            }
        }

        return entities;
    }

    public static  Entity createEntity(Loader loader, Terrain terrain, float x, float z, String modelFileName, String textureFileName)
    {
        RawModel model = OBJLoader.loadObjModel(modelFileName, loader);
        ModelTexture mt = new ModelTexture(loader.loadTexture(textureFileName));
        mt.setShineDamper(10000);
        mt.setReflectivity(0);
        TexturedModel tm = new TexturedModel(model, mt);

        return new Entity(tm, new Vector3f(x,terrain.getHeightOfTerrain(x, z),z), 0, 90, 0, 1);
    }
}
