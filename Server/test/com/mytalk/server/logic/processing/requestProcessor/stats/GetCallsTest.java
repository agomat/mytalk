/**
* Filename: GetCallsTest.java
* Package: com.mytalk.server.logic.processing.requestProcessor.stats
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


package com.mytalk.server.logic.processing.requestProcessor.stats;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mytalk.server.data.storage.DataAccessTest;
import com.mytalk.server.logic.processing.Convert;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.GiveCallPack;

public class GetCallsTest {
	private Convert conv=new Convert();
	/**
	 * Test method for {@link com.mytalk.server.logic.processing.requestProcessor.stats.GetCalls#manage(com.mytalk.server.logic.shared.ARI)}.
	 */
	@Test
	public void testManage() {
		DataAccessTest dat=new DataAccessTest();
		dat.setTestEnvironment();
		Authentication authRightTest=new Authentication("user1","user1","1.1.1.1");
		Authentication authWrongTest=new Authentication("user1","user2","1.1.1.1");
		
		ARI ariRightTest=new ARI(authRightTest,"GetCalls",null);
		ARI ariWrongTest=new ARI(authWrongTest,"GetCalls",null);
		
		GetCalls getCallsTest=new GetCalls();
		
		ARI ariSuccess=getCallsTest.manage(ariRightTest);
		assertEquals("L'autenticazione è corretta ma viene fallita l'autenticazione","GiveCalls", ariSuccess.getReq());
		
		ARI ariUnsuccess=getCallsTest.manage(ariWrongTest);
		assertEquals("L'autenticazione è corretta ma viene fallita l'autenticazione","AuthenticationFail", ariUnsuccess.getReq());
	
		String giveCallPack=ariSuccess.getInfo();
		GiveCallPack packTest=(GiveCallPack)conv.convertJsonToJava(giveCallPack, GiveCallPack.class);
		int totR=0;
		int totS=0;
		int totD=0;
		for(int i=0;i<packTest.getWrapperCall().getList().size();i++){
			totR=totR+packTest.getWrapperCall().getList().get(i).getByteReceived();
			totS=totS+packTest.getWrapperCall().getList().get(i).getByteSent();
			totD=totD+packTest.getWrapperCall().getList().get(i).getDuration();
		}
		assertEquals("Le statistiche totali relative ai byte ricevuti sono errate",totR,packTest.getWrapperCall().getTotalByteReceived());
		assertEquals("Le statistiche totali relative ai byte mandati sono errate",totS,packTest.getWrapperCall().getTotalByteSent());
		assertEquals("Le statistiche totali relative alla durata sono errate",totD,packTest.getWrapperCall().getTotalDuration());
	}

}
