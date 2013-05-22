/**
* Filename: CreateAccountTest.java
* Package: com.mytalk.server.logic.processing.requestProcessor.user
* Author: Michael Ferronato
* Date: 2013-05-06
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-05-06 |   MF      | [+] Inserimento classe     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

package com.mytalk.server.logic.processing.request_processor.user;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.mytalk.server.EnvironmentSetter;
import com.mytalk.server.logic.processing.Convert;
import com.mytalk.server.logic.processing.request_processor.user.CreateAccount;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.WorldPack;
import com.mytalk.server.logic.shared.model_client.PersonalData;
import com.mytalk.server.logic.shared.model_client.WorldPersonalData;

public class CreateAccountTest {

	private EnvironmentSetter envSetter=new EnvironmentSetter();
	private Convert conv=new Convert();
	
	@Before
	public void setTestEnvironment(){
		envSetter.cleanDB();
		envSetter.initDB();
	}
	
	@Test
	public void testManage() {
		CreateAccount createAccount=new CreateAccount();
		Authentication auth=new Authentication(null,null,"123.123.123.11");
		PersonalData personalData=new PersonalData(12,"user11","user11","user11","user11","user11@mytalk.com","us11us11us11us11us11us11us11us11","1.1.1.3");
		WorldPersonalData wpd=new WorldPersonalData(personalData);
		WorldPack pack=new WorldPack(null,wpd);
		String packString=conv.convertJavaToJson(pack);
		
		ARI ari=new ARI(auth,"CreateAccount",packString);
		ARI ariResult=createAccount.manage(ari);
		
		assertEquals("Creazione account fallita ma username non è già presente","SuccessfulCreateAccount",ariResult.getReq());
		
		personalData=new PersonalData(2,"user1","user1","user1","user1","user1@mytalk.com","us01us01us01us01us01us01us01us01","1.1.1.4");
		
		pack=new WorldPack(null,wpd);
		packString=conv.convertJavaToJson(pack);
		ari.setInfo(packString);
		ariResult=createAccount.manage(ari);
		
		assertEquals("Creazione account riuscita ma username è già presente","UsernameAlreadyExisting",ariResult.getReq());
		
	}

}
