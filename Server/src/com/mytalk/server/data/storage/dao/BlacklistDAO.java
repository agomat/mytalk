/**
* Filename: BlacklistDAO.java
* Package: com.mytalk.server.data.storage.dao
* Author: Michael Ferronato
* Date: 2013-04-16
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-04-16 | MF        | [+] Creazione classe e definizione metodi  
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

package com.mytalk.server.data.storage.dao;

import com.mytalk.server.data.model.*;
import java.util.*;
import org.hibernate.*;

public class BlacklistDAO extends GenericDAO{
	
	public BlacklistDAO(){}
	
	//Aggiunge un oggetto Blacklist ricevuto in input
	public void save(Blacklist blacklistObj){
		Transaction t=session.beginTransaction();
		session.save(blacklistObj);
		t.commit();
	}
	
	//Aggiorna un oggetto Blacklist passato in input
	public void update(Blacklist blacklistObj){
		Transaction t=session.beginTransaction();
		Blacklist blacklistEntity=this.get(blacklistObj.getOwner());
		if(blacklistObj.getUsername()==null){
			blacklistObj.setUsername(blacklistEntity.getUsername());
		}
		session.update(blacklistObj);
		t.commit();
	}
	
	//Ottenere un oggetto di tipo Blacklist
	public Blacklist get(String primaryKey){
		Transaction t=session.beginTransaction();
		Blacklist blacklistObj=(Blacklist) session.get(Blacklist.class,primaryKey);
		t.commit();
		return blacklistObj;
	}
	
	public List<Blacklist> getUserBlacklist(String primaryKey){
		Transaction t=session.beginTransaction();
		SQLQuery query=session.createSQLQuery("SELECT * FROM Blacklists WHERE owner='"+primaryKey+"'");
		List<Blacklist> ownerBlacklist=null;
		query=query.addEntity(Blacklist.class);
		ownerBlacklist=query.list();		
		t.commit();
		return ownerBlacklist;
	}
	
	//Cancella un oggetto Blacklist passato in input
	public void delete(Blacklist blacklistObj){
		Transaction t=session.beginTransaction();
		session.delete(blacklistObj);
		t.commit();
	}
}