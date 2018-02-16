package com.upmc.twister.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Database {
	private static Database database = null;
	private DataSource dataSource;

	private Database(String jndiname) throws SQLException {
		try {
			dataSource = (DataSource) new InitialContext()
					.lookup("java:comp/env/" + jndiname);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			throw new SQLException(jndiname + " is missing JNDI!: "
					+ e.getMessage());
		}
	}

	private Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	public static Connection getMySQLConnection() throws SQLException, ClassNotFoundException {
		if (TwisterContract.mysql_pooling == false) {
			Class.forName("com.mysql.jdbc.Driver"); 
			return DriverManager.getConnection("jdbc:mysql://"
					+ TwisterContract.mysql_host + "/" + TwisterContract.mysql_db,
					TwisterContract.mysql_username, TwisterContract.mysql_password);
		} else {
			if (database == null) {
				database = new Database("jdbc/db");
			}
			return database.getConnection();
		}
	}
}
