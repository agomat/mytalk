/**
* Filename: GenericDAO.java
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

import org.hibernate.*;
import com.mytalk.server.data.persistence.HibernateUtil;


public abstract class GenericDAO {
	protected Session session=HibernateUtil.getSessionFactory().openSession();
	protected Transaction t=session.beginTransaction();
	
}
