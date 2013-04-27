/**
* Filename: UserDao.java
* Package: com.mytalk.server.data.storage.dao
* Author: Michael Ferronato
* Date: 2013-04-12
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-04-15 | MF        | [+] Creazione classe e definizione metodi  
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

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class UserDAO {
	public UserDAO(){}
	
	//Aggiunge un oggetto User ricevuto in input
	public void save(User userObj){
		SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
		Session session=sessionFactory.openSession();
		Transaction t=session.beginTransaction();
		session.save(userObj);
		t.commit();
		session.close();
	}
		
	public void update(User userObj){
		SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
		Session session=sessionFactory.openSession();
		Transaction t=session.beginTransaction();
		User userEntity=(User) session.get(User.class,userObj.getUsername());
		if(userObj.getPassword().contains("null")){
			userObj.setPassword(userEntity.getPassword());
		}
		if(userObj.getEmail().contains("null")){
			userObj.setEmail(userEntity.getEmail());
		}
		session.update(userObj);
		t.commit();
		session.close();
	}	
	
	//Ottenere un oggetto di tipo Blacklist
	public User get(String primaryKey){
		SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
		Session session=sessionFactory.openSession();
		Transaction t=session.beginTransaction();
		User userEntity=(User) session.get(User.class,primaryKey);
		t.commit();
		session.close();
		return userEntity;
	}
	
	//Restituire tutti gli oggetti di tipo User
	public List<User> getAllUsers(){
		SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
		Session session=sessionFactory.openSession();
		Transaction t=session.beginTransaction();
		SQLQuery query=session.createSQLQuery("SELECT * FROM Users");
		query=query.addEntity(User.class);
		List<User> listUsers=query.list();
		t.commit();
		session.close();
		return listUsers;
	}
	
	//Cancella un oggetto Blacklist passato in input
	public void delete(User userObj){
		SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
		Session session=sessionFactory.openSession();
		Transaction t=session.beginTransaction();
		session.delete(userObj);
		t.commit();
		session.close();
	}
}