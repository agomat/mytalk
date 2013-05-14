/**
* Filename: Sender.java
* Package: com.mytalk.server.comunication
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


package com.mytalk.server.comunication;

public class Sender extends WebSocketServer implements Runnable {

	BufferOutcoming x=BufferOutcoming.getInstance();

	@Override
	public void run() {
		while(true){
			String pack=x.popPacket();
			Collection<WebSocket> con = connections();
			boolean trov=false;
			synchronized (con) {
			for( WebSocket c : con && !trov) {
				if(IP.equals(c.getRemoteSocketAddress().getAddress().getHostAddress())){
					c.send(pack);
					trov=true;
					}
				}
			}
		}
	}
}
