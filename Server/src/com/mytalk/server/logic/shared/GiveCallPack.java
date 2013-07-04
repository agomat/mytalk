/**
* Filename: GiveCallPack.java
* Package: com.mytalk.server.logic.shared
* Author: Nicolo' Toso
* Date: 2013-04-23
*
* Diary:
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.3     | 2013-07-04 |	MF	   | [#] Modifica nome campo dati wc in w_call
* 0.2     | 2013-06-24 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.1	  |	2013-04-23 |    NT     | [+] Creazione classe e costruttore
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Classe che rappresenta l'insieme delle informazioni riguardanti le chiamate di un client
*/

package com.mytalk.server.logic.shared;

import com.mytalk.server.logic.shared.model_client.WrapperCall;

public class GiveCallPack extends Information{
	/**
	 * Oggetto che rappresenta un insieme di chiamate scambiate fra client e server
	 * 
	 * @property -w_call
	 * @type {WrapperCall}
	 */
	private WrapperCall w_call;
	
	/**
	 * Costruttore della classe che inzializza il campo dati w_call con l'oggetto in input
	 * 
	 * @method +GiveCallPack
	 * @param {WrapperCall} c e' l'oggetto utlizzato per inizializzare il campo dati w_call
	 */
	public GiveCallPack(WrapperCall c){
		w_call=c;
	}
	
	/**
	 * Ritorna il valore dell'oggetto w_call
	 * 
	 * @method +getWrapperCall
	 * @return {WrapperCall}
	 */
	public WrapperCall getWrapperCall(){
		return w_call;
	}
	
	/**
	 * Imposta il valore nell'oggetto w_call
	 * 
	 * @method +setWrapperCall
	 * @param {WrapperCall} c e' l'oggetto assegnato al campo dati w_call 
	 */
	public void setWrapperCall(WrapperCall c){
		w_call=c;
	}
}

