/**
* Filename: WorldPersonalData.java
* Package: com.mytalk.server.logic.shared.model_client
* Author: Michael Ferronato
* Date: 2013-05-02
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.2     | 2013-06-24 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.1	  |	2013-05-02 |   MF      | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Classe che rappresenta i dati personali di un utente
*/


package com.mytalk.server.logic.shared.model_client;



public class WorldPersonalData {
	/**
	 * Oggetto che rappresenta le informazioni personali dell'utente
	 * 
	 * @property -pd
	 * @type {PersonalData}
	 */
	private PersonalData pd;
	/**
	 * Costruttore della classe che inizializza il campo dati pd
	 * 
	 * @method +WorldPersonalData
	 */
	public WorldPersonalData(PersonalData pdEntity){
		pd=pdEntity;
	}
	/**
	 * Ritorna il campo dati pd
	 * 
	 * @method +getPersonalData
	 * @return {pd}
	 */
	public PersonalData getPersonalData(){
		return pd;
	}
	/**
	 * Imposta il campo dati pd
	 * 
	 * @method +setPersonalData
	 * @param {PersonalData} personalData e' l'oggetto assegnato al campo dati pd
	 * @return {void}
	 */
	public void setPersonalData(PersonalData personalData){
		pd=personalData;
	}
}
