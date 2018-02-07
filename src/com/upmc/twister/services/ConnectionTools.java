package com.upmc.twister.services;

import java.util.HashMap;
import java.util.Map;

import com.upmc.twister.model.User;

public class ConnectionTools {
	private static Map<String, Integer> connection = new HashMap<String, Integer>();

	public static boolean isConnected(String key) throws Exception{
		return connection.containsKey(key);
	}

	public static void addKey(String key, int user) throws Exception {
		connection.put(key, user);
	}

	public static void removeKey(String key)throws Exception {
		if (connection.containsKey(key))
			connection.remove(key);
		throw new Exception("NO CONNECTION");
	}
}
