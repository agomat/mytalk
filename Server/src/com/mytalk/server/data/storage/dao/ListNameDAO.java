/**
* Filename: ListNameDAO.java
* Package: com.mytalk.server.data.storage.dao
* Author: Nicolo' Toso
* Date: 2013-04-11
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.2     | 2013-06-18 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.1	  |	2013-04-15 |    NT     | [+] Creato classe e metodi  
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Classe che offre operazioni di lettura e scrittura sul database riguardanti la tabella delle liste
*/

package com.mytalk.server.data.storage.dao;

import org.hibernate.*;

import com.mytalk.server.data.model.ListName;

import java.util.List;

public class ListNameDAO extends GenericDAO{
	
	/**
	 * Costruttore della classe con corpo vuoto poiche' non vi sono campi dati da inizializzare
	 */
	public ListNameDAO() {}
	
	/**
	 * Salva nel database il record corrispondente all'oggetto listObj passato
	 * 
	 * @method +save
	 * @param {ListName} listObj e' l'oggetto utilizzato da Hibernate per il salvataggio nel database
	 * @return {void}
	 */
	public void save(ListName listObj){
		Transaction t=session.beginTransaction();
		session.save(listObj);
		t.commit();
	}
	
	/**
	 * Aggiorna nel database il record avente chiave primaria uguale a quella di listObj usando
	 *  gli attributi in listObj
	 *  
	 *  @method +update
	 *  @param {ListName} listObj e' l'oggetto utilizzato da Hibernate per fare l'aggiornamento del
	 *  database
	 *  @return {void}
	 */
	public void update(ListName listObj){
		Transaction t=session.beginTransaction();
		ListName listEntity=(ListName)session.get(ListName.class, listObj.getId());
		if(listObj.getOwner()==null){
			listObj.setOwner(listEntity.getOwner());
		}
		if(listObj.getName()==null){
			listObj.setName(listEntity.getName());
		}
		session.update(listObj);
		t.commit();
	}
	
	/**
	 * Ritorna l'oggetto ListName avente chiave primaria l'intero primaryKey; se non è presente nel 
	 * database tale record ritorna un oggetto ListName=null
	 * 
	 * @method +get
	 * @param {int} primaryKey e' il valore utilizzato da Hibernate per ottenere una determinata 
	 * entita' dal database
	 * @return {Call}
	 */
	public ListName get(int primaryKey){
		Transaction t=session.beginTransaction();
		ListName list=(ListName)session.get(ListName.class, primaryKey);
		t.commit();
		return list;
	}
	
	/**
	 * Ritorna una lista di oggetti ListName aventi come attributo owner la stringa username, 
	 * ovvero ritorna le liste di un utente username
	 * 
	 * @method +getUserLists
	 * @param {String} username e' l'oggetto utilizzato per ottenere tutte le lista di un determinato
	 * utente identificato da tale oggetto
	 * @return {List<ListName>}
	 */
	public List<ListName> getUserLists(String username){
		Transaction t=session.beginTransaction();
		List<ListName> list=null;
		SQLQuery query=session.createSQLQuery("SELECT * FROM ListNames WHERE owner='"+username+"'");
		query=query.addEntity(ListName.class);
		list=(List<ListName>)query.list();
		t.commit();
		return list;
	}
	
	/**
	 * Ritorna l'oggetto ListName con attributo owner uguale a listObj.owner e attributo name 
	 * uguale a listObj.name; metodo di utilità
	 * 
	 * @method +getByNameOwner
	 * @param {ListName} listObj e' l'oggetto utilizzato per ottenere un oggetto lista corrispondente
	 * agli attributi dell'oggetto listObj
	 * @return {ListName}
	 */
	public ListName getByNameOwner(ListName listObj){
		Transaction t=session.beginTransaction();
		String name=listObj.getName();
		String owner=listObj.getOwner();
		ListName list=null;
		SQLQuery query=session.createSQLQuery("SELECT * FROM ListNames WHERE owner='"+owner+"' && BINARY name='"+name+"'");
		query=query.addEntity(ListName.class);
		list=(ListName)query.uniqueResult();
		t.commit();
		return list;
	}
	
	/**
	 * Elimina dal database il record avente chiave primaria uguale a quella di listObj
	 * 
	 * @method +delete
	 * @param {ListName} listObj e' l'oggetto utilizzato da Hibernate per eliminare un record dal 
	 * database
	 * @return {void}
	 */
	public void delete(ListName listObj){
		Transaction t=session.beginTransaction();
		session.delete(listObj);
		t.commit();
	}
}