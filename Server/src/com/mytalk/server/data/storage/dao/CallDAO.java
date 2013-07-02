/**
* Filename: CallDAO.java
* Package: com.mytalk.server.data.storage.dao
* Author: Nicolo' Mazzucato
* Date: 2013-04-15
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.2     | 2013-06-18 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.1	  |	2013-04-16 |    NM     | [+] Creazione classe e definizione metodi  
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Classe che offre operazioni di lettura e scrittura sul database riguardanti la tabella delle
* chiamate
*/

package com.mytalk.server.data.storage.dao;

import java.util.List;
import org.hibernate.*;

import com.mytalk.server.data.model.Call;

public class CallDAO extends GenericDAO{

	/**
	 * Costruttore della classe con corpo vuoto poiche' non vi sono campi da inizializzare
	 * 
	 * @method +CallDAO
	 */
	public CallDAO(){}
	
	/**
	 * Salva nel database il record corrispondente all'oggetto callObj passato
	 * 
	 * @method +save
	 * @param {Call} callObj e' l'oggetto utilizzato da Hibernate per il salvataggio nel database
	 * @return {void}
	 */
	public void save(Call callObj){
		Transaction t=session.beginTransaction();
		session.save(callObj);
		t.commit();
	}
	
	/**
	 * Aggiorna nel database il record avente chiave primaria uguale a quella di callObj usando
	 *  gli attributi in callObj
	 *  
	 *  @method +update
	 *  @param {Call} callObj e' l'oggetto utilizzato da Hibernate per fare l'aggiornamento del
	 *  database
	 *  @return {void}
	 */
	public void update(Call callObj){
		Transaction t=session.beginTransaction();
		Call callEntity=(Call) session.get(Call.class, callObj.getId());
		if(callObj.getCaller()==null){
			callObj.setCaller(callEntity.getCaller());
		}
		if(callObj.getReceiver()==null){
			callObj.setReceiver(callEntity.getReceiver());
		}
		if(callObj.getByteReceived()==0){
			callObj.setByteReceived(callEntity.getByteReceived());
		}
		if(callObj.getByteSent()==0){
			callObj.setByteSent(callEntity.getByteSent());
		}
		if(callObj.getDuration()==0){
			callObj.setDuration(callEntity.getDuration());
		}
		if(callObj.getStartdate()==null){
			callObj.setStartdate(callEntity.getStartdate());
		}
		session.update(callObj);
		t.commit();
		session.close();
	}
	
	/**
	 * Ritorna l'oggetto Call avente chiave primaria l'intero primaryKey; se non è presente nel 
	 * database tale record ritorna un oggetto Call=null
	 * 
	 * @method +get
	 * @param {int} primaryKey e' il valore utilizzato da Hibernate per ottenere una determinata 
	 * entita' dal database
	 * @return {Call}
	 */
	public Call get(int primaryKey){
		Transaction t=session.beginTransaction();
		Call callObj=(Call) session.get(Call.class,primaryKey);
		t.commit();
		return callObj;
	}
	
	/**
	 * Ritorna una lista di oggetti Call corrispondenti a record del database aventi come 
	 * attributo caller o receiver la stringa primaryKey, ovvero ritorna la lista di chiamate in 
	 * cui l'utente primaryKey figura come caller o receiver
	 * 
	 * @method +getAllUserCalls
	 * @param {String} primaryKey e' l'oggetto utilizzato per ottenere tutte le chiamate di un 
	 * certo utente identificato da tale oggetto
	 * @return {List<Call>}
	 */
	public List<Call> getAllUserCalls(String primaryKey){
		Transaction t=session.beginTransaction();
		List<Call> listOfUserCalls=null;
		SQLQuery query=session.createSQLQuery("SELECT * FROM Calls WHERE caller='"+primaryKey+"' OR receiver='"+primaryKey+"'");
		query=query.addEntity(Call.class);
		listOfUserCalls=query.list();
		t.commit();
		return listOfUserCalls;
	}
		
	/**
	 * Elimina dal database il record avente chiave primaria uguale a quella di callObj	
	 * 
	 * @method +delete
	 * @param {Call} callObj e' l'oggetto utilizzato da Hibernate per eliminare un record dal 
	 * database
	 * @return {void}
	 */
	public void delete(Call callObj){
		Transaction t=session.beginTransaction();
		session.delete(callObj);
		t.commit();
	}
}
