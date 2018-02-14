package com.upmc.twister.services;

import org.json.JSONObject;

import com.upmc.twister.model.User;
import com.upmc.twister.dao.*;

import com.upmc.twister.servlets.Response;

public class UserServices {

	public static JSONObject login(String username, String password) {
		if (username == null || password == null)
			return Response.BAD_REQUEST.parse();
		try {
			if (!ServiceTools.isExist(username))
				return Response.UNKWOWN_USER.parse();

			if (!ServiceTools.checkPassword(username, password))
				return Response.WRONG_PASSWORD.parse();

			int id = ServiceTools.getUserId(username);
			String key = ServiceTools.insertConnection(id, false);
			JSONObject response = new JSONObject();
			response.put("key", key);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return Response.INTERNAL_SERVER_ERROR.parse();
		}
	}

	public static JSONObject create(String l_name,String f_name, 
			String username, String password) {
		if (username == null || password == null || f_name == null
				|| l_name == null)
			return Response.BAD_REQUEST.parse();
		try {
			if (ServiceTools.isExist(username))
				return Response.USER_ALREADY_EXISTS.parse();

			User user = new User(l_name, f_name, username, password);
			DAOFactory.USER_DAO.get().create(user);
			// TODO create user
			return Response.OK.parse();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.INTERNAL_SERVER_ERROR.parse();
		}

	}

	public static JSONObject logout(String key) {
		try {
			if (key == null)
				return Response.BAD_REQUEST.parse();
			if (!ServiceTools.isConnected(key))
				return Response.UNKNOWN_CONNECTION.parse();
			ServiceTools.removeKey(key);
			return Response.OK.parse();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.INTERNAL_SERVER_ERROR.parse();

		}
	}
}
