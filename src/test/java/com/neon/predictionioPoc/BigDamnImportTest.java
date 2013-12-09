package com.neon.predictionioPoc;

import static org.junit.Assert.*;

import java.util.Set;

import io.prediction.Client;

import org.junit.BeforeClass;
import org.junit.Test;

public class BigDamnImportTest {
	
	private static Client client;

	@BeforeClass
	public static void before() {
		String apiKey = "J00zzxkKUHDXKN89x8snwKe3qoRWzFiqWBVIEalP37Udx79InfvkLwIAVxM5c6Hb";
		String url = "http://localhost:8000";
		client = new Client(apiKey, url);
	}
	
	@Test
	public void shouldImportSomeUsers() {
		BigDamnImporter importer = new BigDamnImporter();
		Set<String> users = importer.importUsers();
		assertNotNull(users);
		assertFalse(users.size() == 0);
	}

}
