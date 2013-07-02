/**
* Filename: Sender.java
* Package: com.mytalk.server.communication
* Author: Nicolo' Mazzucato
* Date: 2013-05-14
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.3     | 2013-06-18 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.2     | 2013-05-14 |    NT     | [+] Riempimento classi    
* 0.1	  |	2013-05-14 |    NM     | [+] Inserimento classe, oggetti e costruttore

*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Thread che prende i messaggi in uscita dal buffer apposito, una alla volta in ordine FIFO, e
* li invia al client specificato nel messaggio oppure in broadcast a tutti i client connessi
*/


package com.mytalk.server.communication;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;

import org.java_websocket.WebSocket;

import com.mytalk.server.communication.buffer.BufferOutgoing;
import com.mytalk.server.communication.buffer.Message;
import com.mytalk.server.exceptions.IpNotFound;

public class Sender implements Runnable {
	
	/**
	 * Riferimento a un oggetto di tipo Receiver, serve al Sender per cercare nella collezione
	 * di connessioni aperte, contenuta in questo oggetto, l'oggetto WebSocket corrispondente 
	 * al client a cui inviare un messaggio
	 * 
	 * @property -receiver
	 * @type {Receiver}
	 */
	private Receiver receiver;
	
	/**
	 * Riferimento al singleton di tipo BufferOutgoing inerente alle richieste in uscita dal 
	 * server; serve alla classe Sender per inviare i messaggi contenuti in esso ai corrispettivi 
	 * client
	 * 
	 * @property -bufferOut
	 * @type {BufferOutgoing}
	 */
	private BufferOutgoing bufferOut=BufferOutgoing.getInstance();

	/**
	 * Metodo che deve invocare la classe che istanzia un oggetto di tipo Receiver che ne 
	 * memorizza il riferimento nell'oggetto Sender
	 * 
	 * @method +registerReceiver
	 * @param {Receiver} rec e' l'oggetto che identifica un receiver da registrare
	 * @return {void}
	 */
	public void registerReceiver(Receiver rec){
		receiver=rec;
	}
	
	/**
	 * Override del metodo run dell'interfaccia Runnable implementata; il corpo del metodo viene 
	 * eseguito quando si esegue il metodo start sul thread. Prende i messaggi in uscita dal buffer 
	 * apposito BufferOutgoing, uno alla volta usando il metodo pop, controlla a che ip mandare 
	 * il messaggio e in caso l'ip sia uguale a "/broadcast" invoca il metodo sendToAll su quel 
	 * messaggio; altrimenti usa il metodo searchConnection reso disponibile dall'oggetto Receiver 
	 * per cercare l'oggetto WebSocket con cui invocare il metodo send passando come parametro 
	 * il messaggio da inviare
	 * 
	 * @method +run
	 * @return {void}
	 */
	@Override
	public void run() {
		while(true){
			Message pack=bufferOut.pop();
			String ip= pack.getIp();
			Calendar cal = Calendar.getInstance();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			try{
				if(ip.equals("/broadcast")){
					sendToAll(pack.getJson());
					System.out.println("["+dateFormat.format(cal.getTime())+"] Messaggio broadcast");
				}else{
					WebSocket ws=receiver.searchConnection(ip);
					String json=pack.getJson();
					ws.send(json);
					System.out.println("["+dateFormat.format(cal.getTime())+"] Risposta inviata");
				}
			}catch(IpNotFound exc){
				exc.printStackTrace();
			}
		}
	}
	
	/**
	 * Metodo che invia in broadcast a tutti i client connessi la stringa JSON passata; per 
	 * cercare i client connessi usa il riferimento all'oggetto Receiver
	 * 
	 * @method +sendToAll
	 * @param {String} json e' l'oggetto che identifica il messaggio da mandare a tutti i client
	 * @return {void}
	 */
	private void sendToAll(String json){
		Iterator<WebSocket> iterator = receiver.connections().iterator();
		while (iterator.hasNext()) {
			WebSocket ws=iterator.next();
			ws.send(json);
		}
	}
}
