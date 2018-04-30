package com.upmc.twister.servlets;

import com.upmc.twister.services.UserServices;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AddCommentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        resp.setContentType("text/plain");

        JSONObject json = UserServices.addComment(req.getParameter("key"),
                req.getParameter("sweetId"), req.getParameter("commentMessage"));
        PrintWriter out = resp.getWriter();
        out.println(json);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        resp.setContentType("application/json");

        JSONObject json = UserServices.addComment(req.getParameter("key"),
                req.getParameter("sweetId"), req.getParameter("commentMessage"));
        PrintWriter out = resp.getWriter();
        out.println(json);

    }

}