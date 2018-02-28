package com.upmc.twister.dao;

import java.net.UnknownHostException;
import java.util.Map;
import java.util.Map.Entry;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.upmc.twister.model.Comment;

public class MongoUtils {
	private static final String host = "localhost";
	private static final int prot = 27017;
	public static DBCollection getCollection(String name){
		try {
			Mongo mongo = new Mongo("localhost",27017);
			mongo.getDB(TwisterContract.db_name).getCollection(name);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
}
