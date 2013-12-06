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

	@BeforeClass
	public static void before() {
		String apiKey = "31biODHABXLSrrf76E3I4Ll0pO0v3s59BdOOr92mimQQuZU0gCnzXl8vTYsOOG8s";
		String url = "http://localhost:8000";
		client = new Client(apiKey, url);
		importer = new Importer();
		users = Importer.generateMockUids();
	}

	@Test
	public void ImportShouldGenerateSomeMoviesToo() {
		Map<String, String> movies = Importer.generateMockMovies();
		assertNotNull(movies);
		assertTrue(movies.size() > 0);
		assertTrue(movies.get("6").equals("Shrek"));
		assertFalse(movies.get("8").equals("Wrong Movie Title"));
	}

	@Test
	public void InputShouldGenerateSomeMockWatchedMovies() {
		Map<String, Set<String>> watchedMovies = Importer.generateMockWatchedMovies();
		assertNotNull(watchedMovies);
		assertFalse(watchedMovies.size() == 0);
		assertTrue(watchedMovies.get("3").contains("4"));
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
	
	@After
	public void cleanup() {
		for (String user : users) {
			try {
				client.deleteUser(user);
			} catch (ExecutionException | InterruptedException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
