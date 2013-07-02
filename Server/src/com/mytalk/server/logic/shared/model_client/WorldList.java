/**
* Filename: WorldList.java
* Package: com.mytalk.server.logic.shared.model_client
* Author: Michael Ferronato
* Date: 2013-05-02
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.2     | 2013-06-24 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.1	  |	2013-05-02 |    MF     | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Classe che rappresenta tutte le liste personali di un utente, compresa la lista di tutti gli 
* utenti
*/


package com.mytalk.server.logic.shared.model_client;

import java.util.List;


public class WorldList {
	/**
	 * Rappresenta l'insieme delle liste personali dell'utente
	 * 
	 * @property -userList
	 * @type {List<UserList>}
	 */
	private List<UserList> userList;
	/**
	 * Rappresenta la lista di tutti gli utenti
	 * 
	 * @property -list
	 * @type {List<User>}
	 */
	private List<User> list;
	/**
	 * Costruttore della classe che inizializza i campi dati userList e list
	 * 
	 * @method +WorldList
	 * @param {List<UserList>} ul e' l'oggetto utilizzato per inizializzare il campo dati userList
	 * @param {List<User>} l e' l'oggetto utilizzato per inizializzare il campo dati list
	 */
	public WorldList(List<UserList> ul, List<User> l){
		userList=ul;
		list=l;
	}
	/**
	 * Ritorna la lista userList
	 * 
	 * @method +getUserList
	 * @return {List<UserList>}
	 */
	public List<UserList> getUserList(){
		return userList;
	}
	/**
	 * Imposta la lista userlist
	 * 
	 * @method +setUserList
	 * @param {List<UserList>} l e' l'oggetto assegnato al campo dati userList
	 * @return {void}
	 */
	public void setUserList(List<UserList> l){
		userList=l;
	}
	/**
	 * Ritorna la lista list
	 * 
	 * @method +getList
	 * @return {List<User>}
	 */
	public List<User> getList(){
		return list;
	}
	/**
	 * Imposta la lista list
	 * 
	 * @method +setList
	 * @param {List<User>} c e' l'oggetto assegnato al campo dati list
	 * @return {void}
	 */
	public void setList(List<User> c){
		list=c;
	}

}
