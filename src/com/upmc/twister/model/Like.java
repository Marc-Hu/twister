package com.upmc.twister.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class Like {
	private long userId;
	private Date date;
	public Like() {
		
	}
	public Like(long userId) {
		this.userId = userId;
		date = new Date();
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + (int) (userId ^ (userId >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Like other = (Like) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}
	
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
	
		//Object to JSON in String
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Error";
	}
	public DBObject toDBObject() {
		return new BasicDBObject()
				.append("userId", userId)
				.append("time",date);
	}
	public static List<DBObject> asDBObjects(List<Like> likes) {
		// TODO Auto-generated method stub
		List<DBObject> likesDBObjects = new ArrayList<DBObject>();
		for (Like like : likes) {
			
			likesDBObjects.add(like.toDBObject());
		}
		return likesDBObjects;
	}
	
	

}
