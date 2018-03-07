package com.upmc.twister.model;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import com.upmc.twister.dao.TwisterContract;

public class UserConnection {
	private String key;
	private User user;
	private Date time;
	private boolean root;

	public UserConnection(String key) {
		super();
		this.key = key;

	}
	
	/**
	 * @param user
	 * @param root
	 */
	public UserConnection(long id, boolean root) {
		super();
		// generate a random string of 32 length 
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
	public void setUser(long userId) {
		this.user = new User(userId);
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
	
	/**
	 * This method takes a user connection and its data as a map
	 * it's iterate through each Entry in the map then check the key of the map
	 * and set the data according to that column  
	 * each key in this map is a column of the UserConnectionEntry class,
	 * 
	 * */
	public static void fill(UserConnection uc, Map<String, Object> data) {
		if (data == null || uc == null)
			return;
		// iterate through the map using the the Map.Entry class
		for (Entry<String, Object> entry : data.entrySet()) {
			// get they entry key
			switch (entry.getKey()) {
				// the current data is an id
				case TwisterContract.UserConnectionEntry._ID:
					// set the user id
					uc.setKey((String)entry.getValue());
					break;
				case TwisterContract.UserConnectionEntry.COLUMN_ROOT:
					uc.setRoot((Boolean) entry.getValue());
					break;
				case TwisterContract.UserConnectionEntry.COLUMN_USER:
					uc.setUser((long) entry.getValue());
					break;
				case TwisterContract.UserConnectionEntry.COLUMN_TIMESTAMP:
					//uc.setTime((String) entry.getValue());
					break;

			}
		}
	}
}
