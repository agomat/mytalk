/**
* Filename: BufferTest.java
* Package: com.mytalk.server.communication.buffer
* Author: Armando Caprio
* Date: 2013-05-22
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-05-22 |    AC     | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/


package com.mytalk.server.communication.buffer;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mytalk.server.communication.buffer.BufferIncoming;
import com.mytalk.server.communication.buffer.Message;

import java.util.Vector;

public class BufferTest {
		
	private class PushThread extends Thread{
		private Vector<Message> toPush= new Vector<Message>();
		
		public void addMessage(Message m){
			toPush.add(m);
		}
		
		public void run(){
			for(int i=0; i < toPush.size(); i++){
				BufferIncoming.getInstance().push(toPush.get(i));
			}
		}
	}
	
	private class PullThread extends Thread{
		private int numeroPull;
		
		public PullThread(int n){
			numeroPull=n;
		}
				
		public void run(){
			for(int i=0; i<numeroPull; i++){
				Message m = BufferOutgoing.getInstance().pop();
				assertEquals("Il PullThread ottiene un message (" + m.getIp() + ") nell'ordine sbagliato", m.getIp(), ""+i );
			}
		}
	}
	
	private class DispatcherThread extends Thread{
		private int numeroOperazioni;
		
		public DispatcherThread(int n){
			numeroOperazioni=n;
		}
		
		public void run(){
			for (int i=0; i<numeroOperazioni; i++){
				Message m = BufferIncoming.getInstance().pop();
				BufferOutgoing.getInstance().push(m);
			}
		}
	}
	
	
	@Test
	public void CheckRightOrder() {
		int numeroMessaggi=500000;
		PushThread pushThread = new PushThread();
		PullThread pullThread = new PullThread(numeroMessaggi);
		DispatcherThread dispatcherThread = new DispatcherThread(numeroMessaggi);
		
		for(int i=0; i<numeroMessaggi; i++){
			Message m = new Message(""+i, "");
			pushThread.addMessage(m);
		}
		
		pushThread.start();
		pullThread.start();
		dispatcherThread.start();
	}

}
