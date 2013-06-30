/**
* Filename: ListName.java
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
* Rappresenta una lista di contatti di proprieta' di un utente
*/

package com.mytalk.server.data.model;

public class ListName{
	/**
	 * Id della lista, usato come chiave primaria nel database
	 * 
	 * @property -id
	 * @type {int}
	 */
	private int id=0;
	
	/**
	 * Nome della lista
	 * 
	 * @property -name
	 * @type {String}
	 */
	private String name;
	
	/**
	 * Username dell'utente proprietario della lista
	 * 
	 * @property -owner
	 * @type {String}
	 */
	private String owner;

	/**
	 * Costruttore della classe con corpo vuoto necessario al framework Hibernate
	 * 
	 * @method +Call
	 */
	public ListName(){}
	
	/**
	 * Costruttore della classe che inizializza i campi dati name ed owner
	 * 
	 * @method +Call
	 * @param {String} n e' l'oggetto utilizzato per inizializzare il campo dati name
	 * @param {String} o e' l'oggetto utilizzato per inizializzare il campo dati owner
	 */
	public ListName(String n, String o){
		name=n;
		owner=o;
	}
	
	/**
	 * Ritorna il valore del campo dati idritorna il valore del campo dati id
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
	 * @param {int} n e' il valore che viene assegnato al campo dati id
	 * @return {void}
	 */
	public void setId(int n){
		id=n;
	}
	
	/**
	 * Ritorna il valore del campo dati name
	 * 
	 * @method +getName
	 * @return {String}
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Imposta il valore del campo dati name uguale a s
	 * 
	 * @method +setName
	 * @param {String} s e' l'oggetto assegnato al campo dati name
	 * @return {void}
	 */
	public void setName(String s){
		name=s;
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
	 * Imposta il valore del campo dati owner uguale a s
	 * 
	 * @method +setOwner
	 * @param {String} s e' l'oggetto assegnato al campo dati owner
	 * @return {void}
	 */
	public void setOwner(String s){
		owner=s;
	}
}
