/**
* Filename: MainClass.java
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

import java.net.InetSocketAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.mytalk.server.data.storage.HibernateUtil;

public class MainClass {

	public static void main(String[] args) {
		new HibernateUtil();
		InetSocketAddress address=new InetSocketAddress(8887);
		Receiver receiver=new Receiver(address);
		Thread receiverThread= new Thread(receiver);
		Sender sender=new Sender();
		Thread senderThread=new Thread(sender);
		Dispatcher dispatcher=new Dispatcher();
		Thread dispatcherThread=new Thread(dispatcher);
		sender.registerReceiver(receiver);
		receiverThread.start();
		senderThread.start();
		dispatcherThread.start();
		Calendar cal = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		System.out.println("["+dateFormat.format(cal.getTime())+"] Server started. Listening on port "+address.getPort());
	}

}
