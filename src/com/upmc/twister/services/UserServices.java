package com.upmc.twister.services;

import com.upmc.twister.dao.DAOFactory;
import com.upmc.twister.dao.UserDAO;
import com.upmc.twister.model.Friends;
import com.upmc.twister.model.User;
import org.apache.commons.fileupload.FileItem;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
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
            response.put("code", 200);
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
            response.put("f_name", user.getFirstName());
            response.put("l_name", user.getLastName());
            response.put("username", user.getUsername());
            response.put("id", user.getId());
            response.put("code", 200);
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
     * Methode qui recupere et renvoie une liste de personne qui match avec le parametre d'entre
     *
     * @param username
     * @return
     */
    public static JSONObject searchProfile(String key, String username) {
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
                user.put("f_name", u.getFirstName());
                user.put("l_name", u.getLastName());
                user.put("username", u.getUsername());

                ja.put(user);
            }
            response.put("users", ja);
            response.put("code", 1);
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
    public static JSONObject getFollowingUsers(String key) {
        if (key == null)
            return Response.BAD_REQUEST.parse();
        try {
            if (!ServiceTools.isConnected(key)) {
                return Response.UNKNOWN_CONNECTION.parse();
            }
            long id = ServiceTools.getUser(key).getId();
            List<Friends> userlist = ServiceTools.getFollowings(id);
            JSONObject response = new JSONObject();
            JSONArray ja = new JSONArray();
            for (Friends u : userlist) {
                JSONObject user = new JSONObject();
                user.put("f_id", u.getFollowed().getId());
                user.put("f_l_name", u.getFollowed().getLastName());
                user.put("f_f_name", u.getFollowed().getFirstName());
                user.put("f_username", u.getFollowed().getUsername());
                user.put("time", u.getTime());

                ja.put(user);
            }
            response.put("users", ja);
            response.put("code", 200);
            return response;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            return Response.INTERNAL_SERVER_ERROR.parse();
        }
    }


    public static JSONObject uploadPic(FileItem fileItem, File file, String key) {
        if (key == null || fileItem == null || file == null)
            return Response.BAD_REQUEST.parse();
        try {
            if (!ServiceTools.isConnected(key))
                return Response.UNKNOWN_CONNECTION.parse();

            long id = ServiceTools.getUser(key).getId();
            ((UserDAO)DAOFactory.USER_DAO.get()).setProfilePic(file.getName(),id);
            fileItem.write(file);
            return Response.OK.parse();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.INTERNAL_SERVER_ERROR.parse();
        }


    }
}
