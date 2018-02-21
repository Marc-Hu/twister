package com.upmc.twister.dao;

import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;
import com.upmc.twister.model.Friends;
/**
 * 
 * 
 * 
 * */
public class FriendsDAO  extends DAO{

	@Override
	public void create(Object o) throws Exception {
		// TODO Auto-generated method stub
		if(!checkParameter(o, Friends.class))
			return;
		Friends f = (Friends)o;
		try {
			// get the connection
			cnx = Database.getMySQLConnection();
			// the query to insert the user INSERT INTO User(columns) VALUES(values);
			String query = "INSERT INTO " + TwisterContract.FriendsEntry.TABLE_NAME + " ("
					+ TwisterContract.FriendsEntry.COLUMN_FOLLOWER + "," +
					TwisterContract.FriendsEntry.COLUMN_FOLLOWED+ "," 
					+ TwisterContract.FriendsEntry.COLUMN_TIMESTAMP+ ") " + 
					"VALUES (?, ?, now());";
			// prepare the query
			st = (PreparedStatement) cnx.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			// fill the query 
			st.setLong(1, f.getFollower().getId());
			st.setLong(2, f.getFollowed().getId());
			

			// execute it
			st.executeUpdate();


		} catch (Exception e) {
			e.printStackTrace();

			throw new DBException(e.getMessage());
		} finally {
			close();
		}
		
	}

	@Override
	public void update(Object o) throws DBException {
		// TODO Auto-generated method stub
		if(!checkParameter(o, Friends.class))
			return;
		Friends f = (Friends)o;
		
	}

	@Override
	public void delete(Object o) throws DBException {
		// TODO Auto-generated method stub
		if(!checkParameter(o, Friends.class))
			return;
		Friends f = (Friends)o;
		
	}

	@Override
	public Object find(int id) throws DBException {
		return 0;
	}

	
	
}
