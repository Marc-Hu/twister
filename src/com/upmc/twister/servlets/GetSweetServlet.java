package com.upmc.twister.servlets;

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
public class GetSweetServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        resp.setContentType("text/plain");
        Enumeration<String> e = req.getParameterNames();
        List<String> list = new ArrayList<>();
        while (e.hasMoreElements()) {
            String element = e.nextElement();
           if (!element.equals("key"))
                list.add(req.getParameter(element));
        }
        JSONObject json = UserServices.getSweet(req.getParameter("key"), list);
        PrintWriter out = resp.getWriter();
        out.println(json);

    }

//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
//			throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		resp.setContentType("text/plain");
//		List<String> list = new ArrayList<String>(Arrays.asList(req.getParameterValues("ids"))); 
//		JSONObject json = UserServices.getSweet(req.getParameter("key"), list);
//		PrintWriter out = resp.getWriter();
//		out.println(json);
//
//	}
}