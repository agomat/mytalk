/**
* Filename: HibernateUtil.java
* Package: com.mytalk.server.data.persistence
* Author: Michael Ferronato
* Date: 2013-04-16
*
* Diary:
*
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.2     | 2013-06-18 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.1	  |	2013-04-16 |    MF     | [+] Creazione classe e definizione metodi  
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Classe di utilita' per l'inizializzazione di Hibernate
*/
package com.mytalk.server.data.storage;

import org.hibernate.*;
import org.hibernate.cfg.*;


public class HibernateUtil {
	/**
	 * Memorizza un riferimento all'oggetto SessionFactory creato
	 * 
	 * @property -sessionFactory
	 * @type {SessionFactory}
	 */
	private static SessionFactory sessionFactory = initHibernateUtil();
	
	/**
	 * Istanzia un oggetto di tipo org.hibernate.Configuration e avvia il processo di mapping
	 * 
	 * @method -initHibernateUtil
	 * @return {SessionFactory}
	 */
	private static SessionFactory initHibernateUtil(){
	try {
		return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		} catch (HibernateException ex) {
		throw new ExceptionInInitializerError(ex); 
		}
	}
	
	/**
	 * Ritorna il riferimento all'attributo sessionFactory
	 * 
	 * @method +getSessionFactory
	 * @return {SessionFactory}
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	/**
	 * Chiude le connessioni e pulisce la cache
	 * 
	 * @method +shutdown
	 * @return {void}
	 */
	public static void shutdown() {
		getSessionFactory().close(); //Close caches and connection pools
	} 
}
