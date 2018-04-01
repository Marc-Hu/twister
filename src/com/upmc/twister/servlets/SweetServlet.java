package com.upmc.twister.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.upmc.twister.services.UserServices;
/**
 * Servlet qui permet de créer un sweet par rapport à sa clé de connection
 * @author march
 *
 */
public class SweetServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setContentType("text/plain");
		
		JSONObject json = UserServices.sweet(req.getParameter("key"),
				req.getParameter("sweet"));
		PrintWriter out = resp.getWriter();
		out.println(json);

	}
	
}
