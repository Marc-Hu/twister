package com.upmc.twister.dao;

import com.mysql.jdbc.PreparedStatement;
import com.upmc.twister.dao.TwisterContract.UserEntry;
import com.upmc.twister.model.User;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>The UserDAO is the responsible class for handling the CURD
 * operations of the {@link UserEntry} Contract.</p><p>This class extends the
 * DAO class and implements its abstracts methods, and add other specific
 * methods according to the needs of the handling data.</p> <p>The class will use the
 * protected fields of the DAO class to establish connection, prepare the
 * statement and get the result self if needed, each query must be prepared by
 * calling the prepareStatemnt method to avoid SQL Injections.</p><p>Within the end of
 * each communication , each method must call the close method, to close any
 * used resources</p>
 *
 * @author Nadir Belarouci
 * @author Marc Hu
 * @version 1.0
 */
public class UserDAO extends AbstractDAO {
    /**
     * This method check if a user login credentials are valid.
     * i.e his username and password
     *
     * @param username
     * @param password
     * @return true if the user exists
     */
    public boolean checkPassword(String username, String password) throws Exception {
        boolean test = false;
        try {
            cnx = Database.getMySQLConnection();
            // query select id from User where username = ? and password = ?
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
            e.printStackTrace();

            throw new DBException(e.getMessage());
        } finally {
            close();
        }

        return test;

    }

