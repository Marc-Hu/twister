package com.upmc.twister.services;

import com.upmc.twister.dao.DAOFactory;
import com.upmc.twister.dao.UserConnectionDAO;
import com.upmc.twister.dao.UserDAO;
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

	public static boolean isConnected(String key) throws Exception {
		// TODO Auto-generated method stub
		UserConnectionDAO  ucDAO = (UserConnectionDAO)DAOFactory.USER_CONNECTION_DAO.get();
		
		return ucDAO.isConnected(key);
	}

	public static void removeKey(String key) throws Exception {
		UserConnectionDAO  ucDAO = (UserConnectionDAO)DAOFactory.USER_CONNECTION_DAO.get();
		ucDAO.delete(new UserConnection(key));		
	}

}
