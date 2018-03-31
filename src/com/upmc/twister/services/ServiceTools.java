package com.upmc.twister.services;

import java.util.List;

import com.upmc.twister.dao.DAOFactory;
import com.upmc.twister.dao.DBException;
import com.upmc.twister.dao.FriendsDAO;
import com.upmc.twister.dao.UserConnectionDAO;
import com.upmc.twister.dao.UserDAO;
import com.upmc.twister.model.Friends;
import com.upmc.twister.model.User;
import com.upmc.twister.model.UserConnection;

public class ServiceTools {
	/**
	 * Fonction qui renvoie un boolean pour savoir si l'utilisateur avec un username existe déjà ou non
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public static boolean isExist(String username) throws Exception {
		UserDAO userDAO = (UserDAO) DAOFactory.USER_DAO.get();

		return userDAO.isExist(username);
	}
	
	/**
	 * Fonction qui renvoie un boolean pour savoir si l'id existe déjà
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public static boolean isExist(int userId) throws Exception {
		UserDAO userDAO = (UserDAO) DAOFactory.USER_DAO.get();

		return userDAO.isExist(userId);
	}
	
	/**
	 * Fonction qui renvoie un utilisateur selon sa clé dans la table de connection
	 * @param key
	 * @return
	 * @throws DBException
	 */
	public static User getUser(String key) throws DBException {
		UserConnectionDAO ucDAO = (UserConnectionDAO) DAOFactory.USER_CONNECTION_DAO.get();
		return ucDAO.find(key).getUser();
	}
	
	/**
	 * Fonction qui renvoie un utilisateur selon don id dans la table Connection
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public static User getUser(long id) throws Exception{
		UserConnectionDAO ucDAO = (UserConnectionDAO) DAOFactory.USER_CONNECTION_DAO.get();
		return ucDAO.find(id).getUser();
	}
	
	/**
	 * Fonction qui renvoie un utilisateur selon son ID dans la table User
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public static User getUserInUserTable(long id) throws Exception{
		UserDAO ucDAO = (UserDAO) DAOFactory.USER_DAO.get();
		return ucDAO.find(id);
	}
	
	/**
	 * Fonction qui renvoie la liste des utlisateurs de l'appli
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public static List<User> getUserList(String username) throws Exception {
		UserDAO userDAO = (UserDAO) DAOFactory.USER_DAO.get();
		return userDAO.getUsersByUsername(username);
	}
	
	/**
	 * Fonction qui verifie que le mot de passe est bon
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static boolean checkPassword(String username, String password) throws Exception {
		UserDAO userDAO = (UserDAO) DAOFactory.USER_DAO.get();

		return userDAO.checkPassword(username, password);
	}

	/**
	 * Fonction qui ajoute une nouvelle connection dans la table de connection
	 * @param id
	 * @param root
	 * @return
	 * @throws Exception
	 */
	public static String insertConnection(long id, boolean root) throws Exception {
		UserConnectionDAO ucDAO = (UserConnectionDAO) DAOFactory.USER_CONNECTION_DAO.get();
		UserConnection uc = ucDAO.find(id);
		if (uc == null) {
			uc = new UserConnection(id, root);
			ucDAO.create(uc);

		}
		return uc.getKey();
	}
	
	/**
	 * Fonction qui permet de récupérer l'id d'une personne par rapport à son username
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public static long getUserId(String username) throws Exception {
		UserDAO userDAO = (UserDAO) DAOFactory.USER_DAO.get();

		return userDAO.find(username).getId();

	}
	
	/**
	 * Fonction qui va renvoyer le profile de la personne avec username
	 * @param username Username de la personne 
	 * @return
	 * @throws Exception
	 */
	public static User getUserProfile(String username) throws Exception{
		UserDAO userDAO = (UserDAO) DAOFactory.USER_DAO.get();
		return userDAO.find(username);
	}
	
	/**
	 * Fonction qui renvoie un boolean pour savoir si il est connecté ou non
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static boolean isConnected(String key) throws Exception {
		// TODO Auto-generated method stub
		UserConnectionDAO ucDAO = (UserConnectionDAO) DAOFactory.USER_CONNECTION_DAO.get();

		return ucDAO.isConnected(key);
	}
	
	/**
	 * Fonction qui va delete un utilisateur dans la table de connection (pour dire qu'il se déco)
	 * @param key
	 * @throws Exception
	 */
	public static void removeKey(String key) throws Exception {
		UserConnectionDAO ucDAO = (UserConnectionDAO) DAOFactory.USER_CONNECTION_DAO.get();
		ucDAO.delete(new UserConnection(key));
	}
	
	/**
	 * Fonction qui permet de follow une personne
	 * @param follower
	 * @param followed
	 * @throws Exception
	 */
	public static void insertFriends(User follower, User followed) throws Exception {
		Friends f = new Friends(follower, followed);
		FriendsDAO fDAO = (FriendsDAO) DAOFactory.FRIENDS_DAO.get();
		fDAO.create(f);

	}
	
	/**
	 * Fonction ui permet de unfollow une personne
	 * @param follower
	 * @param followed
	 * @throws DBException
	 */
	public static void removeFriends(User follower, User followed) throws DBException {
		Friends f = new Friends(follower, followed);
		FriendsDAO fDAO = (FriendsDAO) DAOFactory.FRIENDS_DAO.get();
		fDAO.delete(f);
	}
	
	/**
	 * Fonction qui va renvoyer la liste des personnes followed par l'id
	 * @param id Personne dont on veut ses followed
	 * @return list d'amis de la personne avec l'id id
	 * @throws Exception
	 */
	public static List<Friends> listFollowed(long id) throws Exception{
		FriendsDAO fDAO = (FriendsDAO) DAOFactory.FRIENDS_DAO.get();
		return fDAO.getUsersByUsername(id);
	}

}
