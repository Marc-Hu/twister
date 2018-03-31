package com.upmc.twister.services;

import org.json.JSONObject;

import com.upmc.twister.model.Comment;
import com.upmc.twister.model.Sweet;
import com.upmc.twister.model.User;
import com.upmc.twister.dao.*;

public class UserServices {

	public static JSONObject login(String username, String password) {
		if (username == null || password == null)
			return Response.BAD_REQUEST.parse();
		try {
			if (!ServiceTools.isExist(username))
				return Response.UNKWOWN_USER.parse();

			if (!ServiceTools.checkPassword(username, password))
				return Response.WRONG_PASSWORD.parse();

			long id = ServiceTools.getUserId(username);
			String key = ServiceTools.insertConnection(id, false);
//			String key="e";
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

	public static JSONObject follow(String key, String followed)  {
		if(key== null || followed == null)
			return Response.BAD_REQUEST.parse();
		int followedId = 0;
		try {
			followedId = Integer.valueOf(followed);
		}catch(NumberFormatException e) {
			return Response.BAD_REQUEST.parse();
		}
		
		try {
			if(!ServiceTools.isConnected(key)) {
				return Response.UNKNOWN_CONNECTION.parse();
			}
			if(!ServiceTools.isExist(followedId)) {
				return Response.UNKWOWN_USER.parse();
			}
			User user = ServiceTools.getUser(key);
			ServiceTools.insertFriends(user, new User(followedId));
			return Response.OK.parse();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			return Response.INTERNAL_SERVER_ERROR.parse();
		}
	}
	
	public static JSONObject unfollow(String key, String followed)  {
		if(key== null || followed == null)
			return Response.BAD_REQUEST.parse();
		int followedId = 0;
		try {
			followedId = Integer.valueOf(followed);
		}catch(NumberFormatException e) {
			return Response.BAD_REQUEST.parse();
		}
		
		try {
			if(!ServiceTools.isConnected(key)) {
				return Response.UNKNOWN_CONNECTION.parse();
			}
			if(!ServiceTools.isExist(followedId)) {
				return Response.UNKWOWN_USER.parse();
			}
			User user = ServiceTools.getUser(key);
			ServiceTools.removeFriends(user, new User(followedId));
			return Response.OK.parse();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			return Response.INTERNAL_SERVER_ERROR.parse();
		}
	}

	public static JSONObject sweet(String key, String sweetMessage) {
		if(key== null || sweetMessage == null)
			return Response.BAD_REQUEST.parse();
		
		
		try {
			if(!ServiceTools.isConnected(key)) {
				return Response.UNKNOWN_CONNECTION.parse();
			}

			User user = ServiceTools.getUser(key);
			Sweet sweet = new Sweet(sweetMessage, user.getId());
			SweetsDB sweetsDB = new SweetsDB();
			sweetsDB.create(sweet);
			return Response.OK.parse();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			return Response.INTERNAL_SERVER_ERROR.parse();
		}
	}
	
	public static JSONObject addComment(String key, String sweetId,String commentMessage) {
		if(key== null || sweetId == null)
			return Response.BAD_REQUEST.parse();
		
		
		try {
			if(!ServiceTools.isConnected(key)) {
				return Response.UNKNOWN_CONNECTION.parse();
			}

			User user = ServiceTools.getUser(key);
			SweetsDB sweetsDB = new SweetsDB();
			sweetsDB.addComment(sweetId, new Comment(user.getId(),commentMessage));
			return Response.OK.parse();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			return Response.INTERNAL_SERVER_ERROR.parse();
		}
	}
	
}
