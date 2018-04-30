package com.upmc.twister.servlets;

import com.upmc.twister.services.UserServices;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet qui permet de recureper la liste de toutes les personne qu'un id follow
 *
 * @author march
 */
public class ListFollowedServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        resp.setContentType("application/json");

        JSONObject json = UserServices.getFollowedList(req.getParameter("key"));
        PrintWriter out = resp.getWriter();
        out.println(json);

    }
}
