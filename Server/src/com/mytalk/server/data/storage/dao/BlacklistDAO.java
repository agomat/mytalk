/**
* Filename: BlacklistDAO.java
* Package: com.mytalk.server.data.storage.dao
* Author: Michael Ferronato
* Date: 2013-04-16
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.2     | 2013-06-18 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.1	  |	2013-04-16 |    MF     | [+] Creazione classe e definizione metodi  
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Classe che offre operazioni di lettura e scrittura sul database riguardanti la tabella delle
* blacklist
*/

package com.mytalk.server.data.storage.dao;

import java.util.List;
import org.hibernate.*;

import com.mytalk.server.data.model.Blacklist;

public class BlacklistDAO extends GenericDAO{
	/**
	 * Costruttore della classe con corpo vuoto poiche' non contiene campi dati
	 * 
	 * @method +BlacklistDAO
	 */
	public BlacklistDAO(){}
	
	/**
	 * salva nel database il record corrispondente all'oggetto blacklistObj passato
	 * 
	 * @method +save
	 * @param {Blacklist} blacklistObj e' l'oggetto utilizzato da Hibernate per il salvataggio 
	 * nel database
	 * @return {void}
	 */
	public void save(Blacklist blacklistObj){
		Transaction t=session.beginTransaction();
		session.save(blacklistObj);
		t.commit();
	}
	
	/**
	 * Aggiorna nel database il record avente chiave primaria uguale a quella di blacklistObj 
	 * usando gli attributi in blacklistObj
	 * 
	 * @method +update
	 *  @param {Blacklist} blacklistObj e' l'oggetto che contiene i dati necessari per fare un 
	 * aggiornamento nel database
	 * @return {void}
	 */
	public void update(Blacklist blacklistObj){
		Transaction t=session.beginTransaction();
		Blacklist blacklistEntity=(Blacklist) session.get(Blacklist.class,blacklistObj);
		if(blacklistObj.getUsername()==null){
			blacklistObj.setUsername(blacklistEntity.getUsername());
		}
		session.update(blacklistObj);
		t.commit();
	}
	
	/**
	 * Ritorna l'oggetto Blacklist avente chiave primaria la coppia (primaryKeyOwner, primaryKeyUser);
	 *  se non è presente nel database tale record ritorna un oggetto 
	 * Blacklist=null
	 * 
	 * @method +get
	 * @param {String} primaryKeyOwner e' l'oggetto che contiene i dati del proprietario della
	 * blacklist
	 * @param {String} primaryKeyUser e' l'oggetto che contiene i dati dell'utente da mettere 
	 * nella blacklist
	 * @return {Blacklist}
	 */
	public Blacklist get(String primaryKeyOwner, String primaryKeyUser ){
		Transaction t=session.beginTransaction();
		Blacklist blacklistEntity = new Blacklist(primaryKeyOwner, primaryKeyUser);
		Blacklist blacklistObj=(Blacklist) session.get(Blacklist.class,blacklistEntity);
		t.commit();
		return blacklistObj;
	}
	
	/**
	 * Ritorna una lista di oggetti Blacklist in cui l'attributo owner è uguale a primaryKey, 
	 * ovvero ritorna gli utenti in blacklist di un certo utente
	 * 
	 * @method +getUserBlacklist
	 * @param {String} primaryKey e' l'oggetto che contiene i dati relativi all'utente da cercare
	 * @return {List<Blacklist>}
	 */
	public List<Blacklist> getUserBlacklist(String primaryKey){
		Transaction t=session.beginTransaction();
		SQLQuery query=session.createSQLQuery("SELECT * FROM Blacklists WHERE owner='"+primaryKey+"'");
		List<Blacklist> ownerBlacklist=null;
		query=query.addEntity(Blacklist.class);
		ownerBlacklist=query.list();		
		t.commit();
		return ownerBlacklist;
	}
	
	/**
	 * Elimina dal database il record avente chiave primaria uguale a quella di blacklistObj
	 * 
	 * @method +delete
	 * @param {Blacklist} blacklistObj e' l'oggetto che contiene i dati necessari per fare 
	 * un'eliminazione dal database
	 * @return {void}
	 */
	public void delete(Blacklist blacklistObj){
		Transaction t=session.beginTransaction();
		session.delete(blacklistObj);
		t.commit();
	}
}