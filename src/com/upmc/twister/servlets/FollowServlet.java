package com.upmc.twister.servlets;

import com.upmc.twister.services.Response;
import com.upmc.twister.services.UserServices;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet qui permet de follow un utilisateur
 *
 * @author march
 */
public class FollowServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        // TODO Auto-generated method stub
        resp.setContentType("text/plain");
        String follow = req.getParameter("follow");
        if (follow == null) {
            out.println(Response.BAD_REQUEST.parse());
            return;
        }
        JSONObject json;
        if(follow.equalsIgnoreCase("true")){
             json = UserServices.follow(req.getParameter("key"),
                    req.getParameter("followedId"));
        }else if(follow.equalsIgnoreCase("false")){
            json = UserServices.unfollow(req.getParameter("key"),
                    req.getParameter("followedId"));
        }else{
            json = Response.BAD_REQUEST.parse();
        }

        out.println(json);

    }

}
