/**
* Filename: ListPack.java
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
* Classe che rappresenta l'insieme delle liste degli utenti
*/

package com.mytalk.server.logic.shared;
import java.util.List;

import com.mytalk.server.logic.shared.model_client.UserList;

public class ListPack extends Information{
	/**
	 * Lista di elementi che rappresentano le liste degli utenti
	 * 
	 * @property -list
	 * @type {List<UserList>}
	 */
	private List<UserList> list;
	
	/**
	 * Costruttore della classe che inizializza il campo dati list
	 * 
	 * @method +ListPack
	 * 
	 */
	public ListPack(List<UserList> l){
		list=l;
	}
	
	/**
	 * Ritorna la lista riferita da list
	 * 
	 * @method +getList
	 * @return {List<UserList>}
	 */
	public List<UserList> getList(){
		return list;
	}
	
	/**
	 * Imposta il valore del campo dati list
	 * 
	 * @method +setList
	 * @param {List<UserList>} l e' l'oggetto assegnato al campo dati list
	 * @return {void}
	 */
	public void setList(List<UserList> l){
		list=l;
	}
}
