<%@page import="java.util.HashSet"%>
<%@page import="com.user.User"%>
<%@ page errorPage="errorpage.jsp" %>
<%! @SuppressWarnings("unchecked") %>
<jsp:include page="header.jsp"></jsp:include>
<%
	HttpSession sess = request.getSession(false);
	if(sess==null || (sess!=null && sess.getAttribute("user")==null)) {
		response.sendRedirect("index.jsp?msg=Login Required..");
		return;
	}

	User user = (User)session.getAttribute("user");
	String name = user.getFirstname()+" "+user.getLastname();
	
	String msg = null;
	if(request.getAttribute("msg")!=null) {
		msg = (String)request.getAttribute("msg");
	}
	
	if(request.getParameter("msg")!=null) {
		msg=request.getParameter("msg");
	}
	
%>
<div class="mainbox">
	<%if(msg!=null) {%>
	<span style="color: lime; font-size: 20px;"><%=msg %></span>
	<%} %>
	<div class="mainprofilepic">
	<%if(user.getUrl().equals("null")) {%>
		<img src="image/dp.jpg" class="profileImage" alt="Profile Picture">
	<%} else { %>
		<img src="profilepics/<%=user.getUrl() %>" class="profileImage" alt="Profile Picture">
	<%} %>
	</div>
	<div class="maindetails">
		<table class="table">
			<form action="updatedp" method="POST" enctype="multipart/form-data">
				<tr>
					<td><input class="form-control" type="file" name="file" placeholder="Select Profile Pic" accept=".jpg" required /></td>
					<td><button type="submit" class="btn btn-info">Update Profile Picture</button></td>
				</tr>
			</form>
		</table>
		<div id="msg" style="font-size: 40px;"></div>
		<table class="table" style="font-family: cursive;">
			<tbody>
				<tr>
					<td>Name:</td>
					<td><%=name %></td>
				</tr>
				<tr>
					<td>Username:</td>
					<td><%=user.getUsername() %></td>
				</tr>
				<tr>
					<td>Email:</td>
					<td><%=user.getEmail() %></td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
<jsp:include page="footer.jsp"></jsp:include>