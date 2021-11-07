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

public class FriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//friendlist.jsp
		HttpSession sess = request.getSession(false);
		if(sess==null || (sess!=null && sess.getAttribute("user")==null)) {
			response.sendRedirect("index.jsp?msg=Login Required..");
			return;
		}
		User user = (User)sess.getAttribute("user");
		
		UserController control = new UserController();
		
		ArrayList<User> friendList = null;
					friendList=control.getFriendList(user.getId());
			
		if(friendList.size()>0) {
			sess.setAttribute("friendList", friendList);
			response.sendRedirect("friendlist.jsp");
			
		} else {
			response.sendRedirect("friendlist.jsp?msg=No Friends Added");
		}
	}

}
