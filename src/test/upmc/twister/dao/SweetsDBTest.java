package test.upmc.twister.dao;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.upmc.twister.dao.MongoConnection;
import com.upmc.twister.dao.SweetsDB;
import com.upmc.twister.model.Comment;
import com.upmc.twister.model.Like;
import com.upmc.twister.model.Sweet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SweetsDBTest {
    SweetsDB sweetsdb;

    private Sweet createSweet() {
        Sweet sweet = new Sweet("Hello,World!", 1);
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment(2, "Hi, Welcome"));
        comments.add(new Comment(1, "thanks"));
        comments.add(new Comment(3, "Noobs!"));
        comments.add(new Comment(2, "Go Away!!"));
        comments.get(0).setLikes(Arrays.asList(new Like(1)));
        comments.get(1).setLikes(Arrays.asList(new Like(2), new Like(3)));
        sweet.setComments(comments);
        return sweet;
    }

    @Test
    public void likeComment() {
        Like like = new Like(10);
        sweetsdb.likeComment("5ae849499a831b6058e8c9a1", "5ae849499a831b6058e8c998", like);
    }

    @Test

    public void removeComment() {
        Comment comment = new Comment("5ae849209a832a2926627453");
        sweetsdb.removeComment(1, 1, "5ae849209a832a292662745c", comment);
    }

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

        sweetsdb.create(createSweet());
        DBCursor cursor = sweetsdb.getSweetsCollection().find();

        for (DBObject o : cursor) {
            System.out.println(o);
        }
    }

    @Test
    public void testDelete() throws Exception {
        Sweet sweet = new Sweet("5ae849419a8341908f3dc628");
        sweet.setUserId(0);
        sweetsdb.delete(sweet);
    }

    @Test
    public void testUpdate() {
    }
}
