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
 * Servlet qui permet de recuperer un addSweet par rapport e son Id
 *
 * @author march
 */
public class GetUserSweets extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        resp.setContentType("application/json");

        JSONObject json = SweetServices.getUserSweets(req.getParameter("key"), req.getParameter("id"));
        PrintWriter out = resp.getWriter();
        out.println(json);

    }
}