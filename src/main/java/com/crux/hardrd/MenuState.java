package com.crux.hardrd;

import java.io.File;
import java.util.List;

import com.crux.hardrd.controller.Client;
import com.crux.hardrd.controller.PlayerResource;
import com.crux.hardrd.controller.UserResource;
import de.lessvoid.nifty.controls.ListBox;
import de.lessvoid.nifty.controls.listbox.builder.ListBoxBuilder;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.crux.hardrd.controller.ApplicationController;
import com.crux.hardrd.entities.Camera;
import com.crux.hardrd.entities.Entity;
import com.crux.hardrd.entities.Player;
import com.crux.hardrd.fontMeshCreator.FontType;
import com.crux.hardrd.fontMeshCreator.GUIText;
import com.crux.hardrd.fontRendering.TextMaster;
import com.crux.hardrd.terrains.Terrain;
import com.crux.hardrd.test.MasterRenderer;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.builder.ControlBuilder;
import de.lessvoid.nifty.builder.EffectBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.controls.ButtonClickedEvent;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.controls.TextFieldChangedEvent;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.controls.textfield.builder.TextFieldBuilder;
import de.lessvoid.nifty.elements.Element;
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
//TODO:Add Error Flow
//TODO: Refactor class
//TODO: Add "back" flow
public class MenuState extends State{
	//private static final float SKY_R = 0.5f;
	//private static final float SKY_G = 0.5f;
	//private static final float SKY_B = 1.5f;
	//GUIText singlePlayer;
	//GUIText multiPlayer;
	//private Light light;
	private Client client;
	//private Camera camera;
	 private MasterRenderer masterRenderer;
	 Nifty nifty;
	public MenuState(ApplicationController controller, Client client) {
		super(controller);

		this.nifty = controller.getNifty();
		this.client = client;
		//singlePlayer = new GUIText("Single player",1f,controller.getFont(),new Vector2f(0.45f,0.4f),0.5f, false);
		//multiPlayer = new GUIText("Multiplayer",1f,controller.getFont(),new Vector2f(0.45f,0.5f),0.5f, false);
		//masterRenderer = new MasterRenderer();
		//light = new Light(new Vector3f(20000,20000,2000), new Vector3f(1,1,1));
	
	
	

		 createMainScreen(nifty, new MyScreenController());
		 serverConnectOperationsScreen(nifty, new MyScreenController());
		 chooseServerOperationsScreen(nifty, new MyScreenController());
		 loginScreen(nifty, new MyScreenController());
		 playerManagementScreen(nifty, new MyScreenController());
        registerScreen(nifty, new MyScreenController());
        createPlayerScreen(nifty, new MyScreenController());
		 nifty.gotoScreen("start");
	}
	
	 private static Screen createMainScreen(final Nifty nifty, final ScreenController controller) {
		    return new ScreenBuilder("start") {{
		      controller(controller);
		      layer(new LayerBuilder("layer") {{
		        childLayoutCenter();
		        onStartScreenEffect(new EffectBuilder("fade") {{
		          length(500);
		          effectParameter("start", "#0");
		          effectParameter("end", "#f");
		        }});
		        onEndScreenEffect(new EffectBuilder("fade") {{
		          length(500);
		          effectParameter("start", "#f");
		          effectParameter("end", "#0");
		        }});
		        onActiveEffect(new EffectBuilder("gradient") {{
		          effectValue("offset", "0%", "color", "#333f");
		          effectValue("offset", "100%", "color", "#ffff");
		        }});
		        panel(new PanelBuilder() {{
		          childLayoutVertical();
		          text(new TextBuilder() {{
		            text("Main Menu");
		            style("base-font");
		            color(Color.BLACK);
		            alignCenter();
		            valignCenter();
		          }});
		          panel(new PanelBuilder(){{
		            height(SizeValue.px(10));
		          }});
		          control(new ButtonBuilder("singleplayer", "Single Player") {{

		            alignCenter();
		            valignCenter();
		          }});
		          control(new ButtonBuilder("multiplayer", "Multiplayer") {{
			            alignCenter();
			            valignCenter();
			          }});
		          control(new ButtonBuilder("settings", "Settings") {{
			            alignCenter();
			            valignCenter();
			          }});
		          control(new ButtonBuilder("exit", "exit") {{
			            alignCenter();
			            valignCenter();
			          }});
		        }});
		      }});
		    }}.build(nifty);
		  }
	 
	 
	 
