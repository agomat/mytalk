/**
* Filename: UpdateListPack.java
* Package: com.mytalk.server.logic.shared
* Author: Nicolo' Toso
* Date: 2013-04-23
*
* Diary:
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.2     | 2013-06-24 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.1	  |	2013-04-23 |    NT     | [+] Creazione classe e costruttore
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Classe che rappresenta l'insieme di informazioni necessarie per la creazione, eliminazione e 
* la modifica del nome di una lista
*/
package com.mytalk.server.logic.shared;

public class UpdateListPack extends Information{
	/**
	 * Identifica il nome della lista
	 * 
	 *  @property -listName
	 *  @type {String}
	 */
	private String listName;
	/**
	 * Identifica l'username del proprietario
	 * 
	 * @property -owner
	 * @type {String}
	 */
	private String owner;
	
	/**
	 * Identifica il nuovo nome da attribuire alla lista
	 * 
	 * @property -newListName
	 * @type {String}
	 */
	private String newListName;
	
	/**
	 * Costruttore della classe che inizializza i campi dati listName, owner, newListName
	 * 
	 * @method +UpdateListPack
	 * @param {String} n e' l'oggetto utilizzato per inizializzare il campo dati listName
	 * @param {String} o e' l'oggetto utilizzato per inizializzare il campo dati owner
	 * @param {String} nn e' l'oggetto utilizzato per inizializzare il campo dati newListName
	 */
	public UpdateListPack(String n,String o,String nn){
		listName=n;
		owner=o;
		newListName=nn;
	}
	
	/**
	 * Ritorna il valore del campo dati listName
	 * 
	 * @method +getListName
	 * @return {String}
	 */
	public String getListName(){
		return listName;
	}
	
	/**
	 * Imposta il valore del campo dati listName
	 * 
	 * @method +setListName
	 * @param {String} n e' l'oggetto assegnato al campo dati listName
	 * @return {void}
	 */
	public void setListName(String n){
		listName=n;
	}
	
	/**
	 * Ritorna il valore del campo dati owner
	 * 
	 * @method +getOwner
	 * @return {String}
	 */
	public String getOwner(){
		return owner;
	}
	
	/**
	 * Imposta il valore del campo dati owner
	 * 
	 * @method +setOwner
	 * @param {String} o e' l'oggetto assegnato al campo dati owner
	 * @return {void}
	 */
	public void setOwner(String o){
		owner=o;
	}
	
	/**
	 * Ritorna il valore del campo dati newListName
	 * 
	 * @method +getNewListName
	 * @return {String}
	 */
	public String getNewListName(){
		return newListName;
	}
	
	/**
	 * Imposta il valore del campo dati newListName
	 * 
	 * @method +setNewListName
	 * @param {String} n e' l'oggetto assegnato al campo dati newListName
	 * @return {void}
	 */
	public void setNewListName(String n){
		newListName=n;
	}
}
