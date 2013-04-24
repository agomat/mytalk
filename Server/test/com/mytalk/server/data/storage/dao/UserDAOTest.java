/**
* Filename: UserDaoTest.java
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
import java.util.*;

public class UserDAOTest {

	private SessionFactory session=HibernateUtil.getSessionFactory();
	private UserDAO dao=new UserDAO();
	private int numberUser = 5;
		
	@Before
	public void  initDB(){
		Session s = session.openSession();
		Transaction t = s.beginTransaction();
		
		Query query = s.createSQLQuery("DELETE FROM Users");
		query.executeUpdate();
		
		t.commit();
		
		t=s.beginTransaction();
		for(int i=0; i<numberUser; i++){
			User u= new User("User"+i,"User"+i,"User"+i+"@mytalk.com");
			s.save(u);
		}
		t.commit();
		s.close();
	}
			
	@Test
	public void checkPassword(){
		//Check della password corretta
		boolean result = dao.checkAuthenticationData("User0", "User0");
		assertTrue("Segna la password sbagliata quando invece user e password corrispondono", result);
		
		//Check della password sbagliata
		result = !dao.checkAuthenticationData("User0", "Wrong");
		assertTrue("Segna la password corretta quando invece l'utente ha un altra password", result);
		
		//Check della password di un altro utente
		result = !dao.checkAuthenticationData("User0", "User1");
		assertTrue("Segna la password corretta se l'utente ha un password di un altro utente", result);
		
		//Check della password di un utente che non esiste
		result = !dao.checkAuthenticationData("Wrong", "User1");
		assertTrue("Segna la password corretta quando invece l'utente non esiste", result);
	}
	
	@Test
	public void checkDelete(){
		//Prova ad eliminare un utente non presente
		dao.deleteAccount("Wrong");
		Session s = session.openSession();
		Transaction t = s.beginTransaction();
		List<User> allUser = s.createCriteria(User.class).list();
		assertEquals("Viene eliminato un utente quando provo ad eliminare un utente che non esiste", allUser.size(), numberUser);
		t.commit();
		s.close();
	}
	
	@Test
	public void checkGetAllUser(){
		//Verifica che mi ritornino tutti gli utenti
		List<User> allUser = dao.giveListUser();
		int sizeList = allUser.size();
		assertEquals("Vengono ritornati solo "+sizeList+" quando invece dovrebbero essere "+numberUser, sizeList, numberUser);
	}
	
	@Test
	public void checkChangePassword(){
		//Prova a cambiare la password ad un utente che non esiste
		dao.changeUserPassword("Wrong", "Wrong");
		Session s = session.openSession();
		Transaction t = s.beginTransaction();
		List<User> allUser = s.createCriteria(User.class).list();
		t.commit();
		s.close();
		assertEquals("Cambiando password si perde un utente", allUser.size(), numberUser);
		for(int i=0; i<numberUser; i++){
			User u = allUser.get(i);
			assertEquals("Cambiando password ad un utente che non esiste, viene cambiata la password di "+u.getUsername(), u.getPassword(), "User"+i);
		}
	}
	
	@Test
	public void checkAdd(){
		//Controlla che un utente
	}
}
