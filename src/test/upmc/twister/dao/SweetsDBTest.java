package test.upmc.twister.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.upmc.twister.dao.MongoConnection;
import com.upmc.twister.dao.SweetsDB;
import com.upmc.twister.dao.TwisterContract;
import com.upmc.twister.model.Comment;
import com.upmc.twister.model.Like;
import com.upmc.twister.model.Sweet;

public class SweetsDBTest {
	SweetsDB sweetsdb;

	@Before
	public void setUp() throws Exception {
		MongoConnection.getInstance();
		sweetsdb = new SweetsDB();
	}

	@After
	public void tearDown() throws Exception {
		MongoConnection.close();
	}

	@Test
	public void testCreate() throws Exception {
		Sweet sweet = new Sweet("Hello,World!", 1);
		List<Comment> comments = new ArrayList<>();
		comments.add(new Comment(2, "Hi, Welcome"));
		comments.add(new Comment(1, "thanks"));
		comments.add(new Comment(3, "Noobs"));
		comments.add(new Comment(2, "Go Away!!"));
		comments.get(0).setLikes(Arrays.asList(new Like(1)));
		comments.get(1).setLikes(Arrays.asList(new Like(2), new Like(3)));
		sweet.setComments(comments);

		sweetsdb.create(sweet);

		Sweet s = sweetsdb.getSweetsCollection().find().first();
		assertNotNull(s);
		System.out.println(s);

	}
	/*
	 * @Test public void testUpdate() { }
	 * 
	 * @Test public void testDelete() { }
	 * 
	 * @Test public void testFind() { }
	 */
}