	 private static Screen serverConnectOperationsScreen(final Nifty nifty, final ScreenController controller) {
		    return new ScreenBuilder("connectToServer") {{
		      controller(controller);
		      layer(new LayerBuilder("layer") {{
		        childLayoutCenter();
		        onStartScreenEffect(new EffectBuilder("fade") {{
		          length(500);
		          effectParameter("start", "#0");
		          effectParameter("end", "#f");
		        }});
		        onEndScreenEffect(new EffectBuilder("fade") {{
		          length(500);
		          effectParameter("start", "#f");
		          effectParameter("end", "#0");
		        }});
		        onActiveEffect(new EffectBuilder("gradient") {{
		          effectValue("offset", "0%", "color", "#333f");
		          effectValue("offset", "100%", "color", "#ffff");
		        }});
		        panel(new PanelBuilder() {{
		          childLayoutVertical();
		          text(new TextBuilder() {{
		            text("Server Operations");
		            style("base-font");
		            color(Color.BLACK);
		            alignCenter();
		            valignCenter();
		          }});
		          panel(new PanelBuilder(){{
		            height(SizeValue.px(10));
		          }});
		          control(new ButtonBuilder("login", "Log In") {{
		            alignCenter();
		            valignCenter();
		          }});
		          control(new ButtonBuilder("register", "Register") {{
			            alignCenter();
			            valignCenter();
			          }});
		          control(new ButtonBuilder("settings", "Settings") {{
			            alignCenter();
			            valignCenter();
			          }});
		          control(new ButtonBuilder("back", "back") {{
			            alignCenter();
			            valignCenter();
			          }});
		        }});
		      }});
		    }}.build(nifty);
		  }
	 
	 
	 private static Screen chooseServerOperationsScreen(final Nifty nifty, final ScreenController controller) {
		    return new ScreenBuilder("serverLogIn") {{
		      controller(controller);
		      layer(new LayerBuilder("layer") {{
		        childLayoutCenter();
		        onStartScreenEffect(new EffectBuilder("fade") {{
		          length(500);
		          effectParameter("start", "#0");
		          effectParameter("end", "#f");
		        }});
		        onEndScreenEffect(new EffectBuilder("fade") {{
		          length(500);
		          effectParameter("start", "#f");
		          effectParameter("end", "#0");
		        }});
		        onActiveEffect(new EffectBuilder("gradient") {{
		          effectValue("offset", "0%", "color", "#333f");
		          effectValue("offset", "100%", "color", "#ffff");
		        }});
		        panel(new PanelBuilder() {{
		          childLayoutVertical();
		          text(new TextBuilder() {{
		            text("Server Operations");
		            style("base-font");
		            color(Color.BLACK);
		            alignCenter();
		            valignCenter();
		          }});
		          panel(new PanelBuilder(){{
		            height(SizeValue.px(10));
		          }});
		          control(new ButtonBuilder("connect", "Connect To Server") {{
		            alignCenter();
		            valignCenter();
		          }});
		        }});
		      }});
		    }}.build(nifty);
		  }
	 
	 
	 private static Screen playerManagementScreen(final Nifty nifty, final ScreenController controller) {
		    return new ScreenBuilder("playerManagement") {{
		      controller(controller);
		      layer(new LayerBuilder("layer") {{
		        childLayoutCenter();
		        onStartScreenEffect(new EffectBuilder("fade") {{
		          length(500);
		          effectParameter("start", "#0");
		          effectParameter("end", "#f");
		        }});
		        onEndScreenEffect(new EffectBuilder("fade") {{
		          length(500);
		          effectParameter("start", "#f");
		          effectParameter("end", "#0");
		        }});
		        onActiveEffect(new EffectBuilder("gradient") {{
		          effectValue("offset", "0%", "color", "#333f");
		          effectValue("offset", "100%", "color", "#ffff");
		        }});
		        panel(new PanelBuilder() {{
		          childLayoutVertical();
		          text(new TextBuilder() {{
		            text("Server Operations");
		            style("base-font");
		            color(Color.BLACK);
		            alignCenter();
		            valignCenter();
		          }});
		          panel(new PanelBuilder(){{
		            height(SizeValue.px(10));
		          }});
                    control(new ListBoxBuilder("avaliablePlayersListBox") {{
                        displayItems(4);
                        selectionModeMutliple();
                        optionalHorizontalScrollbar();
                        optionalVerticalScrollbar();
                        width("*"); // standard nifty width attribute
                    }});
                    control(new ButtonBuilder("createPlayer", "Create User") {{
                        marginTop("10");
                        alignCenter();
                        valignCenter();
                    }});
		          control(new ButtonBuilder("play", "Play For The Selected Player") {{
		            alignCenter();
		            valignCenter();
		          }});
		        }});
		      }});
		    }}.build(nifty);
		  }
	 
