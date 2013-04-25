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

public class CallDAO{

	public CallDAO(){}
	
	//Aggiunge un oggetto Call ricevuto in input
		public void save(Call callObj){
			SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
			Session session=sessionFactory.openSession();
			Transaction t=session.beginTransaction();
			session.save(callObj);
			t.commit();
			session.close();
		}
		
		//Aggiorna un oggetto Call passato in input (SERVE?????)
		public void update(Call callObj){
			SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
			Session session=sessionFactory.openSession();
			Transaction t=session.beginTransaction();
			session.update(callObj);
			t.commit();
			session.close();
		}
		
		//Ottenere un oggetto di tipo Call
		public Call get(String primaryKey){
			SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
			Session session=sessionFactory.openSession();
			Transaction t=session.beginTransaction();
			Call callObj=(Call) session.get(Call.class,primaryKey);
			t.commit();
			session.close();
			return callObj;
		}
		
		public List<Call> getAllUserCalls(String u){
			SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
			Session session=sessionFactory.openSession();
			Transaction t=session.beginTransaction();
			SQLQuery query=session.createSQLQuery("SELECT * FROM Calls WHERE caller='"+u+"' OR receiver='"+u+"'");
			query=query.addEntity(Call.class);
			List<Call> listOfUserCalls=query.list();
			t.commit();
			session.close();
			return listOfUserCalls;
		}
		
		//Cancella un oggetto Call passato in input
		public void delete(Call callObj){
			SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
			Session session=sessionFactory.openSession();
			Transaction t=session.beginTransaction();
			session.delete(callObj);
			t.commit();
			session.close();
		}
}
