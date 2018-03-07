package com.upmc.twister.dao;

import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;
import com.upmc.twister.model.Friends;

/**
 * 
 * 
 * 
 * */
public class FriendsDAO extends AbstractDAO {

	@Override
	public void create(Object o) throws Exception {
		// TODO Auto-generated method stub
		if (!checkParameter(o, Friends.class))
			return;
		Friends f = (Friends) o;
		try {
			// get the connection
			cnx = Database.getMySQLConnection();
			// the query to insert the user INSERT INTO User(columns) VALUES(values);
			String query = "INSERT INTO " + TwisterContract.FriendsEntry.TABLE_NAME + " ("
					+ TwisterContract.FriendsEntry.COLUMN_FOLLOWER + "," + TwisterContract.FriendsEntry.COLUMN_FOLLOWED
					+ "," + TwisterContract.FriendsEntry.COLUMN_TIMESTAMP + ") " + "VALUES (?, ?, now());";
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
		if (!checkParameter(o, Friends.class))
			return;
		Friends f = (Friends) o;

	}

	@Override
	public void delete(Object o) throws DBException {
		// TODO Auto-generated method stub
		if (!checkParameter(o, Friends.class))
			return;
		Friends f = (Friends) o;
		try {
			// get the connection
			cnx = Database.getMySQLConnection();

			String query = "DELETE FROM " + TwisterContract.FriendsEntry.TABLE_NAME + " WHERE "
					+ TwisterContract.FriendsEntry.COLUMN_FOLLOWED + " = ? AND "
					+ TwisterContract.FriendsEntry.COLUMN_FOLLOWER + " = ? ;";

			// prepare the query
			st = (PreparedStatement) cnx.prepareStatement(query);
			// fill the query with data
			st.setLong(1, f.getFollowed().getId());
			st.setLong(2, f.getFollower().getId());

			// execute
			st.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();

			throw new DBException(e.getMessage());
		} finally {
			close();
		}
	}

	@Override
	public Object find(long id) throws DBException {
		throw new UnsupportedOperationException();
	}

}
