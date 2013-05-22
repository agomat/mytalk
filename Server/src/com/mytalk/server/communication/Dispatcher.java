/**
* Filename: Dispatcher.java
* Package: com.mytalk.server.communication
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


package com.mytalk.server.communication;

import java.util.List;

import com.mytalk.server.communication.buffer.BufferIncoming;
import com.mytalk.server.communication.buffer.BufferOutgoing;
import com.mytalk.server.communication.buffer.Message;
import com.mytalk.server.logic.processing.IProcessor;
import com.mytalk.server.logic.processing.Processor;

public class Dispatcher implements Runnable {
	
	private IProcessor processor=new Processor();
	private BufferIncoming bufferIn=BufferIncoming.getInstance();
	private BufferOutgoing bufferOut=BufferOutgoing.getInstance();
	
	@Override
	public void run() {
		while(true){
			Message request=bufferIn.pop();
			List<Message> response=processor.processRequest(request);
			for(int i=0;i<response.size();i++){
				bufferOut.push(response.get(i));
			}
		}
	}

}
