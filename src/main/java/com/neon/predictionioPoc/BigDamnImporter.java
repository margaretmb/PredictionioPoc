package com.neon.predictionioPoc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class BigDamnImporter {

	public Set<String> importUsers() {
		Set<String> users = new TreeSet<String>();
		File file = new File("src/main/resources/ml-100k/u.user");
		Reader in;
		try {
			in = new FileReader(file);
			BufferedReader reader = new BufferedReader(in);

			String line = reader.readLine();
			while (line != null) {
				String[] splitLine = line.split("\\|");
				users.add(splitLine[0]);
				line = reader.readLine();
			}

			in.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return users;
	}

	public Map<String, String> importMovies() {
		Map<String, String> movies = new HashMap<>();

		try {
			File file = new File("src/main/resources/ml-100k/u.item");
			FileReader in = new FileReader(file);
			BufferedReader reader = new BufferedReader(in);

			String line = reader.readLine();
			while (line != null) {
				String[] splitLine = line.split("\\|");
				movies.put(splitLine[0], splitLine[1]);
				line = reader.readLine();
			}

			reader.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return movies;
	}

	public List<String[]> importRatings() {
		List<String[]> ratings = new ArrayList<>();

		try {
			File file = new File("src/main/resources/ml-100k/u.data");
			FileReader in = new FileReader(file);
			BufferedReader reader = new BufferedReader(in);
			
			String line = reader.readLine();
			while (line != null) {
				String[] splitLine = line.split("\t");
				String[] subArray = Arrays.copyOfRange(splitLine, 0, 3);
				
				ratings.add(subArray);
				
				line = reader.readLine();
			}
			
			in.close();
			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return ratings;
	}

}
