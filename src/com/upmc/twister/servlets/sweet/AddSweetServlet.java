package com.upmc.twister.servlets.sweet;

import com.upmc.twister.services.SweetServices;
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
public class AddSweetServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        resp.setContentType("application/json");


        JSONObject json = SweetServices.addSweet(req.getParameter("key"),
                req.getParameter("sweet"));
        PrintWriter out = resp.getWriter();
        out.println(json);

    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        resp.setContentType("application/json");


        JSONObject json = SweetServices.addSweet(req.getParameter("key"),
                req.getParameter("sweet"));
        PrintWriter out = resp.getWriter();
        out.println(json);

    }

}
