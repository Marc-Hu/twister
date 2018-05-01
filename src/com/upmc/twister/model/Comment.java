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

public class Comment {

    private ObjectId id;
    private long userId;
    private String comment;
    private Date date;
    private List<Like> likes = new ArrayList<Like>();
    public Comment(String commentId){
        this.id =  new ObjectId(commentId);
    }

    public Comment(long userId, String comment) {
        this.userId = userId;
        this.comment = comment;
        date = new Date();
        id = new ObjectId();
    }


    public static Object asDBObjects(List<Comment> comments) {
        // TODO Auto-generated method stub
        List<DBObject> commentsDBObjects = new ArrayList<DBObject>();
        for (Comment cmt : comments) {
            commentsDBObjects.add(cmt.toDBObject());
        }
        return commentsDBObjects;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment1 = (Comment) o;
        return userId == comment1.userId &&
                Objects.equals(id, comment1.id) &&
                Objects.equals(comment, comment1.comment) &&
                Objects.equals(date, comment1.date) &&
                Objects.equals(likes, comment1.likes);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userId, comment, date, likes);
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
                .append("_id", id)
                .append("comment", comment)
                .append("userId", userId)
                .append("date", date)
                .append("likes", Like.asDBObjects(likes));
    }
}
