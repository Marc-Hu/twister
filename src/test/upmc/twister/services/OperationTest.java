package test.upmc.twister.services;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.upmc.twister.services.Operation;

public class OperationTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		assertEquals(12,Operation.calcul(5, 7, "+"),0.001);
		assertEquals(8,Operation.calcul(2, 6, "+"),0.001);
		assertEquals(10,Operation.calcul(15, 5, "-"),0.001);
		assertEquals(21,Operation.calcul(14, -7, "-"),0.001);
		assertEquals(0,Operation.calcul(0, 5, "*"),0.001);
		assertEquals(35,Operation.calcul(5, 7, "*"),0.001);
		assertEquals(12,Operation.calcul(36, 3, "/"),0.001);
		assertEquals(2.5,Operation.calcul(5, 2, "/"),0.001);




	}

}
