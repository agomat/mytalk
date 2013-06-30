/**
* Filename: OnlineUserDAO.java
* Package: com.mytalk.server.data.storage.dao
* Author: Nicolo' Mazzucato
* Date: 2013-04-15
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.2     | 2013-06-18 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.1	  |	2013-04-15 |    NM     | [+] Creazione classe e definizione metodi  
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Classe che offre operazioni di lettura e scrittura sul database riguardanti la tabella degli
* utenti autenticati
*/

package com.mytalk.server.data.storage.dao;

import org.hibernate.*;

import com.mytalk.server.data.model.OnlineUser;

import java.util.List;

public class OnlineUserDAO extends GenericDAO{
	
	/**
	 * Costruttore della classe con corpo vuoto poiche' non vi sono campi dati da inizializzare
	 */
	public OnlineUserDAO(){}
	
	/**
	 * Salva nel database il record corrispondente all'oggetto onlineObj passato
	 * 
	 * @method +save
	 * @param {OnlineUser} onlineObj e' l'oggetto utilizzato da Hibernate per il salvataggio nel database
	 * @return {void}
	 */
	public void save(OnlineUser onlineObj){
		Transaction t=session.beginTransaction();
		session.save(onlineObj);
		t.commit();
	}
	
	/**
	 * Elimina dal database il record avente chiave primaria uguale a quella di onlineObj
	 * 
	 * @method +delete
	 * @param {OnlineUser} onlineObj e' l'oggetto utilizzato da Hibernate per eliminare un record dal 
	 * database
	 * @return {void}
	 */
	public void delete(OnlineUser onlineObj){
		Transaction t=session.beginTransaction();
		session.delete(onlineObj);
		t.commit();
	}
	
	/**
	 * Aggiorna nel database il record avente chiave primaria uguale a quella di onlineObj usando
	 *  gli attributi in onlineObj
	 *  
	 *  @method +update
	 *  @param {OnlineUser} onlineObj e' l'oggetto utilizzato da Hibernate per fare l'aggiornamento del
	 *  database
	 *  @return {void}
	 */
	public void update(OnlineUser onlineObj){
		Transaction t=session.beginTransaction();
		session.update(onlineObj);
		t.commit();
	}
	
	/**
	 * Ritorna l'oggetto OnlineUser avente chiave primaria la stringa primaryKey; se non è presente
	 *  nel database tale record ritorna un oggetto OnlineUser=null
	 *  
	 *  @method +get
	 *  @param {String} primaryKey e' il valore utilizzato da Hibernate per ottenere una determinata 
	 * entita' dal database
	 *  @return {OnlineUser}
	 */
	public OnlineUser get(String primaryKey){
		Transaction t=session.beginTransaction();
		OnlineUser online=(OnlineUser)session.get(OnlineUser.class, primaryKey);
		t.commit();
		return online;
	}
	
	/**
	 * Ritorna l'attributo ip del record avente username=name; se non esiste tale record ritorna 
	 * null	
	 * 
	 * @method +getUserIp
	 * @param {String} name e' l'oggetto utilizzato per ottenere l'indirizzo ip dell'utente 
	 * identificato da tale oggetto
	 * @return {String}
	 */
	public String getUserIp(String name){
		Transaction t=session.beginTransaction();
		OnlineUser user=null;
		SQLQuery query=session.createSQLQuery("SELECT * FROM OnlineUsers WHERE username='"+name+"'");
		query=query.addEntity(OnlineUser.class);
		user=(OnlineUser)query.uniqueResult();
		String ip=null;
		if(user!=null){
			ip=user.getIp();
		}
		t.commit();
		return ip;
	}
	
	/**
	 * Ritorna l'attributo username dell'utente autenticato con ip passato
	 * 
	 * @method +getUsernameByIp
	 * @param {String} ip e' l'oggetto utilizzato per ottenere l'username corrispondente 
	 * all'indirizzo ip identificato da tale oggetto
	 * @return {String}
	 */
	public String getUsernameByIp(String ip){
		Transaction t=session.beginTransaction();
		OnlineUser user=null;
		SQLQuery query=session.createSQLQuery("SELECT * FROM OnlineUsers WHERE ip='"+ip+"'");
		query=query.addEntity(OnlineUser.class);
		user=(OnlineUser)query.uniqueResult();
		String username=null;
		if(user!=null){
			username=user.getUsername();
		}
		t.commit();
		return username;
	}
	
	/**
	 * Ritorna una lista di OnlineUser, ovvero ritorna tutti i record di utenti online aventi 
	 * un username diverso da null
	 * 
	 * @method +getOnlineUsers
	 * @return {List<OnlineUser>}
	 */
	public List<OnlineUser> getOnlineUsers(){
		Transaction t=session.beginTransaction();
		SQLQuery query=session.createSQLQuery("SELECT * FROM OnlineUsers WHERE username IS NOT NULL");
		query=query.addEntity(OnlineUser.class);
		List<OnlineUser> list=query.list();
		t.commit();
		return list;
	}
	
	/**
	 * Verifica se esiste un record avente attributo ip uguale alla stringa passata e in caso 
	 * positivo ritorna true, altrimenti ritorna false
	 * 
	 * @method +checkIpConnected
	 * @param {String} ip e' l'oggetto utilizzato per verificare la presenza online di un determinato
	 * indirizzo ip identificato da tale oggetto
	 * @return {boolean}
	 */
	public boolean checkIpConnected(String ip){
		boolean result=false;
		OnlineUser newUser=get(ip);
		if(newUser!=null){
			result=true;
		}
		return result;
	}
	
	/**
	 * Verifica se esiste un record avente attributo username uguale alla stringa passata e in 
	 * caso positivo ritorna true, altrimenti ritorna false
	 * 
	 * @method +checkUsernameConnected
	 * @param {String} username e' l'oggetto utilizzato per verificare la presenza online di un 
	 * determinato utente identificato da tale oggetto
	 * @return {boolean}
	 */
	public boolean checkUsernameConnected(String username){
		Transaction t=session.beginTransaction();
		boolean result=false;
		OnlineUser user=null;
		SQLQuery query=session.createSQLQuery("SELECT * FROM OnlineUsers WHERE username='"+username+"'");
		query=query.addEntity(OnlineUser.class);
		user=(OnlineUser)query.uniqueResult();
		if(user!=null){
			result=true;
		}
		t.commit();
		return result;
	}
}
