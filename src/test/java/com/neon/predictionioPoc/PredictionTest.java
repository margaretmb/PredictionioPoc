package com.neon.predictionioPoc;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import io.prediction.Client;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class PredictionTest {

	private static Client client;
	private static Importer importer;
	private static Set<String> users;
	private static Map<String, Set<String>> watchedMovies;
	private static Map<String, String> movies;

	@BeforeClass
	public static void before() {
		String apiKey = "31biODHABXLSrrf76E3I4Ll0pO0v3s59BdOOr92mimQQuZU0gCnzXl8vTYsOOG8s";
		String url = "http://localhost:8000";
		client = new Client(apiKey, url);
		importer = new Importer();
		users = Importer.generateMockUids();
		movies = Importer.generateMockMovies();
		watchedMovies = Importer.generateMockWatchedMovies();
	}

	@Test
	public void clientShouldAddSomeUsers() {
		try {
			for (String user : users) {
				client.createUser(user);
			} 


			for (int i = 1; i <= 4; i++) {
				assertNotNull(client.getUser(String.valueOf(i)));	
			}	

		} catch (ExecutionException | InterruptedException | IOException e) {
			fail("nope: " + e.getMessage());
		}
	}

	@Test
	public void clientShouldAddSomeMovies() {
		try {

			String[] type = new String[1];
			type[0] = "movie";

			for (String movie : movies.keySet()) {
				client.createItem(movie, type);
			}

			assertNotNull(client.getItem("4"));
			assertArrayEquals(type, client.getItem("6").getItypes());

		} catch (ExecutionException | InterruptedException | IOException e) {
			fail("nope: " + e.getMessage());
		}
	}

	@Test
	public void clientShouldMakeWatchingHappen() {
		for (String user : watchedMovies.keySet()) {
			Set<String> watched = watchedMovies.get(user);


			try {
				for (String movie : watched) {
					client.userActionItem(user, "view", movie);
				}
			} catch (ExecutionException | InterruptedException
					| IOException e) {
				fail("nope: " + e.getMessage());
			}
		}
	}
	
	@Test
	public void status() {
		try {
			System.out.println(client.getStatus());
		} catch (ExecutionException | InterruptedException | IOException e) {
			fail("nope: " + e.getMessage());
		}
	}

	//	@After
	//	public void cleanup() {
	//		for (String user : users) {
	//			try {
	//				client.deleteUser(user);
	//			} catch (ExecutionException | InterruptedException | IOException e) {
	//				e.printStackTrace();
	//			}
	//		}
	//	}

}
