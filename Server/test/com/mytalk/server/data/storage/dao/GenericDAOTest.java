/**
* Filename: GenericDAOTest.java
* Package: com.mytalk.server.data.storage.dao
* Author: Armando Caprio
* Date: 2013/04/25
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013/04/25 |    AC     | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

package com.mytalk.server.data.storage.dao;

import static org.junit.Assert.*;

import org.junit.Test;


public class GenericDAOTest extends GenericDAO{

	@Test
	public void connectionTest() {
		if(session==null){
			fail("La sessione non viene inizializzata nel costruttore");
		}else if(!session.isOpen()){
			fail("La sessione viene inizializzata nel costruttore ma non aperta");
		}
		
		UserDAO userDAO = new UserDAO();
		if(session==null){
			fail("La sessione viene persa dopo che creo un nuovo oggetto DAO");
		}else if(!session.isOpen()){
			fail("La sessione viene  chiusa dopo che creo un nuovo oggetto DAO");
		}
		
		GenericDAO.closeSession();
		if(session==null){
			fail("La sessione viene persa quando la chiudo");
		}else if(session.isOpen()){
			fail("La sessione non viene chiusa correttamente quando chiamo il metodo closeSession()");
		}
		
		CallDAO callDAO = new CallDAO();
		if(session==null){
			fail("La sessione viene persa quando creo un nuovo DAO con la sessione chiusa");
		}else if(!session.isOpen()){
			fail("Con la sessione chiusa manualmente: non viene aperta quando creo un nuovo DAO");
		}	
	}
}