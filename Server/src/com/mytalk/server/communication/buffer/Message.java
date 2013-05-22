/**
* Filename: Message.java
* Package: com.mytalk.server.communication.buffer
* Author: Nicolò Mazzucato
* Date: 2013-15-05
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-15-05 |    NM     | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

package com.mytalk.server.communication.buffer;

public class Message {

	private String json;
	
	private String ip;
	
	public Message(String newIp, String newJson){
		ip=newIp;
		json=newJson;
	}
	
	public String getIp(){
		return ip;
	}
	
	public void setIp(String newIp){
		ip=newIp;
	}
	
	public String getJson(){
		return json;
	}
	
	public void setJson(String newJson){
		json=newJson;
	}
}
