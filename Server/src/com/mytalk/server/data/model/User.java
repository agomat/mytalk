/**
* Filename: User.java
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
* Rappresenta un utente registrato nel sistema MyTalk
*/

package com.mytalk.server.data.model;

public class User{
	/**
	 * Numero identificativo dell'utente, di utilita' per il client
	 * 
	 * @property -id
	 * @type {int}
	 */
	private int id=0;
	/**
	 * Username dell'utente
	 * 
	 * @property -username
	 * @type {String}
	 */
	private String username;
	/**
	 * Password dell'utente
	 * 
	 * @property -password
	 * @type {String}
	 */
	private String password;
	/**
	 * Nome dell'utente
	 * 
	 * @property -name
	 * @type {String}
	 */
	private String name;
	/**
	 * Cognome dell'utente
	 * 
	 * @property -surname
	 * @type {String}
	 */
	private String surname;
	/**
	 * Indirizzo e-mail dell'utente
	 * 
	 * @property -email
	 * @type {String}
	 */
	private String email;
	/**
	 * Hash md5 dell'indirizzo e-mail dell'utente, di utilita' per il client 
	 * 
	 * @property -emailHash
	 * @type {String}
	 */
	private String emailHash;

	/**
	 * Costruttore della classe con corpo vuoto necessario al framework Hibernate
	 * 
	 * @method +User
	 */
	public User (){}
	
	/**
	 * Costruttore della classe che inizializza i campi dati username, password, email, name,
	 * surname ed emailHash
	 * 
	 * @method +User
	 * @param {String} us e' utilizzato per inizializzare il campo dati username
	 * @param {String} pwd e' utilizzato per inizializzare il campo dati password
	 * @param {String} mail e' utlilizzato per inizializzare il campo dati email
	 * @param {String} n e' utilizzato per inizializzare il campo dati name
	 * @param {String} s e' utlizzato per inizializzare il campo dati surname
	 * @param {String} eh e' utilizzato per inizializzare il campo dati emailHash  
	 */
	public User(String us, String pwd, String mail, String n, String s, String eh){
		username=us;
		password=pwd;
		email=mail;
		name=n;
		surname=s;
		emailHash=eh;
	}
	
	/**
	 * Ritorna il valore dell'attributo id
	 * 
	 * @method +getId
	 * @return {int}
	 */
	public int getId(){
		return id;
	}
	
	/**
	 * Imposta il valore dell'attributo id uguale a n
	 * 
	 * @method +setId
	 * @param {int} n e' il valore assegnato al campo dati id
	 * @return {void}
	 */
	public void setId(int n){
		id=n;
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
	 * Imposta il valore dell'attributo username uguale a u
	 * 
	 * @method +setUsername
	 * @param {String} u e' il valore assegnato al campo dati username
	 * @return {void}
	 */
	public void setUsername(String u){
		username=u;
	}
	
	/**
	 * Ritorna il valore dell'attributo password
	 * 
	 * @method +getPassword
	 * @return {String}
	 */
	public String getPassword(){
		return password;
	}
	
	/**
	 * Imposta il valore dell'attributo password uguale a p
	 * 
	 * @method +setPassword
	 * @param {String} p e' l'oggetto assegnato al campo dati password
	 * @return {void}
	 */
	public void setPassword(String p){
		password=p;
	}
	
	/**
	 * Ritorna il valore dell'attributo name
	 * 
	 * @method +getName
	 * @return {String}
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Imposta il valore dell'attributo name uguale a n
	 * 
	 * @method +setName
	 * @param {String} n e' l'oggetto assegnato al campo dati name
	 * @return {void}
	 */
	public void setName(String n){
		name=n;
	}
	
	/**
	 * Ritorna il valore dell'attributo surname
	 * 
	 * @method +getSurname
	 * @return {String}
	 */
	public String getSurname(){
		return surname;
	}
	
	/**
	 * Imposta il valore dell'attributo surname uguale a s
	 * 
	 * @method +setSurname
	 * @param {String} s e' l'oggetto assegnato al campo dati surname
	 * @return {void}
	 */
	public void setSurname(String s){
		surname=s;
	}
	
	/**
	 * Ritorna il valore dell'attributo email
	 * 
	 * @method +getEmail
	 * @return {String}
	 */
	public String getEmail(){
		return email;
	}
	
	/**
	 * Imposta il valore dell'attributo email uguale a e
	 * 
	 * @method +setEmail
	 * @param {String} e e' l'oggetto assegnato al campo dati email
	 * @return {void}
	 */
	public void setEmail(String e){
		email=e;
	}
	
	/**
	 * Ritorna il valore dell'attributo emailHash
	 * 
	 * @method +getEmailHash
	 * @return {String}
	 */
	public String getEmailHash(){
		return emailHash;
	}
	
	/**
	 * Imposta il valore dell'attributo emailHash uguale a e
	 * 
	 * @method +setEmailHash
	 * @param {String} e e' l'oggetto assegnato al campo dati emailHash
	 * @return {void}
	 */	
	public void setEmailHash(String e){
		emailHash=e;
	}
}
