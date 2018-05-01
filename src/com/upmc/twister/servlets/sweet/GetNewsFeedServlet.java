package com.upmc.twister.servlets.sweet;

import com.upmc.twister.services.SweetServices;
import com.upmc.twister.services.UserServices;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Servlet qui renvoie tous les sweets par rapport aux ids dans les params
 *
 * @author marc hu
 */
public class GetNewsFeedServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        resp.setContentType("application/json");
        JSONObject json = SweetServices.getNewsFeed(req.getParameter("key"));
        PrintWriter out = resp.getWriter();
        out.println(json);
    }

}