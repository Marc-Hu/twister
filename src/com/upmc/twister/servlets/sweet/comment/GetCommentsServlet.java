package com.upmc.twister.servlets.sweet.comment;

import com.upmc.twister.services.CommentServices;
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
public class GetCommentsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        resp.setContentType("application/json");


        JSONObject json = CommentServices.getComments(req.getParameter("key"),req.getParameter("sweetId"));
        PrintWriter out = resp.getWriter();
        out.println(json);

    }

}
