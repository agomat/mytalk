/**
* Filename: LogoutAsAnonymousTest.java
* Package: com.mytalk.server.logic.processing.requestProcessor.user
* Author: Michael Ferronato
* Date: 2013/05/17
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013/05/17 |   MF      | [+] Inserimento classe, oggetti e costruttore     
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
import com.mytalk.server.logic.shared.modelClient.WorldPersonalData;

public class LogoutAsAnonymousTest {

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
		PersonalData personalData=new PersonalData("user1","user1","user1","user1","user1@mytalk.com","us01us01us01us01us01us01us01us01","123.123.123.1");
		WorldPersonalData wpd=new WorldPersonalData(personalData);
		WorldPack pack=new WorldPack(null,wpd);
		String packString=conv.convertJavaToJson(pack);
		
		ARI ari=new ARI(auth,"Logout",packString);
		ARI ariResult=logout.manage(ari);
		assertEquals("Logout fallito nonostante dati coerenti","SuccessfulLogout",ariResult.getReq());
		
		auth=new Authentication("user4","user4","123.123.123.4");
		ari.setAuth(auth);
		personalData=new PersonalData("user4","user4","user4","user4","user4@mytalk.com","us04us04us04us04us04us04us04us04","123.123.123.4");
		wpd.setPersonalData(personalData);
		packString=conv.convertJavaToJson(pack);
		ariResult=logout.manage(ari);
		assertEquals("Logout di un user non loggato","LogoutException",ariResult.getReq());
	}

}
