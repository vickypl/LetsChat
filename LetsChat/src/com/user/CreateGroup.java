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
import javax.servlet.http.HttpSession;

public class CreateGroup extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//fetching param
		String groupname = request.getParameter("gname");
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		String grpname = groupname.trim();
		if(!isGroupNameValid(groupname)) {
			out.print("<span style='color: red; font:size: 15px;'>Please input a valid group name.<span><br>");
			return;
		}
		
		//session
		HttpSession sess = request.getSession(false);
		if(sess==null || (sess!=null && sess.getAttribute("user")==null)) {
			response.sendRedirect("index.jsp?msg=Login Required..");
			return;
		}
		
		User user = (User)sess.getAttribute("user");
		int creatorId = Integer.parseInt(user.getId());
		
		UserController control = new UserController();
		boolean createStatus = control.createGroup(creatorId, grpname);
		
		//creating this user as one of the member of the group;
		int thisusersid = Integer.parseInt(user.getId());
		int grpId = control.getletestGroupId();
		boolean isAdded = control.addToGroup(grpId, thisusersid);
		
		if(isAdded && createStatus) {
			out.print("<span style='color: green; font:size: 15px;'>Group Successfully Created.<span>");
		} else {
			out.print("<span style='color: red; font:size: 15px;'>Problem in creating new group. try later.<span>");
		}
		
	}
	
	public static boolean isGroupNameValid(String str) {
        return str.matches("[a-zA-Z0-9 ]+");
    }

}
