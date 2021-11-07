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

public class ShowFriendMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	@SuppressWarnings("unchecked")
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
		groupList=(ArrayList<Groups>)sess.getAttribute("groupList");
		
		//fetching user with who the msgs exchanged
		sql="select * from client where id='"+friend+"'";
		User frnd = control.getUser(sql); 
		
		//presentation logic
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html; charset=UTF-8");
		out.print("<div class='card-header msg_head'>");
		out.print("<div class='d-flex bd-highlight'>");
		out.print("<div class='img_cont'>");
		if(!frnd.getUrl().equals("null")) {
			out.print("<img src='profilepics\' class='rounded-circle user_img'>");
		} else {
			out.print("<img src='image/dp.jpg' class='rounded-circle user_img'>");			
		}
		out.print("<span class='online_icon'></span>");
		out.print("</div>");
		out.print("<div class='user_info'>");
		out.print("<span>"+frnd.getFirstname()+" "+frnd.getLastname()+"</span>");
		out.print("<p>"+msgList.size()+" Messages</p>");
		out.print("</div>");
		out.print("<table>");
		out.print("<tr>");
		out.print("<td>");
		out.print("<input type='hidden' id='frndid' value='"+frnd.getId()+"'/>");
		out.print("<select class='form-control' id='grpaddid' value=''>");
		if(groupList!=null) {
			for(Groups grp: groupList) {
				out.print("<option value='"+grp.getId()+"'>"+grp.getGroupName()+"</option>");
			}
		}
		out.print("</select>");
		out.print("</td>");
		out.print("<td>");
		out.print("<button class='btn btn-success' onclick='addToGroup()' >Add To Group</button>");
		out.print("</td>");
		out.print("</tr>");
		out.print("</table>");
		out.print("</div>");
		out.print("</div>");
		out.print("<div class='card-body msg_card_body' id='slidebox'>");
		for(Messages msg : msgList) {
			if(Integer.parseInt(msg.getToId())==friend) {
				out.print("<div class='d-flex justify-content-end mb-4'>");
				out.print("<div class='msg_cotainer_send'>");
				out.print(msg.getMessage());
				out.print("<span class='msg_time_send'>"+msg.getTime()+"</span>"); //time
				out.print("</div>");
				out.print("</div>");
			} else {
				out.print("<div class='d-flex justify-content-start mb-4'>");
				out.print("<div class='msg_cotainer'>");
				out.print(msg.getMessage());
				out.print("<span class='msg_time'>"+msg.getTime()+"</span>"); //time
				out.print("</div>");
				out.print("</div>");
			}
		}
		out.print("</div>");
		out.print("<script type='text/javascript'>");
		out.print("function fun() {");
		out.print("alert('hellowaorld');");
		out.print("}");
		out.print("</script>");
		out.print("<button onclick='alert('afsf')' class='btn btn-success'>hellow this is</button>");
	}

}