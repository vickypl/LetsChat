package com.user;
/**
author: Vicky pl
email: vicky542011@gmail.com
mobile: 7828789845
**/
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		HttpSession sess = request.getSession(false);
		if(sess!=null && sess.getAttribute("user")!=null) {
			
			//removing user from the active list
			User user = (User)sess.getAttribute("user");
			UserController control = new UserController();
			control.removeFromActiveSet(user);
			
			sess.invalidate();
			response.sendRedirect("index.jsp?Logged Out Successfully...");
		}
	}

}
