package com.upmc.twister.model;

import com.upmc.twister.dao.TwisterContract;

import java.util.Date;
import java.util.Map;

public class Friends {
    private User follower;
    private User followed;
    private Date time;

    public Friends(User follower, User followed) {
        this.follower = follower;
        this.followed = followed;
        this.time = new Date();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((followed == null) ? 0 : followed.hashCode());
        result = prime * result
                + ((follower == null) ? 0 : follower.hashCode());
        result = prime * result + ((time == null) ? 0 : time.hashCode());
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
        Friends other = (Friends) obj;
        if (followed == null) {
            if (other.followed != null)
                return false;
        } else if (!followed.equals(other.followed))
            return false;
        if (follower == null) {
            if (other.follower != null)
                return false;
        } else if (!follower.equals(other.follower))
            return false;
        if (time == null) {
            if (other.time != null)
                return false;
        } else if (!time.equals(other.time))
            return false;
        return true;
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public User getFollowed() {
        return followed;
    }

    public void setFollowed(User followed) {
        this.followed = followed;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }


    /**
     * This method takes a user connection and its data as a map
     * it's iterate through each Entry in the map then check the key of the map
     * and set the data according to that column
     * each key in this map is a column of the UserConnectionEntry class,
     */
    public static void fill(Friends f, Map<String, Object> data) {
        if (data == null || f == null)
            return;
        // iterate through the map using the the Map.Entry class
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            // get they entry key
            switch (entry.getKey()) {
                // the current data is an id
                case TwisterContract.FriendsEntry.COLUMN_FOLLOWED:
                    f.getFollowed().setId((long) entry.getValue());
                    break;
                case TwisterContract.FriendsEntry.COLUMN_FOLLOWER:
                    f.getFollower().setId((long) entry.getValue());
                    break;
                case TwisterContract.FriendsEntry.COLUMN_TIMESTAMP:

                    break;
                case TwisterContract.UserEntry.COLUMN_FIRST_NAME:
                    f.getFollowed().setFirstName((String) entry.getValue());
                    break;
                case TwisterContract.UserEntry.COLUMN_LAST_NAME:
                    f.getFollowed().setLastName((String) entry.getValue());
                    break;
                case TwisterContract.UserEntry.COLUMN_USERNAME:
                    f.getFollowed().setUsername((String) entry.getValue());
                    break;


            }
        }
    }

}
