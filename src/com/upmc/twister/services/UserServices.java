package com.upmc.twister.services;

import org.json.JSONObject;

import com.upmc.twister.model.User;

public class UserServices {
	private static final String WRONG_PARAMETERS = "Wrong parameters";
	private static final String SERVER_ERROR = "SERVER_ERROR";
	private static final String USER_EXISTS = "USER_EXISTS";
	private static final String SUCUESS = "SUCESS";

	private static final String UNKWOWN_USER = "User unknown";
	private static final String BAD_PASSWORD = "Wrong password";
	private static final String UNKNOWN_CONNECTION = "Wrong connection";
	private static final int WRONG_PARAMETERS_CODE = -1;
	private static final int UNKWOWN_USER_CODE = 1;
	private static final int BAD_PASSWORD_CODE = 2;
	private static final int UNKNOWN_CONNECTION_CODE = 3;

	public static JSONObject login(String username, String password) {
		if (username == null || password == null)
			return ServiceTools.serviceRefused(WRONG_PARAMETERS,
					WRONG_PARAMETERS_CODE);
		try {
			if (!ServiceTools.isExist(username))
				return ServiceTools.serviceRefused(UNKWOWN_USER,
						UNKWOWN_USER_CODE);
			if (!ServiceTools.checkPassword(username, password))
				return ServiceTools.serviceRefused(BAD_PASSWORD,
						BAD_PASSWORD_CODE);
			int id = ServiceTools.getUserId(username);
			String key = ServiceTools.insertConnection(id, false);
			JSONObject response = new JSONObject();
			response.put("key", key);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return ServiceTools.serviceRefused(SERVER_ERROR, 10000);
		}
	}
	
	public static JSONObject create(String username, String password, String f_name,String l_name){
		if (username == null || password == null || f_name==null||l_name==null)
			return ServiceTools.serviceRefused(WRONG_PARAMETERS,
					WRONG_PARAMETERS_CODE);
		try {
			if (ServiceTools.isExist(username))
				return ServiceTools.serviceRefused(USER_EXISTS,
						UNKWOWN_USER_CODE);
			// TODO create user
			return ServiceTools.serviceAccepted();
		} catch (Exception e) {
			e.printStackTrace();
			return ServiceTools.serviceRefused(SERVER_ERROR, 10000);
		}
		
	}
	public static JSONObject logout(String key){
		try {
			if (key == null )
				return ServiceTools.serviceRefused(WRONG_PARAMETERS,
						WRONG_PARAMETERS_CODE);
			if(!ConnectionTools.isConnected(key))
				return ServiceTools.serviceRefused(UNKNOWN_CONNECTION,
						UNKNOWN_CONNECTION_CODE); 
			ConnectionTools.removeKey(key);
			return ServiceTools.serviceAccepted();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ServiceTools.serviceRefused(SERVER_ERROR, 10000);

		}
	}
}
