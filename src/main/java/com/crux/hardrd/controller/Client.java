package com.crux.hardrd.controller;

import java.util.List;

import com.crux.hardrd.Updates;

public interface Client {
    void sendUpdatesToServer(Updates updates);

    void createPlayer(Updates updates);

    List<PlayerResource> getPlayersFromServer();

    PlayerResource getPlayer(String name);

    MapResource getMap(int colNum, int RowNum);

    void createUser(UserResource user);

    Boolean login(String username, String password);

    UserResource getUser(String username);
}
