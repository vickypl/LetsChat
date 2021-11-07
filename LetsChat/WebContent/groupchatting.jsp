<!DOCTYPE html>
<%@page import="com.user.GroupMessages"%>
<%@page import="com.user.Messages"%>
<%@page import="com.user.Groups"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.user.User"%>
<%@ page errorPage="errorpage.jsp" %>
<%! @SuppressWarnings("unchecked") %>
<html lang="en">
<%
	//session
	HttpSession sess = request.getSession(false);
	if(sess==null || (sess!=null && sess.getAttribute("user")==null)) {
		response.sendRedirect("index.jsp?msg=Login Required..");
		return;
	}
	User user = (User)sess.getAttribute("user");
	
	Groups group = null;
	if(request.getAttribute("group")!=null) {
		group = (Groups)request.getAttribute("group");
	}
	
	ArrayList<GroupMessages> groupMessages = null;
	if(request.getAttribute("groupMsgList")!=null) {
		groupMessages=(ArrayList<GroupMessages>)request.getAttribute("groupMsgList");
	}
	
%>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="icon" href="image/ico.png" type="image/icon type">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="css/chatstyle.css">
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
  
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <title>Let's Chat</title>
</head>
<body>
    <div style="text-align: center; margin: 1% auto;">
        <a href="getgroupchats"><button class="btn btn-secondary">back</button></a>
    </div>
    <div class="groups">
        <table style="margin: 0% auto;">
            <tr>
                <td>
					  <button type="button" onclick="displayMembers(<%=group.getId() %>)" class="btn btn-primary" data-toggle="modal" data-target="#myModal">
						View Group Members
					  </button>
                </td>
            </tr>
            <tr>
            <td>
                <div id="gmsg"></div>
            </td>
            </tr>
        </table>
    </div>
    <div class="container">
        <div class="chatwindow">
            <div class="chat_header">
                <div class="chatimg">
                    	<img src="image/ico.png" class="chatimage" alt="">
                </div>
                <div class="clientname">
                    <h3><%=group.getGroupName() %></h3>
                    <small><%=groupMessages.size() %> Messages</small>
                </div>
                <%if(groupMessages.size()>0) { %>
                <div class="grpbtn">
                <!--  -->
                </div>
                <%} else {%>
                <div></div><!--  in case there is no group created by this user -->
                <%} %>
            </div>
            <div class="chatbox">
                <div class="msgviewbox" id="grpmsgviewbox">
                <%if(groupMessages.size()>0) {%>
                <%for(GroupMessages msg : groupMessages) {%>
                    <%if(!msg.getSenderName().equals(user.getUsername())) {%>
                    <div class="leftmsg">
                   		<small><mark><%=msg.getSenderName() %></mark></small>
                        <%=msg.getMessage() %>
                        <br><small><%=msg.getSendOn() %></small>
                    </div>
                    <%} else { %>
                    <div class="rightmsg">
                       <%=msg.getMessage() %>
                        <br><small><%=msg.getSendOn() %></small>
                    </div>
                    <%} %>
                    <%} %>
                    <%}else { %>
                    <div>No Messages Available..</div>
                    <%} %>
                </div>
                <div class="msgtextbox">
                    <table style="width: 100%;">
                        <tr>
                            <td>
                                <input id="grpmsgbox" onkeypress="return  runScriptForGroup(<%=group.getId() %>, event), groupBoxSlideDown()" type="text" class="form-control" style="font-family: cursive;"
                                    maxlength="299" />
                            </td>
                            <td>
                                <button onclick="sendFun()" class="btn btn-block"><i class="fa fa-send"></i></button>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
<div class="container">
  <div class="modal fade" id="myModal">
    <div class="modal-dialog modal-dialog-scrollable">
      <div class="modal-content text-dark">
        <div class="modal-header">
          <h4 class="modal-title">Group Members</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        <div class="modal-body" id="showmembers">
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
        </div>
	</div>
    </div>
  </div>
  </div>
<script src="js/myjs.js"></script>
    <script type="text/javascript">
	var viewbox =  document.getElementById("grpmsgviewbox");
    
    //socket Connection
    var senderName = "<%=user.getUsername()%>~";
	var groupId= "<%=group.getId()%>~";
	var id = "<%=group.getId()%>";
	
	var systime = new Date().toLocaleDateString();
	var webSocket;

	function connectionVerifier() {
			webSocket = new WebSocket("ws://localhost:8088/LetsChat/letsgroupchat/"+id);
	}
	
	
	function openSocket() {
		if(webSocket!==undefined && webSocket.readyState!==WebSocket.CLOSED) {
			webSocket = new WebSocket("ws://localhost:8088/LetsChat/letsgroupchat/"+id);
			viewbox.innerHtml+="<br/>"+"Web Socket is already open.";
			return;
		}
		
		webSocket = new WebSocket("ws://localhost:8088/LetsChat/letsgroupchat/"+id);
		
		webSocket.onopen = function(event) {
			if(event.data===undefined)
				return;
		};
		
		webSocket.onmessage = function(event) {
			viewbox.innerHTML+="<div class='leftmsg'>"+event.data+"<br><small>"+systime+"</small></div>";
			groupBoxSlideDown();
		}; 
		
		webSocket.onerror = function(event) {
            onError(event);
         };
		
		webSocket.onclose = function(event){
			viewbox.innerHTML+= "<div class='rightmsg'>"+event.data+"<br><small>Online</small></div>";
	     };
	}
	
	//to send msg
	function sendMessage(dataType, dataValue) {
		webSocket.send(dataValue);
		//document.getElementById("msgbox").value="";
	}
	
	//to close the connection
	function closeSocket(){
		if (webSocket.readyState === WebSocket.OPEN) {
			    webSocket.close();
		} else {
			viewbox.innerHTML= "<span style='color: red;'>Connection Already Closed.<span><br>";
		}
	}
	
	function sendFun() {
		var textMsg = document.getElementById("grpmsgbox").value;
		if (textMsg.length == 0) {
			return;
		}
		var toSent=senderName+groupId+textMsg;
		viewbox.innerHTML+="<div class='rightmsg'>"+textMsg+"<br><small>Few Moments Ago</small></div>";
		sendMessage("Text", toSent);
		sendGroupMessage(<%=group.getId() %>);
		groupBoxSlideDown();
	}
    
	function runScriptForGroup(a, e) {
		if (e.keyCode == 13) {
			sendFun();
			sendGroupMessage(<%=group.getId() %>);
			document.getElementById("grpmsgbox").focus();
			groupBoxSlideDown();
			clearGroupTextBox();
		}
	}
	
	$(document).ready(function() {
		groupBoxSlideDown();
    	openSocket();
    });
    </script>
</body>
</html>