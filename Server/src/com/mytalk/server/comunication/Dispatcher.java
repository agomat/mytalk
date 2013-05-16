/**
* Filename: Dispatcher.java
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

import com.mytalk.server.logic.processing.*;

public class Dispatcher implements Runnable {
	
	Processor processor=new Processor();
	BufferIncoming bufferIn=BufferIncoming.getInstance();
	BufferOutgoing bufferOut=BufferOutgoing.getInstance();
	
	@Override
	public void run() {
		while(true){
			Message request=bufferIn.pop();
			Message response=processor.processRequest(request);
			bufferOut.push(response);
		}
	}

}
