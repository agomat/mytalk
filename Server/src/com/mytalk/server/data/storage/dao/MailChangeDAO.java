/**
* Filename: MailChangeDAO.java
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
import org.hibernate.*;
import com.mytalk.server.data.persistence.HibernateUtil;

public class MailChangeDAO{
	
	public MailChangeDAO(){}
	
	public void save(MailChange mailObj){
		SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
		Session session=sessionFactory.openSession();
		Transaction t=session.beginTransaction();
		session.save(mailObj);
		t.commit();
		session.close();
	}
	
	public void delete(MailChange mailObj){
		SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
		Session session=sessionFactory.openSession();
		Transaction t=session.beginTransaction();
		session.delete(mailObj);
		t.commit();
		session.close();
	}
	
	public void update(MailChange mailObj){
		SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
		Session session=sessionFactory.openSession();
		Transaction t=session.beginTransaction();
		session.update(mailObj);
		t.commit();
		session.close();
	}
	
	public MailChange get(String primaryKey){
		SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
		Session session=sessionFactory.openSession();
		Transaction t=session.beginTransaction();
		MailChange mail=(MailChange)session.get(MailChange.class, primaryKey);
		t.commit();
		session.close();
		return mail;
	}
}
