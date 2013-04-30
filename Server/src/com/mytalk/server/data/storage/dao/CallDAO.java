/**
* Filename: CallDAO.java
* Package: com.mytalk.server.data.storage.dao
* Author: Nicolò Mazzucato
* Date: 2013-04-15
*
* Diary:
*
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-04-16 | NM        | [+] Creazione classe e definizione metodi  
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

package com.mytalk.server.data.storage.dao;

import com.mytalk.server.data.model.*;

import java.util.*;
import java.sql.Timestamp;
import org.hibernate.*;

import com.mytalk.server.data.persistence.HibernateUtil;

public class CallDAO extends GenericDAO{

	public CallDAO(){}
	
	//Aggiunge un oggetto Call ricevuto in input
	public void save(Call callObj){
		Transaction t=session.beginTransaction();
		session.save(callObj);
		t.commit();
	}
	
	//Aggiorna un oggetto Call passato in input (SERVE?????)
	public void update(Call callObj){
		Transaction t=session.beginTransaction();
		Call callEntity=this.get(callObj.getId());
		if(callObj.getCaller().equals("null")){
			callObj.setCaller(callEntity.getCaller());
		}
		if(callObj.getReceiver().equals("null")){
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
	
	//Ottenere un oggetto di tipo Call
	public Call get(int primaryKey){
		Transaction t=session.beginTransaction();
		Call callObj=(Call) session.get(Call.class,primaryKey);
		t.commit();
		return callObj;
	}
		
	public List<Call> getAllUserCalls(String primaryKey){
		Transaction t=session.beginTransaction();
		List<Call> listOfUserCalls=null;
		SQLQuery query=session.createSQLQuery("SELECT * FROM Calls WHERE caller='"+primaryKey+"' OR receiver='"+primaryKey+"'");
		query=query.addEntity(Call.class);
		listOfUserCalls=query.list();
		t.commit();
		return listOfUserCalls;
	}
		
	//Cancella un oggetto Call passato in input
	public void delete(Call callObj){
		Transaction t=session.beginTransaction();
		session.delete(callObj);
		t.commit();
	}
}
