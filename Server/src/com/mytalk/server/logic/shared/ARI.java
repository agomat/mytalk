/**
* Filename: ARI.java
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
* Classe che rappresenta la struttura del pacchetto che viene scambiato tra client e server
*/
package com.mytalk.server.logic.shared;

public class ARI{
	
	/**
	 * Identifica l'oggetto che contiene i dati di autenticazione
	 * 
	 * @property -auth
	 * @type {Authentication}
	 */
	private Authentication auth;
	
	/**
	 * Identifica il tipo di richiesta
	 * 
	 * @property -req
	 * @type {String}
	 */
	private String req;
	/**
	 * Identifica il JSON contenente i dati necessari per la richiesta
	 * 
	 * @property -info
	 * @type {String}
	 */
	private String info;
	
	/**
	 * Costruttore della classe che inizializza i campi dati auth, req e info con i valori in input
	 * 
	 * @method +ARI
	 * @param {Authentication} a e' l'oggetto utilizzato per inizializzare il campo dati auth
	 * @param {String} r e' l'oggetto utilizzato per inizializzare il campo dati req
	 * @param {String} i e' l'oggetto utilizzato per inizializzare il campo dati info
	 */
	public ARI(Authentication a, String r, String i){
		auth=a;
		req=r;
		info=i;
	}
	
	/**
	 * Imposta il valore nel campo dati auth
	 * 
	 * @method +setAuth
	 * @param {Authentication} a e' l'oggetto che viene assegnato al campo dati auth
	 * @return {void}
	 */
	public void setAuth(Authentication a){
		auth=a;
	}
	
	/**
	 * Ritorna l'oggetto riferito dal campo dati auth
	 * 
	 * @method +getAuth
	 * @return {Authentication}
	 */
	public Authentication getAuth(){
		return auth;
	}
	
	/**
	 * Imposta il valore nel campo dati req
	 * 
	 * @method +setReq
	 * @param {String} r e' l'oggetto che viene assegnato al campo dati req
	 * @return {void}
	 */
	public void setReq(String r){
		req=r;
	}
	
	/**
	 * Ritorna l'oggetto riferito dal campo dati req
	 * 
	 * @method +getReq
	 * @return {String}
	 */
	public String getReq(){
		return req;
	}
	
	/**
	 * Imposta il valore nel campo dati req
	 * 
	 * @method +setInfo
	 * @param {String} i e' l'oggetto che viene assegnato al campo dati info
	 * @return {void}
	 */
	public void setInfo(String i){
		info=i;
	}
	
	/**
	 * Ritorna l'oggetto riferito dal campo dati info
	 * 
	 * @method +getInfo
	 * @return {String}
	 */
	public String getInfo(){
		return info;
	}
}
