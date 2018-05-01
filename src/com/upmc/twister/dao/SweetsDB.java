package com.upmc.twister.dao;

import com.mongodb.*;
import com.upmc.twister.model.Comment;
import com.upmc.twister.model.Like;
import com.upmc.twister.model.Sweet;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static com.upmc.twister.dao.AbstractDAO.checkParameter;
import static com.upmc.twister.dao.TwisterContract.db_name;

public class SweetsDB implements DAO {
    private DBCollection sweets;

    public SweetsDB() {
        sweets = MongoConnection.getDatabase(db_name).getCollection("sweets");
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
    public void update(Object o) throws Exception {
        if (!checkParameter(o, Sweet.class))
            return;

        Sweet sweet = (Sweet) o;
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Object o) throws Exception {
        if (!checkParameter(o, Sweet.class))
            return;
        Sweet sweet = (Sweet) o;
        sweets.remove(new BasicDBObject("_id",sweet.getId()));
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
     * Methode qui recupere les sweets selon une liste d'ids
     *
     * @param ids
     * @return
     * @throws Exception
     */
    public JSONObject find(List<String> ids) throws Exception {
        BasicDBObject gtQuery = new BasicDBObject();
        BasicDBList list = new BasicDBList(); //Contiendra une liste de BasicDBObject
        for (String s : ids) {
            gtQuery.put("userId", new Long(s));
            list.add(gtQuery);
            gtQuery = new BasicDBObject();
        }
        DBObject query = new BasicDBObject("$or", list); //Ajout d'un OU mongo
        DBCursor cursor = sweets.find(query);
        JSONObject jsonobj = new JSONObject();
        JSONArray ja = new JSONArray();
        while (cursor.hasNext()) {
//			System.out.println(cursor.next());
            BasicDBObject obj = (BasicDBObject) cursor.next();
            jsonobj = new JSONObject(obj.toString());
            ja.put(jsonobj);
        }
        jsonobj = new JSONObject();
        jsonobj.put("list", ja);
        jsonobj.put("code", 200);
        return jsonobj;
    }

    public DBCollection getSweetsCollection() {
        return sweets;
    }

    public void addComment(String id, Comment comment) {
        BasicDBObject newComment = new BasicDBObject()
                .append("$push",
                        new BasicDBObject()
                                .append("comments", comment.toDBObject()));
        sweets.update(new BasicDBObject("_id", new ObjectId(id)), newComment);
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
            obj.put("sweet", new BasicDBObject("$regex", ".*" + s + ".*"));
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

    public void removeComment(String id, Comment comment) {

    }

    public void editComment(String id, Comment comment) {

    }

    public void likeSweet(String id, Like like) {

    }

    public void likeComment(String id, Like like) {

    }

    public void unlikeSweet(String id, Like like) {

    }

    public void unlikeComment(String id, Like like) {

    }

}
