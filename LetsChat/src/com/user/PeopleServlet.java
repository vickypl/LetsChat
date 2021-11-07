package com.user;
/**
author: Vicky pl
email: vicky542011@gmail.com
mobile: 7828789845
**/
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PeopleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession sess = request.getSession(false);
		if(sess==null || (sess!=null && sess.getAttribute("user")==null)) {
			response.sendRedirect("index.jsp?msg=Login Required..");
			return;
		}
		User user = (User)sess.getAttribute("user");
		
		
		UserController control = new UserController();
		String sql="select * from client";
		ArrayList<User> userList = control.getUserList(sql);
		ArrayList<User> friendList = control.getFriendList(user.getId());
		
		ArrayList<User> peopleList = new ArrayList<User>();
		
		
		//extracting people who are already added into friend list
		for(User usr : userList) {
			if(!friendList.contains(usr)) {
				peopleList.add(usr);
			}
		}
		
		sess.setAttribute("userList", peopleList);
		response.sendRedirect("people.jsp");
		
	}

}
