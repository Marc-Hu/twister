package com.upmc.twister.dao;

import static com.upmc.twister.dao.AbstractDAO.checkParameter;
import static com.upmc.twister.dao.TwisterContract.db_name;

import com.mongodb.DBCollection;
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
	public Object find(long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	public DBCollection getSweetsCollection(){
		return sweets;
	}
	
	public void addComment(String id,Comment comment) {
		
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
