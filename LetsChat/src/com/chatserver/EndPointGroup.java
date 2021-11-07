/**
 * 
 */
package com.chatserver;
/**
	author: Vicky pl
	email: vicky542011@gmail.com
	mobile: 7828789845
 **/
/**
 * @author Anonymox
 *
 */
import javax.websocket.*;
import javax.websocket.server.*;

import com.chat.db.DatabaseConnector;

import java.io.*;
import java.util.*;

@ServerEndpoint(value="/letsgroupchat/{togroupid}")
public class EndPointGroup {

	static HashMap<String, Session> grpsessions = new HashMap<String, Session>();
	
	String servletname = this.getClass().getName();
	DatabaseConnector db = new DatabaseConnector();
	
	@OnOpen
	public void onOpen(Session session,  @PathParam("togroupid") String groupId) {
		grpsessions.put(groupId, session);
		System.out.println("Connected group ID: "+groupId);
	}
	
	@OnMessage
	public void onMessage(String msg, Session session) {
		System.out.println("recived msg:  "+msg); //var toSent=senderName+groupId+textMsg;
		
		String[] msgInfo = msg.split("~"); //name id msg
		System.out.println("1 "+msgInfo[0]);
		System.out.println("2 "+msgInfo[1]);
		System.out.println("3 "+msgInfo[2]);
		
		//System.out.println("msg array length: "+msgInfo.length);
		
		boolean isKeyPresent = grpsessions.containsKey(msgInfo[1]);
		
		if(isKeyPresent) { //checking if the target session is online or not
			Session ts = grpsessions.get(msgInfo[1]);
			try {
				ts.getBasicRemote().sendText(msgInfo[0]+": "+msgInfo[2]);
			} catch(IOException e) {
				e.printStackTrace();
			}
		} else {
			
			Session ts = grpsessions.get(msgInfo[0]);
			try {
				ts.getBasicRemote().sendText("Offline but you can message..");
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("Error in WebSocket:-> "+error);
	}
	
	@OnClose
	public void onClose(Session session) {
		try {
			grpsessions.remove(session);
				session.close();
		} catch (IOException | IllegalStateException e) {
			e.printStackTrace();
		}
		System.out.println(session.getId()+" is Dissconnected..");
	}
	
}
