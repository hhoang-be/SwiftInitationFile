package com.tunz.generation;

import java.util.HashMap;
import java.util.Map;

public class ArgParser {

	Map<String, String> processArgs = new HashMap<>();

	public ArgParser(String[] args) {
		for (int i = 0; i < args.length; i++) {
			if (args.length > i + 1 && !args[i + 1].matches("^-(\\D)*")) {
				if (args.length > i + 1) {
					processArgs.put(args[i], args[++i]);
				}
				else {
					processArgs.put(args[i], null);
				}
			}
			else {
				processArgs.put(args[i], null);
			}
		}
	}

	public boolean contains(String key) {
		return processArgs.containsKey(key);
	}

	public String get(String key) throws IllegalArgumentException {
		if (processArgs.get(key) == null) {
			throw new IllegalArgumentException("Flag '" + key + "' should be followed by a parameter value");
		}
		return processArgs.get(key);
	}

	public String get(String key, String defaultValue) throws IllegalArgumentException {
		String value = processArgs.get(key);
		if (processArgs.containsKey(key) && value == null) {
			throw new IllegalArgumentException("Flag '" + key + "' should be followed by a parameter value");
		}
		return value == null ? defaultValue : value;
	}
}
