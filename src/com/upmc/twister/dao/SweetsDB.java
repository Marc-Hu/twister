package com.upmc.twister.dao;

import com.mongodb.*;
import com.upmc.twister.model.Comment;
import com.upmc.twister.model.Like;
import com.upmc.twister.model.Sweet;
import com.upmc.twister.model.User;
import com.upmc.twister.services.ServiceTools;
import com.upmc.twister.services.UserServices;
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
                .append("$push", new BasicDBObject()
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
        WriteResult result = sweets.remove(new BasicDBObject("_id", sweet.getId()).append("userId", sweet.getUserId()));
        if (result.getN() != 0) {
            sweet.setId(null);
        }
    }

    @Override
    public JSONObject find(long id) throws Exception {
        BasicDBObject gtQuery = new BasicDBObject();
        gtQuery.put("userId", id);
        DBCursor cursor = sweets.find(gtQuery);
        JSONObject jsonobj = new JSONObject();
        JSONArray ja = new JSONArray();
        User user = ServiceTools.getUserProfile(id+"");

        while (cursor.hasNext()) {
            BasicDBObject obj = (BasicDBObject) cursor.next();
            jsonobj = new JSONObject(obj.toString()); //Le contructeur JSONObject peut prendre un string de format JSON en parametre
            jsonobj.put("f_name",user.getFirstName());
            jsonobj.put("l_name",user.getLastName());
            jsonobj.put("username",user.getUsername());
            jsonobj.put("pic",user.getPic());
            ja.put(jsonobj);
        }
        jsonobj = new JSONObject();
        jsonobj.put("sweets", ja);
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
        params.put("$in", data.keySet()); // ids are the set
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
            sweet.put("username", u.getUsername());
            sweet.put("pic",u.getPic());
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

    public void likeComment(String sweetId, String commentId, Like like) {
        BasicDBObject newLike = new BasicDBObject()
                .append("$push", new BasicDBObject("comments.$.likes", like.toDBObject()));

        DBObject params = new BasicDBObject("_id", new ObjectId(sweetId))
                .append("comments._id", new ObjectId(commentId));
        sweets.update(params, newLike);
    }

    public void likeSweet(String id, Like like) {
        BasicDBObject newLike = new BasicDBObject()
                .append("$push",
                        new BasicDBObject()
                                .append("likes", like.toDBObject()));
        sweets.update(new BasicDBObject("_id", new ObjectId(id)), newLike);
    }

    public void removeComment(long myId, long userIdOfthisSweet, String sweetId, Comment comment) {
        BasicDBObject query = new BasicDBObject();
        BasicDBObject condition = new BasicDBObject();
        if (myId == userIdOfthisSweet) {

            query.append("$pull", new BasicDBObject("comments", new BasicDBObject("_id", comment.getId())));
            condition.append("_id", new ObjectId(sweetId)).append("userId", myId);


        } else {
            query.append("$pull",
                    new BasicDBObject("comments",
                            new BasicDBObject("_id", comment.getId()).append("userId", myId)));
            condition.append("_id", new ObjectId(sweetId));
        }

        WriteResult result = sweets.update(condition, query);
        if (result.getN() != 0) {
            comment.setId(null);
        }
    }

    public void unlikeComment(long userId, String sweetId, String commentId) {
        BasicDBObject query = new BasicDBObject();
        BasicDBObject condition = new BasicDBObject();
        query.append("$pull", new BasicDBObject("comments.$.likes", new BasicDBObject("userId", userId)));
        condition.append("_id", new ObjectId(sweetId)).append("comments._id", new ObjectId(commentId));
         sweets.update(condition, query);

    }

    public void unlikeSweet(long userId,String sweetId) {
        BasicDBObject query = new BasicDBObject();
        BasicDBObject condition = new BasicDBObject();
        query.append("$pull", new BasicDBObject("likes", new BasicDBObject("userId", userId)));
        condition.append("_id", new ObjectId(sweetId));
        sweets.update(condition, query);

    }
    public JSONObject getComments(String sweetId) throws Exception{
        BasicDBObject query = new BasicDBObject();
        query.put("_id",new ObjectId(sweetId)); // ids are the set

        DBObject sweetObj = sweets.findOne(query);
        JSONObject sweet = new JSONObject(sweetObj.toString());
        JSONArray comments = sweet.getJSONArray("comments");
        for(int i = 0;i<comments.length();i++){
            JSONObject comment = comments.getJSONObject(i);
            User user = ServiceTools.getUserProfile(""+comment.getLong("userId"));
            comment.put("f_name",user.getFirstName());
            comment.put("l_name",user.getLastName());
            comment.put("username",user.getUsername());
            comment.put("pic",user.getPic());
        }
        sweet.put("code",200);
        return sweet;
    }

    @Override
    public void update(Object o) throws Exception {
        if (!checkParameter(o, Sweet.class))
            return;

        Sweet sweet = (Sweet) o;
        throw new UnsupportedOperationException();
    }

}