	 private static Screen loginScreen(final Nifty nifty, final ScreenController controller) {
		    return new ScreenBuilder("serverLogIn") {{
		      controller(controller);
		      layer(new LayerBuilder("layer") {{
		        childLayoutCenter();
		        onStartScreenEffect(new EffectBuilder("fade") {{
		          length(500);
		          effectParameter("start", "#0");
		          effectParameter("end", "#f");
		        }});
		        onEndScreenEffect(new EffectBuilder("fade") {{
		          length(500);
		          effectParameter("start", "#f");
		          effectParameter("end", "#0");
		        }});
		        onActiveEffect(new EffectBuilder("gradient") {{
		          effectValue("offset", "0%", "color", "#333f");
		          effectValue("offset", "100%", "color", "#ffff");
		        }});
		        panel(new PanelBuilder() {{
		          childLayoutVertical();
		          text(new TextBuilder() {{
		            text("Server Operations");
		            style("base-font");
		            color(Color.BLACK);
		            alignCenter();
		            valignCenter();
		          }});
		          panel(new PanelBuilder(){{
		            height(SizeValue.px(10));
		          }});


		          control(new TextFieldBuilder("username", "username") {{ // init with the text "*"
		        	  maxLength(10); // force only a single character input
		        	  width("100px");
		        	  alignCenter();
		        	  valignCenter();
		        	}});
		          control(new TextFieldBuilder("password", "password") {{ // init with the text "*"
		        	  maxLength(10); // force only a single character input
		        	  passwordChar('*');
		        	  width("100px");
		        	  alignCenter();
		        	  valignCenter();
		        	}});
		          control(new ButtonBuilder("userInfoCheck", "Log In") {{
			            alignCenter();
			            valignCenter();
			          }});
                    control(new ButtonBuilder("register", "Register") {{
                        marginTop("10");
                        alignCenter();
                        valignCenter();
                    }});
		        }});
		      }});
		    }}.build(nifty);
		  }

