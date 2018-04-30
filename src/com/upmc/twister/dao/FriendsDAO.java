package com.upmc.twister.dao;

import com.mysql.jdbc.PreparedStatement;
import com.upmc.twister.model.Friends;
import com.upmc.twister.model.User;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        throw  new UnsupportedOperationException();

    }

    public List<Friends> getFollowedList(long id) throws Exception {
        List<Friends> friendlist = new ArrayList<>();
        try {
            cnx = Database.getMySQLConnection();
            // query: select columns from User where username =?
            String query = "SELECT " + TwisterContract.FriendsEntry.COLUMN_FOLLOWED + ", "
                    + TwisterContract.UserEntry.COLUMN_FIRST_NAME + ", "
                    + TwisterContract.UserEntry.COLUMN_LAST_NAME + ", "
                    + TwisterContract.UserEntry.COLUMN_USERNAME+", "
                    + TwisterContract.FriendsEntry.COLUMN_TIMESTAMP + " FROM "
                    + TwisterContract.FriendsEntry.TABLE_NAME + " ,"
                    + TwisterContract.UserEntry.TABLE_NAME + "  WHERE "
                    + TwisterContract.FriendsEntry.COLUMN_FOLLOWED + " = " + TwisterContract.UserEntry._ID + " AND"
                    + TwisterContract.FriendsEntry.COLUMN_FOLLOWER + " = ?";
            // prepare query
            st = (PreparedStatement) cnx.prepareStatement(query);
            // execute it
            st.setLong(1, id);
            rs = st.executeQuery();
            // get the result, the username is unique,
            //so there might be just one result to read
            User follower = new User(id);
            while (rs.next()) {
                // get the data
                Map<String, Object> data = readResultSet(TwisterContract.FriendsEntry.COLUMN_FOLLOWED,
                        TwisterContract.UserEntry.COLUMN_USERNAME,
                        TwisterContract.UserEntry.COLUMN_FIRST_NAME,
                        TwisterContract.UserEntry.COLUMN_LAST_NAME,
                        TwisterContract.FriendsEntry.COLUMN_TIMESTAMP);
                Friends f = new Friends(follower, new User());
                Friends.fill(f,data);
                friendlist.add(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DBException(e.getMessage());
        } finally {
            close();
        }
        return friendlist;
    }

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
