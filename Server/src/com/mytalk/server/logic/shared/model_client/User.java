/**
* Filename: User.java
* Package: com.mytalk.server.logic.shared.model_client
* Author: Nicolo' Toso
* Date: 2013-04-11
*
* Diary:
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.2     | 2013-06-24 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.1	  |	2013-04-11 |    NT     | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Classe che fornisce i dati dell'utente
*/

package com.mytalk.server.logic.shared.model_client;

public class User{
	/**
	 * Identifica l'id dell'utente
	 * 
	 * @property -id
	 * @type {Integer}
	 */
	private Integer id;
	/**
	 * Identifica l'username dell'utente
	 * 
	 * @property -username
	 * @type {String}
	 */
	private String username;
	/**
	 * Identifica il nome dell'utente
	 *  
	 * @property -name
	 * @type {String}
	 */
	private String name;
	/**
	 * Identifica il cognome dell'utente
	 * 
	 * @property -surname
	 * @type {String}
	 */
	private String surname;	
	/**
	 * Identifica l'indirizzo email, codificato, dell'utente
	 * 
	 * @property -md5
	 * @type {String}
	 */
	private String md5;
	/**
	 * Identifica l'indirizzo ip dell'utente
	 * 
	 * @property -ip
	 * @type {String}
	 */
	private String ip;
	/**
	 * Identifica lo stato dell'utente
	 * 
	 * @property -online
	 * @type {boolean}
	 */
	private boolean online;
	
	/**
	 * Costruttore della classe che inizializza i campi dati 
	 * 
	 * @method +User
	 * @param {Integer} id_user e' l'oggetto utilizzato per inizializzare il campo dati id
	 * @param {String} us e' l'oggetto utilizzato per inizializzare il campo dati username
	 * @param {String} n e' l'oggetto utilizzato per inizializzare il campo dati name
	 * @param {String} sn e' l'oggetto utilizzato per inizializzare il campo dati surname
	 * @param {String} m e' l'oggetto utilizzato per inizializzare il campo dati md5
	 * @param {String} i e' l'oggetto utilizzato per inizializzare il campo dati ip
	 * @param {boolean} o e' l'oggetto utilizzato per inizializzare il campo dati online
	 */
	public User(Integer id_user, String us, String n, String sn, String m, String i, boolean o){
		id=id_user;
		username=us;
		name=n;
		surname=sn;
		online=o;
		ip=i;
		md5=m;
	}
	/**
	 * Ritorna il campo dati id
	 * 
	 * @method +getId
	 * @return {Integer}
	 */
	public Integer getId(){
		return id;
	}
	/** 
	 * Imposta il campo dati id
	 * 
	 * @method +setId
	 * @param {Integer} id_user e' l'oggetto assegnato al campo dati id
	 * @return {void}
	 */
	public void setId(Integer id_user){
		id=id_user;
	}
	/**
	 * Ritorna il campo dati username
	 * 
	 * @method +getUsername
	 * @return {String}
	 */
	public String getUsername(){
		return username;
	}
	/** 
	 * Imposta il campo dati username
	 * 
	 * @method +setUsername
	 * @param {String} u e' l'oggetto assegnato al campo dati username
	 * @return {void}
	 */
	public void setUsername(String u){
		username=u;
	}
	/**
	 * Ritorna il campo dati name
	 * 
	 * @method +getName
	 * @return {String}
	 */
	public String getName(){
		return name;
	}
	/** 
	 * Imposta il campo dati name
	 * 
	 * @method +setName
	 * @param {String} n e' l'oggetto assegnato al campo dati name
	 * @return {void}
	 */
	public void setName(String n){
		name=n;
	}
	/**
	 * Ritorna il campo dati surname
	 * 
	 * @method +getSurname
	 * @return {String}
	 */
	public String getSurname(){
		return surname;
	}
	/** 
	 * Imposta il campo dati surname
	 * 
	 * @method +setSurname
	 * @param {String} sn e' l'oggetto assegnato al campo dati surname
	 * @return {void}
	 */
	public void setSurname(String sn){
		surname=sn;
	}
	/**
	 * Ritorna il campo dati online
	 * 
	 * @method +getOnline
	 * @return {boolean}
	 */
	public boolean getOnline(){
		return online;
	}
	/** 
	 * Imposta il campo dati online
	 * 
	 * @method +setStatus
	 * @param {boolean} o e' l'oggetto assegnato al campo dati online
	 * @return {void}
	 */
	public void setStatus(boolean o){
		online=o;
	}
	/**
	 * Ritorna il campo dati md5
	 * 
	 * @method +getMd5
	 * @return {String}
	 */
	public String getMd5(){
		return md5;
	}
	/** 
	 * Imposta il campo dati md5
	 * 
	 * @method +setMd5
	 * @param {String} m e' l'oggetto assegnato al campo dati md5
	 * @return {void}
	 */
	public void setMd5(String m){
		md5=m;
	}
	/**
	 * Ritorna il campo dati ip
	 * 
	 * @method +getIp
	 * @return {String}
	 */
	public String getIp(){
		return ip;
	}
	/** 
	 * Imposta il campo dati ip
	 * 
	 * @method +setIp
	 * @param {String} i e' l'oggetto assegnato al campo dati ip
	 * @return {void}
	 */
	public void setIp(String i){
		ip=i;
	}
}
