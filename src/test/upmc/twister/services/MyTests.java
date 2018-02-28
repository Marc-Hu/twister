package test.upmc.twister.services;

import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.upmc.twister.dao.Database;
import com.upmc.twister.dao.MongoConnection;

public class MyTests {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMySQLConnection() throws SQLException, ClassNotFoundException {
		assertNotNull(Database.getMySQLConnection());
	}
	@Test
	public void testMongoDBConnectio() {
		assertNotNull(MongoConnection.getInstance());
	}
	

}
