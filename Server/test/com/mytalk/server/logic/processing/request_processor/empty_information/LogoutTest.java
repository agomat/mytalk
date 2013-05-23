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
import com.mytalk.server.logic.processing.Convert;
import com.mytalk.server.logic.processing.request_processor.empty_information.Logout;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.WorldPack;
import com.mytalk.server.logic.shared.model_client.PersonalData;
import com.mytalk.server.logic.shared.model_client.WorldPersonalData;

public class LogoutTest {

	private EnvironmentSetter envSetter=new EnvironmentSetter();
	private Convert conv=new Convert();
	
	@Before
	public void setTestEnvironment(){
		envSetter.cleanDB();
		envSetter.initDB();
	}
	
	
	@Test
	public void testManage() {
		Logout logout=new Logout();
		Authentication auth=new Authentication("user1","user1","123.123.123.1");
		
		ARI ari=new ARI(auth,"Logout",null);
		ARI ariResult=logout.manage(ari);
		assertEquals("Logout fallito nonostante dati coerenti","Logout",ariResult.getReq());
		
		auth=new Authentication("user4","user4","123.123.123.4");
		ari.setAuth(auth);
		ariResult=logout.manage(ari);
		assertEquals("Logout di un user non loggato","Logout",ariResult.getReq());
		
		ari.getAuth().setIp(null);
		ariResult=logout.manage(ari);
		assertEquals("Autenticazione mal formata","CorruptedPack",ariResult.getReq());
	}

}
