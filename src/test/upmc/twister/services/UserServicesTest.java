package test.upmc.twister.services;

import com.upmc.twister.dao.DAOFactory;
import com.upmc.twister.dao.UserDAO;
import com.upmc.twister.services.Response;
import com.upmc.twister.services.UserServices;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserServicesTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void login() throws Exception {
        assertEquals(Response.BAD_REQUEST.parse(), UserServices.login(null, "pass"));
        assertEquals(Response.UNKWOWN_USER.parse(), UserServices.login("cc", "lala"));
        assertEquals(Response.WRONG_PASSWORD.parse(), UserServices.login("hu", "lala"));
        JSONObject json = UserServices.login("hu", "12345");
        assertTrue(json.has("key"));
        assertEquals(32, json.get("key").toString().length());
        assertEquals(Response.OK.parse(), UserServices.logout(json.getString("key")));


    }

    @Test
    public void create() throws Exception{
        assertEquals(Response.BAD_REQUEST.parse(), UserServices.create("Marc", "Hu", "marc_hu", null));
        assertEquals(Response.USER_ALREADY_EXISTS.parse(), UserServices.create("Marc", "Hu", "hu", "111"));
        assertEquals(Response.OK.parse(), UserServices.create("Marc", "Hu", "marc_hu", "password"));
        UserDAO userDAO = (UserDAO) DAOFactory.USER_DAO.get();
        userDAO.delete(userDAO.find("marc_hu"));
    }

    @Test
    public void getProfile() {
    }

    @Test
    public void logout() throws Exception{
        JSONObject json = UserServices.login("hu", "12345");
        System.out.println(json);
        assertEquals(Response.BAD_REQUEST.parse(), UserServices.logout(null));
        assertEquals(Response.UNKNOWN_CONNECTION.parse(), UserServices.logout("72473a1b86df439a999475384456c2f2"));
        assertEquals(Response.OK.parse(), UserServices.logout(json.getString("key")));
    }

    @Test
    public void followAndUnfollow() throws Exception{

        UserDAO userDAO = (UserDAO) DAOFactory.USER_DAO.get();
        assertEquals(Response.OK.parse(), UserServices.create("U1", "Hu", "u1", "password"));
        assertEquals(Response.OK.parse(), UserServices.create("U2", "Hu", "u2", "password"));
        assertEquals(Response.OK.parse(), UserServices.create("U3", "Hu", "u3", "password"));
        JSONObject json = UserServices.login("u1", "password");
        assertTrue(json.has("key"));
        assertEquals(32, json.get("key").toString().length());

        assertEquals(Response.OK.parse(), UserServices.follow(json.get("key").toString(), userDAO.find("u2").getId() + ""));
        assertEquals(Response.OK.parse(), UserServices.follow(json.get("key").toString(), userDAO.find("u3").getId() + ""));

        assertEquals(Response.OK.parse(), UserServices.unfollow(json.get("key").toString(), userDAO.find("u2").getId() + ""));
        assertEquals(Response.OK.parse(), UserServices.unfollow(json.get("key").toString(), userDAO.find("u3").getId() + ""));

        userDAO.delete(userDAO.find("u1"));
        userDAO.delete(userDAO.find("u2"));
        userDAO.delete(userDAO.find("u3"));
    }

    @Test
    public void sweet() {
    }

    @Test
    public void getSweetById() {
    }

    @Test
    public void addComment() {
    }

    @Test
    public void getUserListByUsername() {
    }

    @Test
    public void getFollowedList() {
    }

    @Test
    public void getProfileById() {
    }

    @Test
    public void getSweet() {
    }

    @Test
    public void getMessageByQuery() {
    }
}