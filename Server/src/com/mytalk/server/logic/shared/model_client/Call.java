/**
* Filename: Call.java
* Package: com.mytalk.server.logic.shared.model_client
* Author: Nicolo' Toso
* Date: 2013-04-18
*
* Diary:
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.4     | 2013-07-04 |    MF     | [+] Aggiunta campo dati id con getter e setter
* 0.3     | 2013-07-04 |	MF	   | [#] Modifica nome speaker, byteSent, byteReceived e startDate
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
	 * Rappresenta un id univoco della chiamata
	 * 
	 * @property -id
	 * @type {Integer}
	 */
	private Integer id;
	/**
	 * Rappresenta l'id associato all'interlocutore della chiamata
	 * 
	 * @property -speaker_id
	 * @type {Integer}
	 */ 
	private Integer speaker_id; 
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
	 * @property -start_date
	 * @type {String}
	 */
	private String start_date;
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
	 * @property -byte_sent
	 * @type {Integer}
	 */
	private Integer byte_sent;
	/**
	 * Rappresenta il numero di byte ricevuti
	 * 
	 * @property -byte_received
	 * @type {Integer}
	 */
	private Integer byte_received;
	
	/**
	 * Costruttore della classe che inizializza i campi dati speaker_id, caller, startData, duration, 
	 * byte_sent e byte_received
	 * 
	 * @method +Call
	 * @param {Integer} s e' l'oggetto utilizzato per inizializzare il campo dati speaker_id
	 * @param {boolean} c e' l'oggetto utilizzato per inizializzare il campo dati caller
	 * @param {String} sd e' l'oggetto utilizzato per inizializzare il campo dati start_date
	 * @param {Integer} d e' l'oggetto utilizzato per inizializzare il campo dati duration
	 * @param {Integer} bs e' l'oggetto utilizzato per inizializzare il campo dati byte_sent
	 * @param {Integer} br e' l'oggetto utilizzato per inizializzare il campo dati byte_received
	 */
	public Call(int i,Integer s, boolean c, String sd, Integer d, Integer bs, Integer br){
		id=i;
		speaker_id=s;
		byte_sent=bs;
		byte_received=br;
		duration=d;
		start_date=sd;
		caller=c;
	}
	/**
	 *  Ritorna il valore del campo id
	 *  
	 *  @method +getId
	 *  @return {Integer}
	 */
	public Integer getId(){
		return id;
	}
	/**
	 * Imposta il campo dati id
	 * 
	 * @method +setId
	 * @param {Integer} i e' l'oggetto assegnato al campo dati id
	 * @return {void}
	 */
	public void setId(Integer i){
		id=i;
	}
	/**
	 * Ritorna il campo dati speaker_id
	 * 
	 * @method +getSpeakerId
	 * @return {Integer}
	 */
	public Integer getSpeakerId(){
		return speaker_id;
	}
	
	/**
	 * Imposta il campo dati speaker_id
	 * 
	 * @method +setSpeakerId
	 * @param {Integer} n e' l'oggetto assegnato al campo dati speaker_id
	 * @return {void}
	 */
	public void setSpeakerId(Integer n){
		speaker_id=n;
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
	 * Ritorna il campo dati byte_sent
	 * 
	 * @method +getByteSent
	 * @return {Integer}
	 */
	public Integer getByteSent(){
		return byte_sent;
	}
	
	/**
	 * Imposta il campo dati byte_sent
	 * 
	 * @method +setByteSent
	 * @param {Integer} n e' l'oggetto assegnato al campo dati byte_sent
	 * @return {void}
	 */
	public void setByteSent(Integer n){
		byte_sent=n;
	}
	
	/**
	 * Ritorna il campo dati byte_received
	 * 
	 * @method +getByteReceived
	 * @return {Integer}
	 */
	public Integer getByteReceived(){
		return byte_received;
	}
	
	/**
	 * Imposta il campo dati byte_received
	 * 
	 * @method +setByteReceived
	 * @param {Integer} n e' l'oggetto assegnato al campo dati byte_received
	 * @return {void}
	 */
	public void setByteReceived(Integer n){
		byte_received=n;
	}
	
	/**
	 * Ritorna il campo dati start_date
	 * 
	 * @method +getStartDate
	 * @return {String}
	 */
	public String getStartDate(){
		return start_date;
	}
	
	/**
	 * Imposta il campo dati start_date
	 * 
	 * @method +setStartDate
	 * @param {String} t e' l'oggetto assegnato al campo dati startDate
	 * @return {void}
	 */
	public void setStartDate(String t){
		start_date=t;
	}
	
}
