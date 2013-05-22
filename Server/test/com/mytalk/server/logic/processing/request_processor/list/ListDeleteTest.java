/**
* Filename: ListDeleteTest.java
* Package: com.mytalk.server.logic.processing.requestProcessor.list
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


package com.mytalk.server.logic.processing.request_processor.list;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.mytalk.server.EnvironmentSetter;
import com.mytalk.server.logic.processing.Convert;
import com.mytalk.server.logic.processing.request_processor.list.ListDelete;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.ListPack;
import com.mytalk.server.logic.shared.model_client.UserList;

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
		UserList userList=new UserList(2,"friends",null);
		List<UserList> listUserList=new ArrayList<UserList>();
		listUserList.add(userList);
		ListPack listPack=new ListPack(listUserList);
		String packString=conv.convertJavaToJson(listPack);
		ARI ari=new ARI(auth,"ListDelete",packString);
		
		ARI ariResponse=listDelete.manage(ari);
		assertEquals("Dati corretti ma non avviene l'eliminazione","SuccessfulListDelete",ariResponse.getReq());
	
		ariResponse=listDelete.manage(ari);
		assertEquals("Lista non esiste ma viene processata","ListNotExisting",ariResponse.getReq());
	
		auth.setPwd("user3");
		ariResponse=listDelete.manage(ari);
		assertEquals("Autenticazione deve fallire","AuthenticationFail",ariResponse.getReq());
		
		//UsernameNotCorresponding non viene sollevata per formazione
	}

}
