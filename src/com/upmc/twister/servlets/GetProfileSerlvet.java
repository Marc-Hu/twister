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
 * Servlet qui permet de recuperer un profil par rapport e un id
 *
 * @author march
 */
public class GetProfileSerlvet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        resp.setContentType("text/plain");

        JSONObject json = UserServices.getProfile(req.getParameter("key"), req.getParameter("id"));
        PrintWriter out = resp.getWriter();
        out.println(json);

    }
}
