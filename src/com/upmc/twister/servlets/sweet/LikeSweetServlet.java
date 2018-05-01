package com.upmc.twister.servlets.sweet;

import com.upmc.twister.services.Response;
import com.upmc.twister.services.SweetServices;
import com.upmc.twister.services.UserServices;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet qui permet de creer un addSweet par rapport e sa cle de connection
 *
 * @author march
 */
public class LikeSweetServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        resp.setContentType("application/json");

        PrintWriter out = resp.getWriter();

        String like = req.getParameter("like");
        if (like == null) {
            out.println(Response.BAD_REQUEST.parse());
            return;
        }
        JSONObject json;
        if(like.equalsIgnoreCase("true")){
            json = SweetServices.likeSweet(req.getParameter("key"),
                    req.getParameter("sweetId"));
        }else if(like.equalsIgnoreCase("false")){
            json = SweetServices.unlikeSweet(req.getParameter("key"),
                    req.getParameter("sweetId"));
        }else{
            json = Response.BAD_REQUEST.parse();
        }

        out.println(json);


    }

}
