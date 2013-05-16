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

public interface Buffer {

	public void waitConsumers() throws InterruptedException;
	
	public void notifyConsumers();
	
	public void registerConsumer(Thread consumer);
	
	public void push(Message packet);
	
	public Message pop();
}
