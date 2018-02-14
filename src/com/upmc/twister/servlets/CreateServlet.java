package com.upmc.twister.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.upmc.twister.services.UserServices;

public class CreateServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setContentType("text/plain");
		JSONObject json =  UserServices.create(req.getParameter("username"), req.getParameter("password"),
				req.getParameter("f_name"),req.getParameter("l_name"));
		PrintWriter out = resp.getWriter();
		out.println(json);
		
	}
}
