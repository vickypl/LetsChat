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

public class ChatService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//session
		HttpSession sess = request.getSession(false);
		if(sess==null || (sess!=null && sess.getAttribute("user")==null)) {
			response.sendRedirect("index.jsp?msg=Login Required..");
			return;
		}
		User user = (User)sess.getAttribute("user");
		
		
		//fetching the conversation with id
		String withId = request.getParameter("id");
		
		int friend = Integer.parseInt(withId); //whom user want to see msgs
		int me = Integer.parseInt(user.getId()); //users id
				
		
		//fetching msgs;
		UserController control = new UserController();
		String sql="select * from user_messages where fromid='"+friend+"' and toid='"+me+"' or fromid='"+me+"' and toid='"+friend+"'";
		ArrayList<Messages> msgList = control.getMessages(sql);
		
		//fetching groups
		ArrayList<Groups> groupList = null;
		sql="select * from groups where CREATORID='"+user.getId()+"'";
		groupList = control.getGroups(sql);
		sess.setAttribute("groupList", groupList);
		//groupList=(ArrayList<Groups>)sess.getAttribute("groupList");
		
		//fetching user with who the msgs exchanged
		sql="select * from client where id='"+friend+"'";
		User frnd = control.getUser(sql); 
		
		request.setAttribute("msgs", msgList);
		//request.setAttribute("groupList", groupList);
		request.setAttribute("frnd", frnd);
		
		RequestDispatcher rd = request.getRequestDispatcher("chatting.jsp");
		rd.forward(request, response);
		
	}

}
