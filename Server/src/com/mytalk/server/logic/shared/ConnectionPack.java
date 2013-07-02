/**
* Filename: ConnectionPack.java
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
* Classe che rappresenta le informazioni necessarie per la connessione fra due client, alcune
* delle quali vengono utilizzate dal server per dei controlli
*/

package com.mytalk.server.logic.shared;

public class ConnectionPack extends Information{
	/**
	 * Identifica l'indirizzo ip del chiamante
	 * 
	 * @property -myIp
	 * @type {String}
	 */
	private String myIp;
	
	/**
	 * Identifica l'id associato all'username del chiamante
	 * 
	 * @property -myUserId
	 * @type {Integer}
	 */
	private Integer myUserId;
	
	/**
	 * Identifica l'indirizzo ip del ricevente
	 * 
	 * @property -speakerIp
	 * @type {String}
	 */
	private String speakerIp;
	
	/**
	 * Identifica l'id associato all'username del ricevente
	 * 
	 * @property -speakerUserId
	 * @type {Integer}
	 */
	private Integer speakerUserId;
	
	/**
	 * Identifica l'informazione necessaria per la instaurare la connessione fra i due client
	 * 
	 * @property -RTCinfo
	 * @type {String}
	 */
	private String RTCinfo;
	
	/**
	 * Costruttore della classe che inizializza i campi dati myIp, speakerIp, RTCinfo, myUserId, 
	 * speakerUserId con i valori in input
	 * 
	 * @method +ConnectionPack
	 * @param {String} cIp e' l'oggetto assegnato al campo dati myIp
	 * @param {Integer} cn e' l'oggetto assegnato al campo dati myUserId
	 * @param {String} rIp e' l'oggetto assegnato al campo dati speakerIp
	 * @param {Integer} rn e' l'oggetto assegnato al campo dati speakerUserId
	 * @param {String} rtc e' l'oggetto assegnato al campo dati RTCinfo
	 */
	public ConnectionPack(String cIp,Integer cn,String rIp,Integer rn,String rtc){
		myIp=cIp;
		speakerIp=rIp;
		RTCinfo=rtc;
		myUserId=cn;
		speakerUserId=rn;
	}
	
	/**
	 * Ritorna il valore del campo dati myIp
	 * 
	 * @method +getMyIp
	 * @return {String}
	 */
	public String getMyIp(){
		return myIp;
	}
	
	/**
	 * Imposta il valore del campo dati myIp
	 * 
	 * @method +setMyIp
	 * @param {void} ip e' l'oggetto che viene assegnato al campo dati myIp
	 */
	public void setMyIp(String ip){
		myIp=ip;
	}
	
	/**
	 * Ritorna il valore del campo dati speakerIp
	 * 
	 * @method +getSpeakerIp
	 * @return {String}
	 */
	public String getSpeakerIp(){
		return speakerIp;
	}
	
	/**
	 * Imposta il valore del campo dati speakerIp
	 * 
	 * @method +setSpeakerIp
	 * @param {void} ip e' l'oggetto che viene assegnato al campo dati speakerIp
	 */
	public void setSpeakerIp(String ip){
		speakerIp=ip;
	}
	
	/**
	 * Ritorna il valore del campo dati RTCinfo
	 * 
	 * @method +getRTCinfo
	 * @return {String}
	 */
	public String getRTCinfo(){
		return RTCinfo;
	}
	
	/**
	 * Imposta il valore del campo dati RTCinfo
	 * 
	 * @method +setRTCinfo
	 * @param {void} rtc e' l'oggetto che viene assegnato al campo dati RTCinfo
	 */
	public void setRTCinfo(String rtc){
		RTCinfo=rtc;
	}
	
	/**
	 * Ritorna il valore del campo dati myUserId
	 * 
	 * @method +getMyUserId
	 * @return {Integer}
	 */
	public Integer getMyUserId(){
		return myUserId;
	}
	
	/**
	 * Imposta il valore del campo dati myUserId
	 * 
	 * @method +setMyUserId
	 * @param {void} mi e' l'oggetto che viene assegnato al campo dati myUserId
	 */
	public void setMyUserId(Integer mi){
		myUserId=mi;
	}
	
	/**
	 * Ritorna il valore del campo dati speakerUserId
	 * 
	 * @method +getSpeakerUserId
	 * @return {Integer}
	 */
	public Integer getSpeakerUserId(){
		return speakerUserId;
	}
	
	/**
	 * Imposta il valore del campo dati speakerUserId
	 * 
	 * @method +setSpeakerUserId
	 * @param {void} si e' l'oggetto che viene assegnato al campo dati speakerUserId
	 */
	public void setSpeakerUserId(Integer si){
		speakerUserId=si;
	}
}
