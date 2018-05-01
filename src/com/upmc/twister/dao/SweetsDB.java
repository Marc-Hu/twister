package com.upmc.twister.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.upmc.twister.model.Comment;
import com.upmc.twister.model.Like;
import com.upmc.twister.model.Sweet;
import com.upmc.twister.model.User;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

import static com.upmc.twister.dao.AbstractDAO.checkParameter;
import static com.upmc.twister.dao.TwisterContract.db_name;

public class SweetsDB implements DAO {
    private DBCollection sweets;

    public SweetsDB() {
        sweets = MongoConnection.getDatabase(db_name).getCollection("sweets");
    }

    public void addComment(String id, Comment comment) {
        BasicDBObject newComment = new BasicDBObject()
                .append("$push",
                        new BasicDBObject()
                                .append("comments", comment.toDBObject()));
        sweets.update(new BasicDBObject("_id", new ObjectId(id)), newComment);
    }

    @Override
    public void create(Object o) throws Exception {
        // TODO Auto-generated method stub
        if (!checkParameter(o, Sweet.class))
            return;
        Sweet sweet = (Sweet) o;
        sweets.insert(sweet.toDBObject());
    }

    @Override
    public void delete(Object o) throws Exception {
        if (!checkParameter(o, Sweet.class))
            return;
        Sweet sweet = (Sweet) o;
        sweets.remove(new BasicDBObject("_id", sweet.getId()));
    }

    public void editComment(String id, Comment comment) {

    }

    @Override
    public JSONObject find(long id) throws Exception {
        BasicDBObject gtQuery = new BasicDBObject();
        gtQuery.put("userId", id);
        DBCursor cursor = sweets.find(gtQuery);
        JSONObject jsonobj = new JSONObject();
        JSONArray ja = new JSONArray();
        while (cursor.hasNext()) {
//			System.out.println(cursor.next());
            BasicDBObject obj = (BasicDBObject) cursor.next();
            jsonobj = new JSONObject(obj.toString()); //Le contructeur JSONObject peut prendre un string de format JSON en parametre
            ja.put(jsonobj);
        }
        jsonobj = new JSONObject();
        jsonobj.put("list", ja);
        jsonobj.put("code", 200);
        return jsonobj;
    }

    /**
     * this method returns the sweet of the newsfeed
     *
     * @param data
     * @return
     * @throws Exception
     */
    public JSONObject find(Map<Long, User> data) throws Exception {
        BasicDBObject params = new BasicDBObject();
        params.put("$in", data.entrySet()); // ids are the set
        DBObject query = new BasicDBObject("userId", params); //Ajout d'un OU mongo
        DBObject sort = new BasicDBObject("date", -1);
        DBCursor cursor = sweets.find(query).limit(150).sort(sort);


        JSONArray ja = new JSONArray();
        while (cursor.hasNext()) {
            BasicDBObject sweetObj = (BasicDBObject) cursor.next();
            JSONObject sweet = new JSONObject(sweetObj.toString());
            User u = data.get(sweet.getLong("userId"));
            sweet.put("f_name", u.getFirstName());
            sweet.put("l_name", u.getLastName());
            sweet.put("username", u.getLastName());
            ja.put(sweet);
        }
        JSONObject result = new JSONObject();
        result.put("sweets", ja);
        return result;
    }

    public ArrayList<BasicDBObject> getMessagesByQuery(String query) {
        String[] q = query.split(" ");
        HashSet<String> w = new HashSet<>();
        for (String s : q) {
            if (!w.contains(s))
                w.add(s);
        }
        System.out.println(w.toString());

        HashMap<String, Double> scores = new HashMap<>();
        for (String s : w) {
            BasicDBObject obj = new BasicDBObject();
            obj.put("addSweet", new BasicDBObject("$regex", ".*" + s + ".*"));
            DBCursor cursor = sweets.find(obj);
            try {
                int i = 0;
                List<DBObject> doc = new ArrayList<>();
                while (cursor.hasNext()) {
                    BasicDBObject res = (BasicDBObject) cursor.next();
                    System.out.println(res.toString());
                    doc.add(res);
                    String id = res.get("_id").toString();
                    System.out.println(doc.get(i).get("score").toString());
                    Double val = Double.valueOf(doc.get(i).get("score").toString()); // Pas fini il faut modifier pour matche
                    System.out.println("test");
                    Double score = scores.get(id);
                    score = (score == null) ? val : (score + val);
                    scores.put(id, score);
                    i++;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        System.out.println(scores.toString());
        return null;
    }

    public DBCollection getSweetsCollection() {
        return sweets;
    }

    public void likeComment(String id, Like like) {

    }

    public void likeSweet(String id, Like like) {

    }

    public void removeComment(String id, Comment comment) {

    }

    public void unlikeComment(String id, Like like) {

    }

    public void unlikeSweet(String id, Like like) {

    }

    @Override
    public void update(Object o) throws Exception {
        if (!checkParameter(o, Sweet.class))
            return;

        Sweet sweet = (Sweet) o;
        throw new UnsupportedOperationException();
    }

}
