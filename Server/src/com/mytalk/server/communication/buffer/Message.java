/**
* Filename: Message.java
* Package: com.mytalk.server.communication.buffer
* Author: Nicolò Mazzucato
* Date: 2013-15-05
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.2     | 2013-06-18 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.1	  |	2013-15-05 |    NM     | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Classe che funge da contenitore di due stringhe: una rappresenta un indirizzo ip, mentre la
* seconda rappresenta un oggetto JSON. e' indispensabile per lo strato Logic Layer avere un
* indirizzo ip a cui rispondere alle richieste che quindi viene fornito passando oggetti di tipo
* Message
*/

package com.mytalk.server.communication.buffer;

public class Message {
	
	/**
	 * Stringa dell'oggetto JSON
	 * 
	 * @property -json
	 * @type {String}
	 */
	private String json;
	
	/**
	 * Indirizzo ip del mittente/ricevente del messaggio
	 * 
	 * @property -ip
	 * @type {String}
	 */
	private String ip;
	
	/**
	 * Costruttore della classe Message
	 * 
	 * @method +Message
	 * @param {String} newIp e' l'oggetto che identifica un indirizzo ip
	 * @param {String} newJson e' l'oggetto che identifica un JSON
	 */
	
	public Message(String newIp, String newJson){
		ip=newIp;
		json=newJson;
	}
	
	/**
	 * Ritorna il valore dell'attributo ip
	 * 
	 * @method +getIp
	 * @return {String}
	 */
	public String getIp(){
		return ip;
	}
	
	/**
	 * Imposta il valore dell'attributo ip uguale a newIp
	 * @method +setIp
	 * @param {String} newIp e' l'oggetto che identifica un indirizzo ip
	 * @return {void}
	 */
	public void setIp(String newIp){
		ip=newIp;
	}
	
	/**
	 * Ritorna il valore dell'attributo json
	 * 
	 * @method +getJson
	 * @return {String}
	 */
	public String getJson(){
		return json;
	}
	
	/**
	 * Imposta il valore dell'attributo json uguale a newJson
	 * @method +setJson
	 * @param {String} newJson e' l'oggetto che identifica un nuovo json
	 * @return {void}
	 */
	public void setJson(String newJson){
		json=newJson;
	}
}
