/**
* Filename: GetCallsTest.java
* Package: com.mytalk.server.logic.processing.request_processor.give_call_information
* Author: Michael Ferronato
* Date: 2013-05-04
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-05-04 |   MF      | [+] Inserimento classe  
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/


package com.mytalk.server.logic.processing.request_processor.give_call_information;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.mytalk.server.EnvironmentSetter;
import com.mytalk.server.logic.processing.Convert;
import com.mytalk.server.logic.processing.request_processor.give_call_information.GetCalls;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.GiveCallPack;

public class GetCallsTest {
	EnvironmentSetter envSetter=new EnvironmentSetter();
	private Convert conv=new Convert();
	
	@Before
	public void setTestEnvironment(){
		envSetter.cleanDB();
		envSetter.initDB();
	}
		
	@Test
	public void testManage() {
		
		Authentication authRightTest=new Authentication("user1","user1","1.1.1.1");
		Authentication authWrongTest=new Authentication("user1","user2","1.1.1.1");
		
		ARI ariRightTest=new ARI(authRightTest,"GetCalls",null);
		ARI ariWrongTest=new ARI(authWrongTest,"GetCalls",null);
		
		GetCalls getCallsTest=new GetCalls();
		
		ARI ariSuccess=getCallsTest.manage(ariRightTest);
		assertEquals("L'autenticazione e' corretta ma viene fallita l'autenticazione","GiveCalls", ariSuccess.getReq());
		
		ARI ariUnsuccess=getCallsTest.manage(ariWrongTest);
		assertEquals("L'autenticazione e' corretta ma viene fallita l'autenticazione","AuthenticationFailGetCalls", ariUnsuccess.getReq());
	
		String giveCallPack=ariSuccess.getInfo();
		GiveCallPack packTest=(GiveCallPack)conv.convertJsonToJava(giveCallPack, GiveCallPack.class);
		
		assertEquals("Le statistiche totali relative ai byte ricevuti sono errate",new Integer(60000),packTest.getWrapperCall().getTotalByteReceived());
		assertEquals("Le statistiche totali relative ai byte mandati sono errate",new Integer(70000),packTest.getWrapperCall().getTotalByteSent());
		assertEquals("Le statistiche totali relative alla durata sono errate",new Integer(1700),packTest.getWrapperCall().getTotalDuration());
	
		/**
		 * UsernameNotExisting non viene sollevata perche' usa dati provvenienti
		 *  dal db e quindi consistenti
		 */
	}

}
