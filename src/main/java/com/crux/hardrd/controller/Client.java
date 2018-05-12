package com.crux.hardrd.controller;

import java.util.List;

import com.crux.hardrd.Updates;

public interface Client {
	void sendUpdatesToServer(Updates updates);
	List<PlayerResource> getPlayersFromServer();
	MapResource getMap(int colNum, int RowNum);
}
