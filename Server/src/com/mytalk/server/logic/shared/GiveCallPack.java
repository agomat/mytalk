/**
* Filename: GiveCallPack.java
* Package: com.mytalk.server.logic.shared
* Author: Nicolo' Toso
* Date: 2013-04-23
*
* Diary:
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
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
	 * @property -wc
	 * @type {WrapperCall}
	 */
	private WrapperCall wc;
	
	/**
	 * Costruttore della classe che inzializza il campo dati wc con l'oggetto in input
	 * 
	 * @method +GiveCallPack
	 * @param {WrapperCall} c e' l'oggetto utlizzato per inizializzare il campo dati wc
	 */
	public GiveCallPack(WrapperCall c){
		wc=c;
	}
	
	/**
	 * Ritorna il valore dell'oggetto wc
	 * 
	 * @method +getWrapperCall
	 * @return {WrapperCall}
	 */
	public WrapperCall getWrapperCall(){
		return wc;
	}
	
	/**
	 * Imposta il valore nell'oggetto wc
	 * 
	 * @method +setWrapperCall
	 * @param {WrapperCall} c e' l'oggetto assegnato al campo dati wc 
	 */
	public void setWrapperCall(WrapperCall c){
		wc=c;
	}
}

