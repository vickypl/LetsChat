package com.user;
/**
author: Vicky pl
email: vicky542011@gmail.com
mobile: 7828789845
**/
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.chat.db.DatabaseConnector;

public class RemoveFriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String servletname = this.getClass().getName();
		
		HttpSession sess = request.getSession(false);
		if(sess==null || (sess!=null && sess.getAttribute("user")==null)) {
			response.sendRedirect("index.jsp?msg=Login Required..");
			return;
		}
		User user = (User)sess.getAttribute("user");
		
		String id = request.getParameter("id");
		
		DatabaseConnector db = new DatabaseConnector();

		Connection connection = null;
		Statement statement = null;
		
		//hence we know friends exists in pairs 
		boolean delStatus = false;
		//from is the user who added to is the user whom to add
		int from = Integer.parseInt(user.getId());
		int to = Integer.parseInt(id);
		
		try {
			connection = db.connectTo();
			statement = connection.createStatement();
			
			//commit false as it will applied after both queries post successfully
			connection.setAutoCommit(false);
			//batch of two quaries together
			
			String sql = "delete from connection where CONNECTION_FROM_ID="+from+" and CONNECTION_TO_ID="+to+"";
			statement.addBatch(sql);
			sql="delete from connection where CONNECTION_FROM_ID="+to+" and CONNECTION_TO_ID="+from+"";
			statement.addBatch(sql);
			
			int[] result = statement.executeBatch();
			
			if(result.length>=2) {
				connection.commit();
				delStatus=true;
			}
			
		} catch (SQLException e) {
			db.logToFile(e, servletname);
		} catch (Exception e) {
			db.logToFile(e, servletname);
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch(SQLException e) {
					db.logToFile(e, servletname);					
				}
			}
		}
		
		if(delStatus) {
			sess.setAttribute("frdrmmsg", "Friend Removed..");
			response.sendRedirect("getfriends");
		} else {
			sess.setAttribute("frdrmmsg", "Problem in Removing Friend..");
			response.sendRedirect("getfriends");
		}
	}

}
