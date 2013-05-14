/**
* Filename: Buffer.java
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

import java.util.Vector;

public class Buffer {
	
	private Buffer(){
		bufferIncoming=new Vector<String>();
		bufferOutgoing=new Vector<String>();
	}

	private Vector<String> bufferIncoming;
	private Vector<String> bufferOutgoing;
	
	private static final Buffer instance = new Buffer();
	
	public static Buffer getInstance() {
        return instance;
    }
	
	public void insertIncoming(String packet){
		synchronized(bufferIncoming){
			bufferIncoming.add(packet);
		}
	}
	
	public String getIncoming(){
		synchronized(bufferIncoming){
			int lastIndex=bufferIncoming.size();
			return bufferIncoming.remove(lastIndex);
		}
	}
	
	public void insertOutgoing(String packet){
		synchronized(bufferOutgoing){
			bufferOutgoing.add(packet);
		}
	}
	
	public String getOutgoing(){
		synchronized(bufferOutgoing){
			int lastIndex=bufferOutgoing.size();
			return bufferOutgoing.remove(lastIndex);
		}
	}
}
