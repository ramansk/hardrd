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
			String res = webTarget.path("/update").request().post(Entity.json(updates)).readEntity(String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

    @Override
    public void createPlayer(Updates updates) {
        try {
            String res = webTarget.path("/create").request().post(Entity.json(updates)).readEntity(String.class);
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
    public PlayerResource getPlayer(String name) {
        Response response = webTarget.path("/getPlayer").path(name).request().get(Response.class);
        PlayerResource player= response.readEntity(new GenericType<PlayerResource>() {
        });

        return player;
    }

	@Override
	public MapResource getMap(int colNum, int rowNum) {
		Response response = webTarget
				.path("/getTerrain").path(String.valueOf(colNum)).path(String.valueOf(rowNum))
				//.queryParam("colNum", colNum)
				//.queryParam("rowNum", rowNum)
				.request()
				.get(Response.class);
		MapResource map = response.readEntity(new GenericType<MapResource>() {
		});

		return map;

	}

	@Override
	public void createUser(UserResource user)
	{
		try {
			String res = webTarget.path("/user/create").request().post(Entity.json(user)).readEntity(String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Boolean login(String username, String password)
	{
		Response response = webTarget.path("/user/login").path(username).path(password).request().get(Response.class);
		return response.readEntity(Boolean.class);
	}

	@Override
	public UserResource getUser(String username)
	{
		Response response = webTarget
				.path("/user/get").path(username)
				//.queryParam("colNum", colNum)
				//.queryParam("rowNum", rowNum)
				.request()
				.get(Response.class);
		UserResource userData = response.readEntity(new GenericType<UserResource>() {
		});

		return userData;
	}
}
