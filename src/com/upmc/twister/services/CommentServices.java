package com.upmc.twister.services;

import com.upmc.twister.dao.SweetsDB;
import com.upmc.twister.model.Comment;
import com.upmc.twister.model.User;
import org.json.JSONObject;

public class CommentServices {

    /**
     * Methode qui ajoute un commentaire par rapport e l'Id du addSweet
     *
     * @param key            Cle du addSweet
     * @param sweetId        Id du addSweet
     * @param commentMessage
     * @return
     */
    public static JSONObject addComment(String key, String sweetId, String commentMessage) {

        if (key == null || sweetId == null || commentMessage == null)
            return Response.BAD_REQUEST.parse();


        try {
            if (!ServiceTools.isConnected(key)) {
                return Response.UNKNOWN_CONNECTION.parse();
            }

            User user = ServiceTools.getUser(key);
            SweetsDB sweetsDB = new SweetsDB();
            sweetsDB.addComment(sweetId, new Comment(user.getId(), commentMessage));
            return Response.OK.parse();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            return Response.INTERNAL_SERVER_ERROR.parse();
        }
    }

    public static JSONObject getComments(String key, String sweetId) {
        return null;
    }

    public static JSONObject removeComment(String key, String sweetId, String commentId) {
        return null;
    }

    public static JSONObject unlikeComment(String key, String sweetId, String commentId) {
        return null;
    }

    public static JSONObject likeComment(String key, String sweetId, String commentId) {
        return null;
    }
}
