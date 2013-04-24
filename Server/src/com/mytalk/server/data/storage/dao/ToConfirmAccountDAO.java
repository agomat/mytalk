/**
* Filename: ToConfirmAccountDAO.java
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
import org.hibernate.*;
import java.util.*;

public class ToConfirmAccountDAO{
	public ToConfirmAccountDAO(){}
	//save update delete get
	
	public void save(ToConfirmAccount toConfirmAccountObj){
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		session.save(toConfirmAccountObj);
		t.commit();
		session.close();
	}
	
	public void update(ToConfirmAccount toConfirmAccountObj){
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		session.update(toConfirmAccountObj);
		t.commit();
		session.close();
	}
	
	public void delete(ToConfirmAccount toConfirmAccountObj){
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		session.delete(toConfirmAccountObj);
		t.commit();
		session.close();
	}
	
	public ToConfirmAccount get(String primaryKey){
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		ToConfirmAccount account = session.get(primaryKey);
		t.commit();
		session.close();
	}
	
	
	
	// elimina tutti i record della tabella ToConfirmAccount
	public void deleteUnconfirmedAccount(){
		List<ToConfirmAccount> ltca= session.createSQLQuery("SELECT * FROM ToConfirmAccounts").addEntity(ToConfirmAccount.class).list();
		for(int i=0;i<ltca.size();i++){
			session.delete(ltca.get(i));
		}
		t.commit();
		session.close();
	}
	
}
