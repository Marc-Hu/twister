package com.upmc.twister.servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import java.math.*;

public class SimpleServlet extends HttpServlet {
	private int counter = 0;
	private String mutex = "";

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.getWriter().println("<HTML><BODY>");
		resp.getWriter().println(this + ": <br>");
		synchronized (mutex) {
			for (int c = 0; c < 10; c++) {
				resp.getWriter().println("Counter = " + counter + "<BR>");
				counter++;
			}
		}
		resp.getWriter().println("</BODY></HTML>");
	}
}