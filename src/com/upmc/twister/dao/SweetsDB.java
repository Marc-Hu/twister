package com.upmc.twister.dao;

import static com.upmc.twister.dao.AbstractDAO.checkParameter;
import static com.upmc.twister.dao.TwisterContract.db_name;

import com.mongodb.client.MongoCollection;

import com.upmc.twister.model.Comment;
import com.upmc.twister.model.Sweet;
import com.mongodb.Block;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import static java.util.Arrays.asList;

public class SweetsDB implements DAO {
	private MongoCollection<Sweet> sweets;

	public SweetsDB() {
		sweets = MongoConnection.getDatabase(db_name).getCollection("sweets", Sweet.class);
	}

	@Override
	public void create(Object o) throws Exception {
		// TODO Auto-generated method stub
		if (!checkParameter(o, Sweet.class))
			return;

		Sweet sweet = (Sweet) o;
		sweets.insertOne(sweet);
	}

	@Override
	public void update(Object o) throws Exception {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		if (!checkParameter(o, Sweet.class))
			return;

		Sweet sweet = (Sweet) o;

		sweets.replaceOne(eq("id", sweet.getId()), sweet);

	}

	@Override
	public void delete(Object o) throws Exception {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		if (!checkParameter(o, Sweet.class))
			return;

		Sweet sweet = (Sweet) o;

		sweets.deleteOne(eq("id", sweet.getId()));

	}

	@Override
	public Object find(int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	public MongoCollection<Sweet> getSweetsCollection(){
		return sweets;
	}

}
