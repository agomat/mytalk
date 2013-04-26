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
import java.util.List;

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
	
	//interroga il db e restituisce le liste dell'utente
	public List<ListName> getUserLists(User us){
		SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
		Session session=sessionFactory.openSession();
		Transaction t=session.beginTransaction();
		String username=us.getUsername();
		SQLQuery query=session.createSQLQuery("select * from ListNames where owner='"+username+"'");
		query=query.addEntity(ListName.class);
		List<ListName> list=(List<ListName>)query.list();
		t.commit();
		session.close();
		return list;
	}
	
	//get di una lista sapendo nome e proprietario
	public ListName getByNameOwner(ListName listObj){
		SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
		Session session=sessionFactory.openSession();
		Transaction t=session.beginTransaction();
		String name=listObj.getName();
		String owner=listObj.getOwner();
		SQLQuery query=session.createSQLQuery("select * from ListNames where owner='"+owner+"' && name='"+name+"'");
		query=query.addEntity(ListName.class);
		ListName list=(ListName)query.uniqueResult();
		t.commit();
		session.close();
		return list;
	}
}