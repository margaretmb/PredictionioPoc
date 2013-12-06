package com.neon.predictionioPoc;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Set;

import io.prediction.Client;

import org.junit.BeforeClass;
import org.junit.Test;

public class PredictionTest {

	private static Client client;
	private static Importer importer;

	@BeforeClass
	public static void before() {
		String apiKey = "31biODHABXLSrrf76E3I4Ll0pO0v3s59BdOOr92mimQQuZU0gCnzXl8vTYsOOG8s";
		String url = "http://localhost:8000";
		client = new Client(apiKey, url);
		importer = new Importer();
	}

	@Test
	public void ImporterShouldGenerateSomeIds() {

		Set<String> uids = Importer.generateMockUids();
		assertNotNull(uids);
		assertTrue(uids.size() == 4);
		assertTrue(uids.contains("1"));
		assertFalse(uids.contains("72"));
		
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

}
