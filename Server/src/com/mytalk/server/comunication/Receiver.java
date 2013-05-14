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

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class Receiver extends WebSocketServer implements Runnable {

	public void run(){
			
	}
	
	@Override
	public void onOpen(WebSocket arg0, ClientHandshake arg1) {
		addConnection(arg0); //by WebSocketServer, riga 512
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
	public void onMessage(WebSocket arg0, String arg1) {
		// manda l'IP e JSON nel buffer
		BufferIncoming x=BufferIncoming.getInstance();	//provvisorio
		x.pushPacket(arg0.getRemoteSocketAddress().getAddress().getHostAddress(), arg1); //provvisorio
	}

	

}
