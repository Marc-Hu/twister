package com.upmc.twister.model;

import java.util.Date;
import java.util.UUID;

public class UserConnection {
	private String key;
	private User user;
	private Date time;
	private boolean root;

	public UserConnection(String key, User user) {
		super();
		this.key = key;
		this.user = user;
		time = new Date();
		root = false;
	}
	
	/**
	 * @param user
	 * @param root
	 */
	public UserConnection(int id, boolean root) {
		super();
		key = UUID.randomUUID().toString().replace("-", "");
		this.user = new User(id);
		this.root = root;
		time = new Date();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public boolean isRoot() {
		return root;
	}

	public void setRoot(boolean root) {
		this.root = root;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + (root ? 1231 : 1237);
		result = prime * result + ((time == null) ? 0 : time.hashCode());
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
		UserConnection other = (UserConnection) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (root != other.root)
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	

}
