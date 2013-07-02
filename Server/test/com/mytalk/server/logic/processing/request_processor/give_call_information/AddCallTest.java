/**
* Filename: AddCallTest.java
* Package: com.mytalk.server.logic.processing.request_processor.give_call_information
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


package com.mytalk.server.logic.processing.request_processor.give_call_information;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import com.mytalk.server.EnvironmentSetter;
import com.mytalk.server.logic.processing.Convert;
import com.mytalk.server.logic.processing.request_processor.give_call_information.AddCall;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.GiveCallPack;
import com.mytalk.server.logic.shared.model_client.Call;
import com.mytalk.server.logic.shared.model_client.WrapperCall;

public class AddCallTest {
	
	EnvironmentSetter envSetter=new EnvironmentSetter();
	
	@Before
	public void setTestEnvironment(){
		envSetter.cleanDB();
		envSetter.initDB();
	}

	@Test
	public void testManage() {
		
		Authentication authRightTest=new Authentication("user1","user1","1.1.1.1");
		Authentication authWrongTest=new Authentication("user1","user2","1.1.1.1");
		Call call1=new Call(3,true,"2013-05-03 13.37:58",1000,100,100);
		Call call2=new Call(20,true,"2013-05-03 13.37:58",1000,100,100);
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
		assertEquals("L'autenticazione e' corretta ma viene fallita l'autenticazione","SuccessfulAddCall", ariSuccess.getReq());
		
		ARI ariUnsuccess=addCall.manage(ariWrongTest);
		assertEquals("L'autenticazione e' errata ma fa l'autenticazione lo stesso","AuthenticationFailAddCall", ariUnsuccess.getReq());
		
		callListTest.remove(0);
		callListTest.add(call2);
		packJson=conv.convertJavaToJson(packTest);
		ariRightTest= new ARI(authRightTest,"AddCall",packJson);
		ARI ariIdNotRight=addCall.manage(ariRightTest);
		assertEquals("L'id fornito e' inesistente","IdNotFoundAddCall", ariIdNotRight.getReq());
	
	}
}
