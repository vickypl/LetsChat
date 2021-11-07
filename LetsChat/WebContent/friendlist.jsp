<%@page import="java.util.HashSet"%>
<%@page import="com.user.User"%>
<%@page import="java.util.ArrayList"%>
<%@ page errorPage="errorpage.jsp" %>
<%! @SuppressWarnings("unchecked") %>
<jsp:include page="header.jsp"></jsp:include>
<%
	HttpSession sess = request.getSession(false);
	if(sess==null || (sess!=null && sess.getAttribute("user")==null)) {
		response.sendRedirect("index.jsp?msg=Login Required..");
		return;
	}
	
	String msg = null;
	if(request.getParameter("msg")!=null){
		msg=request.getParameter("msg");
	}
	
	if(sess.getAttribute("frdrmmsg")!=null){
		msg=(String)sess.getAttribute("frdrmmsg");
	}
	
	HashSet<User> activeUser = null;
	activeUser=(HashSet<User>)sess.getAttribute("activeSet");
	
	ArrayList<User> friendList = null;
	if(sess.getAttribute("friendList")!=null) {
		friendList=(ArrayList<User>)sess.getAttribute("friendList");
	}
	
%>
<div class="mainbox">
<center><h3 style="font-family: cursive;"><u>Your Friends</u></h3></center>	
	<%if(msg!=null) {%>
	<span style="color: lime; font-size: 20px;"><%=msg %></span>
	<%} %>
	<table id="mytable" style="font-family: cursive;">
		<thead>
			<tr>
				<th></th>
				<th scope="col">Friends</th>
				<th scope="col"></th>
				<th scope="col"></th>
			</tr>
		</thead>
			<tbody>
		<%if(friendList!=null) { 
			for(User user: friendList) {
				String name = user.getFirstname()+" "+user.getLastname();
				String dpurl = user.getUrl();
		%>
			<tr>
				<td style="text-align: right;">
				<% if(!dpurl.equals("null")) { %>
					<div class="dpppic"><img class="dppic" src="profilepics\<%=user.getUrl()%>"></div>
				<%} else {%>
					<div class="dpppic"><img class="dppic" src="image/dp.jpg"></div>
				<%} %></td>
				<%if(activeUser.contains(user)) {%>
				<td style="color: lime; text-align: left;"><%=name %><small><br>Active Now</small></td>
				<%} else { %>
				<td style="text-align: left;"><%=name %><small><br>Lastseen: <%=user.getLastlogin() %></small></td>				
				<%} %>
				<td><a href="chatservice?id=<%=user.getId() %>"><button class="btn btn-success">Chat</button></a></td>
				<td style="text-align: right;"><a href="removefriend?id=<%=user.getId() %>"><button class="btn btn-danger">Remove Friend</button></a></td>
			</tr>
		<%
				}
			} %>
		</tbody>
	</table>
</div>
<jsp:include page="footer.jsp"></jsp:include>