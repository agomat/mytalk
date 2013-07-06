/**
* Filename: WrapperCall.java
* Package: com.mytalk.server.logic.shared.model_client
* Author: Nicolo' Toso
* Date: 2013-04-18
*
* Diary:
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.4     | 2013-07-04 |    MF     | [+] Aggiunta campo dati id con getter e setter
* 0.3     | 2013-07-04 |	MF	   | [#] Modifica nome totalByteSent, totalByteReceived e totaleDuration
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
	 * Id necessario al client
	 * 
	 * @property -id
	 * @type {Integer}
	 */
	private int id;
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
	 * @property -total_byte_sent
	 * @type {Integer}
	 */
	private Integer total_byte_sent=0;
	/**
	 * Rappresenta il totale dei byte ricevuti, inizializzato a zero
	 * 
	 * @property -total_byte_received
	 * @type {Integer}
	 */
	private Integer total_byte_received=0;
	/**
	 * Rappresenta la durata totale di chiamate, inizializzato a 0
	 * 
	 * @property -total_duration
	 * @type {Integer}
	 */
	private Integer total_duration=0;
	/**
	 * Costruttore della classe che inizializza il campo dati list
	 * 
	 * @method +WrapperCall
	 * @param {List<Call>} c e' l'oggetto utilizzato per inizializzare il campo dati list
	 */
	public WrapperCall(List<Call> c){
		list=c;
		id=0;
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
	 * Ritorna il campo dati total_byte_sent
	 * 
	 * @method +getTotalByteSent
	 * @return {Integer}
	 */
	public Integer getTotalByteSent(){
		return total_byte_sent;
	}
	
	/**
	 * Incrementa il campo dati total_byte_sent
	 * 
	 * @method +increaseTotalByteSent
	 * @param {Integer} increase e' l'oggetto che viene sommato al valore di total_byte_sent
	 * @return {void}
	 */
	public void increaseTotalByteSent(Integer increase){
		total_byte_sent=total_byte_sent+increase;
	}
	/**
	 * Ritorna il campo dati total_byte_received
	 * 
	 * @method +getTotalByteReceived
	 * @return {Integer}
	 */
	public Integer getTotalByteReceived(){
		return total_byte_received;
	}
	/**
	 * Incrementa il campo dati total_byte_received
	 * 
	 * @method +increaseTotalByteReceived
	 * @param {Integer} increase e' l'oggetto che viene sommato al valore di total_byte_received
	 * @return {void}
	 */
	public void increaseTotalByteReceived(Integer increase){
		total_byte_received=total_byte_received+increase;
	}
	/**
	 * Ritorna il campo dati total_duration
	 * 
	 * @method +getTotalDuration
	 * @return {Integer}
	 */
	public Integer getTotalDuration(){
		return total_duration;
	}
	/**
	 * Incrementa il campo dati total_duration
	 * 
	 * @method +increaseTotalDuration
	 * @param {Integer} increase e' l'oggetto che viene sommato al valore di total_duration
	 * @return {void}
	 */
	public void increaseTotalDuration(Integer increase){
		total_duration=total_duration+increase;
	}
}
