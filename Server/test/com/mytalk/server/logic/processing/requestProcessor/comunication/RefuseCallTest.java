/**
* Filename: RefuseCallTest.java
* Package: com.mytalk.server.logic.processing.requestProcessor.comunication
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


package com.mytalk.server.logic.processing.requestProcessor.comunication;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.mytalk.server.EnvironmentSetter;
import com.mytalk.server.logic.processing.Convert;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.ConnectionPack;

public class RefuseCallTest {
	
	EnvironmentSetter envSetter=new EnvironmentSetter();
	private Convert conv=new Convert();

	@Before
	public void setTestEnvironment(){
		envSetter.cleanDB();
		envSetter.initDB();
	}
	
	@Test
	public void testManage() {
		RefuseCall refuseCall=new RefuseCall();
		ConnectionPack packTest=new ConnectionPack(null,"","","","sdp");
		String packString=conv.convertJavaToJson(packTest);
		ARI ari=new ARI(null,"RefuseCall",packString);
		ARI ariResponse=refuseCall.manage(ari);
		assertEquals("Pacchetto formato errato","CorruptedConnectionPack",ariResponse.getReq());
		
		packTest=new ConnectionPack("123.123.123.0",null,"123.123.123.1",null,"sdp");
		packString=conv.convertJavaToJson(packTest);
		ari=new ARI(null,"RefuseCall",packString);
		ariResponse=refuseCall.manage(ari);
		assertEquals("Dati corretti ma non viene processata la richiesta","SuccessfulRefuseCall",ariResponse.getReq());
		assertEquals("Ip a cui mandare errato","123.123.123.1",ariResponse.getAuth().getIp());
		
		packTest=new ConnectionPack("123.123.123.0","user0","123.123.123.4","user1","sdp");
		packString=conv.convertJavaToJson(packTest);
		ari=new ARI(null,"RefuseCall",packString);
		ariResponse=refuseCall.manage(ari);
		assertEquals("IpSpeaker non è online ma viene processata lo stesso","UnsuccessfulRefuseCall",ariResponse.getReq());
	}

}
