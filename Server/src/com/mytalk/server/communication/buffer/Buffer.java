/**
* Filename: Buffer.java
* Package: com.mytalk.server.comunication.buffer
* Author: Nicolò Mazzucato
* Date: 2013-05-14
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.2     | 2013-06-18 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.1	  |	2013-05-14 |    NM     | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Interfaccia che definisce un Buffer contenente oggetti di tipo CSCOM1.buffer.Message esponendo
* i metodi disponibili nelle classi che la implementano
*/

package com.mytalk.server.communication.buffer;

public interface Buffer {
	/**
	 * Metodo che inserisce un oggetto Message nel buffer
	 * 
	 * @method +push
	 * @param {Message} packet è l'oggetto che rappresenta una coppia indirizzo ip e json
	 * @return {void}
	 */
	public void push(Message packet);
	
	/**
	 * Metodo che ritorna un oggetto Message presente nel buffer
	 * 
	 * @method +pop
	 * @return {Message}
	 */
	public Message pop();
}
