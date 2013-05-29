/**
* Filename: HibernateUtil.java
* Package: com.mytalk.server.data.persistence
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
package com.mytalk.server.data.storage;

import org.hibernate.*;
import org.hibernate.cfg.*;


public class HibernateUtil {
	private static SessionFactory sessionFactory = initHibernateUtil();
	
	private static SessionFactory initHibernateUtil(){
	try {
		return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		} catch (HibernateException ex) {
		throw new ExceptionInInitializerError(ex); 
		}
	}
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public static void shutdown() {
		getSessionFactory().close(); //Close caches and connection pools
	} 
}
