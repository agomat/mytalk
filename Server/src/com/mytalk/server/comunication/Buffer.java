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

public abstract class Buffer {
	
	private Vector<Runnable> consumers;
	
	protected Vector<Object> buffer;
	
	public Buffer(){
		buffer=new Vector<Object>();
	}
	
	public synchronized void waitConsumers(){
		for(int i=0;i<consumers.size();i++){
			consumers.get(i).wait();
		}
	}
	
	public synchronized void notifyConsumers(){
		for(int i=0;i<consumers.size();i++){
			consumers.get(i).notify();
		}
	}
	
	public synchronized void registerConsumer(Runnable thread){
		consumers.add(thread);
	}
	
	public synchronized void push(Object obj){
		buffer.add(obj);
	}
	
	public synchronized Object pop(){
		int lastIndex=buffer.size();
		Object obj=buffer.remove(lastIndex);
		return obj;
	}
}
