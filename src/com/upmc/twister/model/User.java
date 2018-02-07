package com.upmc.twister.model;

import java.util.HashSet;

public class User {
	private static HashSet<User> users = new HashSet<User>();
	private int id;
	private String name;
	private String userName;
	private String password;

	/**
	 * @param name
	 * @param userName
	 * @param password
	 */
	public User(String name, String userName, String password) {
		super();
		id = users.size()+1;
		this.name = name;
		this.userName = userName;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
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
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	public static boolean add(User user) {
		return users.add(user);
	}

	public static boolean remove(User user) {
		return users.remove(user);
	}

	public static boolean isExist(String username) {
		for (User user : users) {
			if(user.userName.equals(username))
				return true;
		}
		return false;
	}

	public static boolean checkPassword(String username, String password) {
		for (User user : users) {
			if(user.userName.equals(username)&&user.password.equals(password))
				return true;
		}
		return false;
	
	}

	public static int getUserId(String username) throws Exception {
		for (User user : users) {
			if(user.userName.equals(username))
				return user.id;
		}
		throw new Exception("User don't EXIST");
	}

}
