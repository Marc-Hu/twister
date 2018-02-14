package com.upmc.twister.dao;

public final class TwisterContract {
	public static boolean mysql_pooling = false;
	public static String mysql_host = "localhost";
	public static String mysql_password = "root";
	public static String mysql_username = "root";
	public static String mysql_db = "twister";

	
	private TwisterContract() {

	}

	public static class UserEntry  {
		public static final String TABLE_NAME = "User";
		public static final String _ID = "id_user";
		public static final String COLUMN_LAST_NAME = "last_name";
		public static final String COLUMN_FIRST_NAME = "first_name";
		public static final String COLUMN_USERNAME = "username";
		public static final String COLUMN_PASSWORD = "password";
	}
	public static class FriendsEntry  {
		public static final String TABLE_NAME = "Friends";
		public static final String COLUMN_FOLLOWER = "from_id";
		public static final String COLUMN_FOLLOWED = "to_id";
		public static final String COLUMN_TIMESTAMP = "timestamp";

	}
	public static class UserConnectionEntry  {
		public static final String TABLE_NAME = "Connection";
		public static final String _ID= "key";
		public static final String COLUMN_USER = "id";
		public static final String COLUMN_TIMESTAMP = "timestamp";
		public static final String COLUMN_ROOT = "root";


	}

}
