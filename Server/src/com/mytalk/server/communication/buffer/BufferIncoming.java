/**
* Filename: BufferIncoming.java
* Package: com.mytalk.server.comunication.buffer
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
* Classe che memorizza oggetti Message relativi alle richieste in arrivo dai client in modo da
* renderli disponibili per il processing. La classe implementa il pattern singleton in modo che
* esista solo un oggetto di questo tipo
*/


package com.mytalk.server.communication.buffer;

import java.util.Vector;

public class BufferIncoming implements Buffer{
	
	/**
	 * Contenitore di oggetti Message
	 * 
	 * @property -buffer 
	 * @type {Vector<Message>}
	 */
	private Vector<Message> buffer;
	
	/**
	 * Costruttore di oggetti BufferIncoming che inizializza il campo dati buffer con un vettore di 
	 * oggetti Message
	 * 
	 * @method -BufferIncoming
	 * 
	 */
	private BufferIncoming(){
		buffer=new Vector<Message>();
	}
	
	/**
	 * Riferimento all'unica istanza della classe presente
	 * 
	 * @property -instance
	 * @type {BufferIncoming}
	 */
	private static final BufferIncoming instance = new BufferIncoming();
	
	/**
	 * Ritorna il valore dell'attributo instance
	 * 
	 * @method +getInstance
	 * @return {BufferIncoming}
	 */
	public static BufferIncoming getInstance() {
        return instance;
    }
	
	/**
	 * Inserisce l'oggetto Message passato nel contenitore buffer; l'inserimento viene fatto
	 * in coda al contenitore; se il contenitore era vuoto prima dell'inserimento il metodo 
	 * sveglia il thread consumatore
	 * 
	 * @method +push
	 * @param {Message} packet e' l'oggetto che identifica la coppia indirizzo ip e json
	 * @return {void}
	 */
	public synchronized void push(Message packet){
		if(buffer.isEmpty()){
			notify();
		}
		buffer.add(packet);
	}
	
	/**
	 * Rimuove e ritorna un oggetto Message dal contenitore buffer; la rimozione viene fatta 
	 * in testa al contenitore; se il contenitore e' vuoto non c'e' niente da rimuovere e il 
	 * thread consumatore viene messo in attesa
	 * 
	 * @method +pop
	 * @return {Message}
	 */
	public synchronized Message pop(){
		if(buffer.isEmpty()){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Message msg=buffer.firstElement();
		buffer.remove(msg);
		return msg;
	}
}
