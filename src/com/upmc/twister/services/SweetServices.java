package com.upmc.twister.services;

import com.upmc.twister.dao.SweetsDB;
import com.upmc.twister.model.*;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SweetServices {
    /**
     * Methode qui va ajouter un addSweet dans la BDD
     *
     * @param key          Key de la personne connecte et qui veut ajouter un addSweet
     * @param sweetMessage
     * @return
     */
    public static JSONObject addSweet(String key, String sweetMessage) {
        if (key == null || sweetMessage == null)
            return Response.BAD_REQUEST.parse();
        try {
            if (!ServiceTools.isConnected(key)) {
                return Response.UNKNOWN_CONNECTION.parse();
            }

            User user = ServiceTools.getUser(key);
            user = ServiceTools.getUserProfile(user.getId()+"");
            Sweet sweet = new Sweet(sweetMessage, user.getId());
            SweetsDB sweetsDB = new SweetsDB();
            sweetsDB.create(sweet);
            JSONObject sweetJson = new JSONObject(sweet.toDBObject().toString());
            sweetJson.put("f_name",user.getFirstName());
            sweetJson.put("l_name",user.getLastName());
            sweetJson.put("username",user.getUsername());
            sweetJson.put("pic",user.getPic());
            sweetJson.put("code",200);
            return sweetJson;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            return Response.INTERNAL_SERVER_ERROR.parse();
        }
    }

    /**
     * Methode qui va recuperer et renvoyer les addSweet selon l'ID d'une personne
     *
     * @param id
     * @return
     */
    public static JSONObject getUserSweets(String key, String id) {
        if (id == null)
            return Response.BAD_REQUEST.parse();
        try {
            if (!ServiceTools.isConnected(key)) {
                return Response.UNKNOWN_CONNECTION.parse();
            }
            Long user_id = new Long(id);
            SweetsDB sweetsDB = new SweetsDB();
            return sweetsDB.find(user_id);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            return Response.INTERNAL_SERVER_ERROR.parse();
        }
    }


    public static JSONObject getMessageByQuery(String key, String query) {
        if (query == null && key == null) {
            return Response.BAD_REQUEST.parse();
        }
        try {
            if (!ServiceTools.isConnected(key)) {
                return Response.UNKNOWN_CONNECTION.parse();
            }
            SweetsDB sweetsDB = new SweetsDB();
            sweetsDB.getMessagesByQuery(query);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return Response.INTERNAL_SERVER_ERROR.parse();
        }
    }

    public static JSONObject getNewsFeed(String key) {
        if (key == null)
            return Response.BAD_REQUEST.parse();
        try {
            if (!ServiceTools.isConnected(key)) {
                return Response.UNKNOWN_CONNECTION.parse();
            }
            User me = ServiceTools.getUserProfile(ServiceTools.getUser(key).getId() + "");
            List<Friends> friends = ServiceTools.getFollowings(me.getId());
            Map<Long, User> data = new HashMap<>();
            data.put(me.getId(), me);
            for (Friends following : friends) {
                data.put(following.getFollowed().getId(), following.getFollowed());
            }

            SweetsDB sweetsDB = new SweetsDB();
            JSONObject result = sweetsDB.find(data);
            result.put("code", 200);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return Response.INTERNAL_SERVER_ERROR.parse();
        }

    }

    public static JSONObject removeSweet(String key, String sweetId) {
        if (key == null || sweetId == null)
            return Response.BAD_REQUEST.parse();


        try {
            if (!ServiceTools.isConnected(key)) {
                return Response.UNKNOWN_CONNECTION.parse();
            }

            User user = ServiceTools.getUser(key);
            Sweet sweet = new Sweet(sweetId);
            sweet.setUserId(user.getId());

            SweetsDB sweetsDB = new SweetsDB();
            sweetsDB.delete(sweet);
            if (sweet.getId() == null)
                return Response.OK.parse();
            else
                return Response.UNAUTHORIZED.parse();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            return Response.INTERNAL_SERVER_ERROR.parse();
        }
    }

    public static JSONObject likeSweet(String key, String sweetId) {
        if (key == null || sweetId == null)
            return Response.BAD_REQUEST.parse();


        try {
            if (!ServiceTools.isConnected(key)) {
                return Response.UNKNOWN_CONNECTION.parse();
            }

            User user = ServiceTools.getUser(key);
            SweetsDB sweetsDB = new SweetsDB();
            sweetsDB.likeSweet(sweetId, new Like(user.getId()));
            return Response.OK.parse();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            return Response.INTERNAL_SERVER_ERROR.parse();
        }
    }

    public static JSONObject unlikeSweet(String key, String sweetId) {
        if (key == null || sweetId == null)
            return Response.BAD_REQUEST.parse();


        try {
            if (!ServiceTools.isConnected(key)) {
                return Response.UNKNOWN_CONNECTION.parse();
            }

            User user = ServiceTools.getUser(key);
            SweetsDB sweetsDB = new SweetsDB();
            sweetsDB.unlikeSweet(user.getId(),sweetId);
            return Response.OK.parse();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            return Response.INTERNAL_SERVER_ERROR.parse();
        }
    }
}
