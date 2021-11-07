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

public class ShowGroupMessages extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String grpId = request.getParameter("id");
		
		//session
		HttpSession sess = request.getSession(false);
		if(sess==null || (sess!=null && sess.getAttribute("user")==null)) {
			response.sendRedirect("index.jsp?msg=Login Required..");
			return;
		}
		
		UserController control = new UserController();
		
		//getting group info
		String sql="select * from groups where id='"+grpId+"'";
		Groups group = control.getSingleGroups(sql);
		
		
		ArrayList<GroupMessages> groupMsgList = null;
		sql="select * from GROUP_MSGS where GROUPID='"+grpId+"'";
		groupMsgList = control.getGroupMessages(sql);
			
		request.setAttribute("group", group);
		request.setAttribute("groupMsgList", groupMsgList);
		
		RequestDispatcher rd = request.getRequestDispatcher("groupchatting.jsp");
		rd.forward(request, response);
		
	}
}
