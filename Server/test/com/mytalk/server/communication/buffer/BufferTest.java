/**
* Filename: BufferTest.java
* Package: com.mytalk.server.comunication
* Author: 
* Date:
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	           |           | [+] Inserimento classe, oggetti e costruttore     
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
	
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
