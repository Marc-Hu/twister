package com.upmc.twister.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.mysql.jdbc.PreparedStatement;
import com.upmc.twister.model.User;

public abstract class DAO implements AutoCloseable {
	protected Connection cnx;
	protected PreparedStatement st;
	protected ResultSet rs;

	public abstract void create(Object o) throws Exception;

	public abstract void update(Object o) throws Exception;

	public abstract void delete(Object o) throws Exception;

	public abstract Object find(int id) throws Exception;

	public void close() throws Exception {
		if (cnx != null)
			cnx.close();
		if (st != null)
			st.close();
		if (rs != null)
			rs.close();
	}

	protected int getGeneratedKey() throws SQLException {
		if (st != null)
			try (ResultSet key = st.getGeneratedKeys()) {

				if (key.next()) {
					return (st.getGeneratedKeys().getInt(1));
				}
				
			}
		return -1;
	}

	protected boolean checkParamter(Object o, Class clazz) {
		if (!clazz.isInstance(o))
			throw new IllegalArgumentException();
		User user = (User) o;
		if (user == null)
			return false;

		return true;
	}
	protected Map<String,Object> fill(String... columns) throws SQLException{
		if(rs == null)
			return null;
		Map<String,Object> data = new HashMap<String,Object>();
		
		for (String column : columns) {
			data.put(column, rs.getObject(column));
		}
		
		return data;
	}
}
