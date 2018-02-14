package com.upmc.twister.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DAO<T> {
	abstract void create(T o) throws DBException;

	abstract void update(T o) throws DBException;

	abstract void delete(T o) throws DBException;

	abstract T find(int id) throws DBException;
	
	protected  void close(Connection cnx, Statement statement,
			ResultSet result) throws Exception {
		if (cnx != null)
			cnx.close();
		if (statement != null)
			statement.close();
		if (result != null)
			result.close();
	}
	
}
