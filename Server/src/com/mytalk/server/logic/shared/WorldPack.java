/**
* Filename: WorldPack.java
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
* Classe che rappresenta l'insieme di tutte le informazioni riguardanti gli utenti, ossia la 
* lista degli utenti, le liste personali, la blacklist e i dati personali di un utente
*/

package com.mytalk.server.logic.shared;

import com.mytalk.server.logic.shared.model_client.WorldList;
import com.mytalk.server.logic.shared.model_client.WorldPersonalData;

public class WorldPack extends Information{
	/**
	 * Oggetto che rappresenta l'insieme delle informazioni sulle liste
	 * 
	 * @property -worldList
	 * @type {WorldList}
	 */
	private WorldList worldList;
	
	/**
	 * Oggetto che rappresenta l'insieme delle informazioni riguardati i dati personali 
	 * degli utenti
	 * 
	 * @property -worldPersonalData
	 * @type {WorldPersonalData}
	 */
	private WorldPersonalData worldPersonalData;
	
	/**
	 * Costruttore della classe che inizializza i campi dati worldList e worldPersonalData
	 * 
	 * @method +WorldPack
	 * @param {WorldList} wpl e' l'oggetto utilizzato per inizializzare il campo dati worldList
	 * @param {WorldPersonalData} wppd e l'oggetto utilizzato per inizializzare il campo dati
	 * worldPersonalData
	 */
	public WorldPack(WorldList wpl,WorldPersonalData wppd){
		worldList=wpl;
		worldPersonalData=wppd;
	}
	
	/**
	 * Imposta il campo dati nell'oggetto worldList
	 * 
	 * @method +setWorldList
	 * @param {WorldList} wpl e' l'oggetto assegnato al campo dati worldList
	 * @return {void}
	 */
	public void setWorldList(WorldList wpl){
		worldList=wpl;
	}
	
	/**
	 * Ritorna il valore dell'oggetto worldList
	 * 
	 * @method +getWorldList
	 * @return {WorldList}
	 */
	public WorldList getWorldList(){
		return worldList;
	}
	
	/**
	 * Imposta il campo dati nell'oggetto worldPersonalData
	 * 
	 * @method +setWorldPersonalData
	 * @param {WorldList} wppd e' l'oggetto assegnato al campo dati worldPersonalData
	 * @return {void}
	 */
	public void setWorldPersonalData(WorldPersonalData wppd){
		worldPersonalData=wppd;
	}
	
	/**
	 * Ritorna il valore dell'oggetto worldPersonalData
	 * 
	 * @method +getWorldPersonalData
	 * @return {WorldPersonalData}
	 */
	public WorldPersonalData getWorldPersonalData(){
		return worldPersonalData;
	}
}
