/**
* Filename: MainClass.java
* Package: com.mytalk.server.communication
* Author: Nicolo' Mazzucato
* Date: 2013-05-14
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.2     | 2013-06-18 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.1	  |	2013-05-14 |    NM     | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Classe che ha il compito di istanziare e avviare l'esecuzione dei thread di tipo Receiver, 
* Dispatcher e Sender. Inoltre avvia il mapping di Hibernate e specifica su che porta e' in
* ascolto il server
*/


package com.mytalk.server.communication;

import java.net.InetSocketAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.mytalk.server.data.storage.HibernateUtil;

public class MainClass {

	/**
	 * Metodo che avvia il mapping di Hibernate, istanzia e avvia i thread di tipo Receiver, 
	 * Dispatcher e Sender e invoca il metodo registerReceiver sull'oggetto Sender passando 
	 * il riferimento all'oggetto Receiver
	 * 
	 * @method +main
	 * @param {String[]} args e' l'oggetto che identifica i parametri di ingresso di tipo String
	 * @return {void}
	 */
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
