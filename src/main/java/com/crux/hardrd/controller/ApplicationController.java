package com.crux.hardrd.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public FontType getFont() {
        return font;
    }

    public void setFont(FontType font) {
        this.font = font;
    }

    GUIText text;

    public MapResource getMap(int colNum, int rowNum) {
        return client.getMap(colNum, rowNum);
    }

    public Loader getLoader() {
        return loader;
    }

    private GameStateManager gsm;
    private Client client;

    public GameStateManager getGsm() {
        return gsm;
    }

    public void setGsm(GameStateManager gsm) {
        this.gsm = gsm;
    }

    public ApplicationController() {
        TextMaster.init(loader);
        font = new FontType(loader.loadTexture("latin"), new File("res/latin.fnt"));

        client = new JacksonRestClient("http://localhost:8080");


    }

    TerrainTexturePack terrainTexturePack;
    TerrainTexture blendMap;

    public Boolean login(String username, String password) {
        return client.login(username, password);
    }

    public void register(String username, String password) {
        UserResource ur = new UserResource();
        ur.setName(username);
        ur.setPassword(password);
        client.createUser(ur);
    }

    public void createPlayer(Updates u) {
        client.createPlayer(u);
    }

    public UserResource getUser(String username) {
        return client.getUser(username);
    }

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

    //add action response
    public void loadResources() {
        //load static resources action
        //load dynamic resources action


        TerrainTexture bt = new TerrainTexture(loader.loadTexture("grass"));
        TerrainTexture r = new TerrainTexture(loader.loadTexture("dirt"));
        TerrainTexture g = new TerrainTexture(loader.loadTexture("pinkFlowers"));
        TerrainTexture b = new TerrainTexture(loader.loadTexture("path"));
        terrainTexturePack = new TerrainTexturePack(bt, r, g, b);
        blendMap = new TerrainTexture(loader.loadTexture("blendMap"));


        //ActionResponse response = new ActionResponse();
        //response.setState("main");
        gsm = new GameStateManager(this);
    }

    public void updateState() {
        gsm.getCurrentState().update();
        DisplayManager.update();
        gsm.getCurrentState().draw();
    }

    public void sendUpdatesToServer(Updates updates) {
        client.sendUpdatesToServer(updates);
    }

    public List<PlayerResource> getPlayers() {
        return client.getPlayersFromServer();
    }

    public PlayerResource getPlayer(String name) {
        return client.getPlayer(name);
    }

    public String getCurrentPlayerId() {
        return currentPlayerId;
    }

    public void setCurrentPlayerId(String currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }
}
