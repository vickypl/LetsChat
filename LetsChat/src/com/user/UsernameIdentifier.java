package com.user;
/**
author: Vicky pl
email: vicky542011@gmail.com
mobile: 7828789845
**/
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UsernameIdentifier extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String usrname = request.getParameter("usr");
		
		UserController control = new UserController();
		User user = null;
		String sql="select * from client where username='"+usrname+"'";
					user = control.getUser(sql);
	
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		if(user!=null) {
			out.print("<span style='color: red;'>Username Not Available.</span>");			
		} else {			
			out.print("<span style='color: lime;'>Username Available.. Minimum length of username must be 8.</span>");
		}
		
	}

}