    private static Screen registerScreen(final Nifty nifty, final ScreenController controller) {
        return new ScreenBuilder("register") {{
            controller(controller);
            layer(new LayerBuilder("layer") {{
                childLayoutCenter();
                onStartScreenEffect(new EffectBuilder("fade") {{
                    length(500);
                    effectParameter("start", "#0");
                    effectParameter("end", "#f");
                }});
                onEndScreenEffect(new EffectBuilder("fade") {{
                    length(500);
                    effectParameter("start", "#f");
                    effectParameter("end", "#0");
                }});
                onActiveEffect(new EffectBuilder("gradient") {{
                    effectValue("offset", "0%", "color", "#333f");
                    effectValue("offset", "100%", "color", "#ffff");
                }});
                panel(new PanelBuilder() {{
                    childLayoutVertical();
                    text(new TextBuilder() {{
                        text("Server Operations");
                        style("base-font");
                        color(Color.BLACK);
                        alignCenter();
                        valignCenter();
                    }});
                    panel(new PanelBuilder(){{
                        height(SizeValue.px(10));
                    }});

                    text(new TextBuilder() {{
                        text("Username");
                        style("base-font");
                        color(Color.BLACK);
                        alignCenter();
                        valignCenter();
                    }});
                    control(new TextFieldBuilder("username", "username") {{ // init with the text "*"
                        maxLength(10); // force only a single character input
                        width("100px");
                        alignCenter();
                        valignCenter();
                    }});
                    text(new TextBuilder() {{
                        text("Password");
                        style("base-font");
                        color(Color.BLACK);
                        alignCenter();
                        valignCenter();
                    }});
                    control(new TextFieldBuilder("password") {{ // init with the text "*"
                        maxLength(10); // force only a single character input
                        passwordChar('*');
                        width("100px");
                        alignCenter();
                        valignCenter();
                    }});
                    text(new TextBuilder() {{
                        text("Repeat Pasword");
                        style("base-font");
                        color(Color.BLACK);
                        alignCenter();
                        valignCenter();
                    }});
                    control(new TextFieldBuilder("passwordRepeat") {{ // init with the text "*"
                        maxLength(10); // force only a single character input
                        passwordChar('*');
                        width("100px");
                        alignCenter();
                        valignCenter();
                    }});
                    control(new ButtonBuilder("registerEnd", "Register") {{
                        marginTop("20");
                        alignCenter();
                        valignCenter();
                    }});
                }});
            }});
        }}.build(nifty);
    }


    private static Screen createPlayerScreen(final Nifty nifty, final ScreenController controller) {
        return new ScreenBuilder("createPlayer") {{
            controller(controller);
            layer(new LayerBuilder("layer") {{
                childLayoutCenter();
                onStartScreenEffect(new EffectBuilder("fade") {{
                    length(500);
                    effectParameter("start", "#0");
                    effectParameter("end", "#f");
                }});
                onEndScreenEffect(new EffectBuilder("fade") {{
                    length(500);
                    effectParameter("start", "#f");
                    effectParameter("end", "#0");
                }});
                onActiveEffect(new EffectBuilder("gradient") {{
                    effectValue("offset", "0%", "color", "#333f");
                    effectValue("offset", "100%", "color", "#ffff");
                }});
                panel(new PanelBuilder() {{
                    childLayoutVertical();
                    text(new TextBuilder() {{
                        text("Server Operations");
                        style("base-font");
                        color(Color.BLACK);
                        alignCenter();
                        valignCenter();
                    }});
                    panel(new PanelBuilder(){{
                        height(SizeValue.px(10));
                    }});

                    text(new TextBuilder() {{
                        text("Player Name");
                        style("base-font");
                        color(Color.BLACK);
                        alignCenter();
                        valignCenter();
                    }});
                    control(new TextFieldBuilder("playername") {{ // init with the text "*"
                        maxLength(10); // force only a single character input
                        width("100px");
                        alignCenter();
                        valignCenter();
                    }});

                    control(new ButtonBuilder("createPlayerEnd", "Create") {{
                        marginTop("20");
                        alignCenter();
                        valignCenter();
                    }});
                }});
            }});
        }}.build(nifty);
    }

