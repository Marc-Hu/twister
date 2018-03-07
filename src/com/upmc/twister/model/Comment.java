package com.upmc.twister.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class Comment {

	private ObjectId id;
	private long userId;
	private String comment;
	private Date date;
	private List<Like> likes = new ArrayList<Like>();

	public Comment() {
		
	}
	public Comment(long userId, String comment) {
		this.userId = userId;
		this.comment = comment;
		date = new Date();

	}

	public Comment(int userId, String comment, Date date) {
		this.userId = userId;
		this.comment = comment;
		this.date = date;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<Like> getLikes() {
		return likes;
	}

	public void setLikes(List<Like> likes) {
		this.likes = likes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((likes == null) ? 0 : likes.hashCode());
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
		Comment other = (Comment) obj;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (likes == null) {
			if (other.likes != null)
				return false;
		} else if (!likes.equals(other.likes))
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
				.append("comment",comment)
				.append("userId",userId)
				.append("date", date)
				.append("likes", Like.asDBObjects(likes));
	}
	public static Object asDBObjects(List<Comment> comments) {
		// TODO Auto-generated method stub
		List<DBObject> commentsDBObjects = new ArrayList<DBObject>();
		for (Comment cmt : comments) {
			commentsDBObjects.add(cmt.toDBObject());
		}
		return commentsDBObjects;
	}
}
