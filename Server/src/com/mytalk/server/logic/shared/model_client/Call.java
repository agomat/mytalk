/**
* Filename: Call.java
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
* Classe che contiene tutte le informazioni riguardanti la chiamata
*/

package com.mytalk.server.logic.shared.model_client;

public class Call{
	/**
	 * Rappresenta l'id associato all'interlocutore della chiamata
	 * 
	 * @property -speaker
	 * @type {Integer}
	 */ 
	private Integer speaker; 
	/**
	 * Identifica se chi richiede tale chiamata era il chiamante oppure no
	 * 
	 * @property -caller
	 * @type {boolean}
	 */
	private boolean caller;
	/**
	 * Rappresenta la data in cui \`e stata effettuata la chiamata
	 * 
	 * @property -startDate
	 * @type {String}
	 */
	private String startDate;
	/**
	 * Rappresenta la durata della telefonata
	 * 
	 * @property -duration
	 * @type {Integer}
	 */
	private Integer duration;
	/**
	 * Rappresenta il numero di byte inviati
	 * 
	 * @property -byteSent
	 * @type {Integer}
	 */
	private Integer byteSent;
	/**
	 * Rappresenta il numero di byte ricevuti
	 * 
	 * @property -byteReceived
	 * @type {Integer}
	 */
	private Integer byteReceived;
	
	/**
	 * Costruttore della classe che inizializza i campi dati speaker, caller, startData, duration, 
	 * byteSent e byteReceived
	 * 
	 * @method +Call
	 * @param {Integer} s e' l'oggetto utilizzato per inizializzare il campo dati speaker
	 * @param {boolean} c e' l'oggetto utilizzato per inizializzare il campo dati caller
	 * @param {String} sd e' l'oggetto utilizzato per inizializzare il campo dati startDate
	 * @param {Integer} d e' l'oggetto utilizzato per inizializzare il campo dati duration
	 * @param {Integer} bs e' l'oggetto utilizzato per inizializzare il campo dati byteSent
	 * @param {Integer} br e' l'oggetto utilizzato per inizializzare il campo dati byteReceived
	 */
	public Call(Integer s, boolean c, String sd, Integer d, Integer bs, Integer br){
		speaker=s;
		byteSent=bs;
		byteReceived=br;
		duration=d;
		startDate=sd;
		caller=c;
	}
	
	/**
	 * Ritorna il campo dati speaker
	 * 
	 * @method +getSpeaker
	 * @return {Integer}
	 */
	public Integer getSpeaker(){
		return speaker;
	}
	
	/**
	 * Imposta il campo dati speaker
	 * 
	 * @method +setSpeaker
	 * @param {Integer} n e' l'oggetto assegnato al campo dati speaker
	 * @return {void}
	 */
	public void setSpeaker(Integer n){
		speaker=n;
	}
	
	/**
	 * Ritorna il campo dati caller
	 * 
	 * @method +getCaller
	 * @return {boolean}
	 */
	public boolean getCaller(){
		return caller;
	}
	
	/**
	 * Imposta il campo dati caller
	 * 
	 * @method +setCaller
	 * @param {boolean} s e' l'oggetto assegnato al campo dati caller
	 * @return {void}
	 */
	public void setCaller(boolean s){
		caller=s;
	}
	
	/**
	 * Ritorna il campo dati duration
	 * 
	 * @method +getDuration
	 * @return {Integer}
	 */
	public Integer getDuration(){
		return duration;
	}
	
	/**
	 * Imposta il campo dati duration
	 * 
	 * @method +setDuration
	 * @param {Integer} t e' l'oggetto assegnato al campo dati duration
	 * @return {void}
	 */
	public void setDuration(Integer t){
		duration=t;
	}
	
	/**
	 * Ritorna il campo dati byteSent
	 * 
	 * @method +getByteSent
	 * @return {Integer}
	 */
	public Integer getByteSent(){
		return byteSent;
	}
	
	/**
	 * Imposta il campo dati byteSent
	 * 
	 * @method +setByteSent
	 * @param {Integer} n e' l'oggetto assegnato al campo dati byteSent
	 * @return {void}
	 */
	public void setByteSent(Integer n){
		byteSent=n;
	}
	
	/**
	 * Ritorna il campo dati byteReceived
	 * 
	 * @method +getByteReceived
	 * @return {Integer}
	 */
	public Integer getByteReceived(){
		return byteReceived;
	}
	
	/**
	 * Imposta il campo dati byteReceived
	 * 
	 * @method +setByteReceived
	 * @param {Integer} n e' l'oggetto assegnato al campo dati byteReceived
	 * @return {void}
	 */
	public void setByteReceived(Integer n){
		byteReceived=n;
	}
	
	/**
	 * Ritorna il campo dati startDate
	 * 
	 * @method +getStartDate
	 * @return {String}
	 */
	public String getStartDate(){
		return startDate;
	}
	
	/**
	 * Imposta il campo dati byteReceived
	 * 
	 * @method +setStartDate
	 * @param {String} t e' l'oggetto assegnato al campo dati startDate
	 * @return {void}
	 */
	public void setStartDate(String t){
		startDate=t;
	}
	
}
