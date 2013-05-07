/**
* Filename: DeleteAccountTest.java
* Package: com.mytalk.server.logic.processing.requestProcessor.user
* Author: Michael Ferronato
* Date: 2013-05-06
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  | 2013-05-06 |   MF      | [+] Inserimento classe    
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

public class DeleteAccountTest {

	private EnvironmentSetter envSetter=new EnvironmentSetter();
	private Convert conv=new Convert();
	
	@Before
	public void setTestEnvironment(){
		envSetter.cleanDB();
		envSetter.initDB();
	}
	
	@Test
	public void testManage() {
		DeleteAccount deleteAccount=new DeleteAccount();
		Authentication authRightTest=new Authentication("user1","user1","1.1.1.1");
		PersonalData personalData=new PersonalData("user1","user1","user1","user1","user1@mytalk.com","1.1.1.4");
		WorldPack pack=new WorldPack(null,null,personalData);
		String packString=conv.convertJavaToJson(pack);
		ARI ari=new ARI(authRightTest,"DeleteAccount",packString);
		
		ARI ariResult=deleteAccount.manage(ari);
		
		assertEquals("Eliminazione non effettuata anche se l'account esiste","SuccessfulDeleteAccount",ariResult.getReq());
	
		Authentication authWrongTest=new Authentication("user2","user1","1.1.1.1");
		personalData=new PersonalData("user1","user1","user1","user1","user1@mytalk.com","1.1.1.4");
		pack=new WorldPack(null,null,personalData);
		packString=conv.convertJavaToJson(pack);
		ari=new ARI(authWrongTest,"DeleteAccount",packString);
		
		ariResult=deleteAccount.manage(ari);
		
		assertEquals("Eliminazione effettuata anche se autenticazione fallisce","AuthenticationFail",ariResult.getReq());
	}

}
