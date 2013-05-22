/**
* Filename: BufferOutgoing.java
* Package: com.mytalk.server.comunication.buffer
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


package com.mytalk.server.communication.buffer;

import java.util.Vector;

public class BufferOutgoing implements Buffer{
	
	private Vector<Message> buffer;
	
	private BufferOutgoing(){
		buffer=new Vector<Message>();
	}
	
	private static final BufferOutgoing instance = new BufferOutgoing();
	
	public static BufferOutgoing getInstance() {
        return instance;
    }
	
	public synchronized void push(Message packet){
		if(buffer.isEmpty()){
			notify();
		}
		buffer.add(packet);
	}
	
	public synchronized Message pop(){
		if(buffer.isEmpty()){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Message msg=buffer.firstElement();
		buffer.remove(msg);
		return msg;
	}
}
