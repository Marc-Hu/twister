package com.upmc.twister.dao;

public enum DAOFactory {
	USER_DAO(new UserDAO()),
	FRIENDS_DAO(new FriendsDAO()),
	USER_CONNECTION_DAO(new UserConnectionDAO());

	
	private DAO value;
	private DAOFactory(DAO dao)
	{
		this.value = dao;
		
	}
	@Override
	public String toString(){
		return value.toString();
		
	}
}
