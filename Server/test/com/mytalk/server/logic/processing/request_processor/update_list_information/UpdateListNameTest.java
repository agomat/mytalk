/**
* Filename: UpdateListNameTest.java
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
import com.mytalk.server.logic.processing.request_processor.update_list_information.UpdateListName;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.UpdateListPack;

public class UpdateListNameTest {

	EnvironmentSetter envSetter=new EnvironmentSetter();
	private Convert conv=new Convert();

	@Before
	public void setTestEnvironment(){
		envSetter.cleanDB();
		envSetter.initDB();
	}
	
	@Test
	public void testManage() {
		UpdateListName updateListName= new UpdateListName();
		Authentication auth=new Authentication("user0","user0","123.123.123.0");
		UpdateListPack pack=new UpdateListPack("friends","user0","Friends");
		String packString=conv.convertJavaToJson(pack);
		ARI ari= new ARI(auth,"UpdateListName",packString);
		
		ARI ariResponse=updateListName.manage(ari);
		assertEquals("Dati corretti ma richiesta non processata","SuccessfulRenameList",ariResponse.getReq());
	
		auth.setPwd("user1");
		ariResponse=updateListName.manage(ari);
		assertEquals("Autenticazione deve fallire","AuthenticationFailRenameList",ariResponse.getReq());
		
		auth.setPwd("user0");
		pack.setListName("lista");
		packString=conv.convertJavaToJson(pack);
		ari.setInfo(packString);
		ariResponse=updateListName.manage(ari);
		assertEquals("Lista da rinominare non esiste","ListNotExistingRenameList",ariResponse.getReq());
		
		pack.setListName("nuovaLista");
		packString=conv.convertJavaToJson(pack);
		ari.setInfo(packString);
		ariResponse=updateListName.manage(ari);
		assertEquals("C'e' una gia' una lista con il nuovo nome","ListAlreadyExistsRenameList",ariResponse.getReq());
		
		pack.setOwner("user1");
		packString=conv.convertJavaToJson(pack);
		ari.setInfo(packString);
		ariResponse=updateListName.manage(ari);
		assertEquals("Lista da rinominare non esiste","UsernameNotCorrespondingRenameList",ariResponse.getReq());
	}

}
