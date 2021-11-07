package com.user;
/**
author: Vicky pl
email: vicky542011@gmail.com
mobile: 7828789845
**/
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class ShowGroupMembers extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//session
		HttpSession sess = request.getSession(false);
		if(sess==null || (sess!=null && sess.getAttribute("user")==null)) {
			response.sendRedirect("index.jsp?msg=Login Required..");
			return;
		}
		User user = (User)sess.getAttribute("user");
		
		String grpId =request.getParameter("data");
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		//getting group members
		UserController control = new UserController();
		ArrayList<User> membersList = control.getGroupMembers(grpId);
		
		out.print("<span style='color: black; font-weight: bold:'>Group Admin: "+user.getUsername()+"</span>");
		out.print("<div style='height: 450px; overflow: auto'>");
		out.print("<table class='table' style='color: black; padding: 20px; font-family: cursive;'>");
		out.print("<thead>");
		out.print("<tr>");
		out.print("<th>Member Name</th>");
		out.print("<th>Option</th>");
		out.print("</thead>");
		out.print("</tr>");
		out.print("<tbody>");
		for(User mem: membersList) {
			out.print("<tr>");
			out.print("<td>"+mem.getFirstname()+" "+mem.getLastname()+"</td>");
			if(mem.getUsername().equals(user.getUsername())) {
				out.print("<td><button class='btn btn-primary'>Group Admin</button></td>");
			} else {
				out.print("<td><button onclick='removeFromGroup("+mem.getId()+","+grpId+")' class='btn btn-danger'>Remove From Group</button></td>");
			}
			out.print("</tr>");
			out.print("<tr>");
			out.print("<td id='removemsg'></td>");
			out.print("</tr>");
		}
		out.print("</tbody>");
		out.print("</table>");
		out.print("</div>");
		
	}

}
