package com.crux.hardrd;

import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.SphereShape;
import com.bulletphysics.collision.shapes.StaticPlaneShape;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;
import com.crux.hardrd.controller.*;
import com.crux.hardrd.entities.Camera;
import com.crux.hardrd.entities.DynamicEntity;
import com.crux.hardrd.entities.Entity;
import com.crux.hardrd.entities.Player;
import com.crux.hardrd.fontRendering.TextMaster;
import com.crux.hardrd.models.RawModel;
import com.crux.hardrd.models.TexturedModel;
import com.crux.hardrd.terrains.Terrain;
import com.crux.hardrd.test.Loader;
import com.crux.hardrd.test.MasterRenderer;
import com.crux.hardrd.test.OBJLoader;
import com.crux.hardrd.textures.ModelTexture;
import com.crux.hardrd.textures.TerrainTexture;
import com.crux.hardrd.textures.TerrainTexturePack;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.builder.*;
import de.lessvoid.nifty.controls.ButtonClickedEvent;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.nulldevice.NullSoundDevice;
import de.lessvoid.nifty.render.batch.BatchRenderDevice;
import de.lessvoid.nifty.renderer.lwjgl.input.LwjglInputSystem;
import de.lessvoid.nifty.renderer.lwjgl.render.LwjglBatchRenderBackendCoreProfileFactory;
import de.lessvoid.nifty.screen.DefaultScreenController;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.spi.time.impl.AccurateTimeProvider;
import de.lessvoid.nifty.tools.Color;
import de.lessvoid.nifty.tools.SizeValue;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class SinglePlayerGameState extends State {

	Nifty nifty;


	private List<Entity> entities = new ArrayList<>();
	private Player player;
	private Camera camera;
	private Light light;
	private Map<TerrainKey, Terrain> terrains = new ConcurrentHashMap<>();
    private MasterRenderer masterRenderer;
private boolean showMenu;
    private Map<String, DynamicEntity> otherPlayers = new ConcurrentHashMap<>();
    private List<PlayerResource> playersToAdd = new CopyOnWriteArrayList<>();
    private List<MapResource> mapsToAdd = new CopyOnWriteArrayList<>();
	private static Screen createMainScreen(final Nifty nifty, final ScreenController controller) {
		return new ScreenBuilder("start2") {{
			controller(controller);
			layer(new LayerBuilder("layer") {{
				childLayoutCenter();
				/*onStartScreenEffect(new EffectBuilder("fade") {{
					length(500);
					effectParameter("start", "#0");
					effectParameter("end", "#f");
				}});
				onEndScreenEffect(new EffectBuilder("fade") {{
					length(500);
					effectParameter("start", "#f");
					effectParameter("end", "#0");
				}});*/

				panel(new PanelBuilder() {{
					valignBottom();
					childLayoutVertical();
					/*text(new TextBuilder() {{
						text("Main Menu");
						style("base-font");
						color(Color.WHITE);
						alignCenter();
						valignCenter();
					}});*/
					panel(new PanelBuilder(){{
						valignBottom();
						height(SizeValue.px(10));
					}});
					control(new ButtonBuilder("addh", "Add House") {{
						alignRight();
						valignBottom();
					}});
					control(new ButtonBuilder("addtree", "Add Tree") {{
						alignRight();
						valignBottom();
					}});
					control(new ButtonBuilder("addh1", "Add House 1") {{
						alignRight();
						valignBottom();
					}});
                    control(new ButtonBuilder("addh2", "Add House 2") {{
                        alignRight();
                        valignBottom();
                    }});
				}});
			}});
		}}.build(nifty);
	}
	public class MyScreenController extends DefaultScreenController {
		@NiftyEventSubscriber(id="addh")
		public void addh(final String id, final ButtonClickedEvent event) {

		Entity cube = createEntity(controller.getLoader(), terrains.get(TerrainKey.create(0,0)), player.getPosition().x,player.getPosition().z,"test2", "wall", player.getRotX(), player.getRotY(), player.getRotZ(),10);
		ObjMapFileOperations.save(cube.getPosition().x, cube.getPosition().z, 0,0,"test2", "wall",cube.getRotX(), cube.getRotY(), cube.getRotZ(), 8);

		entities.add(cube);


		}

		@NiftyEventSubscriber(id="addh1")
		public void addh1(final String id, final ButtonClickedEvent event) {

			Entity cube = createEntity(controller.getLoader(), terrains.get(TerrainKey.create(0,0)), player.getPosition().x,player.getPosition().z,"house1", "house1", player.getRotX(), player.getRotY(), player.getRotZ(),1);
			ObjMapFileOperations.save(cube.getPosition().x, cube.getPosition().z, 0,0,"house1", "house1",cube.getRotX(), cube.getRotY(), cube.getRotZ(), 1);

			entities.add(cube);


		}

        @NiftyEventSubscriber(id="addh2")
        public void addh2(final String id, final ButtonClickedEvent event) {

            Entity cube = createEntity(controller.getLoader(), terrains.get(TerrainKey.create(0,0)), player.getPosition().x,player.getPosition().z,"house2", "house2", player.getRotX(), player.getRotY(), player.getRotZ(),1);
            ObjMapFileOperations.save(cube.getPosition().x, cube.getPosition().z, 0,0,"house2", "house2",cube.getRotX(), cube.getRotY(), cube.getRotZ(), 1);

            entities.add(cube);


        }

		@NiftyEventSubscriber(id="addtree")
		public void addtree(final String id, final ButtonClickedEvent event) {

			Entity cube = createEntity(controller.getLoader(), terrains.get(TerrainKey.create(0,0)), player.getPosition().x,player.getPosition().z,"lowPolyTree", "lowPolyTree", player.getRotX(), player.getRotY(), player.getRotZ(),1);
			ObjMapFileOperations.save(cube.getPosition().x, cube.getPosition().z, 0,0,"lowPolyTree", "lowPolyTree",cube.getRotX(), cube.getRotY(), cube.getRotZ(), 1);

			entities.add(cube);


		}
	}
		public SinglePlayerGameState(ApplicationController controller) {
		super(controller);



		try {
			nifty = controller.getNifty();
		} catch (Exception e) {
			e.printStackTrace();
		}


		//nifty.gotoScreen("start");



		TerrainTexture bt = new TerrainTexture(controller.getLoader().loadTexture("grass"));
		TerrainTexture r = new TerrainTexture(controller.getLoader().loadTexture("dirt"));
		TerrainTexture g = new TerrainTexture(controller.getLoader().loadTexture("pinkFlowers"));
		TerrainTexture b = new TerrainTexture(controller.getLoader().loadTexture("path"));

		terrainTexturePack = new TerrainTexturePack(bt, r, g, b);
		blendMap = new TerrainTexture(controller.getLoader().loadTexture("blendMap"));

		light = new Light(new Vector3f(20000,20000,2000), new Vector3f(1,1,1));
		masterRenderer = new MasterRenderer();

	
	
		//x 2700, z 2700
		//TODO: Add availability to sent global map row, col during player creation
		//TODO: Add availability to change model
		Player player = createPlayerEntity(controller.getLoader(), 50,50, "box", "box");
		player.setName("test");
		player.setGlobalMapColNum(0);
		player.setGlobalMapRowNum(0);
		Terrain terrain = new Terrain(0, 0, controller.getLoader(), getTerrainTexturePack(), getBlendMap(), "heightmap");
























		
		//generate entities
		/*Random rand = new Random();
		for(int i = 0; i< 100; i++)
		{
			Entity grass = createEntity(controller.getLoader(), terrain, rand.nextFloat()*500,rand.nextFloat()*500,"grassModel", "grassTexture");
			grass.getModel().getTexture().setHasTransparency(true);
			grass.getModel().getTexture().setUseFakeLighting(true);
			entities.add(grass);
			Entity tree = createEntity(controller.getLoader(), terrain, rand.nextFloat()*500,rand.nextFloat()*500,"lowPolyTree", "lowPolyTree");
			entities.add(tree);*/
			///entities.add(createEntity(r.nextFloat()*500,r.nextFloat()*500,"grassModel", "flower"));
			//entities.add(createEntity(r.nextFloat()*500,r.nextFloat()*500,"grassModel", "fern"));
			//entities.add(createEntity(r.nextFloat()*500,r.nextFloat()*500,"grassModel", "grassy"));
			/*ObjMapFileOperations.save(grass.getPosition().x, grass.getPosition().z, 0,0,"grassModel", "grassTexture",grass.getRotX(), grass.getRotY(), grass.getRotZ(), 1);
			ObjMapFileOperations.save(tree.getPosition().x, tree.getPosition().z, 0,0,"lowPolyTree", "lowPolyTree",tree.getRotX(), tree.getRotY(), tree.getRotZ(), 1);

		}*/

		/*Entity cube = createEntity(controller.getLoader(), terrain, 10,10,"test2", "wall");
		ObjMapFileOperations.save(cube.getPosition().x, cube.getPosition().z, 0,0,"test2", "wall",cube.getRotX(), cube.getRotY(), cube.getRotZ(), 1);

		entities.add(cube);
		Entity stall = createEntity(controller.getLoader(), terrain, 30,30,"stall", "stallTexture");
		entities.add(stall);

		ObjMapFileOperations.save(stall.getPosition().x, stall.getPosition().z, 0,0,"stall", "stallTexture",stall.getRotX(), stall.getRotY(), stall.getRotZ(), 1);
*/
		try {
			entities.addAll(ObjMapFileOperations.read("objMap.txt", controller.getLoader(), terrain));
		} catch (IOException e) {
			e.printStackTrace();
		}
		applyPlayer(player);
		applyTerrain(terrain);
		//gsm.getState("main").applyStaticEntities(entities);
			createMainScreen(nifty, new MyScreenController());
			nifty.gotoScreen("start2");
	}

	@Override
	public void applyStaticEntities(List<Entity> entity) {
		this.entities = entity;		
	}

	@Override
	public void applyPlayer(Player player) {
		this.player = player;
		camera = new Camera(player);
	}

	@Override
	public void applyTerrain(Terrain terrain) {
		//this.terrain = terrain;
		terrains.put(TerrainKey.create(terrain.getCol(), terrain.getRow()), terrain);
	}
	private long lastUpdateTime;
	@Override
	public void update() {
		if (Keyboard.isKeyDown(Keyboard.KEY_0)) {
			System.out.println("!");
			showMenu = !showMenu;
		}
		long start = System.nanoTime();
		//entity.increasePosition(0, 0, 0);
		//entity.increaseRotation(0, 1, 0);
		camera.move();
		player.move(terrains.get(TerrainKey.create(player.getGlobalMapColNum(), player.getGlobalMapRowNum())), entities);
		
	 }
	boolean stopSignalSent = false;
	@Override
	public void draw() {

		//Random rand = new Random();
		//entities.add(controller.createEntity(controller.getLoader(), terrain, rand.nextFloat()*500,rand.nextFloat()*500,"lowPolyTree", "lowPolyTree"));
		for(Terrain terrain : terrains.values())
		{
			masterRenderer.processEntity(terrain);
		}
		
		for(Entity entity : entities)
		{
			masterRenderer.processEntity(entity);
		}
		for(Entity p : otherPlayers.values())
		{
			masterRenderer.processEntity(p);
		}
		masterRenderer.processEntity(player);
		masterRenderer.render(light, camera);
		//TextMaster.render();
		if(showMenu)
		{
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			if (nifty.update()) {

			}
			nifty.render(false);
			int error = GL11.glGetError();
			if (error != GL11.GL_NO_ERROR) {
				String glerrmsg = GLU.gluErrorString(error);
				System.err.println(glerrmsg);
			}
		}



	}

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub
		
	}

	public Entity createEntity(Loader loader, Terrain terrain, float x, float z, String modelFileName, String textureFileName, float rotx, float roty, float rotz, int scale)
	{
		RawModel model = OBJLoader.loadObjModel(modelFileName, loader);
		ModelTexture mt = new ModelTexture(loader.loadTexture(textureFileName));
		mt.setShineDamper(10000);
		mt.setReflectivity(0);
		TexturedModel tm = new TexturedModel(model, mt);

		return new Entity(tm, new Vector3f(x,terrain.getHeightOfTerrain(x, z),z), rotx, roty, rotz, scale);
	}
	
    public Entity createEntity(Loader loader, Terrain terrain, float x, float z, String modelFileName, String textureFileName)
    {
    	RawModel model = OBJLoader.loadObjModel(modelFileName, loader);
    	ModelTexture mt = new ModelTexture(loader.loadTexture(textureFileName));
		mt.setShineDamper(10000);
		mt.setReflectivity(0);
    	TexturedModel tm = new TexturedModel(model, mt);
    	
    	return new Entity(tm, new Vector3f(x,terrain.getHeightOfTerrain(x, z),z), 0, 90, 0, 1);
    }
    
    public DynamicEntity createDynamicEntity(Loader loader, Terrain terrain, float x, float z, String modelFileName, String textureFileName, float currentSpeed)
    {
    	RawModel model = OBJLoader.loadObjModel(modelFileName, loader);
    	ModelTexture mt = new ModelTexture(loader.loadTexture(textureFileName));
		mt.setShineDamper(10000);
		mt.setReflectivity(0);
    	TexturedModel tm = new TexturedModel(model, mt);
    	
    	return new DynamicEntity(tm, new Vector3f(x,terrain.getHeightOfTerrain(x, z),z), 0, 90, 0, 1, currentSpeed);
    }
    
    public Player createPlayerEntity(Loader loader, float x, float z, String modelFileName, String textureFileName)
    {
    	RawModel model = OBJLoader.loadObjModel(modelFileName, loader);
    	ModelTexture mt = new ModelTexture(loader.loadTexture(textureFileName));
		mt.setShineDamper(10000);
		mt.setReflectivity(0);
    	TexturedModel tm = new TexturedModel(model, mt);
    	
    	return new Player(tm, new Vector3f(x,0,z), 0, 90, 0, 1);
    	
    }

	TerrainTexturePack terrainTexturePack;
	TerrainTexture blendMap;

	public TerrainTexture getBlendMap() {
		return blendMap;
	}

	public TerrainTexturePack getTerrainTexturePack() {
		return terrainTexturePack;
	}

	public void setTerrainTexturePack(TerrainTexturePack terrainTexturePack) {
		this.terrainTexturePack = terrainTexturePack;
	}

	public void setBlendMap(TerrainTexture blendMap) {
		this.blendMap = blendMap;
	}


}
