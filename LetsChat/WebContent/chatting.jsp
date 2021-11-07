<!DOCTYPE html>
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
	
	User friend = null;
	if(request.getAttribute("frnd")!=null) {
		friend = (User)request.getAttribute("frnd");
	}
	
	ArrayList<Groups> groupList = null;
	if(sess.getAttribute("groupList")!=null) {	
		groupList=(ArrayList<Groups>)sess.getAttribute("groupList");	
	}
	
	ArrayList<Messages> msgs = null;
	if(request.getAttribute("msgs")!=null) {
		msgs=(ArrayList<Messages>)request.getAttribute("msgs");
	}
	
%>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="icon" href="image/ico.png" type="image/icon type">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="css/chatstyle.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <title>Let's Chat</title>
</head>
<body>
    <div style="text-align: center; margin: 1% auto;">
        <a href="getfriends"><button class="btn btn-black">back</button></a>
    </div>
    <div class="groups">
        <table style="margin: 0% auto;">
            <tr>
                <td>
                    <input type="text" id="gnm" name="gname" value="" placeholder="New Group Name" class="form-control"
                        required="required" />
                </td>
                <td>
                    <button onclick="writeNewGroup(), disableElementForSpecifiedTime()" id="gbtn"
                        class="btn btn-success">Create New Group</button>
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
                <%if(friend.getUrl().equals("null")) { %>
                    	<img src="image/dp.jpg" class="chatimage" alt="">
                <%} else { %>
                    	<img src="profilepics\<%=friend.getUrl() %>" class="chatimage">
                <%} %>
                </div>
                <div class="clientname">
                    <h3><%=friend.getFirstname()+" "+friend.getLastname() %></h3>
                    <small><%=msgs.size() %> Messages</small>
                </div>
                <%if(groupList.size()>0) { %>
                <div class="grpbtn">
                    <table style="width: 100%;">
                        <tr>
                            <td>
                            	<input type='hidden' id='frndid' value="<%=friend.getId() %>"/>
                                <select class="form-control"  id="grpaddid">
                                <% for(Groups grp : groupList) { %>
                                    <option value="<%=grp.getId() %>"><%=grp.getGroupName() %></option>
                                  <%} %>
                                </select>
                            </td>
                            <td style="text-align: left;">
                                <button onclick="addToGroup()" class="btn btn-info">Add to Group</button>
                            </td>
                        </tr>
                        <tr>
                        <td><div id="addmsghere"></div></td>
                        </tr>
                    </table>
                </div>
                <%} else {%>
                <div></div><!--  in case there is no group created by this user -->
                <%} %>
            </div>
            <div class="chatbox">
                <div class="msgviewbox" id="viewbox">
                <%if(msgs.size()>0) {%>
                <%for(Messages msg : msgs) {%>
                    <%if(msg.getSenderName().equals(friend.getUsername())) {%>
                    <div class="leftmsg">
                        <%=msg.getMessage() %>
                        <br><small><%=msg.getTime() %></small>
                    </div>
                    <%} else { %>
                    <div class="rightmsg">
                       <%=msg.getMessage() %>
                        <br><small><%=msg.getTime() %></small>
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
                            <td><%-- onkeypress="return runScript(<%=friend.getId() %>, event),  slideDown()"  --%>
                                <input id="msgbox" onkeypress="return runScript(<%=friend.getId() %>, event), slideDown()" type="text" class="form-control" style="font-family: cursive;" maxlength="299" />
                            </td>
                            <td>
                                <button onclick="sendFun(), clearTextBox(), slideDown()" class="btn btn-block"><i class="fa fa-send"></i></button>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <script src="js/myjs.js"></script>
    <script type="text/javascript">
	var viewbox =  document.getElementById("viewbox");
    
    //socket Connection
    var senderId = "<%=user.getId()%>~";
	var toclientid= "<%=friend.getId()%>~";
	var id = "<%=user.getId()%>";
	
	var systime = new Date().toLocaleDateString();
	var webSocket;

	function connectionVerifier() {
			webSocket = new WebSocket("ws://localhost:8088/LetsChat/letschat/"+id);
	}
	
	
	function openSocket() {
		if(webSocket!==undefined && webSocket.readyState!==WebSocket.CLOSED) {
			webSocket = new WebSocket("ws://localhost:8088/LetsChat/letschat/"+id);
			viewbox.innerHtml+="<br/>"+"Web Socket is already open.";
			return;
		}
		
		webSocket = new WebSocket("ws://localhost:8088/LetsChat/letschat/"+id);
		
		webSocket.onopen = function(event) {
			if(event.data===undefined)
				return;
		};
		
		webSocket.onmessage = function(event) {
			viewbox.innerHTML+="<div class='leftmsg'>"+event.data+"<br><small>"+systime+"</small></div>";
			slideDown();
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
		var textMsg = document.getElementById("msgbox").value;
		if (textMsg.length == 0) {
			return;
		}
		var toSent=senderId+toclientid+textMsg;
		viewbox.innerHTML+="<div class='rightmsg'>"+textMsg+"<br><small>Few Moments Ago</small></div>";
		sendMessage("Text", toSent);
		addtodb(<%=friend.getId() %>);
		slideDown();
	}
    
    function runScript(a, e) {
        if (e.keyCode == 13) {
        	addtodb(a);
        	sendFun();
        	document.getElementById("msgbox").focus();
        	slideDown();
        	clearTextBox();
        }
    }
	
	$(document).ready(function() {
    	slideDown();
    	openSocket();
    });
    </script>
</body>
</html>