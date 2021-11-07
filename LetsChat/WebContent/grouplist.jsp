<%@page import="com.user.User"%>
<%@page import="com.user.GroupMessages"%>
<%@page import="com.user.Groups"%>
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
	User user = (User)sess.getAttribute("user");
	
	String msg = null;
	if(request.getParameter("msg")!=null){
		msg=request.getParameter("msg");
	}
	
	ArrayList<Groups> groupList = null;
	if(request.getAttribute("groupList")!=null) {
		groupList=(ArrayList<Groups>)request.getAttribute("groupList");
	}
	
	ArrayList<Groups> otherGroupList = null;
	if(request.getAttribute("otherGroupList")!=null) {
		otherGroupList=(ArrayList<Groups>)request.getAttribute("otherGroupList");
	}
	
%>
<div class="mainbox">
<center><h3 style="font-family: cursive;"><u>Your Groups</u></h3></center>	
	<%if(msg!=null) {%>
	<span style="color: lime; font-size: 20px;"><%=msg %></span>
	<%} %>
	<table id="mytable" style="width: 80%; font-family: cursive;">
		<thead>
			<tr>
				<th></th>
				<th scope="col">Groups</th>
				<th scope="col"></th>
				<th scope="col"></th>
			</tr>
		</thead>
		<tbody>
		<%if(groupList!=null){ %>
		<%for(Groups grp : groupList) {%>
			<tr>
				<td><img src="image/ico.png" class="grpimg"></td>
				<td><%=grp.getGroupName() %></td>
				<td><a href="showgroupmsg?id=<%=grp.getId() %>"><button class="btn btn-success">Chat</button></a></td>
				<td><a href="deletegroup?gid=<%=grp.getId() %>"><button class="btn btn-danger">Delete Group</button></a></td>
			</tr>
		<%} %>
		<%} %>
		<tr>
			<td id="removemsg"></td>
		</tr>
		<%if(otherGroupList!=null) { %>
			<%for(Groups gmsg : otherGroupList) {
				if(!gmsg.getCreatorId().equals(user.getId())) {
			%>
			<tr>
				<td><img src="image/ico.png" class="grpimg"></td>
				<td><%=gmsg.getGroupName() %></td>
				<td><a href="showgroupmsg?id=<%=gmsg.getId() %>"><button class="btn btn-success">Chat</button></a></td>
				<td><button onclick=" removeFromGroup(<%=user.getId() %>, <%=gmsg.getId() %>)" class="btn btn-info">Leave Group</button></a></td>
			</tr>
			<%}
			} %>
		<%} %>
		</tbody>
	</table>
<jsp:include page="footer.jsp"></jsp:include>