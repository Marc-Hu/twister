package com.upmc.twister.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class Comment {
	private Sweet sweet;
	private int id;
	private User user;
	private String comment;
	private Date date;
	private List<Like> likes = new ArrayList<Like>();
	public Comment(long userId,String comment){
		user = new User(userId);
		this.comment = comment;
		date = new Date();
	
		
	}
	public Comment(int userId,String comment,Date date){
		user = new User(userId);
		this.comment = comment;
		this.date = date;
	}

	
	
	public Sweet getSweet() {
		return sweet;
	}
	public void setSweet(Sweet sweet) {
		this.sweet = sweet;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public long getUserId(){
		return user.getId();
	}
	public void setUser(User user) {
		this.user = user;
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
		result = prime * result + id;
		result = prime * result + ((likes == null) ? 0 : likes.hashCode());
		result = prime * result + ((sweet == null) ? 0 : sweet.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		if (id != other.id)
			return false;
		if (likes == null) {
			if (other.likes != null)
				return false;
		} else if (!likes.equals(other.likes))
			return false;
		if (sweet == null) {
			if (other.sweet != null)
				return false;
		} else if (!sweet.equals(other.sweet))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	public   DBObject toDBObject() {
	    return new BasicDBObject("user_id",getUserId())
	                     .append("comment",comment)
	                     .append("date",date)
	                     .append("likes",Like.toDBObjects(likes));
	    
	}
	
}
