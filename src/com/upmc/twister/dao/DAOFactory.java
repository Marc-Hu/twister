package com.upmc.twister.dao;

/**
 * The DAOFactory is a Factory of the multiple DAOs that are subclasses of the
 * DAO Class by using a Factory pattern we ensure that there is one instance of
 * each dao implementation.
 * 
 * @author Nadir Belarouci
 * @author Marc Hu
 * @version 1.0
 */
public enum DAOFactory {
	/** a Singleton to USER_DAO */
	USER_DAO(new UserDAO()),

	/** a Singleton to FRIENDS_DAO */
	FRIENDS_DAO(new FriendsDAO()),

	/** a Singleton to USER_CONNECTION_DAO */
	USER_CONNECTION_DAO(new UserConnectionDAO());

	private AbstractDAO value;

	private DAOFactory(AbstractDAO dao) {
		this.value = dao;

	}

	@Override
	public String toString() {
		return value.toString();

	}

	/**
	 * The get method return the dao reference to one of the constans above this dao
	 * reference will be used to create,delete,update,remove,find data according to
	 * each implementation
	 * @return dao
	 */
	public AbstractDAO get() {
		return value;
	}
}
