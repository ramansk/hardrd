package com.crux.hardrd.controller;

import java.util.List;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.filter.LoggingFilter;

import com.crux.hardrd.Updates;

public class JacksonRestClient implements Client {
	private final WebTarget webTarget;

	public JacksonRestClient(String host) {
		ClientConfig clientConfig = new ClientConfig().property(ClientProperties.READ_TIMEOUT, 30000)
				.property(ClientProperties.CONNECT_TIMEOUT, 5000);

		webTarget = ClientBuilder.newClient(clientConfig).target(host);
	}

	@Override
	public void sendUpdatesToServer(Updates updates) {
		try {
			System.out.println(updates.getCurrentSpeed());
			String res = webTarget.path("/update").request().post(Entity.json(updates)).readEntity(String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<PlayerResource> getPlayersFromServer() {
		Response response = webTarget.path("/get").request().get(Response.class);
		List<PlayerResource> players = response.readEntity(new GenericType<List<PlayerResource>>() {
		});

		return players;
	}

	@Override
	public MapResource getMap(Integer id) {
		Response response = webTarget.path("/getTerrain/").path(id.toString()).request().get(Response.class);
		MapResource map = response.readEntity(new GenericType<MapResource>() {
		});

		return map;
	}
}
