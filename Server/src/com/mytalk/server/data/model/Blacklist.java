/**
* Filename: Blacklist.java
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
* Rappresenta un contatto nella blacklist di un utente
*/

package com.mytalk.server.data.model;

import java.io.Serializable;

public class Blacklist implements Serializable{
	
	/**
	 * Username dell'utente possessore del record in blacklist
	 * 
	 * @property -owner
	 * @type {String}
	 */
	private String owner;
	
	/**
	 * Username dell'utente nella blacklist del possessore
	 * 
	 * @property -username
	 * @type {String}
	 */
	private String username;
	
	/**
	 * Costruttore della classe con corpo vuoto necessario al framework Hibernate
	 * 
	 * @method +Blacklist
	 */
	public Blacklist(){}
	
	/**
	 * Costruttore della classe che inizializza i due campi dati con i valori in input
	 * 
	 * @method +Blacklist
	 * @param {String} o e' l'oggetto utilizzato per inizializzare il campo dati owner
	 * @param {String} us e' l'oggetto utilizzato per inizializzare il campo dati username
	 */
	public Blacklist(String o, String us){
		username=us;
		owner=o;
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
	 * @param {String} s e' l'oggetto utilizzato per assegnare un valore al campo dati username
	 * @return {void}
	 */
	public void setUsername(String s){
		username=s;
	}
	
	/**
	 * Ritorna il valore dell'attributo owner
	 * 
	 * @method +getOwner
	 * @return {String}
	 */
	public String getOwner(){
		return owner;
	}
	
	/**
	 * Imposta il valore dell'attributo owner uguale a s
	 * 
	 * @method +setOwner
	 * @param {String} s e' l'oggetto utilizzato per assegnare un valore al campo dati owner
	 * @return {void}
	 */
	public void setOwner(String s){
		owner=s;
	}
}
