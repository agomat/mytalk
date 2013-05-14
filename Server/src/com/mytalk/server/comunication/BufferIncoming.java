/**
* Filename: BufferIncoming.java
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

public class BufferIncoming extends Buffer {
	
	private static final BufferIncoming instance = new BufferIncoming();
	
	public static BufferIncoming getInstance() {
        return instance;
    }
	
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
