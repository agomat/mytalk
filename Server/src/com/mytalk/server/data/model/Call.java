/**
* Filename: Call.java
* Package: com.mytalk.server.data.model
* Author: Nicolo' Toso
* Date: 2013-04-11
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.2     | 2013-06-18 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.1	  |	2013-04-11 |    NT     | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Rappresenta una chiamata tra due utenti e ne memorizza le statistiche
*/

package com.mytalk.server.data.model;

public class Call{
	
	/**
	 * Id della chiamata, usato come chiave primaria nel database
	 * 
	 * @property -id
	 * @type {int}
	 */
	private int id=0;
	
	/**
	 * Username dell'utente che effettua la chiamata
	 * 
	 * @property -caller
	 * @type {String}
	 */
	private String caller;
	
	/**
	 * Username dell'utente che riceve la chiamata
	 * 
	 * @property -receiver
	 * @type {String}
	 */
	private String receiver;
	
	/**
	 * Durata della chiamata in secondi
	 * 
	 * @property -duration
	 * @type {int}
	 */
	private int duration;
	
	/**
	 * Data di inizio della chiamata nel formato gg-mm-aaaa hh:mm:ss
	 * 
	 * @property -startdate
	 * @type {String}
	 */
	private String startdate;
	
	/**
	 * Numero di byte inviati dal chiamante durante la chiamata
	 * 
	 * @property -byteSent
	 * @type {int}
	 */
	private int byteSent;
	
	/**
	 * Numero di byte ricevuti dal chiamante durante la chiamata
	 * 
	 * @property -byteReceived
	 * @type {int}
	 */
	private int byteReceived;
	
	/**
	 * Costruttore della classe con corpo vuoto necessario al framework Hibernate
	 * 
	 * @method +Call
	 */
	public Call(){}
	
	/**
	 * Costruttore della classe che inizializza i campi dati con i parametri in input
	 * 
	 * @method +Call
	 * @param {String} call e' l'oggetto utilizzato per inizializzare il campo dati caller
	 * @param {String} rece e' l'oggetto utilizzato per inizializzare il campo dati receiver
	 * @param {int} time e' il valore utilizzato per inizializzare il campo dati duration
	 * @param {String} date e' l'oggetto utilizzato per inizializzare il campo dati startdate
	 * @param {int} bs e' il valore utilizzato per inizializzare il campo dati byteSent
	 * @param {int} br e' il valore utilizzato per inizializzare il campo dati byteReceived
	 */
	public Call(String call, String rece, int time, String date, int bs, int br){
		caller=call;
		receiver=rece;
		duration=time;
		startdate=date;
		byteSent=bs;
		byteReceived=br;
	}
	
	/**
	 * Ritorna il valore del campo dati id
	 * 
	 * @method +getId
	 * @return {int}
	 */
	public int getId(){
		return id;
	}
	
	/**
	 * Imposta il valore del campo dati id uguale a n
	 * 
	 * @method +setId
	 * @param {int} n e' il valore assegnato al campo dati id
	 */
	public void setId(int n){
		id=n;
	}
	
	/**
	 * Ritorna il valore del campo dati caller
	 * 
	 * @method +getCaller
	 * @return {String}
	 */
	public String getCaller(){
		return caller;
	}
	
	/**
	 * Imposta il valore del campo dati caller uguale a s
	 * 
	 * @method +setCaller
	 * @param {String} s e' l'oggetto assegnato al campo dati caller
	 */
	public void setCaller(String s){
		caller=s;
	}
	
	/**
	 * Ritorna il valore del campo dati receiver
	 * 
	 * @method +getReceiver
	 * @return {String}
	 */
	public String getReceiver(){
		return receiver;
	}
	
	/**
	 * Imposta il valore del campo dati receiver uguale a s
	 * 
	 * @method +setReceiver
	 * @param {String} s e' l'oggetto assegnato al campo dati receiver
	 */
	public void setReceiver(String s){
		receiver=s;
	}
	
	/**
	 * Ritorna il valore del campo dati duration
	 * 
	 * @method +getDuration
	 * @return {int}
	 */
	public int getDuration(){
		return duration;
	}
	
	/**
	 * Imposta il valore del campo dati duration uguale a t
	 * 
	 * @method +setDuration
	 * @param {int} t e' il valore assegnato al campo dati duration
	 */
	public void setDuration(int t){
		duration=t;
	}
	/**
	 * Ritorna il valore del campo dati byteSent
	 * 
	 * @method +getByteSent
	 * @return {int}
	 */
	public int getByteSent(){
		return byteSent;
	}
	
	/**
	 * Imposta il valore del campo dati byteSent uguale a n
	 * 
	 * @method +setByteSent
	 * @param {int} n e' il valore assegnato al campo dati byteSent
	 */
	public void setByteSent(int n){
		byteSent=n;
	}
	
	/**
	 * Ritorna il valore del campo dati byteReceived
	 * 
	 * @method +getByteReceived
	 * @return {int}
	 */
	public int getByteReceived(){
		return byteReceived;
	}
	
	/**
	 * Imposta il valore del campo dati byteReceived uguale a n
	 * 
	 * @method +setByteReceived
	 * @param {int} n e' il valore assegnato al campo dati byteReceived
	 */
	public void setByteReceived(int n){
		byteReceived=n;
	}
	
	/**
	 * Ritorna il valore del campo dati startdate
	 * 
	 * @method +getStartDate
	 * @return {String}
	 */
	public String getStartdate(){
		return startdate;
	}

	/**
	 * Imposta il valore del campo dati startdate uguale a t
	 * 
	 * @method +setStartdate
	 * @param {String} t e' il valore assegnato al campo dati startdate
	 */
	public void setStartdate(String t){
		startdate=t;
	}
}

	