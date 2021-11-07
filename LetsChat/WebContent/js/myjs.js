/* messseg sending mechenism*/

//for groups


function clearGroupTextBox() {
	document.getElementById("grpmsgbox").value = '';
}

function groupBoxSlideDown() {
	var element=document.getElementById("grpmsgviewbox");
	element.scrollTop = element.scrollHeight ;
}


function sendGroupMessage(grpId) {
	//sendgroupmsg
	var msgh = document.getElementById("grpmsgbox").value;
	var viewbox = document.getElementById("grpmsgviewbox");
	
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.overrideMimeType("text/plain");
	xmlhttp.onreadystatechange=function() {
		//document.getElementById("grpmsgbox").innerHTML="";
		clearGroupTextBox();
		console.log("sent");
	}
	xmlhttp.open("get", "sendgroupmsg?toid="+grpId+"&msg="+msgh, true);
	xmlhttp.send();
	groupBoxSlideDown();
}

function runScriptForGroup(a, e) {
	if (e.keyCode == 13) {
		sendGroupMessage(a);
		document.getElementById("grpmsgbox").focus();
		groupBoxSlideDown();
		clearGroupTextBox();
	}
}

function reciverGroupTexts(grpid) {
	//grouptextreciver
	var viewbox = document.getElementById("grpmsgviewbox");
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange=function() {
		document.getElementById("grpmsgviewbox").innerHTML=xmlhttp.responseText;
		//console.log(xmlhttp.responseText);
	}
	xmlhttp.open("get", "grouptextreciver?grpid="+grpid, true);
	xmlhttp.send();
	groupBoxSlideDown();
}
//end for groups


function slideDown() {
	var element=document.getElementById("viewbox");
	   element.scrollTop = element.scrollHeight ;
}

function clearTextBox() {
	document.getElementById("msgbox").value = '';
}

function messagesReciver(a) {
	var viewbox = document.getElementById("viewbox");
	var xmlhttp = new XMLHttpRequest();
//	xmlhttp.overrideMimeType("text/plain");
	xmlhttp.onreadystatechange=function() {
		document.getElementById("viewbox").innerHTML=xmlhttp.responseText;
		//console.log(xmlhttp.responseText);
	}
	xmlhttp.open("get", "testreciver?toid="+a, true);
	xmlhttp.send();
	slideDown();
}

function addtodb(a) {
	
	var msgh = document.getElementById("msgbox").value;
	var viewbox = document.getElementById("viewbox");

	/*viewbox.innerHTML+="<div class='rightmsg'>"+msgh+"<br><small>Few Moments Ago</small></div>";*/

	var xmlhttp = new XMLHttpRequest();
	xmlhttp.overrideMimeType("text/plain");
	xmlhttp.onreadystatechange=function() {
		document.getElementById("msgbox").innerHTML="";
		console.log("sent");
	}
	xmlhttp.open("get", "sendusrmsg?toid="+a+"&msg="+msgh, true);
	xmlhttp.send();
	slideDown();
}

/* messseg sending mechenism*/

function removeFromGroup(a, b) {
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange=function() {
		document.getElementById("removemsg").innerHTML=xmlhttp.responseText;
	}
	xmlhttp.open("get", "removefromgroup?memid="+a+"&grpid="+b, true);
	xmlhttp.send();
}

function displayMembers(a) {

	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange=function() {
		document.getElementById("showmembers").innerHTML=xmlhttp.responseText;
	}
	xmlhttp.open("get", "showgrpmembers?data="+a, true);
	xmlhttp.send();
}

function addToGroup() {
	var grpId = document.getElementById("grpaddid").value;
	var frndId = document.getElementById("frndid").value;
	var result = window.confirm('Confirm Adding?');
	if (result === true) {
		var xmlhttp = new XMLHttpRequest();
		xmlhttp.onreadystatechange=function() {
			document.getElementById("addmsghere").innerHTML=xmlhttp.responseText;
		}
		xmlhttp.open("get", "addtogrp?grpid="+grpId+"&frndid="+frndId, true);
		xmlhttp.send();
	}
}

function deleteThisGroup(id) {
	var result = window.confirm('Are you sure?');
	if (result === true) {
		var xmlhttp = new XMLHttpRequest();
		xmlhttp.onreadystatechange=function() {
			document.getElementById("showmsgs").innerHTML=xmlhttp.responseText;
		}
		xmlhttp.open("get", "deletegroup?gid="+id, true);
		xmlhttp.send();
	}
}

function disableElementForSpecifiedTime() {
	 var button = document.getElementById('gbtn');
	 button.disabled=true;
	    
    setTimeout(function(){  
    	button.disabled = false;
    }, 30000);
}

function writeNewGroup() {
	var gname = document.getElementById("gnm").value;
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange=function() {
		document.getElementById("gmsg").innerHTML=xmlhttp.responseText;
	}
	xmlhttp.open("get", "creategroup?gname="+gname, true);
	xmlhttp.send();
	reloadChatpage();
}

function selectedGroup(id) {
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange=function() {
		document.getElementById("showmsgs").innerHTML=xmlhttp.responseText;
	}
	xmlhttp.open("get", "showgroupmsg?id="+id, true);
	xmlhttp.send();
}

function selectedGuy(id) {
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange=function() {
		document.getElementById("showmsgs").innerHTML=xmlhttp.responseText;
		document.getElementById("showmsgs").innerHTML+="<div class='card-footer'><div class='input-group'><div class='input-group-append'><span class='input-group-text attach_btn'><i class='fas fa-paperclip'></i></span></div><input id='mainmsg' name='' onkeypress='return runScript("+id+", event)' class='form-control type_msg' placeholder='Type your message...' maxlength='299'></input><div class='input-group-append'><span class='input-group-text send_btn'><i onclick='sendMessage("+id+")' class='fas fa-location-arrow'></i></span></div></div></div>";
	}
	xmlhttp.open("get", "showmsg?id="+id, true);
	xmlhttp.send();
}

//code below this is for connecting websocket

/*var webSocket;
var message = document.getElementById("msg");

//to create connection of socket
function openSocket() {
	if(webSocket!==undefined && webSocket.readyState!==WebSocket.CLOSED) {
		message.innerHTML+="<br>"+"Socket Already Open.";
	}
	
	webSocket = new WebSocket("ws://localhost:8088/LetsChat/letschat");
	
	webSocket.onopen = function (event) {
		if(event.data===undefined)
			return;
	};
	
	webSocket.onmessage = function(event) {
		message.innerHTML="<span style='color: green;''>" +event.data+"<span><br>";
	};
	
	webSocket.onerror = function(event) {
		onError(event);
	};
	
	webSocket.onclose = function(event) {
		message.innerHTML="<span style='color: red;'>You are DisConnected<span><br>";
	}
}

//to send msg
function sendMessage(dataType, dataValue) {
	webSocket.send(dataValue);
}

function closeSocket() {
	if(webSocket.readyState===WebSocket.OPEN) {
		webSocket.close();
	} else {
		message.innerHTML= "<span style='color: red;'>Connection Already Closed.<span><br>";
	}
}*/


/*function runScript(e) {
    if (e.keyCode == 13) {
        sendFun();
        $("#textmsg").focus();
    }
}*/

//when send button is clicked
function sendFun() {
		var textmsg = $('#textmsg').val();
		sendMessage("Text", textmsg);
		refreshFieldSetBox();
}

