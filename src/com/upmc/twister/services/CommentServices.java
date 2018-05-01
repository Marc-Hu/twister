package com.upmc.twister.services;

import com.upmc.twister.dao.SweetsDB;
import com.upmc.twister.model.Comment;
import com.upmc.twister.model.Like;
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
        if (key == null || sweetId == null )
            return Response.BAD_REQUEST.parse();


        try {
            if (!ServiceTools.isConnected(key)) {
                return Response.UNKNOWN_CONNECTION.parse();
            }
            SweetsDB sweetsDB = new SweetsDB();
            return sweetsDB.getComments(sweetId);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            return Response.INTERNAL_SERVER_ERROR.parse();
        }
    }

    public static JSONObject removeComment(String key, String sweetUserId, String sweetId, String commentId) {
        if (key == null || sweetId == null || sweetUserId == null || commentId == null)
            return Response.BAD_REQUEST.parse();
        long sweetUserIdL;
        try {
            sweetUserIdL = Long.valueOf(sweetUserId);
        } catch (NumberFormatException e) {
            return Response.BAD_REQUEST.parse();
        }

        try {
            if (!ServiceTools.isConnected(key)) {
                return Response.UNKNOWN_CONNECTION.parse();
            }

            User me = ServiceTools.getUser(key);
            SweetsDB sweetsDB = new SweetsDB();
            Comment comment = new Comment(commentId);
            sweetsDB.removeComment(me.getId(), sweetUserIdL, sweetId, comment);
            if (comment.getId() == null)
                return Response.OK.parse();
            else
                return Response.UNAUTHORIZED.parse();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            return Response.INTERNAL_SERVER_ERROR.parse();
        }
    }

    public static JSONObject unlikeComment(String key, String sweetId, String commentId) {
        if (key == null || sweetId == null ||commentId ==null)
            return Response.BAD_REQUEST.parse();


        try {
            if (!ServiceTools.isConnected(key)) {
                return Response.UNKNOWN_CONNECTION.parse();
            }

            SweetsDB sweetsDB = new SweetsDB();

            sweetsDB.unlikeComment(ServiceTools.getUser(key).getId(),sweetId,commentId);
            return Response.OK.parse();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            return Response.INTERNAL_SERVER_ERROR.parse();
        }
    }

    public static JSONObject likeComment(String key, String sweetId, String commentId) {
        if (key == null || sweetId == null ||commentId ==null)
              return Response.BAD_REQUEST.parse();


        try {
            if (!ServiceTools.isConnected(key)) {
                return Response.UNKNOWN_CONNECTION.parse();
            }

            SweetsDB sweetsDB = new SweetsDB();
            Like like = new Like(ServiceTools.getUser(key).getId());
            sweetsDB.likeComment(sweetId,commentId,like);
            return Response.OK.parse();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            return Response.INTERNAL_SERVER_ERROR.parse();
        }
    }
}
