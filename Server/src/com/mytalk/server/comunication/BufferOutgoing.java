/**
* Filename: BufferOutgoing.java
* Package: com.mytalk.server.comunication
* Author: Nicolò Mazzucato
* Date: 2013-05-14
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-05-14 |    NM     | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/


package com.mytalk.server.comunication;

public class BufferOutgoing extends Buffer {
	
	private static final BufferOutgoing instance = new BufferOutgoing();
	
	public static BufferOutgoing getInstance() {
        return instance;
    }
	
	//cambiare il tipo string con nuovo tipo
	public synchronized void pushPacket(String packet){
		if(buffer.isEmpty()){
			notifyConsumers();
		}
		push(packet);
	}
	
	public synchronized String popPacket(){
		String packet=(String)pop();
		if(buffer.isEmpty()){
			waitConsumers();
		}
		return packet;
	}
}