	@Override
	public void update() {
		
		if (nifty.update()) {
	        
	      }
	      nifty.render(true);
	      int error = GL11.glGetError();
	      if (error != GL11.GL_NO_ERROR) {
	        String glerrmsg = GLU.gluErrorString(error);
	        System.err.println(glerrmsg);
	      }
		
		
		/*if(Mouse.isButtonDown(0))
		{	
			if(Mouse.getX() > Display.getWidth()*0.45f && Mouse.getX() < Display.getWidth()*0.65f
					&& Mouse.getY() > Display.getHeight()*0.5f && Mouse.getY() < Display.getHeight()*0.6f)
			{
				System.out.println("Single Player");
			}
			if(Mouse.getX() > Display.getWidth()*0.45f && Mouse.getX() < Display.getWidth()*0.65f
					&& Mouse.getY() > Display.getHeight()*0.4f && Mouse.getY() < Display.getHeight()*0.5f)
			{
				System.out.println("Multiplayer");
				controller.getGsm().setCurrentState("test");
			}
			System.out.println(Mouse.getX()+ " "+ Mouse.getY());
		}	*/	
	}

	@Override
	public void draw() {
	//	GL11.glEnable(GL11.GL_DEPTH_TEST);
	//	GL11.glClearColor(SKY_R,SKY_G, SKY_B, 1);
	//	GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
	//	TextMaster.render();
	}

	@Override
	public void applyStaticEntities(List<Entity> entity) {
	}

	@Override
	public void applyPlayer(Player player) {
	}

	@Override
	public void applyTerrain(Terrain terrain) {
	}

	@Override
	public void cleanUp() {
		
		//TextMaster.cleanUp();
	}

	public class MyScreenController extends DefaultScreenController {

	   @NiftyEventSubscriber(id="multiplayer")
	    public void multiplayer(final String id, final ButtonClickedEvent event) {
	    
		   System.out.println("Multiplayer");
		   nifty.gotoScreen("serverLogIn");
	     //nifty.exit();
	     //controller.getGsm().setCurrentState("test");
	    // input.shutdown();
	     //input.shutdown();
	    }
        @NiftyEventSubscriber(id="play")
        public void play(final String id, final ButtonClickedEvent event) {

            ListBox listBox = nifty.getCurrentScreen().findNiftyControl("avaliablePlayersListBox", ListBox.class);
            System.out.println(listBox.getFocusItem());
            controller.setCurrentPlayerId(String.valueOf(listBox.getFocusItem()));
            nifty.exit();
            controller.getGsm().setCurrentState("test");
            //input.shutdown();
        }
	
	   @NiftyEventSubscriber(id="singleplayer")
	    public void singleplayer(final String id, final ButtonClickedEvent event) {
		   controller.getGsm().setCurrentState("singlePlayer");
		   System.out.println("Single Player");
	    // nifty.exit();
	    // controller.getGsm().setCurrentState("test");
	    // input.shutdown();
	     //input.shutdown();
	    }
	   
	   @NiftyEventSubscriber(id="settings")
	    public void settings(final String id, final ButtonClickedEvent event) {
	    
		   System.out.println("settings");
	     //nifty.exit();
	    // controller.getGsm().setCurrentState("test");
	    // input.shutdown();
	     //input.shutdown();
	    }
	   
	   @NiftyEventSubscriber(id="exit")
	    public void exit(final String id, final ButtonClickedEvent event) {
	    
		   System.out.println("Exit");
	     //nifty.exit();
	    // controller.getGsm().setCurrentState("test");
	    // input.shutdown();
	     //input.shutdown();
	    }
	   
	   
	   @NiftyEventSubscriber(id="connect")
	    public void connect(final String id, final ButtonClickedEvent event) {
	    
		   nifty.gotoScreen("connectToServer");
	     //nifty.exit();
	    // controller.getGsm().setCurrentState("test");
	    // input.shutdown();
	     //input.shutdown();
	    }
        @NiftyEventSubscriber(id="createPlayer")
        public void createPlayer(final String id, final ButtonClickedEvent event) {

            nifty.gotoScreen("createPlayer");
            //nifty.exit();
            // controller.getGsm().setCurrentState("test");
            // input.shutdown();
            //input.shutdown();
        }

	   
	   @NiftyEventSubscriber(id="login")
	    public void login(final String id, final ButtonClickedEvent event) {

		   nifty.gotoScreen("serverLogIn");
	     //nifty.exit();
	    // controller.getGsm().setCurrentState("test");
	    // input.shutdown();
	     //input.shutdown();
	    }

