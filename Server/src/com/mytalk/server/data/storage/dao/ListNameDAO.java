/**
* Filename: ListNameDAO.java
* Package: com.mytalk.server.data.storage.dao
* Author: Nicolò Toso
* Date: 2013-04-11
*
* Diary:
*
* Version |
Date
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-04-15 | NT        | [+] Creato classe e metodi  
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

package com.mytalk.server.data.storage.dao;

import com.mytalk.server.data.model.*;
import org.hibernate.*;
import com.mytalk.server.data.persistence.HibernateUtil;

public class ListNameDAO{
	
	public ListNameDAO() {}
	
	public void save(ListName listObj){
		SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
		Session session=sessionFactory.openSession();
		Transaction t=session.beginTransaction();
		session.save(listObj);
		t.commit();
		session.close();
	}
	
	public void delete(ListName listObj){
		SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
		Session session=sessionFactory.openSession();
		Transaction t=session.beginTransaction();
		session.delete(listObj);
		t.commit();
		session.close();
	}
	
	public void update(ListName listObj){
		SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
		Session session=sessionFactory.openSession();
		Transaction t=session.beginTransaction();
		session.update(listObj);
		t.commit();
		session.close();
	}
	
	public ListName get(String primaryKey){
		SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
		Session session=sessionFactory.openSession();
		Transaction t=session.beginTransaction();
		ListName list=(ListName)session.get(ListName.class, primaryKey);
		t.commit();
		session.close();
		return list;
	}
	
	//interroga il db e restituisce le liste degli utenti
	public List<ListName> returnListUser(String username){
		List<ListName> x=session.createSQLQuery("select * from ListNames where owner=:owner").addEntity(ListName.class)
				.setParameter("owner", username)
				.list();
		t.commit();
		session.close();		
		return x;
	}
	
	// verifica che non sia già presente la lista per quell'user aggiunge un record alla tabella List con nome e owner
	public void addRecordToListName(String name, String owner){
		List<ListName> l=session.createSQLQuery("select * from ListNames where owner=:owner && name=:name")
				.addEntity(ListName.class)
				.setParameter("owner", owner)
				.setParameter("name", name)
				.list();
		if(l.isEmpty()){
			ListName ln=new ListName(name,owner);
			session.save(ln);
		}	
		t.commit();
		session.close();	
	}
	
	// verifica che sia presente la lista per quell'user rimuove un record dalla tabella List che corrisponde a nome user e owner
	public void deleteRecordFromListName(String name,String owner){
		List<ListName> l=session.createSQLQuery("select * from ListNames where owner=:owner && name=:name")
				.addEntity(ListName.class)
				.setParameter("owner", owner)
				.setParameter("name", name)
				.list();
		if(!(l.isEmpty())){
			session.delete(l.get(0));	
		}
		t.commit();
		session.close();
	}
	
	
	// verifica che sia già presente nella lista quell'user e prende l'id della lista dalla tabella List
	public Integer getIdList(String name, String owner){
		Integer id=null;
		List<ListName> l=session.createSQLQuery("select * from ListNames where owner=:owner && name=:name")
				.addEntity(ListName.class)
				.setParameter("owner", owner)
				.setParameter("name", name)
				.list();
		if(!(l.isEmpty())){
			id=l.get(0).getId();
		}	
		t.commit();
		session.close();	
		return id;
	}
}
