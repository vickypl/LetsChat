package com.user;
/**
author: Vicky pl
email: vicky542011@gmail.com
mobile: 7828789845
**/
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//fetching params
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		
		UserController control = new UserController();
		String sql="select * from client where username='"+username+"' or email='"+username+"' and password='"+password+"'";
		User user  = null;
				user = control.getUser(sql);
				
		if(user!=null) {
			
			//updating lastlogin
			String usrnm = user.getUsername();
			boolean updateStatus = control.updateLastLogin(usrnm);
			
			if(updateStatus) {
				HttpSession session = request.getSession(true);
				session.setAttribute("user", user);
				control.setUserToActiveSet(user);
				session.setAttribute("activeSet", control.getStaticActiveSet()); //static set to store active user sessions
				System.out.println("Active Set Size: "+control.getActiveSetSize());
				request.setAttribute("msg", "Successfully loggedIn...");
				RequestDispatcher rd = request.getRequestDispatcher("userloggedin.jsp");
				rd.forward(request, response);
			} else {
				response.sendRedirect("index.jsp?msg=Problem in Signing in....try later");
			}
		} else {
			response.sendRedirect("index.jsp?msg=Invalid Login/Password");		
		}
	}

}
