/**
* Filename: ListUserRemoveTest.java
* Package: com.mytalk.server.logic.processing.request_processor.list_information
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


package com.mytalk.server.logic.processing.request_processor.list_information;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.mytalk.server.EnvironmentSetter;
import com.mytalk.server.logic.processing.Convert;
import com.mytalk.server.logic.processing.request_processor.list_information.ListUserRemove;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.ListPack;
import com.mytalk.server.logic.shared.model_client.UserList;

public class ListUserRemoveTest {

	EnvironmentSetter envSetter=new EnvironmentSetter();
	private Convert conv=new Convert();

	@Before
	public void setTestEnvironment(){
		envSetter.cleanDB();
		envSetter.initDB();
	}
	
	@Test
	public void testManage() {
		ListUserRemove listUserRemove=new ListUserRemove();
		Authentication auth=new Authentication("user0","user0","123.123.123.0");
		List<Integer> list=new ArrayList<Integer>();
		list.add(4);
		UserList userList=new UserList(0,"friends",list);
		List<UserList> listUserList=new ArrayList<UserList>();
		listUserList.add(userList);
		ListPack listPack=new ListPack(listUserList);
		String packString=conv.convertJavaToJson(listPack);
		ARI ari=new ARI(auth,"ListUserRemove",packString);
		
		ARI ariResponse=listUserRemove.manage(ari);
		assertEquals("Dati corretti ma non avviene la rimozione","SuccessfulListUserRemove",ariResponse.getReq());
		
		ariResponse=listUserRemove.manage(ari);
		assertEquals("User non presente","UserNotListed",ariResponse.getReq());
		
		auth.setPwd("user1");
		ariResponse=listUserRemove.manage(ari);
		assertEquals("Autenticazione deve fallire","AuthenticationFailListUserRemove",ariResponse.getReq());
		
		auth.setPwd("user0");
		list.remove(0);
		list.add(30);
		packString=conv.convertJavaToJson(listPack);
		ari.setInfo(packString);
		ariResponse=listUserRemove.manage(ari);
		assertEquals("Id non corrisponde a nessun user","IdNotFoundListUserRemove",ariResponse.getReq());
		
		list.remove(0);
		list.add(1);
		packString=conv.convertJavaToJson(listPack);
		ari.setInfo(packString);
		ariResponse=listUserRemove.manage(ari);
		assertEquals("User non può rimuovere se stesso","UserNotExistingListUserRemove",ariResponse.getReq());
		
		userList.setName("lista");
		list.remove(0);
		list.add(7);
		packString=conv.convertJavaToJson(listPack);
		ari.setInfo(packString);
		ariResponse=listUserRemove.manage(ari);
		assertEquals("Lista non esiste per questo user","ListNotExistingListUserRemove",ariResponse.getReq());
		
		//UsernameNotCorresponding non viene sollevata per costruzione
	}

}
