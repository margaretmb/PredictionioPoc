package com.neon.predictionioPoc;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class MockImporter {

	public static Set<String> generateMockUids() {
		Set<String> set = new TreeSet<>();
		set.add("1");
		set.add("2");
		set.add("3");
		set.add("4");
		return set;
	}


	public static Map<String, Set<String>> generateMockWatchedMovies() {
		Map<String, Set<String>> map = new HashMap<>();
		Set<String> movies1 = new TreeSet<>();
		movies1.add("1");
		
		Set<String> movies2 = new TreeSet<>();
		movies2.add("1");
		movies2.add("2");
		movies2.add("7");
		movies2.add("5");
		movies2.add("3");
		
		Set<String> movies3 = new TreeSet<>();
		movies3.add("1");
		movies3.add("4");
		movies3.add("5");
		movies3.add("3");
		
		Set<String> movies4 = new TreeSet<>();
		movies4.add("1");
		movies4.add("4");
		
		map.put("1", movies1);
		map.put("2", movies2);
		map.put("3", movies3);
		map.put("4", movies4);
		
		return map;
	}

	public static Map<String, String> generateMockMovies() {
		Map<String, String> map = new HashMap<>();
		map.put("1", "Titanic");
		map.put("2", "Christmas Story");
		map.put("3", "Lord of the Rings");
		map.put("4", "Harry Potter");
		map.put("5", "Iron Man");
		map.put("6", "Shrek");
		map.put("7", "Fakey McFakeMovie");
		map.put("8", "Emmet Otter's Jug Band Christmas");
		map.put("9", "Treasure Planet");
		map.put("10", "Plan 9 From Outer Space");
		return map;
	}

}
