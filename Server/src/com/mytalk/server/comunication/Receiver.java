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
import com.mytalk.server.exceptions.*;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import com.mytalk.server.exceptions.IpNotFound;

public class Receiver extends WebSocketServer implements Runnable {

	public Receiver(InetSocketAddress address) {
		super(address);
	}
	
	@Override
	public void onOpen(WebSocket ws, ClientHandshake hs) {
		boolean b=addConnection(ws);
	}
	
	@Override
	public void onClose(WebSocket arg0, int arg1, String arg2, boolean arg3) {	
		int i=0;
		boolean trov=false;
		while(i<connections() && !trov){		//ritorna tutte le connessioni, riga 242
			if(arg0.equals(collection[i])){
				removeConnection(i);			//rimuove connessione, riga 507
				trov=true;
			}
			i++;
		}
	}

	@Override
	public void onError(WebSocket arg0, Exception arg1) {
		ex.printStackTrace();
		if( conn != null ) {
			// some errors like port binding failed may not be assignable to a specific websocket
		}
	}

	@Override
	public void onMessage(WebSocket conn, String msg) {
		BufferIncoming bufferIn=BufferIncoming.getInstance();
		Message newMsg=new Message(conn.getRemoteSocketAddress().getAddress().getHostAddress(),msg);
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
