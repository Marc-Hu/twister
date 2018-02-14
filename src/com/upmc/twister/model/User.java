package com.upmc.twister.model;

import java.util.Map;
import java.util.Map.Entry;
import com.upmc.twister.dao.*;

public class User {
	private int id;
	private String lastName;
	private String firstName;
	private String username;
	private String password;

	public User(int id) {
		super();
		this.id = id;
	}

	/**
	 * @param lastName
	 * @param firstName
	 * @param username
	 * @param password
	 */
	public User(String lastName, String firstName, String username,
			String password) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		this.username = username;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
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
		User other = (User) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id != other.id)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	public static void fill(User user, Map<String, Object> data) {
		if (data == null || user == null)
			return;
		for (Entry<String, Object> entry : data.entrySet()) {
			switch (entry.getKey()) {
			case TwisterContract.UserEntry._ID:
				user.setId((int) entry.getValue());
				break;
			case TwisterContract.UserEntry.COLUMN_FIRST_NAME:
				user.setFirstName((String) entry.getValue());
				break;
			case TwisterContract.UserEntry.COLUMN_LAST_NAME:
				user.setLastName((String) entry.getValue());
				break;
			case TwisterContract.UserEntry.COLUMN_USERNAME:
				user.setUsername((String) entry.getValue());
				break;
			case TwisterContract.UserEntry.COLUMN_PASSWORD:
				user.setPassword((String) entry.getValue());
				break;
			}
		}
	}

}
