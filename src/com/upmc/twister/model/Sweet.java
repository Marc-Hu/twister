package com.upmc.twister.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** 
 * This class represent a tweet */
public class Sweet {
	private String sweet;
	private User user;
	private List<Comment> comments = new ArrayList<Comment>();
	
	/**
	 * @param sweet
	 * @param user
	 */
	public Sweet(String sweet, User user) {
		super();
		this.sweet = sweet;
		this.user = user;
	}
	public String getSweet() {
		return sweet;
	}
	public void setSweet(String sweet) {
		this.sweet = sweet;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((comments == null) ? 0 : comments.hashCode());
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
		Sweet other = (Sweet) obj;
		if (comments == null) {
			if (other.comments != null)
				return false;
		} else if (!comments.equals(other.comments))
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
		
	
}
