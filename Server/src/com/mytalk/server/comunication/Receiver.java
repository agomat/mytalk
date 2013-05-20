/**
* Filename: Receiver.java
* Package: com.mytalk.server.comunication
* Author: Nicolò Mazzucato
* Date: 2013-05-14
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-05-14 |    NM     | [+] Inserimento classe, oggetti e costruttore
* 0.2     | 2013-05-14 |	NT	   | [+] Riempimento classi
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/


package com.mytalk.server.comunication;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Iterator;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import com.mytalk.server.exceptions.IpNotFound;

public class Receiver extends WebSocketServer implements Runnable {

	public Receiver(InetSocketAddress address) {
		super(address);
	}
	
	@Override
	public void onOpen(WebSocket conn, ClientHandshake hs) {
		System.out.println("Client connected with IP: "+conn.getRemoteSocketAddress().getAddress().getHostAddress());
		String wsIp=conn.getRemoteSocketAddress().getAddress().getHostAddress();
		String loginAnonymous="{\"auth\":\"{\"username\":null \"password\":null \"ip\":"+wsIp+"}\" \"req\":\"LoginAsAnonymous\" \"info\":null}";//json scritto a mano
		Message openMsg=new Message(wsIp,loginAnonymous);
		BufferIncoming bufferIn=BufferIncoming.getInstance();
		bufferIn.push(openMsg);
	}
	
	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		System.out.println("Client with IP: "+conn.getRemoteSocketAddress().getAddress().getHostAddress()+" disconnected");
		String wsIp=conn.getRemoteSocketAddress().getAddress().getHostAddress();
		String logout="{\"auth\":\"{\"username\":null \"password\":null \"ip\":"+wsIp+"}\" \"req\":\"Logout\" \"info\":null}";//json scritto a mano
		Message closeMsg=new Message(wsIp,logout);
		BufferIncoming bufferIn=BufferIncoming.getInstance();
		bufferIn.push(closeMsg);
	}

	@Override
	public void onError(WebSocket conn, Exception ex) {
		System.out.println("WebsocketServer Error");
		String wsIp=conn.getRemoteSocketAddress().getAddress().getHostAddress();
		String error="{\"auth\":null \"req\":\"ConnectionError\" \"info\":null}";//json scritto a mano
		Message errorMsg=new Message(wsIp,error);
		BufferOutgoing bufferOut=BufferOutgoing.getInstance();
		bufferOut.push(errorMsg);
	}

	@Override
	public void onMessage(WebSocket conn, String msg) {
		System.out.println("New message Received");
		BufferIncoming bufferIn=BufferIncoming.getInstance();
		String wsIp=conn.getRemoteSocketAddress().getAddress().getHostAddress();
		Message newMsg=new Message(wsIp,msg);
		bufferIn.push(newMsg);
	}

	public WebSocket searchConnection(String ip) throws IpNotFound{
		Collection<WebSocket> connections=connections();
		Iterator<WebSocket> iterator = connections.iterator();
		WebSocket wsFound=null;
		while (iterator.hasNext()) {
			WebSocket ws=iterator.next();
			String wsIp=ws.getRemoteSocketAddress().getAddress().getHostAddress();
			if(wsIp.equals(ip)){
				wsFound=ws;
			}
		}
		if(wsFound!=null){
			return wsFound;
		}else{
			throw new IpNotFound();
		}
		
	}

}
