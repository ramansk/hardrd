package com.crux.hardrd.controller;

import java.util.Map;

public class ActionResponse {
	String state;
	Map<String, Object> data;
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public <T> void  putData(String key, T val) {
		data.put(key, val);
	}
}
