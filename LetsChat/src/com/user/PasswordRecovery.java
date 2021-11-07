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

import mail.Email;

public class PasswordRecovery extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("forgotpassword.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = request.getParameter("email");
		
		UserController control = new UserController();
		
		User user = null;
		String sql = "select * from client where email='"+email+"'";
			user = control.getUser(sql); 
		
			if(user!=null) {
				Email mail = new Email("academicaliv@gmail.com", "47881220");
				mail.setRecipient(user.getEmail());
				mail.setSubject("Your Password From LetsChat");
				mail.setMessage("Your Password Is: "+user.getPassword());
				boolean sendStatus = mail.sendMail();
				if(sendStatus) {
					response.sendRedirect("forgotpassword.jsp?msg=Password sent to given email....");
				} else {
					response.sendRedirect("forgotpassword.jsp?msg=Failed to Send the password....");					
				}
			} else {
				response.sendRedirect("forgotpassword.jsp?msg=E-mail Not Registered..");
			}
			
	}

}
