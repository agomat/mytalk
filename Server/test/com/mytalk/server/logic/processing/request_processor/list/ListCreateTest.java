/**
* Filename: ListCreateTest.java
* Package: com.mytalk.server.logic.processing.request_processor.list
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
import com.mytalk.server.logic.processing.request_processor.list.ListCreate;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.ListPack;
import com.mytalk.server.logic.shared.model_client.UserList;

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
		UserList userList=new UserList(0,"NuovaLista",null);
		List<UserList> listUserList=new ArrayList<UserList>();
		listUserList.add(userList);
		ListPack listPack=new ListPack(listUserList);
		String packString=conv.convertJavaToJson(listPack);
		ARI ari=new ARI(auth,"ListCreate",packString);
		
		ARI ariResponse=listCreate.manage(ari);
		assertEquals("Dati corretti ma non avviene la creazione","SuccessfulListCreate",ariResponse.getReq());
	

		listUserList.remove(0);
		listPack.setList(listUserList);
		packString=conv.convertJavaToJson(listPack);
		ari.setInfo(packString);
		
		ariResponse=listCreate.manage(ari);
		assertEquals("Pacchetto corrotto","CorruptedPack",ariResponse.getReq());
		
		userList.setName("friends");
		listUserList.add(userList);
		packString=conv.convertJavaToJson(listPack);
		ari.setInfo(packString);
		
		ariResponse=listCreate.manage(ari);
		assertEquals("Lista esiste già","ListAlreadyExists",ariResponse.getReq());
		
		Authentication authWrong=new Authentication("user1","user0","123.123.123.1");
		ari.setAuth(authWrong);
		ariResponse=listCreate.manage(ari);
		assertEquals("Autenticazione fallita","AuthenticationFailListCreate",ariResponse.getReq());
		
		//UsernameNotCorresponding non viene mai sollevata poiché il prorpietario viene settato con l'username presente in authentication
		
	}

}
