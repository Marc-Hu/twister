package com.upmc.twister.services;

import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import com.upmc.twister.model.User;

public class ServiceTools {
	public static boolean isExist(String username) throws SQLException {

		return false;
	} 	

	public static boolean checkPassword(String username, String password)
			throws SQLException {
		return false;
	}

	public static String insertConnection(int id, boolean root)
			throws Exception {
		byte[] array = new byte[7]; // length is bounded by 7
		new Random().nextBytes(array);
		String generatedString = new String(array, Charset.forName("UTF-8"));
		ConnectionTools.addKey(generatedString, id);
		return generatedString;
	}

	public static int getUserId(String username) throws Exception {
		return 0;
	}

	public static JSONObject serviceRefused(String desc, int code) {
		JSONObject error = null;
		try {

			error = new JSONObject();
			error.put("description", desc);
			error.put("code", code);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return error;
	}

	public static JSONObject serviceAccepted() {
		JSONObject response = null;
		try {
			response = new JSONObject();
			response.put("SUCCESS", true);
			return response;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
}