    /**
     * <p>The create method persist a User object to the UserEntry
     * table.</p><p>The class must check first if the parameter is an instance of the
     * User class to proceed to the persisting phase .
     *
     * @param o, the object to persists</p>
     * @throws Exception
     */
    @Override
    public void create(Object o) throws Exception {
        // check if the parameter is valid
        if (!checkParameter(o, User.class))
            return;

        // cast the parameter
        User user = (User) o;
        try {
            // get the connection
            cnx = Database.getMySQLConnection();
            // the query to insert the user INSERT INTO User(columns) VALUES(values);
            String query = "INSERT INTO " + TwisterContract.UserEntry.TABLE_NAME + " ("
                    + TwisterContract.UserEntry.COLUMN_LAST_NAME + "," +
                    TwisterContract.UserEntry.COLUMN_FIRST_NAME + ","
                    + TwisterContract.UserEntry.COLUMN_USERNAME + ","
                    + TwisterContract.UserEntry.COLUMN_PASSWORD + ") " +
                    "VALUES (?, ?, ?, ?);";
            // prepare the query
            st = (PreparedStatement) cnx.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            // fill the query
            st.setString(1, user.getLastName());
            st.setString(2, user.getFirstName());
            st.setString(3, user.getUsername());
            st.setString(4, user.getPassword());

            // execute it
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

    @Override
    public void delete(Object o) throws Exception {
        if (!checkParameter(o, User.class))
            return;
        User user = (User) o;
        try {
            // get the connection
            cnx = Database.getMySQLConnection();

            String query = "DELETE FROM "
                    + TwisterContract.UserEntry.TABLE_NAME + " WHERE "
                    + TwisterContract.UserEntry._ID + " = ? ";


            // prepare the query
            st = (PreparedStatement) cnx.prepareStatement(query);
            // fill the query with data
            st.setLong(1, user.getId());
            // execute
            st.executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();

            throw new DBException(e.getMessage());
        } finally {
            close();
        }

    }

    public User find(String username) throws Exception {
        User user = new User();
        try {
            cnx = Database.getMySQLConnection();
            // query: select columns from User where username =?
            String query = "SELECT " + TwisterContract.UserEntry._ID + ", "
                    + TwisterContract.UserEntry.COLUMN_LAST_NAME + ", "
                    + TwisterContract.UserEntry.COLUMN_FIRST_NAME + ", "
                    + TwisterContract.UserEntry.COLUMN_PROFILE_PIC + ", "
                    + TwisterContract.UserEntry.COLUMN_USERNAME + " FROM "
                    + TwisterContract.UserEntry.TABLE_NAME + " WHERE "
                    + TwisterContract.UserEntry.COLUMN_USERNAME + " = ?;";
            // prepare query
            st = (PreparedStatement) cnx.prepareStatement(query);
            // fill it
            st.setString(1, username);
            // execute it
            rs = st.executeQuery();
            // get the result, the username is unique,
            //so there might be just one result to read
            if (rs.next()) {
                // get the data
                Map<String, Object> data = readResultSet(TwisterContract.UserEntry._ID,
                        TwisterContract.UserEntry.COLUMN_LAST_NAME,
                        TwisterContract.UserEntry.COLUMN_FIRST_NAME,
                        UserEntry.COLUMN_PROFILE_PIC,
                        TwisterContract.UserEntry.COLUMN_USERNAME);
                // fill the user
                User.fill(user, data);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DBException(e.getMessage());
        } finally {
            close();
        }
        return user;
    }

    /**
     * This method find a User by his id
     * The method must call executeQuery and not executeUpdate to retrieve the data.
     * Unlike the create , update, or remove methods the fins method must call executeQuery
     * to get the ResultSet of that query.
     *
     * @param id, the user's id
     * @throws Exception
     */
    @Override
    public User find(long id) throws Exception {
        User user = new User();
        try {
            // get connection
            cnx = Database.getMySQLConnection();
            // query select columns from User where id = ?
            String query = "SELECT " + TwisterContract.UserEntry._ID + ", "
                    + TwisterContract.UserEntry.COLUMN_LAST_NAME + ", "
                    + TwisterContract.UserEntry.COLUMN_FIRST_NAME + ", "
                    + TwisterContract.UserEntry.COLUMN_PROFILE_PIC + ", "
                    + TwisterContract.UserEntry.COLUMN_USERNAME + " FROM "
                    + TwisterContract.UserEntry.TABLE_NAME + " WHERE "
                    + TwisterContract.UserEntry._ID + " = ?;";
            // prepare the statement
            st = (PreparedStatement) cnx.prepareStatement(query);
            // set the id
            st.setLong(1, id);
            // execute the query
            rs = st.executeQuery();

            // since the id is unique, we just check if there is something to read in the result
            if (rs.next()) {

                // call the static method of the user class which takes a user and a
                // map which this class's fill method create
                // this class's fill method takes the columns that we which to get as parameters
                Map<String, Object> data = readResultSet(TwisterContract.UserEntry._ID,
                        TwisterContract.UserEntry.COLUMN_LAST_NAME,
                        TwisterContract.UserEntry.COLUMN_FIRST_NAME,
                        TwisterContract.UserEntry.COLUMN_PROFILE_PIC,
                        TwisterContract.UserEntry.COLUMN_USERNAME);
                User.fill(user, data);
            }
        } catch (Exception e) {
            e.printStackTrace();

            throw new DBException(e.getMessage());
        } finally {
            close();
        }
        return user;

    }

    /**
     * Fonction qui creer une requete SQL. Renvoie la liste des personne dont le username contient une partie
     * du parametre en entre
     *
     * @param username
     * @return
     * @throws Exception
     */
    public List<User> getUsersByUsername(String username) throws Exception {
        List<User> userlist = new ArrayList<>();
        try {
            cnx = Database.getMySQLConnection();
            // query: select columns from User where username =?
            String query = "SELECT " + TwisterContract.UserEntry._ID + ", "
                    + TwisterContract.UserEntry.COLUMN_LAST_NAME + ", "
                    + TwisterContract.UserEntry.COLUMN_FIRST_NAME + ", "
                    + UserEntry.COLUMN_PROFILE_PIC + ", "
                    + TwisterContract.UserEntry.COLUMN_USERNAME + " FROM "
                    + TwisterContract.UserEntry.TABLE_NAME + " WHERE "
                    + TwisterContract.UserEntry.COLUMN_USERNAME + " LIKE ?";
            // prepare query
            st = (PreparedStatement) cnx.prepareStatement(query);
            // execute it
            st.setString(1, "%"+username+"%");
            rs = st.executeQuery();
            // get the result, the username is unique,
            //so there might be just one result to read
            while (rs.next()) {
                // get the data
                Map<String, Object> data = readResultSet(
                        TwisterContract.UserEntry._ID,
                        TwisterContract.UserEntry.COLUMN_LAST_NAME,
                        UserEntry.COLUMN_PROFILE_PIC,
                        TwisterContract.UserEntry.COLUMN_FIRST_NAME,
                        TwisterContract.UserEntry.COLUMN_USERNAME);

                // fill the user
                User user = new User();
                User.fill(user, data);
                userlist.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DBException(e.getMessage());
        } finally {
            close();
        }
        return userlist;
    }

    /**
     * This method check if a user exists in the UserEntry table
     *
     * @param username
     * @return true if the user exists
     */
    public boolean isExist(String username) throws Exception {
        boolean test = false;
        try {
            cnx = Database.getMySQLConnection();
            // query select id from user where username = ?
            String query = "SELECT " + TwisterContract.UserEntry._ID + " FROM "
                    + TwisterContract.UserEntry.TABLE_NAME + " WHERE " +
                    TwisterContract.UserEntry.COLUMN_USERNAME + " = ?;";

            st = (PreparedStatement) cnx.prepareStatement(query);
            st.setString(1, username);
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

    /**
     * This method check if a user exists in the UserEntry table
     *
     * @param userId
     * @return true if the user exists
     */
    public boolean isExist(long userId) throws Exception {
        boolean test = false;
        try {
            cnx = Database.getMySQLConnection();
            // query select id from user where id = ?
            String query = "SELECT " + TwisterContract.UserEntry._ID + " FROM "
                    + TwisterContract.UserEntry.TABLE_NAME + " WHERE " +
                    TwisterContract.UserEntry._ID + " = ?;";

            st = (PreparedStatement) cnx.prepareStatement(query);
            st.setLong(1, userId);
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

    @Override
    public void update(Object o) {
        if (!checkParameter(o, User.class))
            return;
        User user = (User) o;
        throw new UnsupportedOperationException();
    }

    public void setProfilePic(String pic,long id) throws Exception{
        try {
            // get the connection
            cnx = Database.getMySQLConnection();

            String query = "UPDATE "
                    + TwisterContract.UserEntry.TABLE_NAME + " SET "
                    + UserEntry.COLUMN_PROFILE_PIC +" = ? WHERE "
                    + TwisterContract.UserEntry._ID + " = ? ";


            // prepare the query
            st = (PreparedStatement) cnx.prepareStatement(query);
            // fill the query with data
            st.setString(1, pic);
            st.setLong(2, id);
            // execute
            st.executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();

            throw new DBException(e.getMessage());
        } finally {
            close();
        }
    }

}