        @NiftyEventSubscriber(id="register")
        public void  register(final String id, final ButtonClickedEvent event) {

            nifty.gotoScreen("register");
            //nifty.exit();
            // controller.getGsm().setCurrentState("test");
            // input.shutdown();
            //input.shutdown();
        }

        @NiftyEventSubscriber(id="registerEnd")
        public void  registerEnd(final String id, final ButtonClickedEvent event) {
            //TODO: Add "Register completed window" or something like this.

            TextField un = nifty.getCurrentScreen().findNiftyControl("username", TextField.class);
            //TODO:Add Passrowd validation ( first password must be equal with second password".

            TextField psw = nifty.getCurrentScreen().findNiftyControl("password", TextField.class);
            TextField pswRepeat = nifty.getCurrentScreen().findNiftyControl("password", TextField.class);
			UserResource ur = new UserResource();
			ur.setName(un.getRealText());
			ur.setPassword(psw.getRealText());
			client.createUser(ur);

            nifty.gotoScreen("serverLogIn");
            //nifty.exit();
            // controller.getGsm().setCurrentState("test");
            // input.shutdown();
            //input.shutdown();
        }

        @NiftyEventSubscriber(id="createPlayerEnd")
        public void  createPlayerEnd(final String id, final ButtonClickedEvent event) {
            //TODO: Add "Register completed window" or something like this.

            TextField un = nifty.getCurrentScreen().findNiftyControl("playername", TextField.class);
            //TODO:Add Passrowd validation ( first password must be equal with second password".
            Updates u = new Updates(un.getRealText(), 2700f,0f,2700f,0f,0f,0f, 0f);
            u.setUsername(nifty.getScreen("serverLogIn").findNiftyControl("username", TextField.class).getRealText());
            System.out.println(nifty.getScreen("serverLogIn").findNiftyControl("username", TextField.class).getRealText());
            client.createPlayer(u);
            ListBox listBox = nifty.getScreen("playerManagement").findNiftyControl("avaliablePlayersListBox", ListBox.class);
            listBox.addItem(un.getRealText());
            nifty.gotoScreen("playerManagement");
            //nifty.exit();
            // controller.getGsm().setCurrentState("test");
            // input.shutdown();
            //input.shutdown();
        }



	   @NiftyEventSubscriber(id="userInfoCheck")
	    public void userInfoCheck(final String id, final ButtonClickedEvent event) {
		 //  event.getButton().
		   Element username = nifty.getCurrentScreen().findElementById("username");
		   TextField un = nifty.getCurrentScreen().findNiftyControl("username", TextField.class);
		   TextField psw = nifty.getCurrentScreen().findNiftyControl("password", TextField.class);

		   Boolean loggedIn = client.login(un.getRealText(), psw.getRealText());
		   //System.out.println(un.getDisplayedText());
		  // System.out.println(un.getRealText());
		  // System.out.println(psw.getDisplayedText());
		   //System.out.println(psw.getRealText());
           //TODO:Implement clear list box mechanism
		   if(loggedIn)
		   {
		   	System.out.println("Done!");
               ListBox listBox = nifty.getScreen("playerManagement").findNiftyControl("avaliablePlayersListBox", ListBox.class);
               UserResource ur = client.getUser(un.getRealText());
               for(PlayerResource pr : ur.getPlayers())
               {
                   listBox.addItem(pr.getName());
               }

			   nifty.gotoScreen("playerManagement" );
		   }

	     //nifty.exit();
	    // controller.getGsm().setCurrentState("test");
	    // input.shutdown();
	     //input.shutdown();
	    }
	   
	   
	  }
}
