/**
* Filename: UserDao.java
* Package: com.mytalk.server.data.storage.dao
* Author: Michael Ferronato
* Date: 2013-04-12
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.2     | 2013-06-18 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.1	  |	2013-04-15 |    MF     | [+] Creazione classe e definizione metodi  
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Classe che offre operazioni di lettura e scrittura sul database riguardanti la tabella degli
* utenti registrati nel sistema
*/

package com.mytalk.server.data.storage.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Transaction;

import com.mytalk.server.data.model.User;

public class UserDAO extends GenericDAO{
	/**
	 * Costruttore della classe con corpo vuoto poiche' non vi sono campi dati da inizializzare
	 * 
	 * @method +UserDAO
	 */
	public UserDAO(){}
	
	/**
	 * Salva nel database il record corrispondente all'oggetto userObj passato
	 * 
	 * @method +save
	 * @param {User} userObj e' l'oggetto utilizzato da Hibernate per il salvataggio nel database
	 * @return {void}
	 */
	public void save(User userObj){
		Transaction t=session.beginTransaction();
		session.save(userObj);
		t.commit();
	}
	
	/**
	 * Aggiorna nel database il record avente chiave primaria uguale a quella di userObj usando
	 *  gli attributi in userObj
	 *  
	 *  @method +update
	 *  @param {User} userObj e' l'oggetto utilizzato da Hibernate per fare l'aggiornamento del
	 *  database
	 *  @return {void}
	 */
	public void update(User userObj){
		Transaction t=session.beginTransaction();
		User userEntity=(User) session.get(User.class, userObj.getUsername());
		if(userObj.getName()==null){
			userObj.setName(userEntity.getName());
		}
		if(userObj.getSurname()==null){
			userObj.setSurname(userEntity.getSurname());
		}
		if(userObj.getPassword()==null){
			userObj.setPassword(userEntity.getPassword());
		}
		if(userObj.getEmail()==null){
			userObj.setEmail(userEntity.getEmail());
		}
		if(userObj.getEmailHash()==null){
			userObj.setEmailHash(userEntity.getEmailHash());
		}
		session.update(userObj);
		t.commit();
	}	
	
	/**
	 * Ritorna l'oggetto User avente chiave primaria la stringa primaryKey; se non è presente
	 *  nel database tale record ritorna un oggetto User=null
	 *  
	 *  @method +get
	 *  @param {String} primaryKey e' il valore utilizzato da Hibernate per ottenere una determinata 
	 * entita' dal database
	 *  @return {User}
	 */
	public User get(String primaryKey){
		Transaction t=session.beginTransaction();
		User userEntity=(User) session.get(User.class,primaryKey);
		t.commit();
		return userEntity;
	}
	
	/**
	 * Ritorna una lista contenente tutti gli oggetti User corrispondenti a record nel database, 
	 * ovvero ritorna tutti gli utenti registrati
	 * 
	 * @method +getAllUsers
	 * @return {List<User>}
	 */
	public List<User> getAllUsers(){
		Transaction t=session.beginTransaction();
		List<User> listUsers=null;
		SQLQuery query=session.createSQLQuery("SELECT * FROM Users");
		query=query.addEntity(User.class);
		listUsers=query.list();
		t.commit();
		return listUsers;
	}
	
	/**
	 * Ritorna una lista conentente gli oggetti User corrispondenti agli utenti che non risultano 
	 * al momento autenticati, cioè il cui username non compare in nessun record della tabella 
	 * degli utenti online
	 * 
	 * @method +getOfflineUsers
	 * @return {List<User>}
	 */
	public List<User> getOfflineUsers(){
		Transaction t=session.beginTransaction();
		List<User> listUsers=null;
		SQLQuery query=session.createSQLQuery("SELECT * FROM Users WHERE username NOT IN (SELECT username FROM OnlineUsers WHERE username IS NOT NULL)");
		query=query.addEntity(User.class);
		listUsers=query.list();
		t.commit();
		return listUsers;
	}
	
	/**
	 * Elimina dal database il record avente chiave primaria uguale a quella di userObj
	 * 
	 * @method +delete
	 * @param {User} userObj e' l'oggetto utilizzato da Hibernate per eliminare un record dal 
	 * database
	 * @return {void}
	 */
	public void delete(User userObj){
		Transaction t=session.beginTransaction();
		session.delete(userObj);
		t.commit();
	}
	
	/**
	 * Ritorna l'oggetto User avente attributo id uguale all'intero passato; metodo di utilità
	 * 
	 * @method +getById
	 * @param {int} id e' il valore utilizzato per ottenere l'utente con il corrispondente id
	 * @return {User}
	 */
	public User getById(int id){
		Transaction t=session.beginTransaction();
		SQLQuery query=session.createSQLQuery("SELECT * FROM Users WHERE id='"+id+"'");
		query=query.addEntity(User.class);
		User user=(User)query.uniqueResult();
		t.commit();
		return user;
	}
}