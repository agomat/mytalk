/**
* Filename: LogoutTest.java
* Package: com.mytalk.server.logic.processing.request_processor.empty_information
* Author: Michael Ferronato
* Date: 2013-05-07
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-05-07 |    MF     | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/


package com.mytalk.server.logic.processing.request_processor.empty_information;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.mytalk.server.EnvironmentSetter;
import com.mytalk.server.logic.processing.request_processor.empty_information.Logout;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;

public class LogoutTest {

	private EnvironmentSetter envSetter=new EnvironmentSetter();
	
	@Before
	public void setTestEnvironment(){
		envSetter.cleanDB();
		envSetter.initDB();
	}
	
	
	@Test
	public void testManage() {
		Logout logout=new Logout();
		Authentication auth=new Authentication(null,null,"123.123.123.2");
		
		ARI ari=new ARI(auth,"Logout",null);
		ARI ariResult=logout.manage(ari);
		assertEquals("Logout fallito nonostante dati coerenti","SuccessfulLogout",ariResult.getReq());
		assertEquals("User sloggato errato","user2",ariResult.getAuth().getUser());
		
		auth.setIp("123.123.123.4");
		ari.setAuth(auth);
		ariResult=logout.manage(ari);
		assertEquals("Logout di un user non loggato","UnsuccessfulLogout",ariResult.getReq());
		
		ari.getAuth().setIp(null);
		ariResult=logout.manage(ari);
		assertEquals("Autenticazione mal formata","CorruptedPack",ariResult.getReq());
		
		auth.setIp("123.123.123.1");
		ari=new ARI(auth,"Logout",null);
		ariResult=logout.manage(ari);
		assertEquals("Logout di un anonimo fallito nonostante dati coerenti","SuccessfulLogout",ariResult.getReq());
		assertEquals("User sloggato errato",null,ariResult.getAuth().getUser());
		
		
	}

}
