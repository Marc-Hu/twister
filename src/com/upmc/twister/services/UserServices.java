package com.upmc.twister.services;

import com.upmc.twister.dao.DAOFactory;
import com.upmc.twister.dao.SweetsDB;
import com.upmc.twister.model.Comment;
import com.upmc.twister.model.Friends;
import com.upmc.twister.model.Sweet;
import com.upmc.twister.model.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class UserServices {

    /**
     * Methode qui va loguer une personne et l'ajouter dans la BDD de connection
     *
     * @param username
     * @param password
     * @return
     */
    public static JSONObject login(String username, String password) {
        if (username == null || password == null || username.equals("") || password.equals(""))
            return Response.BAD_REQUEST.parse();
        try {
            if (!ServiceTools.isExist(username))
                return Response.UNKWOWN_USER.parse();

            if (!ServiceTools.checkPassword(username, password))
                return Response.UNKWOWN_USER.parse();

            long id = ServiceTools.getUserId(username);
            String key = ServiceTools.insertConnection(id, false);
            JSONObject response = new JSONObject();
            response.put("key", key);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return Response.INTERNAL_SERVER_ERROR.parse();
        }
    }

    /**
     * Methode qui va creer un utilisateur dans la BDD
     *
     * @param l_name
     * @param f_name
     * @param username
     * @param password
     * @return
     */
    public static JSONObject create(String l_name, String f_name,
                                    String username, String password) {
        if (username == null || password == null || f_name == null
                || l_name == null)
            return Response.BAD_REQUEST.parse();
        try {
            if (ServiceTools.isExist(username))
                return Response.USER_ALREADY_EXISTS.parse();

            User user = new User(l_name, f_name, username, password);
            DAOFactory.USER_DAO.get().create(user);
            // TODO create user
            return Response.OK.parse();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.INTERNAL_SERVER_ERROR.parse();
        }
    }

    /**
     * Methode qui recupere le profile d'une personne par rapport e son username
     *
     * @param username
     * @return
     */
    public static JSONObject getProfile(String key, String username) {

        if (username == null)
            return Response.BAD_REQUEST.parse();
        try {
            if (!ServiceTools.isConnected(key)) {
                return Response.UNKNOWN_CONNECTION.parse();
            }
            User user = ServiceTools.getUserProfile(username);
            JSONObject response = new JSONObject();
            response.put("firstname", user.getFirstName());
            response.put("lastname", user.getLastName());
            response.put("username", user.getUsername());
            response.put("id", user.getId());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return Response.INTERNAL_SERVER_ERROR.parse();
        }
    }

    /**
     * Methode qui permet de se deconnecter de la BDD
     *
     * @param key
     * @return
     */
    public static JSONObject logout(String key) {
        try {
            if (key == null)
                return Response.BAD_REQUEST.parse();
            if (!ServiceTools.isConnected(key))
                return Response.UNKNOWN_CONNECTION.parse();
            ServiceTools.removeKey(key);
            return Response.OK.parse();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return Response.INTERNAL_SERVER_ERROR.parse();

        }
    }

    /**
     * Methode qui permet e un utilisateur de follow une autre personne
     *
     * @param key
     * @param followed
     * @return
     */
    public static JSONObject follow(String key, String followed) {
        if (key == null || followed == null)
            return Response.BAD_REQUEST.parse();
        int followedId = 0;
        try {
            followedId = Integer.valueOf(followed);
        } catch (NumberFormatException e) {
            return Response.BAD_REQUEST.parse();
        }

        try {
            if (!ServiceTools.isConnected(key)) {
                return Response.UNKNOWN_CONNECTION.parse();
            }
            if (!ServiceTools.isExist(followedId)) {
                return Response.UNKWOWN_USER.parse();
            }
            User user = ServiceTools.getUser(key);
            ServiceTools.insertFriends(user, new User(followedId));
            return Response.OK.parse();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            return Response.INTERNAL_SERVER_ERROR.parse();
        }
    }

    /**
     * Methode qui permet de ne plus suivre une personne
     *
     * @param key
     * @param followed
     * @return
     */
    public static JSONObject unfollow(String key, String followed) {
        if (key == null || followed == null)
            return Response.BAD_REQUEST.parse();
        int followedId = 0;
        try {
            followedId = Integer.valueOf(followed);
        } catch (NumberFormatException e) {
            return Response.BAD_REQUEST.parse();
        }

        try {
            if (!ServiceTools.isConnected(key)) {
                return Response.UNKNOWN_CONNECTION.parse();
            }
            if (!ServiceTools.isExist(followedId)) {
                return Response.UNKWOWN_USER.parse();
            }
            User user = ServiceTools.getUser(key);
            ServiceTools.removeFriends(user, new User(followedId));
            return Response.OK.parse();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            return Response.INTERNAL_SERVER_ERROR.parse();
        }
    }

    /**
     * Methode qui va ajouter un sweet dans la BDD
     *
     * @param key          Key de la personne connecte et qui veut ajouter un sweet
     * @param sweetMessage
     * @return
     */
    public static JSONObject sweet(String key, String sweetMessage) {
        if (key == null || sweetMessage == null)
            return Response.BAD_REQUEST.parse();


        try {
            if (!ServiceTools.isConnected(key)) {
                return Response.UNKNOWN_CONNECTION.parse();
            }

            User user = ServiceTools.getUser(key);
            Sweet sweet = new Sweet(sweetMessage, user.getId());
            SweetsDB sweetsDB = new SweetsDB();
            sweetsDB.create(sweet);
            return Response.OK.parse();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            return Response.INTERNAL_SERVER_ERROR.parse();
        }
    }

    /**
     * Methode qui va recuperer et renvoyer les sweet selon l'ID d'une personne
     *
     * @param id
     * @return
     */
    public static JSONObject getSweetById(String key, String id) {
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

    /**
     * Methode qui ajoute un commentaire par rapport e l'Id du sweet
     *
     * @param key            Cle du sweet
     * @param sweetId        Id du sweet
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

    /**
     * Methode qui recupere et renvoie une liste de personne qui match avec le parametre d'entre
     *
     * @param username
     * @return
     */
    public static JSONObject getUserListByUsername(String key, String username) {
        if (username == null)
            return Response.BAD_REQUEST.parse();
        try {
            if (!ServiceTools.isConnected(key)) {
                return Response.UNKNOWN_CONNECTION.parse();
            }
            List<User> userlist = ServiceTools.getUserList(username);
            JSONObject response = new JSONObject();
            JSONArray ja = new JSONArray();
            for (User u : userlist) {
                JSONObject user = new JSONObject();
                user.put("firstname", u.getFirstName());
                user.put("lastname", u.getLastName());
                user.put("username", u.getUsername());

                ja.put(user);
            }
            response.put("list", ja);
            return response;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            return Response.INTERNAL_SERVER_ERROR.parse();
        }
    }

    /**
     * Methode qui va recuperer la liste des personnes qu'on follow
     *
     * @param key
     * @return
     */
    public static JSONObject getFollowedList(String key) {
        if (key == null)
            return Response.BAD_REQUEST.parse();
        try {
            if (!ServiceTools.isConnected(key)) {
                return Response.UNKNOWN_CONNECTION.parse();
            }
            long id = ServiceTools.getUser(key).getId();
            List<Friends> userlist = ServiceTools.getFollowedList(id);
            JSONObject response = new JSONObject();
            JSONArray ja = new JSONArray();
            for (Friends u : userlist) {
                JSONObject user = new JSONObject();
                user.put("followed_id", u.getFollower().getId());
                user.put("followed_lastname", u.getFollower().getLastName());
                user.put("followed_firstname", u.getFollower().getFirstName());
                user.put("followed_username", u.getFollower().getUsername());
                user.put("time", u.getTime());

                ja.put(user);
            }
            response.put("list", ja);
            return response;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            return Response.INTERNAL_SERVER_ERROR.parse();
        }
    }


    /**
     * Methode qui va renvoyer la liste des sweets pour des ids donnes
     *
     * @param ids Tous les ids ou on veut leur sweets
     * @return
     */
    public static JSONObject getSweet(String key, List<String> ids) {
        if (ids.size() == 0)
            return Response.BAD_REQUEST.parse();
        try {
            if (!ServiceTools.isConnected(key)) {
                return Response.UNKNOWN_CONNECTION.parse();
            }
            SweetsDB sweetsDB = new SweetsDB();
            return sweetsDB.find(ids);
        } catch (Exception e) {
            e.printStackTrace();
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

}
