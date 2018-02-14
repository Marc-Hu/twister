package com.upmc.twister.dao;

import com.upmc.twister.model.Friends;
import com.upmc.twister.model.User;

public class FriendsDAO  extends DAO{

	@Override
	public void create(Object o) throws DBException {
		// TODO Auto-generated method stub
		if(!checkParamter(o, Friends.class))
			return;
		Friends f = (Friends)o;
		
	}

	@Override
	public void update(Object o) throws DBException {
		// TODO Auto-generated method stub
		if(!checkParamter(o, Friends.class))
			return;
		Friends f = (Friends)o;
		
	}

	@Override
	public void delete(Object o) throws DBException {
		// TODO Auto-generated method stub
		if(!checkParamter(o, Friends.class))
			return;
		Friends f = (Friends)o;
		
	}

	@Override
	public Object find(int id) throws DBException {
		return 0;
	}

	
	
}
