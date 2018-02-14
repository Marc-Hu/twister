package com.upmc.twister.dao;

import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;
import com.upmc.twister.model.UserConnection;

public class UserConnectionDAO extends DAO {

	@Override
	public void create(Object o) throws Exception {
		// TODO Auto-generated method stub
		if (!checkParamter(o, UserConnection.class))
			return;
		UserConnection uc = (UserConnection) o;
		try {
			cnx = Database.getMySQLConnection();
			String query = "INSERT INTO "
					+ TwisterContract.UserConnectionEntry.TABLE_NAME + " ("
					+ TwisterContract.UserConnectionEntry._ID + ","
					+ TwisterContract.UserConnectionEntry.COLUMN_USER + ","
					+ TwisterContract.UserConnectionEntry.COLUMN_ROOT + ") "
					+ "VALUES (?, ?, ?);";
			st = (PreparedStatement) cnx.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, uc.getKey());
			st.setInt(2, uc.getUser().getId());
			st.setBoolean(3, uc.isRoot());

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
	public void update(Object o) throws DBException {
		// TODO Auto-generated method stub
		if (!checkParamter(o, UserConnection.class))
			return;
		UserConnection uc = (UserConnection) o;
	}

	@Override
	public void delete(Object o) throws DBException {
		// TODO Auto-generated method stub
		if (!checkParamter(o, UserConnection.class))
			return;
		UserConnection uc = (UserConnection) o;
	}

	@Override
	public Object find(int id) throws DBException {
		// TODO Auto-generated method stub
		return null;
	}

}
