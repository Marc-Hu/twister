package com.upmc.twister.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.upmc.twister.services.Operation;

public class Addition extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Map<String, String[]> params = request.getParameterMap();
		response.setContentType(" text / plain ");
			
		if (params.containsKey("a") && params.containsKey("b")
				&& params.containsKey("op")) {
			String op = params.get("op")[0];
			try {
				double a = Double.valueOf(params.get("a")[0]);
				double b = Double.valueOf(params.get("b")[0]);

				response.getWriter()
				.println(a + " " + op + " " + b + " = " + Operation.calcul(a, b, op));
			} catch (Exception e) {
				response.getWriter().println("An error has been occured!");
			}
		} else
			response.getWriter().println("Invalid parameters: need a and b and op! ");

	}
}
