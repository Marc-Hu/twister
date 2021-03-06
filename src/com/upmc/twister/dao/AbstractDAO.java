package com.upmc.twister.dao;

import com.mysql.jdbc.PreparedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is an Abstraction of the Data Access Object Layer It contains the
 * CRUD methods to handle operation in the database
 * </p>
 * <p>
 * The subclasses of this class must implement the abstracts methods according
 * to their use
 * </p>
 * <p>
 * The DAO class provide also some other common methods between the subclasses
 * like close and fill
 * </p>
 *
 * @author Nadir Belarouci
 * @author Marc Hu
 * @version 1.0
 */
public abstract class AbstractDAO implements DAO, AutoCloseable {
    /**
     * A connection to the database
     */
    protected Connection cnx;
    /**
     * PreparedStatement to execute queries
     */
    protected PreparedStatement st;
    /**
     * ResultSet to contain result back from the mysql databse
     */
    protected ResultSet rs;

    /**
     * <p>The checkParamter method ensures that the object being passed to the DAO
     * implementation is valid.</p><p> It checks if the object is an Instance of a
     * particular class and if it's null or not</p>
     *
     * @param o     an object
     * @param clazz a Class ref
     * @return true if o is valid
     */
    protected static boolean checkParameter(Object o, Class clazz) {
        if (!clazz.isInstance(o))
            throw new IllegalArgumentException();
        if (o == null)
            return false;

        return true;
    }

    /**
     * The close method close the connection , the prepared Statement and also the
     * result set This method is used by the end of using these objects, i.e after
     * create or deleting, or fetching data.
     *
     * @throws Exception
     */
    public void close() throws DBException {
        try {
            if (cnx != null)
                cnx.close();
            if (st != null)
                st.close();
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new DBException(e.getMessage());
        }
    }

    /**
     * The getGeneratedKey method get the id of the created Entity.
     * <p>
     * Warning: in order to this method to work as expected, then you must provide
     * the {@link Statement}'s RETURN_GENERATED_KEYS constant while creating the
     * prepared Statement
     * </p>
     *
     * @return the id of the created Entity
     * @throws SQLException
     */
    protected long getGeneratedKey() throws SQLException {

        if (st != null)
            try (ResultSet id = st.getGeneratedKeys()) {
                // check if the id is available
                if (id.next()) {
                    return (id.getLong(1));
                }
            }
        return -1;
    }

    /**
     * <p>The readResultSet method is a helper method to retrieve the corresponding data to
     * the passed columns.</p><p> This method iterate through the columns and retrieve the
     * data from the result set then it puts it in a {@link Map} data structure</p>
     *
     * @param columns an array of column names
     * @return data as a {@code Map<String,Object>}
     */
    protected Map<String, Object> readResultSet(String... columns) throws SQLException {
        if (rs == null)
            return null;
        Map<String, Object> data = new HashMap<String, Object>();

        for (String column : columns) {
            data.put(column, rs.getObject(column));
        }

        return data;
    }
}
