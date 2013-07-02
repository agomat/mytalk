/**
* Filename: Dispatcher.java
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
* Thread che prende le richieste in arrivo dal buffer apposito una alla volta in ordine di arrivo,
* chiama il metodo del Logic Layer che processa la richiesta e mette le eventuali risposte da
* inviare, nel buffer di messaggi in uscita
*/

package com.mytalk.server.communication;

import java.util.List;

import com.google.gson.JsonSyntaxException;
import com.mytalk.server.communication.buffer.BufferIncoming;
import com.mytalk.server.communication.buffer.BufferOutgoing;
import com.mytalk.server.communication.buffer.Message;
import com.mytalk.server.logic.processing.IProcessor;
import com.mytalk.server.logic.processing.Processor;

public class Dispatcher implements Runnable {
	
	/**
	 * Riferimento all'oggetto di tipo IProcessor su cui Dispatcher chiamera' il metodo per 
	 * processare una richiesta
	 * 
	 * @property -processor
	 * @type {IProcessor}
	 */
	private IProcessor processor=new Processor();

	/**
	 * Riferimento al singleton di tipo BufferIncoming inerente alle richieste in entrata nel
	 * server; serve alla classe Dispatcher per eseguire operazioni di pop dei messaggi ricevuti
	 * 
	 * @property -bufferIn
	 * @type {BufferIncoming}
	 */
	private BufferIncoming bufferIn=BufferIncoming.getInstance();
		
	/**
	 * Riferimento al singleton di tipo BufferOutgoing inerente alle richieste in uscita dal 
	 * server; serve alla classe Dispatcher per eseguire operazioni di push delle risposte 
	 * da inviare ai client
	 * 
	 * @property -bufferOut
	 * @type {BufferOutgoing}
	 */
	private BufferOutgoing bufferOut=BufferOutgoing.getInstance();
	
	/**
	 * Override del metodo run dell'interfaccia Runnable implementata; il corpo del metodo 
	 * viene eseguito quando si esegue il metodo start sul thread. Prende i messaggi in 
	 * arrivo dal buffer apposito BufferIncoming, uno alla volta usando il metodo pop, 
	 * li passa al processor invocando il metodo processRequest che ritorna una lista di
	 * zero o più risposte da inviare. Per ognuna di queste risposte le aggiunge al buffer dei
	 * messaggi in uscita BufferOutgoing usando il metodo push
	 * 
	 * @method +run
	 * @return {void}
	 */
	@Override
	public void run() {
		while(true){
			Message request=bufferIn.pop();
			try{
				List<Message> response=processor.processRequest(request);
				for(int i=0;i<response.size();i++){
					Message msg=response.get(i);
					String newIp=msg.getIp();
					newIp="/".concat(newIp);
					msg.setIp(newIp);
					bufferOut.push(msg);
				}
			}catch(JsonSyntaxException e){
				// Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
