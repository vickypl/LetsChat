package com.user;
/**
author: Vicky pl
email: vicky542011@gmail.com
mobile: 7828789845
**/
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetGroupChats extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//session
		HttpSession sess = request.getSession(false);
		if(sess==null || (sess!=null && sess.getAttribute("user")==null)) {
			response.sendRedirect("index.jsp?msg=Login Required..");
			return;
		}
		User user = (User)sess.getAttribute("user");
		
		UserController control = new UserController();
		
		//fetching group created by this user
		ArrayList<Groups> groupList = null;
		String sql="select * from groups where creatorid='"+user.getId()+"'";
		groupList = control.getGroups(sql);
	
		//fetching group this user is in created by other members;
		ArrayList<Groups> otherGroupList = null;
		otherGroupList = control.getGroupListUserParticipatedIn(user.getId());
		
		request.setAttribute("otherGroupList", otherGroupList);
		request.setAttribute("groupList", groupList);
		RequestDispatcher rd = request.getRequestDispatcher("grouplist.jsp");
		rd.forward(request, response);
	}

}
