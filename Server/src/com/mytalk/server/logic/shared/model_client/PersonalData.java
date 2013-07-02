/**
* Filename: PersonalData.java
* Package: com.mytalk.server.logic.shared.model_client
* Author: Nicolo' Toso
* Date: 2013-04-18
*
* Diary:
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.2     | 2013-06-24 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.1	  |	2013-04-12 |    NT     | [+] Creazione classe e metodi   
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Classe che contiene i dati personali dell'utente
*/

package com.mytalk.server.logic.shared.model_client;

public class PersonalData{
	/**
	 * Rappresenta un id inizializzato a 0 per facilitare il matching al client
	 * 
	 * @property -id
	 * @type {Integer}
	 */
	private Integer id=0;
	/**
	 * Rappresenta l'id dell'utente
	 * 
	 * @property -user_id
	 * @type {Integer}
	 */
	private Integer user_id;
	/**
	 * Rappresenta l'username dell'utente
	 * 
	 * @property -username
	 * @type {String}
	 */
	private String username;
	/**
	 * Rappresenta la password in hash dell'utente
	 * 
	 * @property -password
	 * @type {String}
	 */
	private String password;
	/**
	 * Rappresenta il nome dell'utente
	 *  
	 * @property -name
	 * @type {String}
	 */
	private String name;
	/**
	 * Rappresenta il cognome dell'utente
	 * 
	 * @property -surname
	 * @type {String}
	 */
	private String surname;
	/**
	 * Rappresenta l'indirizzo email, in chiaro, dell'utente
	 * 
	 * @property -email
	 * @type {String}
	 */
	private String email;
	/**
	 * Rappresenta l'indirizzo email, codificato, dell'utente
	 * 
	 * @property -md5
	 * @type {String}
	 */
	private String md5;
	/**
	 * Rappresenta l'indirizzo ip dell'utente
	 * 
	 * @property -ip
	 * @type {String}
	 */
	private String ip;
	
	/**
	 * Costruttore della classe che inizializza i campi dati 
	 * 
	 * @method PersonalData
	 * @param {Integer} ui e' l'oggetto utilizzato per inizializzare il campo dati user_id
	 * @param {String} us e' l'oggetto utilizzato per inizializzare il campo dati username
	 * @param {String} pwd e' l'oggetto utilizzato per inizializzare il campo dati password
	 * @param {String} n e' l'oggetto utilizzato per inizializzare il campo dati name
	 * @param {String} s e' l'oggetto utilizzato per inizializzare il campo dati surname
	 * @param {String} mail e' l'oggetto utilizzato per inizializzare il campo dati email
	 * @param {String} m e' l'oggetto utilizzato per inizializzare il campo dati md5
	 * @param {String} ip_user e' l'oggetto utilizzato per inizializzare il campo dati ip
	 */
	public PersonalData(Integer ui,String us, String pwd, String n, String s,String mail, String m,String ip_user){
		user_id=ui;
		username=us;
		password=pwd;
		email=mail;
		name=n;
		surname=s;
		md5=m;
		ip=ip_user;
	}
	/**
	 * Ritorna il campo dati userId
	 * 
	 * @method +getUserId
	 * @return {Integer}
	 */
	public Integer getUserId(){
		return user_id;
	}
	/**
	 * Imposta il campo dati userId
	 * 
	 * @method +setUserId
	 * @param {Integer} ui e' l'oggetto assegnato al campo dati user_id
	 * @return {void}
	 */
	public void setUserId(Integer ui){
		user_id=ui;
	}
	/**
	 * Ritorna il campo dati username
	 * 
	 * @method +getUsername
	 * @return {String}
	 */
	public String getUsername(){
		return username;
	}
	/**
	 * Imposta il campo dati username
	 * 
	 * @method +setUsername
	 * @param {String} u e' l'oggetto assegnato al campo dati username
	 * @return {void}
	 */
	public void setUsername(String u){
		username=u;
	}
	/**
	 * Ritorna il campo dati password
	 * 
	 * @method +getPassword
	 * @return {String}
	 */
	public String getPassword(){
		return password;
	}
	/**
	 * Imposta il campo dati password
	 * 
	 * @method +setPassword
	 * @param {String} p e' l'oggetto assegnato al campo dati password
	 * @return {void}
	 */
	public void setPassword(String p){
		password=p;
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
	 * @param {String} n e' l'oggetto assegnato al campo dati name
	 * @return {void}
	 */
	public void setName(String n){
		name=n;
	}
	/**
	 * Ritorna il campo dati surname
	 * 
	 * @method +getSurname
	 * @return {String}
	 */
	public String getSurname(){
		return surname;
	}
	/**
	 * Imposta il campo dati surname
	 * 
	 * @method +setUsername
	 * @param {String} s e' l'oggetto assegnato al campo dati surname
	 * @return {void}
	 */
	public void setSurname(String s){
		surname=s;
	}
	/**
	 * Ritorna il campo dati email
	 * 
	 * @method +getEmail
	 * @return {String}
	 */
	public String getEmail(){
		return email;
	}
	/**
	 * Imposta il campo dati email
	 * 
	 * @method +setEmail
	 * @param {String} e e' l'oggetto assegnato al campo dati email
	 * @return {void}
	 */
	public void setEmail(String e){
		email=e;
	}
	/**
	 * Ritorna il campo dati md5
	 * 
	 * @method +getMd5
	 * @return {String}
	 */
	public String getMd5(){
		return md5;
	}
	/**
	 * Imposta il campo dati md5
	 * 
	 * @method +setMd5
	 * @param {String} m e' l'oggetto assegnato al campo dati md5
	 * @return {void}
	 */
	public void setMd5(String m){
		md5=m;
	}
	/**
	 * Ritorna il campo dati ip
	 * 
	 * @method +getIp
	 * @return {String}
	 */
	public String getIp(){
		return ip;
	}
	/**
	 * Imposta il campo dati ip
	 * 
	 * @method +setIp
	 * @param {String} ip_user e' l'oggetto assegnato al campo dati ip
	 * @return {void}
	 */
	public void setIp(String ip_user){
		ip=ip_user;
	}
}