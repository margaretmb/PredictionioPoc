package com.neon.predictionioPoc;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import io.prediction.Client;
import io.prediction.CreateItemRequestBuilder;
import io.prediction.FutureAPIResponse;
import io.prediction.ItemRecGetTopNRequestBuilder;
import io.prediction.ItemSimGetTopNRequestBuilder;
import io.prediction.UnidentifiedUserException;
import io.prediction.UserActionItemRequestBuilder;

import org.junit.BeforeClass;
import org.junit.Test;

public class BigDamnImportTest {
	
	private static Client client;
	private static BigDamnImporter importer;
	private static String apiKey;
	private static String url;

	@BeforeClass
	public static void before() {
		apiKey = "cvjMdOrvlQPY0CmQn7yBArK5BP5YpD0vFpJIyFJ3wKYfGqek6yV5QkYHcpijOoii";
		url = "http://localhost:8000";
		client = new Client(apiKey, url);
		importer = new BigDamnImporter();
	}
	
	@Test
	public void clientShouldImportAllTheseNiftyThings() throws ExecutionException, InterruptedException, IOException, UnidentifiedUserException {
		Set<String> users = importer.importUsers();
		for (String uid : users) {
			client.createUser(uid);
			System.out.println("adding user " + uid);
		}
		
		Map<String, String> movies = importer.importMovies();
		String[] type = new String[1];
		type[0] = "movie";
		
		for (String movie : movies.keySet()) {
			client.createItem(movie, type);
			System.out.println("adding movie " + movies.get(movie));
		}
		
		List<String[]> ratings = importer.importRatings();
		
		for (String[] ratingArray : ratings) {
			String ratingUser = ratingArray[0];
			client.identify(ratingUser);
			String ratedMovie = ratingArray[1];
			String rating = ratingArray[2];
			UserActionItemRequestBuilder builder = new UserActionItemRequestBuilder(url, "json", apiKey, "rate", ratingUser, ratedMovie);
			builder.rate(Integer.parseInt(rating));
			client.userActionItem(builder);
			client.userActionItem("view", ratedMovie);
			System.out.println("user " + ratingUser + " viewed " + movies.get(ratedMovie) + " and rated it " + rating + " stars.");
		}
	}
	
	@Test
	public void clientShouldRecommendAllTheThings() throws UnidentifiedUserException, ExecutionException, InterruptedException, IOException {
		String id = "999";
		Map<String, String> movies = importer.importMovies();
		List<String[]> ratings = importer.importRatings();
		
		for (String [] arr : ratings) {
			if (arr[0].equals(id)) {
				String movie = movies.get(arr[1]);
				String rating = arr[2];
				System.out.println("user " + id  + " rated " + movie + " " + rating + " stars." );
			}
		}
		
		System.out.println("");
		
		client.identify(id);
		ItemRecGetTopNRequestBuilder builder = client.getItemRecGetTopNRequestBuilder("BigDamnEngine", 10);
		String[] recs = client.getItemRecTopN(builder);
				
		for (String rec : recs) {
			String title = movies.get(rec);
			System.out.println("user " + id + " might like " + title);
		}
		
		
	}
	
	@Test
	public void clientShouldRecommendThingsToWatch() throws UnidentifiedUserException, ExecutionException, InterruptedException, IOException {
		
		String id = "999";
		Map<String, String> movies = importer.importMovies();
		
		client.identify(id);
		ItemRecGetTopNRequestBuilder builder = client.getItemRecGetTopNRequestBuilder("WatchEngine", 10);
		String[] recs = client.getItemRecTopN(builder);
				
		for (String rec : recs) {
			String title = movies.get(rec);
			System.out.println("user " + id + " could watch " + title);
		}
		
	}
	
	@Test
	public void clientShouldDoStuffWithTheOtherEngineToo() throws ExecutionException, InterruptedException, IOException {
		String[] stuff = 
				client.getItemSimTopN(new ItemSimGetTopNRequestBuilder(url, "json", 
						apiKey, "similarityEngine", "28", 10));
		
		Map<String, String> movies = importer.importMovies();
		
		System.out.println("Stuff similar to Apollo 13: ");
		
		for (String thing : stuff) {
			String title = movies.get(thing);
			System.out.println(title);
		}
		
	}

}
