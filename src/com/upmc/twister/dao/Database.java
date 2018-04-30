package com.upmc.twister.dao;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

    public static Connection getMySQLConnection() throws SQLException, ClassNotFoundException {
        if (TwisterContract.mysql_pooling == false) {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://"
                            + TwisterContract.mysql_host + "/" + TwisterContract.db_name,
                    TwisterContract.mysql_username, TwisterContract.mysql_password);
        } else {
            if (database == null) {
                database = new Database("jdbc/db");
            }
            return database.getConnection();
        }
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
