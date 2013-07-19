/**
* Filename: DeleteAccountTest.java
* Package: com.mytalk.server.logic.processing.request_processor.empty_information
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


package com.mytalk.server.logic.processing.request_processor.empty_information;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.mytalk.server.EnvironmentSetter;
import com.mytalk.server.logic.processing.request_processor.empty_information.DeleteAccount;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;

public class DeleteAccountTest {

	private EnvironmentSetter envSetter=new EnvironmentSetter();
	
	@Before
	public void setTestEnvironment(){
		envSetter.cleanDB();
		envSetter.initDB();
	}
	
	@Test
	public void testManage() {
		DeleteAccount deleteAccount=new DeleteAccount();
		Authentication authRightTest=new Authentication("user0","user0","123.123.123.0");
		ARI ari=new ARI(authRightTest,"DeleteAccount",null);
		
		ARI ariResult=deleteAccount.manage(ari);
		
		assertEquals("Eliminazione non effettuata anche se l'account esiste","SuccessfulDeleteAccount",ariResult.getReq());
	
		Authentication authWrongTest=new Authentication("user0","user1","123.123.123.0");
		ari.setAuth(authWrongTest);
		
		ariResult=deleteAccount.manage(ari);
		
		assertNull("Eliminazione effettuata anche se autenticazione fallisce",ariResult);
	}

}
