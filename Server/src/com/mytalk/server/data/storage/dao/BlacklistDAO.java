/**
* Filename: BlacklistDAO.java
* Package: com.mytalk.server.data.storage.dao
* Author: Michael Ferronato
* Date: 2013-04-16
*
* Diary:
*
* Version |
Date
| Version | Date       | Developer | Changes
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
import com.mytalk.server.data.persistence.HibernateUtil;

import java.util.*;
import java.io.Serializable;

import org.hibernate.*;

import com.mytalk.server.data.persistence.HibernateUtil;

public class BlacklistDAO {
	
	public BlacklistDAO(){}
	
	//Aggiunge un oggetto Blacklist ricevuto in input
	public void save(Blacklist blacklistObj){
		SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
		Session session=sessionFactory.openSession();
		Transaction t=session.beginTransaction();
		session.save(blacklistObj);
		t.commit();
		session.close();
	}
	
	//Aggiorna un oggetto Blacklist passato in input
	public void update(Blacklist blacklistObj){
		SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
		Session session=sessionFactory.openSession();
		Transaction t=session.beginTransaction();
		Blacklist blacklistEntity=(Blacklist)session.get(Blacklist.class, blacklistObj.getOwner());
		if(blacklistObj.getUsername()==null){
			blacklistObj.setUsername(blacklistEntity.getUsername());
		}
		session.update(blacklistObj);
		t.commit();
		session.close();
	}
	
	//Ottenere un oggetto di tipo Blacklist
	public Blacklist get(String primaryKey){
		SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
		Session session=sessionFactory.openSession();
		Transaction t=session.beginTransaction();
		Blacklist blacklistObj=(Blacklist) session.get(Blacklist.class,primaryKey);
		t.commit();
		session.close();
		return blacklistObj;
	}
	
	public List<Blacklist> getUsernameByOwner(String primaryKey){
		SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
		Session session=sessionFactory.openSession();
		Transaction t=session.beginTransaction();

		SQLQuery query=session.createSQLQuery("SELECT * FROM Blacklists WHERE owner='"+primaryKey+"'");
		query=query.addEntity(Blacklist.class);

		List<Blacklist> ownerBlacklist=query.list();		
		t.commit();
		session.close();
		return ownerBlacklist;
	}
	
	//Cancella un oggetto Blacklist passato in input
	public void delete(Blacklist blacklistObj){
		SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
		Session session=sessionFactory.openSession();
		Transaction t=session.beginTransaction();
		session.delete(blacklistObj);
		t.commit();
		session.close();
	}
}