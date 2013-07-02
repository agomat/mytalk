/**
* Filename: ListDeleteTest.java
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
import com.mytalk.server.logic.processing.request_processor.update_list_information.ListDelete;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.UpdateListPack;

public class ListDeleteTest {

	EnvironmentSetter envSetter=new EnvironmentSetter();
	private Convert conv=new Convert();

	@Before
	public void setTestEnvironment(){
		envSetter.cleanDB();
		envSetter.initDB();
	}
	
	@Test
	public void testManage() {
		ListDelete listDelete=new ListDelete();
		Authentication auth=new Authentication("user1","user1","123.123.123.1");
		UpdateListPack listPack=new UpdateListPack("friends","user1",null);
		String packString=conv.convertJavaToJson(listPack);
		ARI ari=new ARI(auth,"ListDelete",packString);
		
		ARI ariResponse=listDelete.manage(ari);
		assertEquals("Dati corretti ma non avviene l'eliminazione","SuccessfulListDelete",ariResponse.getReq());
	
		listPack.setOwner("user2");
		packString=conv.convertJavaToJson(listPack);
		ari.setInfo(packString);	
		ariResponse=listDelete.manage(ari);
		assertEquals("Username di auth e quello del owner sono diversi","UsernameNotCorrespondingListDelete",ariResponse.getReq());
		
		listPack.setOwner("user1");
		packString=conv.convertJavaToJson(listPack);
		ari.setInfo(packString);
		ariResponse=listDelete.manage(ari);
		assertEquals("Lista non esiste ma viene processata","ListNotExistingListDelete",ariResponse.getReq());
	
		auth.setPwd("user3");
		ariResponse=listDelete.manage(ari);
		assertEquals("Autenticazione deve fallire","AuthenticationFailListDelete",ariResponse.getReq());
		
		//UsernameNotCorrespondingException non viene sollevata per formazione
	}

}
