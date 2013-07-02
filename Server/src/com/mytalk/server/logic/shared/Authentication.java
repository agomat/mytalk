/**
* Filename: Authentication.java
* Package: com.mytalk.server.logic.shared
* Author: Nicolo' Toso
* Date: 2013-04-29
*
* Diary:
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.2     | 2013-06-24 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.1	  |	2013-04-29 |    NT     | [+] Creazione classe, costruttore e metodi   
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Classe che rappresenta le informazioni necessarie per fare l'autenticazione
*/
package com.mytalk.server.logic.shared;

public class Authentication{
	
	/**
	 * Identifica l'username che rappresenta l'utente
	 * 
	 * @property -username
	 * @type {String}
	 */
	private String username;
	
	/**
	 * Identifica la password associata all'username
	 * 
	 * @property -password
	 * @type {String}
	 */
	private String password;
	
	/**
	 * Identifica l'indirizzo ip dell'utente
	 * 
	 * @property -ip
	 * @type {String}
	 */
	private String ip;
	
	/**
	 * Costruttore della classe che inzializza i campi dati username, password e ip
	 * 
	 * @method +Authentication
	 * @param {String} user e' l'oggetto che viene assegnato al campo dati username
	 * @param {String} pwd e' l'oggetto che viene assegnato al campo dati password
	 * @param {String} i e' l'oggetto che viene assegnato al campo dati ip
	 * @return {Authentication}
	 */
	public Authentication(String user, String pwd, String i)
	{
		username=user;
		password=pwd;
		ip=i;
	}
	
	/**
	 * Imposta il valore nel campo dati username
	 * 
	 * @method +setUser
	 * @param {String} u e' l'oggetto che viene assegnato al campo dati username
	 * @return {void}
	 */
	public void setUser(String u){
		username=u;
	}
	
	/**
	 * Ritorna il valore del campo dati username
	 * 
	 * @method +getUser
	 * @return {String}
	 */
	public String getUser(){
		return username;
	}
	
	/**
	 * Imposta il valore nel campo dati password
	 * 
	 * @method +setPwd
	 * @param {String} p e' l'oggetto che viene assegnato al campo dati password
	 * @return {void}
	 */
	public void setPwd(String p){
		password=p;
	}
	
	/**
	 * Ritorna il valore del campo dati password
	 * 
	 * @method +getPwd
	 * @return {String}
	 */
	public String getPwd(){
		return password;
	}
	
	/**
	 * Imposta il valore nel campo dati ip
	 * 
	 * @method +setIp
	 * @param {String} i e' l'oggetto che viene assegnato al campo dati ip
	 * @return {void}
	 */
	public void setIp(String i){
		ip=i;
	}
	
	/**
	 * Ritorna il valore del campo dati ip
	 * 
	 * @method +getIp
	 * @return {String}
	 */
	public String getIp(){
		return ip;
	}
}
