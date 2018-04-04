package com.upmc.twister.services;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.upmc.twister.model.Comment;
import com.upmc.twister.model.Friends;
import com.upmc.twister.model.Sweet;
import com.upmc.twister.model.User;
import com.upmc.twister.dao.*;

public class UserServices {

	/**
	 * Méthode qui va loguer une personne et l'ajouter dans la BDD de connection
	 * @param username
	 * @param password
	 * @return
	 */
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
			JSONObject response = new JSONObject();
			response.put("key", key);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return Response.INTERNAL_SERVER_ERROR.parse();
		}
	}
	
	/**
	 * Méthode qui va créer un utilisateur dans la BDD
	 * @param l_name
	 * @param f_name
	 * @param username
	 * @param password
	 * @return
	 */
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
	
	/**
	 * Méthode qui récupère le profile d'une personne par rapport à son username
	 * @param username
	 * @return
	 */
	public static JSONObject getProfile(String username) {
		if(username==null)
			return Response.BAD_REQUEST.parse();
		try {
			User user = ServiceTools.getUserProfile(username);
			JSONObject response = new JSONObject();
			response.put("firstname", user.getFirstName());
			response.put("lastname", user.getLastName());
			response.put("username", user.getUsername());
			response.put("id", user.getId());
			return response;
		}catch(Exception e) {
			e.printStackTrace();
			return Response.INTERNAL_SERVER_ERROR.parse();
		}
	}

	/**
	 * Méthode qui permet de se déconnecter de la BDD
	 * @param key
	 * @return
	 */
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
	
	/**
	 * Méthode qui permet à un utilisateur de follow une autre personne
	 * @param key
	 * @param followed
	 * @return
	 */
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
	
	/**
	 * Méthode qui permet de ne plus suivre une personne
	 * @param key
	 * @param followed
	 * @return
	 */
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
	
	/**
	 * Méthode qui va ajouter un sweet dans la BDD
	 * @param key Key de la personne connecté et qui veut ajouter un sweet
	 * @param sweetMessage
	 * @return
	 */
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
	
	/**
	 * Méthode qui va récupérer et renvoyer les sweet selon l'ID d'une personne
	 * @param id
	 * @return
	 */
	public static JSONObject getSweetById(long id) {
		if(id==0)
			return Response.BAD_REQUEST.parse();
		try {
			SweetsDB sweetsDB = new SweetsDB();
			return sweetsDB.find(id);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			return Response.INTERNAL_SERVER_ERROR.parse();
		}
	}
	
	/**
	 * Méthode qui ajoute un commentaire par rapport à l'Id du sweet
	 * @param key Clé du sweet
	 * @param sweetId Id du sweet
	 * @param commentMessage
	 * @return
	 */
	public static JSONObject addComment(String key, String sweetId,String commentMessage) {
		System.out.println(key);
		System.out.println(sweetId);
		System.out.println(commentMessage);

		if(key== null || sweetId == null || commentMessage==null)
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
	
	/**
	 * Méthode qui récupère et renvoie une liste de personne qui match avec le paramètre d'entré
	 * @param username
	 * @return
	 */
	public static JSONObject getUserListByUsername(String username) {
		if(username==null)
			return Response.BAD_REQUEST.parse();
		try {
			List<User> userlist = ServiceTools.getUserList(username);
			JSONObject response = new JSONObject();
			JSONArray ja = new JSONArray();
			for (User u : userlist) {
				JSONObject user = new JSONObject();
				user.put("firstname", u.getFirstName());
				user.put("lastname", u.getLastName());
				user.put("username", u.getUsername());
				
				ja.put(user);
			}
			response.put("list", ja);
			return response;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			return Response.INTERNAL_SERVER_ERROR.parse();
		}
	}
	
	/**
	 * Méthode qui va récupérer la liste des personnes qu'on follow
	 * @param id
	 * @return
	 */
	public static JSONObject getListFollowed(long id) {
		Long id_user = id;
		if(id_user==null)
			return Response.BAD_REQUEST.parse();
		try {
			List<Friends> userlist = ServiceTools.listFollowed(id);
			JSONObject response = new JSONObject();
			JSONArray ja = new JSONArray();
			for (Friends u : userlist) {
				JSONObject user = new JSONObject();
				user.put("followed_id", u.getFollower().getId());
				user.put("followed_lastname", u.getFollower().getLastName());
				user.put("followed_firstname", u.getFollower().getFirstName());
				user.put("followed_username", u.getFollower().getUsername());
				user.put("time", u.getTime());
				
				ja.put(user);
			}
			response.put("list", ja);
			return response;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			return Response.INTERNAL_SERVER_ERROR.parse();
		}
	}
	
	/**
	 * Méthode qui va renvoyer le profil d'une personne selon un id
	 * @param id Id de la personne dont on veut son profil
	 * @return
	 */
	public static JSONObject getProfileById(long id) {
		Long id_user = id;
		if(id_user==null)
			return Response.BAD_REQUEST.parse();
		try {
			User user = ServiceTools.getUserInUserTable(id_user);
			JSONObject response = new JSONObject();
			response.put("firstname", user.getFirstName());
			response.put("lastname", user.getLastName());
			response.put("username", user.getUsername());
			response.put("id", user.getId());
			return response;
		}catch(Exception e) {
			e.printStackTrace();
			return Response.INTERNAL_SERVER_ERROR.parse();
		}
	}
	
	/**
	 * Méthode qui va renvoyer la liste des sweets pour des ids donnés
	 * @param ids Tous les ids ou on veut leur sweets
	 * @return 
	 */
	public static JSONObject getSweet(List<String> ids) {
		if(ids.size()==0)
			return Response.BAD_REQUEST.parse();
		try {
			SweetsDB sweetsDB = new SweetsDB();
			return sweetsDB.find(ids);
		}catch(Exception e) {
			e.printStackTrace();
			return Response.INTERNAL_SERVER_ERROR.parse();
		}
	}
	
}
