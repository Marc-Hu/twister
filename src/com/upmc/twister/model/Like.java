package com.upmc.twister.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class Like {
	private User user;
	private Date date;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
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
		Like other = (Like) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
	public   DBObject toDBObject() {
	    return new BasicDBObject("user_id",user.getId())
	                     .append("date",date);
	    
	}
	public static List<DBObject> toDBObjects(List<Like> likes){
		List<DBObject> list =new ArrayList<DBObject>();
		for (Like like : likes) {
			list.add(like.toDBObject());
		}
		return list;
	}
	
}
