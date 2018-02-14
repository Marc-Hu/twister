package com.upmc.twister.servlets;

import org.json.JSONException;
import org.json.JSONObject;

public enum Response  {
	OK("OK",200),
	CREATED("CREATED",201),
	NO_CONTENT("NO_CONTENT",204),
	BAD_REQUEST("BAD REQUEST", 400),
	UNAUTHORIZED("UNOTHORIZED",401),
	NOT_FOUND("NOT FOUND",404),
	INTERNAL_SERVER_ERROR("INTERNAL SERVER ERROR",500),
	USER_ALREADY_EXISTS("USER ALREADY EXISTS",1),
	UNKWOWN_USER("UNKNOW USER",-1), 
	WRONG_PASSWORD("Wrong password",-2),
	UNKNOWN_CONNECTION("UKNOWN CONNECTION",-3);

	private String message;
	private int code;

	private Response(String message, int code) {
		this.message = message;
		this.code = code;
	}

	private Response(String message) {
		this.message = message;
	}
	public JSONObject parse(){
		JSONObject response = null;
		try {

			response = new JSONObject();
			response.put("message", message);
			response.put("code", code);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response; 
	}
}
