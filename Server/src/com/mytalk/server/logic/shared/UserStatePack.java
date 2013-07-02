/**
* Filename: UserStatePack.java
* Package: com.mytalk.server.logic.shared
* Author: Nicolo' Toso
* Date: 2013-04-23
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.2     | 2013-06-24 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.1	  | 2013-04-23 |    NT     | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Classe che rappresenta la informazioni relative ad un utente necessarie per notificare 
* il cambio stato
*/


package com.mytalk.server.logic.shared;

import com.mytalk.server.logic.shared.model_client.User;

public class UserStatePack extends Information {
	/**
	 * Rappresenta l'oggetto User che contiene le informazioni sull'utente che ha cambiato stato
	 * 
	 * @property -list
	 * @type {User}
	 */
	private User list;
	
	/**
	 * Costruttore della classe che inizializza il campo dato list
	 * 
	 * @method +UserStatePack
	 */
	public UserStatePack(User u){
		list=u;
	}
	
	/**
	 * Ritorna il valore dell'oggetto user
	 * 
	 * @method +getList
	 * @return {User}
	 */
	public User getList(){
		return list;
	}
	
	/**
	 * Imposta il valore del campo dati user
	 * 
	 * @method +setList
	 * @param {User} u e' l'oggetto assegnato al campo dati list
	 * @return {void}
	 */
	public void setList(User u){
		list=u;
	}
}
