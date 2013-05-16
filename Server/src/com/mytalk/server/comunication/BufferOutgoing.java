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

import java.util.Vector;

public class BufferOutgoing implements Buffer{
	
	private Vector<Thread> consumers;
	
	protected Vector<Message> buffer;
	
	private BufferOutgoing(){
		buffer=new Vector<Message>();
	}
	
	public synchronized void waitConsumers() throws InterruptedException{
		for(int i=0;i<consumers.size();i++){
			consumers.get(i).wait();
		}
	}
	
	public synchronized void notifyConsumers(){
		for(int i=0;i<consumers.size();i++){
			consumers.get(i).notify();
		}
	}
	
	public synchronized void registerConsumer(Thread consumer){
		consumers.add(consumer);
	}
	
	private static final BufferOutgoing instance = new BufferOutgoing();
	
	public static BufferOutgoing getInstance() {
        return instance;
    }
	
	public synchronized void push(Message packet){
		if(buffer.isEmpty()){
			notifyConsumers();
		}
		buffer.add(packet);
	}
	
	public synchronized Message pop(){
		Message msg=buffer.remove(0);
		if(buffer.isEmpty()){
			try {
				waitConsumers();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return msg;
	}
}
