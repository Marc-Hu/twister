package com.upmc.twister.dao;

import static com.upmc.twister.dao.AbstractDAO.checkParameter;
import static com.upmc.twister.dao.TwisterContract.db_name;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.upmc.twister.model.Comment;
import com.upmc.twister.model.Like;
import com.upmc.twister.model.Sweet;

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
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		if (!checkParameter(o, Sweet.class))
			return;

		Sweet sweet = (Sweet) o;


	}

	@Override
	public void delete(Object o) throws Exception {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		if (!checkParameter(o, Sweet.class))
			return;
		Sweet sweet = (Sweet) o;

	}

	@Override
	public JSONObject find(long id) throws Exception {
		BasicDBObject gtQuery = new BasicDBObject();
		gtQuery.put("userId", id);
		DBCursor cursor = sweets.find(gtQuery);
		JSONObject jsonobj = new JSONObject();
		JSONArray ja = new JSONArray();
		while(cursor.hasNext()) {
//			System.out.println(cursor.next());
			BasicDBObject obj = (BasicDBObject) cursor.next();
			jsonobj = new JSONObject(obj.toString()); //Le contructeur JSONObject peut prendre un string de format JSON en paramètre
			ja.put(jsonobj);
		}
		jsonobj = new JSONObject();
		jsonobj.put("list", ja);
		jsonobj.put("code", 200);
		return jsonobj;
	}
	
	/**
	 * Méthode qui récupère les sweets selon une liste d'ids
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public JSONObject find(List<String> ids) throws Exception {
		BasicDBObject gtQuery = new BasicDBObject();
		BasicDBList list = new BasicDBList(); //Contiendra une liste de BasicDBObject
		for(String s : ids) {
			gtQuery.put("userId", new Long(s));
			list.add(gtQuery);
			gtQuery = new BasicDBObject();
		}
		DBObject query = new BasicDBObject("$or", list); //Ajout d'un OU mongo
		DBCursor cursor = sweets.find(query);
		JSONObject jsonobj = new JSONObject();
		JSONArray ja = new JSONArray();
		while(cursor.hasNext()) {
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
	
	public DBCollection getSweetsCollection(){
		return sweets;
	}
	
	public void addComment(String id,Comment comment) {
		BasicDBObject newComment = new BasicDBObject().append("$push",
				new BasicDBObject().append("comments", comment.toDBObject()));
		sweets.update(new BasicDBObject("_id", new ObjectId(id)), newComment);
	}
	
	public ArrayList<BasicDBObject> getMessagesByQuery(String query){
		String[] q = query.split(" ");
		HashSet<String> w = new HashSet<>();
		for (String s:q) {
			if(!w.contains(s))
				w.add(s);
		}
		System.out.println(w.toString());
		
		HashMap<String, Double> scores = new HashMap<>();
		for(String s : w) {
			BasicDBObject obj = new BasicDBObject();
			obj.put("sweet", new BasicDBObject("$regex",".*"+s+".*"));
			DBCursor cursor = sweets.find(obj);
			try {
				int i = 0;
				List<DBObject> doc = new ArrayList<>();
				while(cursor.hasNext()) {
					BasicDBObject res= (BasicDBObject) cursor.next();
					System.out.println(res.toString());
					doc.add(res);
					String id = res.get("_id").toString();
					System.out.println(doc.get(i).get("score").toString());
					Double val = Double.valueOf( doc.get(i).get("score").toString()); // Pas fini il faut modifier pour matché
					System.out.println("test");
					Double score = scores.get(id);
					score = (score==null) ? val : (score+val);
					scores.put(id,  score);
					i++;
				}
			}catch(Exception e) {
				System.out.println(e);
			}
		}
		System.out.println(scores.toString());
//		
//		List<Map.Entry<String, Double>> entries = new ArrayList<>(scores.entrySet());
//		Collections.sort(entries, new Comparator<Map.Entry<String, Double>>() {
//			public int compare(Map.Entry<String, Double> a, Map.Entry<String, Double> b) {
//				return b.getValue().compareTo(a.getValue());
//			}
//		});
//		
//		ArrayList<BasicDBObject> ret = new ArrayList<>();
//		for(Map.Entry<String, Double> entry : entries) {
//			BasicDBObject obj = new BasicDBObject();
//			obj.put("id", entry.getKey());
//			DBCursor cursor = docs.find(obj);
//			try {
//				if(cursor.hasNext()) {
//					BasicDBObject res = (BasicDBObject) cursor.next();
//					ret.add(res);
//				}
//			} catch(Exception e) {
//				System.out.println(e);
//			}
//		}
//		return ret;
		return null;
	}
	
	public void removeComment(String id,Comment comment) {
		
	}
	public void editComment(String id,Comment comment) {
		
	}
	public void likeSweet(String id,Like like) {
		
	}
	public void likeComment(String id,Like like) {
		
	}
	public void unlikeSweet(String id,Like like) {
		
	}
	public void unlikeComment(String id,Like like) {
		
	}

}
