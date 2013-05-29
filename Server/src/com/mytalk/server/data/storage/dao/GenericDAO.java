/**
* Filename: GenericDAO.java
* Package: com.mytalk.server.data.storage
* Author: Nicolò Mazzucato
* Date: 2013-04-12
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-04-12 | NM        | [+] Creazione classe    
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

package com.mytalk.server.data.storage.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.mytalk.server.data.storage.HibernateUtil;

public abstract class GenericDAO {
	protected static Session session;
	public GenericDAO(){
		openSession();
	}
	public static void openSession(){
		SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
		if(session==null){
			session=sessionFactory.openSession();
		}else if(!session.isOpen()){
			session=sessionFactory.openSession();
		}
	}
	public static void closeSession(){
		if(session!=null){
			if(session.isOpen()){
				session.close();
			}
		}
	}
}
