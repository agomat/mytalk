/**
* Filename: GenericDAO.java
* Package: com.mytalk.server.data.storage
* Author: Nicolo' Mazzucato
* Date: 2013-04-12
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.2     | 2013-06-18 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.1	  |	2013-04-12 |    NM     | [+] Creazione classe    
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Classe astratta che offre operazioni di apertura e chiusura della sessione Hibernate alle classi
* che la estendono
*/

package com.mytalk.server.data.storage.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.mytalk.server.data.storage.HibernateUtil;

public abstract class GenericDAO {
	/**
	 * Riferimento alla sessione aperta all'istanziazione della classe
	 * 
	 * @property #session
	 * @type {Session}
	 */
	protected static Session session; 
	
	/**
	 * Costruttore della classe che inizializza la sessione di Hibernate
	 * 
	 * @method +GenericDAO
	 */
	public GenericDAO(){
		openSession();
	}
	
	/**
	 * Usa classe SessionFactory per aprire una sessione, e assegnare il valore dell'oggetto
	 *  Session ritornato all'attributo session
	 *  
	 *  @method +openSession
	 *  @return {void}
	 */
	public static void openSession(){
		SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
		if(session==null){
			session=sessionFactory.openSession();
		}else if(!session.isOpen()){
			session=sessionFactory.openSession();
		}
	}
	
	/**
	 * Chiude la sessione riferita dall'attributo session
	 * 
	 * @method +closeSession
	 * @return {void}
	 */
	public static void closeSession(){
		if(session!=null){
			if(session.isOpen()){
				session.close();
			}
		}
	}
}
