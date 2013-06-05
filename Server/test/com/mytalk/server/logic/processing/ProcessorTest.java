/**
* Filename: ProcessorTest.java
* Package: com.mytalk.server.logic.processing
* Author: Michael Ferronato
* Date: 2013-05-20
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-05-20 |  MF       | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/


package com.mytalk.server.logic.processing;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.mytalk.server.communication.buffer.Message;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.ConnectionPack;
	
	
public class ProcessorTest {
		
	private Convert conv=new Convert();
	
	@Test
	public void testProcessRequest() {
		Processor processor= new Processor();
		Authentication auth= new Authentication(null,null,"ip");
		ConnectionPack pack= new ConnectionPack("myIp",null,"speakerIp",null,"RTCinfo");
		String json=conv.convertJavaToJson(pack);
		ARI ari= new ARI(auth,"UserCall",json);
		String ariString=conv.convertJavaToJson(ari);
		Message message= new Message("ip",ariString);
		List<Message> list=processor.processRequest(message);
		ARI ariResponse=conv.convertJsonToJava(list.get(0).getJson());
		
		assertEquals("test","UnsuccessfulAcceptCall",ariResponse.getReq());
	}
}