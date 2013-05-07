/**
* Filename: LogoutTest.java
* Package: com.mytalk.server.logic.processing.requestProcessor.user
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


package com.mytalk.server.logic.processing.requestProcessor.user;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.mytalk.server.EnvironmentSetter;
import com.mytalk.server.logic.processing.Convert;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.WorldPack;
import com.mytalk.server.logic.shared.modelClient.PersonalData;

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
		Authentication authRightTest=new Authentication("user1","user1","1.1.1.1");
		PersonalData personalData=new PersonalData("user1","user1","user1","user1","user1@mytalk.com","1.1.1.4");
		WorldPack pack=new WorldPack(null,null,personalData);
		String packString=conv.convertJavaToJson(pack);
		
		ARI ari=new ARI(authRightTest,"Login",packString);
		ARI ariResult=logout.manage(ari);
		assertEquals("Logout fallito nonostante dati coerenti","SuccessfulLogout",ariResult.getReq());
	}

}
