/**
* Filename: OnlineUser.java
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
* Rappresenta un utente connesso al sistema Mytalk, sia esso autenticato o non
*/

package com.mytalk.server.data.model;

public class OnlineUser{
	/**
	 * Username dell'utente connesso al sistema, se non autenticato e' null
	 * 
	 * @property -username
	 * @type {String}
	 */
	private String username;
	
	/**
	 * Indirizzo ip nel formato ip:porta di un utente connesso al sistema
	 * 
	 * @property -ip
	 * @type {String}
	 */
	private String ip;

	/**
	 * Costruttore della classe con corpo vuoto necessario al framework Hibernate
	 * 
	 * @method +OnlineUser
	 */
	public OnlineUser(){}
	
	/**
	 * Costruttore della classe che inizializza i campi dati username e ip
	 * 
	 * @method +OnlineUser
	 * @param {String} us e' l'oggetto utilizzato per inizializzare il campo dati username
	 * @param {String} ipadd e' l'oggetto utilizzato per inizializzare il campo dati ip
	 */
	public OnlineUser(String us, String ipadd){
		username=us;
		ip=ipadd;
	}
	
	/**
	 * Ritorna il valore dell'attributo username
	 * 
	 * @method +getUsername
	 * @return {String}
	 */
	public String getUsername(){
		return username;
	}
	
	/**
	 * Imposta il valore dell'attributo username uguale a s
	 * 
	 * @method +setUsername
	 * @param {String} s e' l'oggetto assegnato al campo dati username
	 * @return {void}
	 */
	public void setUsername(String s){
		username=s;
	}
	
	/**
	 * Ritorna il valore dell'attributo ip
	 * 
	 * @method +getIp
	 * @return {String}
	 */
	public String getIp(){
		return ip;
	}
	
	/**
	 * Imposta il valore dell'attributo ip uguale a s
	 * 
	 * @method +setIp
	 * @param {String} s e' l'oggetto assegnato al campo dati ip
	 * @return {void}
	 */
	public void setIp(String s){
		ip=s;
	}
}
