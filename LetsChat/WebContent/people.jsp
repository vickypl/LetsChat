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
	User cuser = (User)sess.getAttribute("user");
	String cusername = cuser.getFirstname()+" "+cuser.getLastname();
	
	ArrayList<User> userList = null;
	if(sess.getAttribute("userList")!=null) {
		userList = (ArrayList<User>)sess.getAttribute("userList");
	}
	
	String msg = null;
	if(sess.getAttribute("frdmsg")!=null) {
		msg = (String)sess.getAttribute("frdmsg");
	}
%>
<div class="mainbox">
	<%if(msg!=null) {%>
	<span style="color: lime; font-size: 20px;"><%=msg %></span>
	<%} %>
	<h3 style="font-family: cursive;"><u>People You May Know</u></h3>
	<table class="table" id="mytable" style="color: black">
		<thead>
			<tr>
				<th></th>
				<th scope="col">People</th>
				<th scope="col">Option</th>
			</tr>
		</thead>
		<tbody>
		<%if(userList!=null) { 
			for(User user: userList) {
				String name = user.getFirstname()+" "+user.getLastname();
				String dpurl = user.getUrl();
				if(!name.equals(cusername)) {
		%>
			<tr>
				<td style="text-align: right;">
				<% if(!dpurl.equals("null")) { %>
					<div class="dpppic"><img class="dppic" src="profilepics\<%=user.getUrl()%>"></div>
				<%} else {%>
					<div class="dpppic"><img class="dppic" src="image/dp.jpg"></div>
				<%} %></td>
				<td style="text-align: left;"><%=name %></td>
				<td style="text-align: center;"><a href="addfriend?id=<%=user.getId()%>"><button class="btn btn-success">Add Friend</button></a></td>
			</tr>
		<%		}
				}
			} %>
		</tbody>
	</table>
</div>
<jsp:include page="footer.jsp"></jsp:include>