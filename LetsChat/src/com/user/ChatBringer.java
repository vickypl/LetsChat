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

public class ChatBringer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession sess = request.getSession(false);
		if(sess==null || (sess!=null && sess.getAttribute("user")==null)) {
			response.sendRedirect("index.jsp?msg=Login Required..");
			return;
		}
		User user = (User)sess.getAttribute("user");
		
		UserController control = new UserController();
		
		//fetching groups
		String sql="select * from groups where creatorid='"+user.getId()+"'";
		ArrayList<Groups> groupList = control.getGroups(sql);
		
		//fetching friends
		ArrayList<User> friendList = null;
		friendList = control.getFriendList(user.getId());
		
		sess.setAttribute("friendList", friendList);
		sess.setAttribute("groupList", groupList);
		response.sendRedirect("chating.jsp");
	}

}
