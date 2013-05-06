/**
* Filename: AddCallTest.java
* Package: com.mytalk.server.logic.processing.requestProcessor.stats
* Author: Michael Ferronato
* Date: 2013-05-03
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-05-03 |   MF      | [+] Inserimento classe  
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/


package com.mytalk.server.logic.processing.requestProcessor.stats;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import com.mytalk.server.data.storage.DataAccess;
import com.mytalk.server.data.storage.DataAccessTest;
import com.mytalk.server.data.storage.IDataAccess;
import com.mytalk.server.exceptions.AuthenticationFail;
import com.mytalk.server.logic.processing.Convert;
import com.mytalk.server.logic.processing.Processor;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.GiveCallPack;
import com.mytalk.server.logic.shared.modelClient.Call;
import com.mytalk.server.logic.shared.modelClient.WrapperCall;

public class AddCallTest {

	/**
	 * Test method for {@link com.mytalk.server.logic.processing.requestProcessor.stats.AddCall#manage(com.mytalk.server.logic.shared.ARI)}.
	 */
	@Test
	public void testManage() {
		DataAccessTest dat=new DataAccessTest();
		dat.setTestEnvironment();
		Authentication authRightTest=new Authentication("user1","user1","1.1.1.1");
		Authentication authWrongTest=new Authentication("user1","user2","1.1.1.1");
		Call call1=new Call("user2",100,100,1000,"2013-05-03 13.37:58",true);
		List<Call> callListTest=new ArrayList<Call>();
		callListTest.add(call1);
		WrapperCall wrapperTest=new WrapperCall(callListTest);
		GiveCallPack packTest=new GiveCallPack(wrapperTest);
		Convert conv=new Convert();
		String packJson=conv.convertJavaToJson(packTest);
		
		ARI ariRightTest=new ARI(authRightTest,"AddCall",packJson);
		ARI ariWrongTest=new ARI(authWrongTest,"AddCall",packJson);
		
		AddCall addCall=new AddCall();

		ARI ariSuccess=addCall.manage(ariRightTest);
		org.junit.Assert.assertEquals("L'autenticazione è corretta ma viene fallita l'autenticazione","SuccessfulAddCall", ariSuccess.getReq());
		
		ARI ariUnsuccess=addCall.manage(ariWrongTest);
		org.junit.Assert.assertEquals("L'autenticazione è errata ma fa l'autenticazione lo stesso","AuthenticationFail", ariUnsuccess.getReq());
	}
}
