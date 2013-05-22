/**
* Filename: UserCall.java
* Package: com.mytalk.server.logic.processing.requestProcessor.communication
* Author: Michael Ferronato
* Date: 2013-05-08
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |2013-05-08  |   MF      | [+] Inserimento classe    
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/


package com.mytalk.server.logic.processing.request_processor.communication;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.mytalk.server.EnvironmentSetter;
import com.mytalk.server.logic.processing.Convert;
import com.mytalk.server.logic.processing.request_processor.communication.UserCall;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.ConnectionPack;

public class UserCallTest {

	EnvironmentSetter envSetter=new EnvironmentSetter();
	private Convert conv=new Convert();

	@Before
	public void setTestEnvironment(){
		envSetter.cleanDB();
		envSetter.initDB();
	}
	
	@Test
	public void testManage() {
		UserCall userCall=new UserCall();
		ConnectionPack packTest=new ConnectionPack(null,null,"",null,"sdp");
		String packString=conv.convertJavaToJson(packTest);
		ARI ari=new ARI(null,"UserCall",packString);
		ARI ariResponse=userCall.manage(ari);
		assertEquals("Pacchetto formato errato","CorruptedPack",ariResponse.getReq());
		
		Authentication auth=new Authentication("user0","user0","123.123.123.0");
		ari.setAuth(auth);
		ariResponse=userCall.manage(ari);
		assertEquals("Pacchetto formato errato con auth","CorruptedPack",ariResponse.getReq());
		
		ari.setAuth(null);
		packTest=new ConnectionPack("123.123.123.0",1,"123.123.123.1",2,"sdp");
		packString=conv.convertJavaToJson(packTest);
		ari.setInfo(packString);
		ariResponse=userCall.manage(ari);
		assertEquals("Dati corretti ma non viene processata la richiesta","SuccessfulUserCall",ariResponse.getReq());
		assertEquals("Ip a cui mandare errato","123.123.123.1",ariResponse.getAuth().getIp());
		
		ari.setAuth(auth);
		ariResponse=userCall.manage(ari);
		assertEquals("Dati corretti ma non viene processata la richiesta con auth","SuccessfulUserCall",ariResponse.getReq());
		assertEquals("Ip a cui mandare errato","123.123.123.1",ariResponse.getAuth().getIp());
		
		ari.setAuth(null);
		packTest=new ConnectionPack("123.123.123.0",1,"123.123.123.4",2,"sdp");
		String packString2=conv.convertJavaToJson(packTest);
		ari.setInfo(packString2);
		ariResponse=userCall.manage(ari);
		assertEquals("IpSpeaker non è online ma viene processata lo stesso","UnsuccessfulUserCall",ariResponse.getReq());
		assertEquals("Ip a cui mandare errato","123.123.123.0",ariResponse.getAuth().getIp());
		
		ari.setAuth(auth);
		ariResponse=userCall.manage(ari);
		assertEquals("IpSpeaker non è online ma viene processata lo stesso con auth","UnsuccessfulUserCall",ariResponse.getReq());
		assertEquals("Ip a cui mandare errato","123.123.123.0",ariResponse.getAuth().getIp());
		
		auth.setUser("user0");
		packTest.setMyUserId(15);
		packString2=conv.convertJavaToJson(packTest);
		ari.setInfo(packString2);
		ariResponse=userCall.manage(ari);
		assertEquals("Autenticazione deve fallire","IdNotFound",ariResponse.getReq());
		assertEquals("Ip a cui mandare errato","123.123.123.0",ariResponse.getAuth().getIp());
		
		auth.setUser("user1");
		ari.setInfo(packString);
		ariResponse=userCall.manage(ari);
		assertEquals("Autenticazione deve fallire","AuthenticationFail",ariResponse.getReq());
		assertEquals("Ip a cui mandare errato","123.123.123.0",ariResponse.getAuth().getIp());
		
		auth.setPwd("user1");
		auth.setIp("123.123.123.1");
		ariResponse=userCall.manage(ari);
		assertEquals("Username non corrispondono","UsernameNotCorresponding",ariResponse.getReq());
		assertEquals("Ip a cui mandare errato","123.123.123.1",ariResponse.getAuth().getIp());		
		
	}

}
