package com.crux.hardrd.controller;

import java.util.concurrent.LinkedBlockingQueue;

import com.crux.hardrd.Updates;

public class UpdateEventsQueue {
	private static LinkedBlockingQueue<Updates> queue = new LinkedBlockingQueue<>();
	
	public static void put(Updates updates)
	{
		try {
			queue.put(updates);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static Updates get()
	{
		Updates result = null;
		try {
			result = queue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static int size()
	{		
		return queue.size();
	}
}
