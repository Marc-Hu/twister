package com.upmc.twister.dao;

/**
 * A contract class is a container for constants that define names for 
 * tables, and columns. The contract class allows you to use the same constants
 * across all the other classes in the same package. This lets you change a
 * column name in one place and have it propagate throughout your code.
 * @author Nadir Belarouci
 * @author Marc Hu
 * @version 1.0
 */
public final class TwisterContract {
	/*
	 * These are the cridentals to connect the databse 
	 * 
	 * */
	public static boolean mysql_pooling = false;
	public static String mysql_host = "localhost:3307";
	public static String mysql_password = "nazareth";
	public static String mysql_username = "root";
	public static String db_name = "twister";
	
	/**
	 * The constrctor is private since we don't need to instanciate this class
	 * */
	private TwisterContract() {

	}

	/**
	 * UserEntry is a static class which contians informations about the User Table 
	 * such its name, and its columnds.
	 * 
	 * */
	public static class UserEntry {
		public static final String TABLE_NAME = "User";
		public static final String _ID = "id_user";
		public static final String COLUMN_LAST_NAME = "last_name";
		public static final String COLUMN_FIRST_NAME = "first_name";
		public static final String COLUMN_USERNAME = "username";
		public static final String COLUMN_PASSWORD = "password";
	}
	
	/**
	 * FriendsTable Infos
	 * */
	public static class FriendsEntry {
		public static final String TABLE_NAME = "Friends";
		public static final String COLUMN_FOLLOWER = "from_id";
		public static final String COLUMN_FOLLOWED = "to_id";
		public static final String COLUMN_TIMESTAMP = "timestamp";

	}

	/**
	 * FriendsTable Infos
	 * */
	public static class UserConnectionEntry {
		public static final String TABLE_NAME = "Connection";
		public static final String _ID = "keycnx";
		public static final String COLUMN_USER = "id";
		public static final String COLUMN_TIMESTAMP = "timestamp";
		public static final String COLUMN_ROOT = "root";

	}

}
