/**
* Filename: LoginAsAnonymousTest.java
* Package: com.mytalk.server.logic.processing.request_processor.world_information
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


package com.mytalk.server.logic.processing.request_processor.world_information;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.mytalk.server.EnvironmentSetter;
import com.mytalk.server.logic.processing.request_processor.world_information.LoginAsAnonymous;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;

public class LoginAsAnonymousTest {

	private EnvironmentSetter envSetter=new EnvironmentSetter();
	
	@Before
	public void setTestEnvironment(){
		envSetter.cleanDB();
		envSetter.initDB();
	}
	@Test
	public void testManage() {
		LoginAsAnonymous loginAsAnonymous=new LoginAsAnonymous();
		Authentication authRightTest=new Authentication("user4","user4","111.111.111.4");
		
		ARI ari=new ARI(authRightTest,"Login",null);
		ARI ariResult=loginAsAnonymous.manage(ari);
		assertEquals("Login fallito ma i dati sono corretti","SuccessfulLoginAsAnonymous",ariResult.getReq());
	
		authRightTest.setIp(null);
		ariResult=loginAsAnonymous.manage(ari);
		assertEquals("Login effettuato ma autenticazione fallita","CorruptedPack",ariResult.getReq());
	
		authRightTest.setIp("111.111.111.1");
		ariResult=loginAsAnonymous.manage(ari);
		assertEquals("Ip gia' in uso","IpAlreadyLoggedLoginAsAnonymous",ariResult.getReq());
	}

}
