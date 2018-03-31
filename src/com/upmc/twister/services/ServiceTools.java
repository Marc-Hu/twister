package com.upmc.twister.services;

import com.upmc.twister.dao.DAOFactory;
import com.upmc.twister.dao.DBException;
import com.upmc.twister.dao.FriendsDAO;
import com.upmc.twister.dao.UserConnectionDAO;
import com.upmc.twister.dao.UserDAO;
import com.upmc.twister.model.Friends;
import com.upmc.twister.model.User;
import com.upmc.twister.model.UserConnection;

public class ServiceTools {
	public static boolean isExist(String username) throws Exception {
		UserDAO userDAO = (UserDAO) DAOFactory.USER_DAO.get();

		return userDAO.isExist(username);
	}

	public static boolean isExist(int userId) throws Exception {
		UserDAO userDAO = (UserDAO) DAOFactory.USER_DAO.get();

		return userDAO.isExist(userId);
	}

	public static User getUser(String key) throws DBException {
		UserConnectionDAO ucDAO = (UserConnectionDAO) DAOFactory.USER_CONNECTION_DAO.get();
		return ucDAO.find(key).getUser();
	}

	public static boolean checkPassword(String username, String password) throws Exception {
		UserDAO userDAO = (UserDAO) DAOFactory.USER_DAO.get();

		return userDAO.checkPassword(username, password);
	}

	public static String insertConnection(long id, boolean root) throws Exception {
		UserConnectionDAO ucDAO = (UserConnectionDAO) DAOFactory.USER_CONNECTION_DAO.get();
		UserConnection uc = ucDAO.find(id);
		if (uc == null) {
			uc = new UserConnection(id, root);
			ucDAO.create(uc);

		}
		return uc.getKey();
	}

	public static long getUserId(String username) throws Exception {
		UserDAO userDAO = (UserDAO) DAOFactory.USER_DAO.get();

		return userDAO.find(username).getId();

	}
	
	public static User getUserProfile(String username) throws Exception{
		UserDAO userDAO = (UserDAO) DAOFactory.USER_DAO.get();
		return userDAO.find(username);
	}

	public static boolean isConnected(String key) throws Exception {
		// TODO Auto-generated method stub
		UserConnectionDAO ucDAO = (UserConnectionDAO) DAOFactory.USER_CONNECTION_DAO.get();

		return ucDAO.isConnected(key);
	}

	public static void removeKey(String key) throws Exception {
		UserConnectionDAO ucDAO = (UserConnectionDAO) DAOFactory.USER_CONNECTION_DAO.get();
		ucDAO.delete(new UserConnection(key));
	}

	public static void insertFriends(User follower, User followed) throws Exception {
		Friends f = new Friends(follower, followed);
		FriendsDAO fDAO = (FriendsDAO) DAOFactory.FRIENDS_DAO.get();
		fDAO.create(f);

	}

	public static void removeFriends(User follower, User followed) throws DBException {
		Friends f = new Friends(follower, followed);
		FriendsDAO fDAO = (FriendsDAO) DAOFactory.FRIENDS_DAO.get();
		fDAO.delete(f);
	}

}
