package com.upmc.twister.dao;

import java.sql.Connection;
import java.sql.ResultSet;

import com.mysql.jdbc.PreparedStatement;
import com.upmc.twister.model.User;

public class UserDAO extends DAO<User> {

	@Override
	public void create(User user) throws DBException {
		try {
			Connection cnx = Database.getMySQLConnection();
			String query = "INSERT INTO "
					+ TwisterContract.UserEntry.TABLE_NAME + " ("
					+ TwisterContract.UserEntry.COLUMN_LAST_NAME + ","
					+ TwisterContract.UserEntry.COLUMN_FIRST_NAME + ","
					+ TwisterContract.UserEntry.COLUMN_USERNAME + ","
					+ TwisterContract.UserEntry.COLUMN_PASSWORD + ") "
					+ "VALUES (?, ?, ?, ?);";
			PreparedStatement statment = (PreparedStatement) cnx
					.prepareStatement(query);
			statment.setString(1, user.getLastName());
			statment.setString(2, user.getFirstName());
			statment.setString(3, user.getUsername());
			statment.setString(4, user.getPassword());
			ResultSet rs = statment.executeQuery();
			close(cnx, statment, rs);
		} catch (Exception e) {
			throw new DBException(e.getMessage());
		}
	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public User find(int id) {
		return null;

	}

	public boolean isExist(String username) throws DBException {
		try {
			Connection cnx = Database.getMySQLConnection();
			String query = "SELECT " + TwisterContract.UserEntry._ID + " FROM "
					+ TwisterContract.UserEntry.TABLE_NAME + " WHERE "
					+ TwisterContract.UserEntry.COLUMN_USERNAME + " = ?;";

			PreparedStatement statment = (PreparedStatement) cnx
					.prepareStatement(query);
			statment.setString(1, username);
			ResultSet rs = statment.executeQuery();
			boolean next = rs.next();
			close(cnx, statment, rs);
			return next;
		} catch (Exception e) {
			throw new DBException(e.getMessage());
		}
	}
}
