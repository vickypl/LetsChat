package com.user;
/**
author: Vicky pl
email: vicky542011@gmail.com
mobile: 7828789845
**/
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chat.db.DatabaseConnector;
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("signup.jsp");
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String servletname = this.getClass().getName();
		
		//fetching params
		String firstname = request.getParameter("fname");
		String lastname = request.getParameter("lname");
		String email = request.getParameter("email");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
//		String cpassword=request.getParameter("cpassword");
		
		DatabaseConnector db = new DatabaseConnector();
		
		//check if email is already available
		UserController control = new UserController();
		User user = null;
		String sql="select * from client where email='"+email+"'";
		user = control.getUser(sql);
		
		if(user!=null) {
			response.sendRedirect("signup.jsp?msg=Email Already Registered..");
			return;
		}
		
		
		Connection connection=null;
		PreparedStatement pstat = null;
		
		boolean signupStatus=false;
		
		try {
			connection = db.connectTo();
			sql = "insert into client(id, firstname, lastname, email, username, password, lastlogin, regdate, dpurl, role)"
					+ " values(client_id.nextval, ?, ?, ?, ?, ?, SYSDATE, SYSDATE, 'null', 'user')";
			pstat = connection.prepareStatement(sql);
			pstat.setString(1, firstname);
			pstat.setString(2, lastname);
			pstat.setString(3, email);
			pstat.setString(4, username);
			pstat.setString(5, password);
			
			int result = pstat.executeUpdate();
			
			if(result>0) {
				signupStatus=true;
			}			
			
		} catch (SQLException e) {
			db.logToFile(e, servletname);
			response.sendRedirect("errorpage.jsp");
		} catch (Exception e) {
			db.logToFile(e, servletname);
			response.sendRedirect("errorpage.jsp");
		}
		
		try {
			db.closeConnection(connection);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (signupStatus) {
			response.sendRedirect("index.jsp?msg=Signup Successfull login now...");
		} else {			
			response.sendRedirect("index.jsp?msg=SignUp Failed...try later");
		}
	}

}
