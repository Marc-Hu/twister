package com.upmc.twister.services;

import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import com.upmc.twister.dao.DAOFactory;
import com.upmc.twister.dao.UserConnectionDAO;
import com.upmc.twister.dao.UserDAO;
import com.upmc.twister.model.User;
import com.upmc.twister.model.UserConnection;

public class ServiceTools {
	public static boolean isExist(String username) throws Exception {
		UserDAO userDAO = (UserDAO) DAOFactory.USER_DAO.get();

		return userDAO.isExist(username);
	}

	public static boolean checkPassword(String username, String password)
			throws Exception {
		UserDAO userDAO = (UserDAO) DAOFactory.USER_DAO.get();

		return userDAO.checkPassword(username, password);
	}

	public static String insertConnection(long id, boolean root)throws Exception {
		UserConnectionDAO ucDAO = (UserConnectionDAO) DAOFactory.USER_CONNECTION_DAO.get();
		UserConnection uc = new UserConnection(id, root);
		ucDAO.create(uc);
		return uc.getKey();
	}

	public static long getUserId(String username) throws Exception {
		UserDAO userDAO = (UserDAO) DAOFactory.USER_DAO.get();

		return userDAO.find(username).getId();
		
	}

	public static boolean isConnected(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	public static void removeKey(String key) {
		// TODO Auto-generated method stub
		
	}

}
