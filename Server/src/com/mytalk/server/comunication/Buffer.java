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

	Vector<String> bufferIncoming;
	Vector<String> bufferOutgoing;
	
	public void insertIncoming(String packet){
		bufferIncoming.add(packet);
	}
	
	public String getIncoming(){
		int lastIndex=bufferIncoming.size();
		return bufferIncoming.remove(lastIndex);
	}
	
	public void insertOutgoing(String packet){
		bufferOutgoing.add(packet);
	}
	
	public String getOutgoing(){
		int lastIndex=bufferOutgoing.size();
		return bufferOutgoing.remove(lastIndex);
	}
}
