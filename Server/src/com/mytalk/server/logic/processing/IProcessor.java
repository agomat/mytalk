/**
* Filename: IProcessor.java
* Package: com.mytalk.server.logic.shared
* Author: Nicolo' Toso
* Date: 2013-04-29
*
* Diary:
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.3     | 2013-06-22 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.2	  | 2013-05-06 |    NT     | [+] Aggiunta metodo 
* 										 public List<Message> processRequest(Message message)
* 0.1	  |	2013-04-29 |    NT     | [+] Creazione interfaccia 
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Interfaccia che rappresenta una generica classe di gestione dei pacchetti
*/


package com.mytalk.server.logic.processing;

import java.util.List;

import com.google.gson.JsonSyntaxException;
import com.mytalk.server.communication.buffer.Message;

public interface IProcessor {
	
	/**
	 * Ritorna una lista di oggetti Message che identifica le risposte alla specifica richiesta 
	 * che è stata processata; esso verrà ridefinito dalle classi che implementano tale interfaccia
	 * 
	 * @method +processRequest
	 * @param {Message} message e' l'oggetto utilizzato che contiene le informazioni necessarie 
	 * per processare la richiesta
	 * @return {List<Message>}
	 * @exception {JsonSyntaxException} viene sollevata se il JSON non e' ben formato sintatticamente
	 */
	public List<Message> processRequest(Message message) throws JsonSyntaxException;
	
}
