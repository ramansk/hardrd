package com.crux.hardrd.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerUpdateJob {
	private static final int WORKERS_COUNT = 1;
	private boolean running = true;
	private ApplicationController controller;
	private Client client;
	ExecutorService es;

	public ServerUpdateJob(ApplicationController controller) {
		super();
		this.controller = controller;
		es = Executors.newFixedThreadPool(WORKERS_COUNT);
		for (int i = 0; i < WORKERS_COUNT; i++) {
			es.submit(new ServerUpdateJobWorker());
		}
	}

	private class ServerUpdateJobWorker implements Runnable {
		@Override
		public void run() {
			while (running) {
				client.sendUpdatesToServer(UpdateEventsQueue.get());
			}
		}
	}
}
