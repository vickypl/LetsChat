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

@ServerEndpoint(value="/letschat/{toclientid}")
public class EndPoint {

	static HashMap<String, Session> sessions = new HashMap<String, Session>();
	
	String servletname = this.getClass().getName();
	DatabaseConnector db = new DatabaseConnector();
	
	@OnOpen
	public void onOpen(Session session,  @PathParam("toclientid") String clientId) {
		sessions.put(clientId, session);
		
		System.out.println("Client ID :::: "+clientId);
		String msg = "You Are Connected..";
		
		try {
			session.getBasicRemote().sendText(msg);
			
			System.out.println(clientId+" is Connected");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@OnMessage
	public void onMessage(String msg, Session session) {
		System.out.println("recived msg:  "+msg);
		
		String[] msgInfo = msg.split("~"); //name id msg
		
		System.out.println("msg array length: "+msgInfo.length);
		
		boolean isKeyPresent = sessions.containsKey(msgInfo[1]);
		
		if(isKeyPresent) { //checking if the target session is online or not
			Session ts = sessions.get(msgInfo[1]);
			try {
				ts.getBasicRemote().sendText(/*msgInfo[0]+*/" "+msgInfo[2]);
			} catch(IOException e) {
				e.printStackTrace();
			}
		} else {
			
			Session ts = sessions.get(msgInfo[0]);
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
				sessions.remove(session);
				session.close();
		} catch (IOException | IllegalStateException e) {
			e.printStackTrace();
		}
		System.out.println(session.getId()+" is Dissconnected..");
	}
	
}
