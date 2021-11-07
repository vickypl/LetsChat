package com.user;
/**
author: Vicky pl
email: vicky542011@gmail.com
mobile: 7828789845
**/
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteGroup extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//session
		HttpSession sess = request.getSession(false);
		if(sess==null || (sess!=null && sess.getAttribute("user")==null)) {
			response.sendRedirect("index.jsp?msg=Login Required..");
			return;
		}
		User user = (User)sess.getAttribute("user");
		
		String grpId = request.getParameter("gid");
		
		UserController control = new UserController();
		boolean deletingMsg = control.deleteGroupMsgs(grpId);
		boolean memberDeleted = control.deleteGroupMember(grpId);
		boolean idDeleted = control.deleteGroup(grpId, user.getId());

		
		if(idDeleted && deletingMsg && memberDeleted) {
			response.sendRedirect("getgroupchats");
		} else {
			response.sendRedirect("getgroupchats");
			//response.sendRedirect("grouplist.jsp?msg=Failed to Delete Try Later.");
		}
		
	}

}
