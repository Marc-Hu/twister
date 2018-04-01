package com.upmc.twister.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.upmc.twister.services.UserServices;
/**
 * Servlet qui renvoie tous les sweets par rapport aux ids dans les params
 * @author march
 *
 */
public class GetSweetServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setContentType("text/plain");
		Enumeration<String> e =  req.getParameterNames();
		List<String> list = new ArrayList<>();
		while(e.hasMoreElements())
			list.add(req.getParameter(e.nextElement()));
		JSONObject json = UserServices.getSweet(list);
		PrintWriter out = resp.getWriter();
		out.println(json);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setContentType("text/plain");
		List<String> list = new ArrayList<String>(Arrays.asList(req.getParameterValues("ids"))); 
		JSONObject json = UserServices.getSweet(list);
		PrintWriter out = resp.getWriter();
		out.println(json);

	}
}