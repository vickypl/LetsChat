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

public class SendUserMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//session
		HttpSession sess = request.getSession(false);
		if(sess==null || (sess!=null && sess.getAttribute("user")==null)) {
			response.sendRedirect("index.jsp?msg=Login Required..");
			return;
		}
		User user = (User)sess.getAttribute("user");
		
		int msgFrom = Integer.parseInt(user.getId());
		String senderName = user.getUsername();
		
		int msgTo = Integer.parseInt(request.getParameter("toid"));
		String msg = request.getParameter("msg");
		
		if(msg.equals("null") || msg.equals(" ")) {
			return;
		}
		
		UserController control = new UserController();
		boolean isSent = control.sendMessage(msgFrom, senderName, msgTo, msg);
		
		response.getWriter().print(isSent);
	
	}

}
