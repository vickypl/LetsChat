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

public class RemoveFromGroup extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//session
		HttpSession sess = request.getSession(false);
		if(sess==null || (sess!=null && sess.getAttribute("user")==null)) {
			response.sendRedirect("index.jsp?msg=Login Required..");
			return;
		}
		
		String memberId = request.getParameter("memid");
		String grpId = request.getParameter("grpid");
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		UserController control = new UserController();
		boolean removeStatus = control.removeMemberFromGroup(grpId, memberId);
		
		if(removeStatus) {
			out.print("<span style='color: green; font:size: 15px; padding: 20px; font-weight: bold;'>Removed...<span><br>");
		} else {
			out.print("<span style='color: red; font:size: 15px; padding: 20px; font-weight: bold;'>Can't Remove try/later...<span><br>");			
		}
		
	}

}
