package com.neon.predictionioPoc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
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

}
