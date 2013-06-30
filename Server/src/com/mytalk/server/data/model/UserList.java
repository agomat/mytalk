/**
* Filename: UserList.java
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
* Rappresenta la relazione di utente in una lista di un altro utente
*/

package com.mytalk.server.data.model;

import java.io.Serializable;

public class UserList implements Serializable{
	
	/**
	 * Id della lista a cui si riferisce l'utente aggiunto
	 * 
	 * @property -idList
	 * @type {int}
	 */
	private int idList;
	
	/**
	 * Utente aggiunto alla lista specificata
	 * 
	 * @property -username
	 * @type {String}
	 */
	private String username;
	
	/**
	 * Costruttore della classe con corpo vuoto necessario al framework Hibernate
	 * 
	 * @method +UserList
	 */
	public UserList(){}

	/**
	 * Costruttore della classe che inizializza i campi dati idList e username
	 * 
	 * @method +UserList
	 * @param {int} ident e' utilizzato per inizializzare il campo dati idList
	 * @param {String} us e' utilizzato per inizializzare il campo dati username
	 */
	public UserList(int ident, String us){
		idList=ident;
		username=us;
	}

	/**
	 * Ritorna il valore dell'attributo idList
	 * 
	 * @method	+getIdList
	 * @return {int}
	 */
	public int getIdList(){
		return idList;
	}
	
	/**
	 * Imposta il valore dell'attributo idList uguale a n
	 * 
	 * @method +setIdList
	 * @param {int} n e' il valore assegnato al campo dati idList
	 * @return {void}
	 */
	public void setIdList(int n){
		idList=n;
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
	 * @method setUsername
	 * @param {String} s e' l'oggetto assegnato al campo dati username
	 * @return {void}
	 */
	public void setUsername(String s){
		username=s;
	}
}
