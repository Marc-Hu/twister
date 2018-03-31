package com.upmc.twister.dao;

import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Map;

import com.mysql.jdbc.PreparedStatement;
import com.upmc.twister.dao.TwisterContract.UserConnectionEntry;
import com.upmc.twister.model.UserConnection;

/**
 * <p>
 * The UserConnectionDAO is the responsible class for handling the CURD
 * operations of the {@link UserConnectionEntry} Contract.
 * </p>
 * <p>
 * This class extends the DAO class and implements its abstracts methods, and
 * add other specific methods according to the needs of the handling data.
 * </p>
 * <p>
 * The class will use the protected fields of the DAO class to establish
 * connection, prepare the statement and get the result self if needed, each
 * query must be prepared by calling the prepareStatemnt method to avoid SQL
 * Injections.
 * </p>
 * <p>
 * Within the end of each communication , each method must call the close
 * method, to close any used resources
 * </p>
 * 
 * @author Nadir Belarouci
 * @author Marc Hu
 * @version 1.0
 */
public class UserConnectionDAO extends AbstractDAO {

	/**
	 * <p>
	 * The create method persist a UserConnection object to the UserConnectionEntry
	 * table.
	 * </p>
	 * <p>
	 * The class must check first if the parameter is an instance of the
	 * UserConnection class to proceed to the persisting phase .
	 * 
	 * @param o,
	 *            the object to persists
	 *            </p>
	 * @throws Exception
	 */
	@Override
	public void create(Object o) throws Exception {
		// check the parameter
		if (!checkParameter(o, UserConnection.class))
			return;
		// cast to UserConnection
		UserConnection uc = (UserConnection) o;
		try {
			// get the connection
			cnx = Database.getMySQLConnection();
			// the insert into query using the UserConnectionEntry constants
			String query = "INSERT INTO " + TwisterContract.UserConnectionEntry.TABLE_NAME + " ("
					+ TwisterContract.UserConnectionEntry.COLUMN_TIMESTAMP + "," 
					+ TwisterContract.UserConnectionEntry._ID + "," + TwisterContract.UserConnectionEntry.COLUMN_USER
					+ "," + TwisterContract.UserConnectionEntry.COLUMN_ROOT + ") " + "VALUES (?, ?, ?, ?);";
			// prepare the query
			st = (PreparedStatement) cnx.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			// fill the query with data
			st.setString(1, new Timestamp(System.currentTimeMillis()).toString());
			st.setString(2, uc.getKey());
			st.setLong(3, uc.getUser().getId());
			st.setBoolean(4, uc.isRoot());
			// execute the query
			st.executeUpdate();

			// int id = getGeneratedKey();
			// if (id != -1) {
			// user.setId(id);
			// }

		} catch (Exception e) {
			e.printStackTrace();

			throw new DBException(e.getMessage());
		} finally {
			close();
		}
	}

	/**
	 * 
	 * */
	@Override
	public void update(Object o) throws DBException {
		// TODO Auto-generated method stub
		if (!checkParameter(o, UserConnection.class))
			return;
		UserConnection uc = (UserConnection) o;
	}

	@Override
	public void delete(Object o) throws Exception {
		// TODO Auto-generated method stub
		if (!checkParameter(o, UserConnection.class))
			return;
		UserConnection uc = (UserConnection) o;
		try {
			// get the connection
			cnx = Database.getMySQLConnection();
			// the insert into query using the UserConnectionEntry constants
			String query = "DELETE FROM " + TwisterContract.UserConnectionEntry.TABLE_NAME + " WHERE "
					+ TwisterContract.UserConnectionEntry._ID + " = ? ";

			// prepare the query
			st = (PreparedStatement) cnx.prepareStatement(query);
			// fill the query with data
			st.setString(1, uc.getKey());
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
	public UserConnection find(long userId) throws DBException {
		UserConnection uc = null;
		try {
			cnx = Database.getMySQLConnection();
			// query select id from user where username = ?
			String query = "SELECT *  FROM " 
					+ TwisterContract.UserConnectionEntry.TABLE_NAME + " WHERE "
					+ TwisterContract.UserConnectionEntry.COLUMN_USER + " = ?;";

			st = (PreparedStatement) cnx.prepareStatement(query);
			st.setLong(1, userId);
			// execute the query
			rs = st.executeQuery();
			// if the user exists then rs.next() will return true
			if (rs.next()) {
				Map<String, Object> data = readResultSet(TwisterContract.UserConnectionEntry._ID,
						TwisterContract.UserConnectionEntry.COLUMN_ROOT,
						TwisterContract.UserConnectionEntry.COLUMN_USER,
						TwisterContract.UserConnectionEntry.COLUMN_TIMESTAMP);
				uc = new UserConnection("");
				UserConnection.fill(uc, data);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new DBException(e.getMessage());
		} finally {
			close();
		}
		return uc;
	}

	public UserConnection find(String key) throws DBException {
		UserConnection uc = null;
		try {
			cnx = Database.getMySQLConnection();
			// query select id from user where username = ?
			String query = "SELECT *  FROM " + TwisterContract.UserConnectionEntry.TABLE_NAME + " WHERE "
					+ TwisterContract.UserConnectionEntry._ID + " = ?;";

			st = (PreparedStatement) cnx.prepareStatement(query);
			st.setString(1, key);
			// execute the query
			rs = st.executeQuery();
			// if the user exists then rs.next() will return true
			if (rs.next()) {
				Map<String, Object> data = readResultSet(TwisterContract.UserConnectionEntry._ID,
						TwisterContract.UserConnectionEntry.COLUMN_ROOT,
						TwisterContract.UserConnectionEntry.COLUMN_USER,
						TwisterContract.UserConnectionEntry.COLUMN_TIMESTAMP);
				uc = new UserConnection(key);
				UserConnection.fill(uc, data);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new DBException(e.getMessage());
		} finally {
			close();
		}
		return uc;
	}

	public boolean isConnected(String key) throws Exception {
		boolean test = false;
		try {
			cnx = Database.getMySQLConnection();
			// query select id from user where username = ?
			String query = "SELECT " + TwisterContract.UserConnectionEntry._ID + " FROM "
					+ TwisterContract.UserConnectionEntry.TABLE_NAME + " WHERE "
					+ TwisterContract.UserConnectionEntry._ID + " = ?;";

			st = (PreparedStatement) cnx.prepareStatement(query);
			st.setString(1, key);
			// execute the query
			rs = st.executeQuery();
			// if the user exists then rs.next() will return true
			test = rs.next();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DBException(e.getMessage());
		} finally {
			close();
		}
		return test;

	}

}
