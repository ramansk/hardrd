package com.crux.hardrd.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.nulldevice.NullSoundDevice;
import de.lessvoid.nifty.render.batch.BatchRenderDevice;
import de.lessvoid.nifty.renderer.lwjgl.input.LwjglInputSystem;
import de.lessvoid.nifty.renderer.lwjgl.render.LwjglBatchRenderBackendCoreProfileFactory;
import de.lessvoid.nifty.spi.time.impl.AccurateTimeProvider;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.crux.hardrd.DisplayManager;
import com.crux.hardrd.GameStateManager;
import com.crux.hardrd.Updates;
import com.crux.hardrd.entities.DynamicEntity;
import com.crux.hardrd.entities.Entity;
import com.crux.hardrd.entities.Player;
import com.crux.hardrd.fontMeshCreator.FontType;
import com.crux.hardrd.fontMeshCreator.GUIText;
import com.crux.hardrd.fontRendering.TextMaster;
import com.crux.hardrd.models.RawModel;
import com.crux.hardrd.models.TexturedModel;
import com.crux.hardrd.terrains.Terrain;
import com.crux.hardrd.test.Loader;
import com.crux.hardrd.test.OBJLoader;
import com.crux.hardrd.textures.ModelTexture;
import com.crux.hardrd.textures.TerrainTexture;
import com.crux.hardrd.textures.TerrainTexturePack;

public class ApplicationController {
    private String currentPlayerId;
    Loader loader = new Loader();
    FontType font;
    LwjglInputSystem input;

    public Nifty getNifty() {
        return nifty;
    }

    public void setNifty(Nifty nifty) {
        this.nifty = nifty;
    }

    Nifty nifty;
    public FontType getFont() {
        return font;
    }

    private LwjglInputSystem initInput() {
        LwjglInputSystem inputSystem = new LwjglInputSystem();
        try {
            inputSystem.startup();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return inputSystem;
    }

    private Nifty initNifty(final LwjglInputSystem inputSystem) throws Exception {
        return new Nifty(
                new BatchRenderDevice(LwjglBatchRenderBackendCoreProfileFactory.create()),
                new NullSoundDevice(),
                inputSystem,
                new AccurateTimeProvider());
    }

    public void setFont(FontType font) {
        this.font = font;
    }

    GUIText text;

    public Loader getLoader() {
        return loader;
    }

    private GameStateManager gsm;


    public GameStateManager getGsm() {
        return gsm;
    }

    public void setGsm(GameStateManager gsm) {
        this.gsm = gsm;
    }

    public ApplicationController() {
        TextMaster.init(loader);
        font = new FontType(loader.loadTexture("latin"), new File("res/latin.fnt"));
        input = initInput();
        try {
            nifty = initNifty(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        nifty.loadStyleFile("nifty-default-styles.xml");
        nifty.loadControlFile("nifty-default-controls.xml");



    }

       //add action response
    public void loadResources() {
        gsm = new GameStateManager(this);
    }

    public void updateState() {
        gsm.getCurrentState().update();
        DisplayManager.update();
        gsm.getCurrentState().draw();
    }

    public String getCurrentPlayerId() {
        return currentPlayerId;
    }

    public void setCurrentPlayerId(String currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }
}
