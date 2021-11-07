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

public class AddToGroup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Session
		HttpSession sess = request.getSession(false);
		if(sess==null || (sess!=null && sess.getAttribute("user")==null)) {
			response.sendRedirect("index.jsp?msg=Login Required..");
			return;
		}
		
		String grpId = request.getParameter("grpid");
		String frndId = request.getParameter("frndid");
		

		UserController control = new UserController();

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		//checking if the member is already present in the group
		GroupMemberRelation gmr = null;
		String sql="select * from group_members where groupid='"+grpId+"' and memberid='"+frndId+"'";
		gmr = control.getGroupMemberList(sql);
		
		if(gmr!=null) {
			out.print("<span style='text-align: center; font-weight: bold; font-size: 30px; color: lime;'>Memeber Already Added.</span><br>");	
			return;
		}
		
	
		int grpid = Integer.parseInt(grpId);
		int frndid = Integer.parseInt(frndId);
		
		
		boolean isAdded = control.addToGroup(grpid, frndid);
		
		
		if(isAdded) {
			out.print("<span style='text-align: center; font-weight: bold; font-size: 15px; color: green;'>Added to Group</span><br>");
		} else {
			out.print("<span style='text-align: center; font-weight: bold; font-size: 15px; color: green;'>Unable To Add This Person</span><br>");			
		}
	}

}
