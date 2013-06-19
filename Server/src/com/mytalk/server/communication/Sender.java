/**
* Filename: Sender.java
* Package: com.mytalk.server.communication
* Author: Nicolò Mazzucato
* Date: 2013-05-14
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-05-14 |    NM     | [+] Inserimento classe, oggetti e costruttore
* 0.2     | 2013-05-14 |    NT     | [+] Riempimento classi    
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/


package com.mytalk.server.communication;

import java.util.Iterator;

import org.java_websocket.WebSocket;

import com.mytalk.server.communication.buffer.BufferOutgoing;
import com.mytalk.server.communication.buffer.Message;
import com.mytalk.server.exceptions.IpNotFound;

public class Sender implements Runnable {

	private Receiver receiver;
	
	private BufferOutgoing bufferOut=BufferOutgoing.getInstance();

	public void registerReceiver(Receiver rec){
		receiver=rec;
	}
	
	@Override
	public void run() {
		while(true){
			Message pack=bufferOut.pop();
			String ip= pack.getIp();
			try{
				if(ip.equals("/broadcast")){
					sendToAll(pack.getJson());
				}else{
					WebSocket ws=receiver.searchConnection(ip);
					String json=pack.getJson();
					ws.send(json);
					System.out.println("Risposta inviata");
				}
			}catch(IpNotFound exc){
				exc.printStackTrace();
			}
		}
	}
	
	private void sendToAll(String json){
		Iterator<WebSocket> iterator = receiver.connections().iterator();
		while (iterator.hasNext()) {
			WebSocket ws=iterator.next();
			ws.send(json);
		}
	}
}
