/**
* Filename: OnlineUserDAO.java
* Package: com.mytalk.server.data.storage.dao
* Author: Nicolò Mazzucato
* Date: 2013-04-15
*
* Diary:
*
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-04-15 | NM        | [+] Creazione classe e definizione metodi  
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

public class OnlineUserDAO{

	public OnlineUserDAO(){}
	
	public void save(OnlineUser onlineObj){
		SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
		Session session=sessionFactory.openSession();
		Transaction t=session.beginTransaction();
		session.save(onlineObj);
		t.commit();
		session.close();
	}
	
	public void delete(OnlineUser onlineObj){
		SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
		Session session=sessionFactory.openSession();
		Transaction t=session.beginTransaction();
		session.delete(onlineObj);
		t.commit();
		session.close();
	}
	
	public void update(OnlineUser onlineObj){
		SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
		Session session=sessionFactory.openSession();
		Transaction t=session.beginTransaction();
		session.update(onlineObj);
		t.commit();
		session.close();
	}
	
	public OnlineUser get(String primaryKey){
		SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
		Session session=sessionFactory.openSession();
		Transaction t=session.beginTransaction();
		OnlineUser online=(OnlineUser)session.get(OnlineUser.class, primaryKey);
		t.commit();
		session.close();
		return online;
	}
	
	//ritorna l'ip dell'user con username passato
	public String getUserIp(String name){
		SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
		Session session=sessionFactory.openSession();
		Transaction t=session.beginTransaction();
		SQLQuery query=session.createSQLQuery("SELECT * FROM OnlineUsers WHERE username='"+name+"'");
		query=query.addEntity(OnlineUser.class);
		OnlineUser user=(OnlineUser)query.uniqueResult();
		String ip=null;
		if(user!=null){
			ip=user.getIp();
		}
		t.commit();
		session.close();
		return ip;
	}
	
	//ritorna gli user presenti anche nella tabella OnlineUsers
	public List<OnlineUser> getOnlineUsers(){
		SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
		Session session=sessionFactory.openSession();
		Transaction t=session.beginTransaction();
		SQLQuery query=session.createSQLQuery("SELECT * FROM OnlineUsers WHERE username IS NOT NULL");
		query=query.addEntity(User.class);
		List<OnlineUser> list=(List<OnlineUser>)query.list();
		t.commit();
		session.close();
		return list;
	}
}
