package test.upmc.twister.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.upmc.twister.dao.DAOFactory;
import com.upmc.twister.dao.Database;
import com.upmc.twister.dao.MongoConnection;
import com.upmc.twister.dao.UserDAO;
import com.upmc.twister.services.Response;
import com.upmc.twister.services.UserServices;

public class MyTests {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

//	@Test
//	public void testMySQLConnection() throws SQLException, ClassNotFoundException {
//		assertNotNull(Database.getMySQLConnection());
//	}
//	@Test
//	public void createUser() throws Exception {
//		assertEquals(Response.BAD_REQUEST.parse().toString(),UserServices.create("Marc","Hu","marc_hu",null).toString());
//		assertEquals(Response.USER_ALREADY_EXISTS.parse().toString(),UserServices.create("Marc","Hu","hu","111").toString());
//
//		assertEquals(Response.OK.parse().toString(),  UserServices.create("Marc","Hu","marc_hu","password").toString());
//		UserDAO userDAO = (UserDAO) DAOFactory.USER_DAO.get();
//		userDAO.delete(userDAO.find("marc_hu"));
//	}
//	@Test
//	public void logout() throws JSONException {
//		
//		JSONObject json = UserServices.login("hu","12345");
//		assertEquals(Response.BAD_REQUEST.parse().toString(),UserServices.logout(null).toString());
//		assertEquals(Response.UNKNOWN_CONNECTION.parse().toString(),UserServices.logout("72473a1b86df439a999475384456c2f2").toString());
//		assertEquals(Response.OK.parse().toString(),UserServices.logout(json.getString("key")).toString());
//
//	}
//	@Test
//	public void login() throws JSONException {
//		assertEquals(Response.BAD_REQUEST.parse().toString(),UserServices.login(null,"pass").toString());
//		assertEquals(Response.UNKWOWN_USER.parse().toString(),UserServices.login("cc","lala").toString());
//		assertEquals(Response.WRONG_PASSWORD.parse().toString(),UserServices.login("hu","lala").toString());
//		JSONObject json = UserServices.login("hu","12345");
//		assertTrue(json.has("key"));
//		assertTrue(json.get("key").toString().length()==32);
//
//
//
//	}
	@Test
	public void list() throws JSONException {
//		System.out.println(UserServices.getUserListByUsername("g").toString());
//		System.out.println(UserServices.sweet("7ad575c94a1e4be092462232d4eff77f", "message test2"));
//		System.out.println(UserServices.getSweetById(new Long(1)) );
		List<String> ids = new ArrayList<>();
		ids.add("1");
		ids.add("6");
		System.out.println(UserServices.getSweet(ids) );
	}
	
	

}
