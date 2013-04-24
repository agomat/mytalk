/**
* Filename: ListNameDaoTest.java
* Package: com.mytalk.server.data.storage.dao
* Author: Armando Caprio
* Date: 2013-04-20
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-04-20 | AC        | [+] Creazione classe e definizione metodi  
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

package com.mytalk.server.data.storage.dao;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import org.hibernate.*;
import com.mytalk.server.data.persistence.HibernateUtil;
import com.mytalk.server.data.model.*;

public class ListNameDAOTest {

	private SessionFactory session=HibernateUtil.getSessionFactory();
	private ListNameDAO dao;
		
	@Before
	public void  initDB(){
		Session s = session.openSession();
		Transaction t = s.beginTransaction();
		
		Query query = s.createSQLQuery("DELETE FROM ListNames");
		query.executeUpdate();
		
		t.commit();
		
		t=s.beginTransaction();
		for(int i=0; i<5; i++){
			
		}
		t.commit();
		s.close();
		
		dao=new ListNameDAO();
	}
			
	@Test
	public void test() {
		fail("not yet implemented");
	}
}