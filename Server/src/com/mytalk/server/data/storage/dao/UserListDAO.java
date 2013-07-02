/**
* Filename: UserListDAO.java
* Package: com.mytalk.server.data.storage.dao
* Author: Nicolo' Toso
* Date: 2013-04-11
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.2     | 2013-06-18 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.1	  |	2013-04-15 | NT        | [+] Creato classe e metodi  
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Classe che offre operazioni di lettura e scrittura sul database riguardanti la tabella degli
* utenti presenti nelle liste
*/

package com.mytalk.server.data.storage.dao;

import org.hibernate.*;

import com.mytalk.server.data.model.UserList;

import java.util.List;


public class UserListDAO extends GenericDAO{
	/**
	 * Costruttore della classe con corpo vuoto poiche' non vi sono campi dati da inizializzare
	 * 
	 * @method +UserListDAO
	 */
	public UserListDAO(){}
	
	/**
	 * Salva nel database il record corrispondente all'oggetto userlistObj passato
	 * 
	 * @method +save
	 * @param {UserList} userlistObj e' l'oggetto utilizzato da Hibernate per il salvataggio 
	 * nel database
	 * @return {void}
	 */
	public void save(UserList userlistObj){
		Transaction t=session.beginTransaction();
		session.save(userlistObj);
		t.commit();
	}
	
	/**
	 * Elimina dal database il record avente chiave primaria uguale a quella di userlistObj
	 * 
	 * @method +delete
	 * @param {UserList} userlistObj e' l'oggetto utilizzato da Hibernate per eliminare un
	 * record dal database
	 * @return {void}
	 */
	public void delete(UserList userlistObj){
		Transaction t=session.beginTransaction();
		session.delete(userlistObj);
		t.commit();
	}
	
	/**
	 * Aggiorna nel database il record avente chiave primaria uguale a quella di userlistObj usando
	 *  gli attributi in userlistObj
	 *  
	 *  @method +update
	 *  @param {UserList} userlistObj e' l'oggetto utilizzato da Hibernate per fare l'aggiornamento
	 *   del database
	 *  @return {void}
	 */
	public void update(UserList userlistObj){
		Transaction t=session.beginTransaction();
		session.update(userlistObj);
		t.commit();
	}

	/**
	 * Ritorna l'oggetto UserList avente chiave primaria la coppia (primaryKeyId, primaryKeyUser); 
	 * se non è presente nel database tale record ritorna un oggetto UserList=null
	 * 
	 * @method +get
	 * @param {int} primaryKeyId e' il valore utilizzato assieme a primaryKeyUser per ottenere 
	 * un determinato oggetto UserList 
	 * @param {String} primaryKeyUser e' l'oggetto utilizzato assieme a primaryKeyId per ottenere 
	 * un determinato oggetto UserList
	 * @return {UserList}
	 */
	public UserList get(int primaryKeyId,String primaryKeyUser){
		Transaction t=session.beginTransaction();
		UserList user=new UserList(primaryKeyId,primaryKeyUser);
		UserList dbUser=(UserList)session.get(UserList.class, user);
		t.commit();
		return dbUser;
	}
	
	/**
	 * Ritorna una lista di oggetti UserList aventi id uguale all'intero passato, ovvero ritorna 
	 * la lista degli utenti presenti nella lista con id passato
	 * 
	 * @method +getUsersInList
	 * @param {int} id e' il valore utilizzato per ottenere gli utenti appartenenti alla lista
	 * identificata da tale id
	 * @return {List<UserList>}
	 */
	public List<UserList> getUsersInList(int id){
		Transaction t=session.beginTransaction();
		SQLQuery query=session.createSQLQuery("SELECT * FROM UserLists WHERE idList='"+id+"'");
		query=query.addEntity(UserList.class);
		List<UserList> list=(List<UserList>)query.list();
		t.commit();
		return list;
	}
}
