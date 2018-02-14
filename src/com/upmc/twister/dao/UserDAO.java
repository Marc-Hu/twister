package com.upmc.twister.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;
import com.upmc.twister.model.User;

public class UserDAO extends DAO {

	@Override
	public void create(Object o) throws Exception {
		if (!checkParamter(o, User.class))
			return;
		User user = (User) o;
		try {
			cnx = Database.getMySQLConnection();
			String query = "INSERT INTO "
					+ TwisterContract.UserEntry.TABLE_NAME + " ("
					+ TwisterContract.UserEntry.COLUMN_LAST_NAME + ","
					+ TwisterContract.UserEntry.COLUMN_FIRST_NAME + ","
					+ TwisterContract.UserEntry.COLUMN_USERNAME + ","
					+ TwisterContract.UserEntry.COLUMN_PASSWORD + ") "
					+ "VALUES (?, ?, ?, ?);";
			st = (PreparedStatement) cnx.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, user.getLastName());
			st.setString(2, user.getFirstName());
			st.setString(3, user.getUsername());
			st.setString(4, user.getPassword());
			rs = st.executeQuery();

			// int id = getGeneratedKey();
			// if (id != -1) {
			// user.setId(id);
			// }

		} catch (Exception e) {
			throw new DBException(e.getMessage());
		} finally {
			close();
		}
	}

	@Override
	public void update(Object o) {
		if (!checkParamter(o, User.class))
			return;
		User user = (User) o;
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Object o) {
		if (!checkParamter(o, User.class))
			return;
		User user = (User) o;
		// TODO Auto-generated method stub

	}

	@Override
	public User find(int id) throws Exception {
		User user = null;
		try {
			cnx = Database.getMySQLConnection();
			String query = "SELECT " + TwisterContract.UserEntry._ID + ", "
					+ TwisterContract.UserEntry.COLUMN_LAST_NAME + ", "
					+ TwisterContract.UserEntry.COLUMN_FIRST_NAME + ", "
					+ TwisterContract.UserEntry.COLUMN_USERNAME + " FROM "
					+ TwisterContract.UserEntry.TABLE_NAME + " WHERE "
					+ TwisterContract.UserEntry._ID + " = ?;";

			st = (PreparedStatement) cnx.prepareStatement(query);
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if (rs.next()){
				
				User.fill(
						user,
						fill(TwisterContract.UserEntry._ID,
								TwisterContract.UserEntry.COLUMN_LAST_NAME,
								TwisterContract.UserEntry.COLUMN_FIRST_NAME,
								TwisterContract.UserEntry.COLUMN_USERNAME));
			}
		} catch (Exception e) {
			throw new DBException(e.getMessage());
		} finally {
			close();
		}
		return user;

	}

	public User find(String username) throws Exception {
		User user = null;
		try {
			cnx = Database.getMySQLConnection();
			String query = "SELECT " + TwisterContract.UserEntry._ID + ", "
					+ TwisterContract.UserEntry.COLUMN_LAST_NAME + ", "
					+ TwisterContract.UserEntry.COLUMN_FIRST_NAME + ", "
					+ TwisterContract.UserEntry.COLUMN_USERNAME + " FROM "
					+ TwisterContract.UserEntry.TABLE_NAME + " WHERE "
					+ TwisterContract.UserEntry.COLUMN_USERNAME + " = ?;";

			st = (PreparedStatement) cnx.prepareStatement(query);
			st.setString(1, username);
			rs = st.executeQuery();
			
			if (rs.next()){
				
				User.fill(
						user,
						fill(TwisterContract.UserEntry._ID,
								TwisterContract.UserEntry.COLUMN_LAST_NAME,
								TwisterContract.UserEntry.COLUMN_FIRST_NAME,
								TwisterContract.UserEntry.COLUMN_USERNAME));
			}
		} catch (Exception e) {
			throw new DBException(e.getMessage());
		} finally {
			close();
		}
		return user;
	}

	public boolean isExist(String username) throws Exception {
		boolean test = false;
		try {
			cnx = Database.getMySQLConnection();
			String query = "SELECT " + TwisterContract.UserEntry._ID + " FROM "
					+ TwisterContract.UserEntry.TABLE_NAME + " WHERE "
					+ TwisterContract.UserEntry.COLUMN_USERNAME + " = ?;";

			st = (PreparedStatement) cnx.prepareStatement(query);
			st.setString(1, username);
			rs = st.executeQuery();
			test = rs.next();

		} catch (Exception e) {
			throw new DBException(e.getMessage());
		} finally {
			close();
		}
		return test;
	}

	public boolean checkPassword(String username, String password)
			throws Exception {
		boolean test = false;
		try {
			cnx = Database.getMySQLConnection();
			String query = "SELECT " + TwisterContract.UserEntry._ID + " FROM "
					+ TwisterContract.UserEntry.TABLE_NAME + " WHERE "
					+ TwisterContract.UserEntry.COLUMN_USERNAME + " = ? AND "
					+ TwisterContract.UserEntry.COLUMN_PASSWORD + " = ?;";

			st = (PreparedStatement) cnx.prepareStatement(query);
			st.setString(1, username);
			st.setString(2, password);

			rs = st.executeQuery();
			test = rs.next();

		} catch (Exception e) {
			throw new DBException(e.getMessage());
		} finally {
			close();
		}

		return test;

	}
}
