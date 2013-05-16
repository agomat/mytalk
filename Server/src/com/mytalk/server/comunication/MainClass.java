/**
* Filename: MainClass.java
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

import java.net.InetSocketAddress;

public class MainClass {

	public static void main(String[] args) {
		InetSocketAddress address=new InetSocketAddress(8887);//porta a caso temporanea
		Receiver receiver=new Receiver(address);
		Thread receiverThread= new Thread(receiver);
		Sender sender=new Sender();
		Thread senderThread=new Thread(sender);
		sender.registerReceiver(receiver);
		Dispatcher dispatcher=new Dispatcher();
		Thread dispatcherThread=new Thread(dispatcher);
		BufferIncoming.getInstance().registerConsumer(dispatcherThread);
		BufferOutgoing.getInstance().registerConsumer(senderThread);
		receiverThread.start();
		senderThread.start();
		dispatcherThread.start();
	}

}
