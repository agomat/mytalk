/**
* Filename: UserList.java
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
* Classe che rappresenta la lista di un utente
*/

package com.mytalk.server.logic.shared.model_client;

import java.util.List;

public class UserList{
	/**
	 * Rappresenta l'id della lista
	 * 
	 * @property -id
	 * @type {Integer}
	 */
	private Integer id;
	/**
	 * Rappresenta il nome della lista
	 * 
	 * @property -name
	 * @type {String}
	 */
	private String name;
	/**
	 * Rappresenta la lista degli id che identificano gli utenti presenti nella lista 
	 * 
	 * @property -user_ids
	 * @type {List<Integer>}
	 */
	private List<Integer> user_ids; 
	/**
	 * Costruttore della classe che inizializza i campi dati user_ids, name e id
	 * 
	 * @method +UserList
	 * @param {Integer} i e' l'oggetto utilizzato per inizializzare il campo dati id
	 * @param {String} n e' l'oggetto utilizzato per inizializzare il campo dati name
	 * @param {List<Integer>} c e' l'oggetto utilizzato per inizializzare il campo dati user_ids
	 */
	public UserList(Integer i,String n,List<Integer> c){
		user_ids=c;
		name=n;
		id=i;
	}
	/**
	 * Ritorna il campo dati id
	 * 
	 * @method +getId
	 * @return {Integer}
	 */
	public int getId(){
		return id;
	}
	/** 
	 * Imposta il campo dati id
	 * 
	 * @method +setId
	 * @param {Integer} i e' l'oggetto assegnato al campo dati id
	 * @return {void}
	 */
	public void setId(int i){
		id=i;
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
	 * @param {String} c e' l'oggetto assegnato al campo dati name
	 * @return {void}
	 */
	public void setName(String c){
		name=c;
	}
	/**
	 * Ritorna la lista list
	 * 
	 * @method +getList
	 * @return {List<Integer>}
	 */
	public List<Integer> getList(){
		return user_ids;
	}
	/**
	 * Imposta il campo dati list
	 * 
	 * @method +setList
	 * @param {List<Integer>} c e' l'oggetto assegnato al campo dati user_ids
	 * @return {void}
	 */
	public void setList(List<Integer> c){
		user_ids=c;
	}
}
