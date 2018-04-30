package com.upmc.twister.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Like {
    private ObjectId id;
    private long userId;
    private Date date;

    public Like(long userId) {
        this.id = new ObjectId();
        this.userId = userId;
        date = new Date();
        id = new ObjectId();
    }

    public static List<DBObject> asDBObjects(List<Like> likes) {
        // TODO Auto-generated method stub
        List<DBObject> likesDBObjects = new ArrayList<DBObject>();
        for (Like like : likes) {

            likesDBObjects.add(like.toDBObject());
        }
        return likesDBObjects;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Like like = (Like) o;
        return userId == like.userId &&
                Objects.equals(id, like.id) &&
                Objects.equals(date, like.date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userId, date);
    }

    public String toString() {
        ObjectMapper mapper = new ObjectMapper();

        //Object to JSON in String
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "Error";
    }

    public DBObject toDBObject() {
        return new BasicDBObject()
                .append("_id",id)
                .append("userId", userId)
                .append("time", date);
    }


}
