/**
* Filename: ListCreateTest.java
* Package: com.mytalk.server.logic.processing.request_processor.update_list_information
* Author: Michael Ferronato
* Date: 2013/05/08
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  | 2013/05/08 |   MF      | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/


package com.mytalk.server.logic.processing.request_processor.update_list_information;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.mytalk.server.EnvironmentSetter;
import com.mytalk.server.logic.processing.Convert;
import com.mytalk.server.logic.processing.request_processor.update_list_information.ListCreate;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.UpdateListPack;

public class ListCreateTest {

	EnvironmentSetter envSetter=new EnvironmentSetter();
	private Convert conv=new Convert();

	@Before
	public void setTestEnvironment(){
		envSetter.cleanDB();
		envSetter.initDB();
	}
	
	@Test
	public void testManage() {
		ListCreate listCreate=new ListCreate();
		Authentication auth=new Authentication("user1","user1","123.123.123.1");
		UpdateListPack listPack=new UpdateListPack("NuovaLista","user1",null);
		String packString=conv.convertJavaToJson(listPack);
		ARI ari=new ARI(auth,"ListCreate",packString);
		
		ARI ariResponse=listCreate.manage(ari);
		assertEquals("Dati corretti ma non avviene la creazione","SuccessfulListCreate",ariResponse.getReq());
		
		listPack.setOwner(null);
		packString=conv.convertJavaToJson(listPack);
		ari.setInfo(packString);
		ariResponse=listCreate.manage(ari);
		assertEquals("Pacchetto corrotto","CorruptedPack",ariResponse.getReq());
		
		listPack.setListName("friends");
		listPack.setOwner("user1");
		packString=conv.convertJavaToJson(listPack);
		ari.setInfo(packString);
		
		ariResponse=listCreate.manage(ari);
		assertEquals("Lista esiste gia'","ListAlreadyExists",ariResponse.getReq());
		
		listPack.setListName("NuovaLista");
		listPack.setOwner("user2");
		packString=conv.convertJavaToJson(listPack);
		ari.setInfo(packString);	
		ariResponse=listCreate.manage(ari);
		assertEquals("Username di auth e quello del owner sono diversi","UsernameNotCorrespondingListCreate",ariResponse.getReq());
		
		Authentication authWrong=new Authentication("user1","user0","123.123.123.1");
		ari.setAuth(authWrong);
		ariResponse=listCreate.manage(ari);
		assertEquals("Autenticazione fallita","AuthenticationFailListCreate",ariResponse.getReq());

	}

}
