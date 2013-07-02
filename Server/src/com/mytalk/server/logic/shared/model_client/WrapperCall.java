/**
* Filename: WrapperCall.java
* Package: com.mytalk.server.logic.shared.model_client
* Author: Nicolo' Toso
* Date: 2013-04-18
*
* Diary:
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.2     | 2013-06-24 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.1	  |	2013-04-18 |    NT     | [+] Creazione classe e metodi   
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Classe che rappresenta le informazioni sulle chiamate
*/

package com.mytalk.server.logic.shared.model_client;

import java.util.List;

public class WrapperCall{
	/**
	 * Rappresenta l'insieme delle chiamate
	 * 
	 * @property -list
	 * @type {List<Call>}
	 */
	private List<Call> list;
	/**
	 * Rappresenta il totale dei byte inviati, inizializzato a zero
	 * 
	 * @property -totalByteSent
	 * @type {Integer}
	 */
	private Integer totalByteSent=0;
	/**
	 * Rappresenta il totale dei byte ricevuti, inizializzato a zero
	 * 
	 * @property -totalByteReceived
	 * @type {Integer}
	 */
	private Integer totalByteReceived=0;
	/**
	 * Rappresenta la durata totale di chiamate, inizializzato a 0
	 * 
	 * @property -totalDuration
	 * @type {Integer}
	 */
	private Integer totalDuration=0;
	/**
	 * Costruttore della classe che inizializza il campo dati list
	 * 
	 * @method +WrapperCall
	 * @param {List<Call>} c e' l'oggetto utilizzato per inizializzare il campo dati list
	 */
	public WrapperCall(List<Call> c){
		list=c;
	}
	/**
	 * Ritorna la lista list
	 * 
	 * @method +getList
	 * @return {List<Call>}
	 */
	public List<Call> getList(){
		return list;
	}
	/**
	 * Imposta il campo dati list
	 * 
	 * @method +setList
	 * @param {List<Call>} c e' l'oggetto assegnato al campo dati list
	 */
	public void setList(List<Call> c){
		list=c;
	}
	/**
	 * Ritorna il campo dati totalByteSent
	 * 
	 * @method +getTotalByteSent
	 * @return {Integer}
	 */
	public Integer getTotalByteSent(){
		return totalByteSent;
	}
	
	/**
	 * Incrementa il campo dati totalByteSent
	 * 
	 * @method +increaseTotalByteSent
	 * @param {Integer} increase e' l'oggetto che viene sommato al valore di totalByteSent
	 * @return {void}
	 */
	public void increaseTotalByteSent(Integer increase){
		totalByteSent=totalByteSent+increase;
	}
	/**
	 * Ritorna il campo dati totalByteReceived
	 * 
	 * @method +getTotalByteReceived
	 * @return {Integer}
	 */
	public Integer getTotalByteReceived(){
		return totalByteReceived;
	}
	/**
	 * Incrementa il campo dati totalByteReceived
	 * 
	 * @method +increaseTotalByteReceived
	 * @param {Integer} increase e' l'oggetto che viene sommato al valore di totalByteReceived
	 * @return {void}
	 */
	public void increaseTotalByteReceived(Integer increase){
		totalByteReceived=totalByteReceived+increase;
	}
	/**
	 * Ritorna il campo dati totalDuration
	 * 
	 * @method +getTotalDuration
	 * @return {Integer}
	 */
	public Integer getTotalDuration(){
		return totalDuration;
	}
	/**
	 * Incrementa il campo dati totalDuration
	 * 
	 * @method +increaseTotalDuration
	 * @param {Integer} increase e' l'oggetto che viene sommato al valore di totalDuration
	 * @return {void}
	 */
	public void increaseTotalDuration(Integer increase){
		totalDuration=totalDuration+increase;
	}
}
