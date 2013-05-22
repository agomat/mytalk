/**
* Filename: BlackListAddTest.java
* Package: com.mytalk.server.logic.processing.request_processor.list
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


package com.mytalk.server.logic.processing.request_processor.list;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.mytalk.server.EnvironmentSetter;
import com.mytalk.server.logic.processing.Convert;
import com.mytalk.server.logic.processing.request_processor.list.BlackListAdd;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.ListPack;
import com.mytalk.server.logic.shared.model_client.User;
import com.mytalk.server.logic.shared.model_client.UserList;

public class BlackListAddTest {

	EnvironmentSetter envSetter=new EnvironmentSetter();
	private Convert conv=new Convert();

	@Before
	public void setTestEnvironment(){
		envSetter.cleanDB();
		envSetter.initDB();
	}
	
	@Test
	public void testManage() {
		BlackListAdd blackListAdd=new BlackListAdd();
		Authentication auth=new Authentication("user1","user1","123.123.123.1");
		List<Integer> listIdUser=new ArrayList<Integer>();
		listIdUser.add(3);
		UserList userList=new UserList(0,"Blacklist",listIdUser);
		List<UserList> listUserList=new ArrayList<UserList>();
		listUserList.add(userList);
		ListPack pack=new ListPack(listUserList);
		String packString=conv.convertJavaToJson(pack);
		ARI ari= new ARI(auth,"BlackListAdd",packString);
		
		ARI ariResponse=blackListAdd.manage(ari);
		assertEquals("Dati corretti ma non viene processata la richiesta","SuccessfulBlacklistAdd",ariResponse.getReq());
		
		ariResponse=blackListAdd.manage(ari);
		assertEquals("User già in blacklist ma viene aggiunto lo stesso","UserAlreadyBlacklisted",ariResponse.getReq());
	
		Authentication new_auth=new Authentication("user1","user2","123.123.123.1");
		ari=new ARI(new_auth,"BlackListAdd",packString);
		ariResponse=blackListAdd.manage(ari);
		assertEquals("Autenticazione assente ma viene aggiunto lo stesso","AuthenticationFail",ariResponse.getReq());
		
		listIdUser.remove(0);
		listIdUser.add(13);
		String new_packString=conv.convertJavaToJson(pack);
		ari=new ARI(auth,"BlackListAdd",new_packString);
		ariResponse=blackListAdd.manage(ari);
		assertEquals("Id non esiste ma viene aggiunto lo stesso","IdNotFound",ariResponse.getReq());
		
		
		listUserList.get(0).setList(null);
		new_packString=conv.convertJavaToJson(pack);
		ari=new ARI(auth,"BlackListAdd",new_packString);
		ariResponse=blackListAdd.manage(ari);
		assertEquals("Nesssun user da aggiungere in blacklist ma processata","CorruptedPack",ariResponse.getReq());
	
	//UsernameNotCorresponding non viene mai sollevata perchè per creare l'user si usa il valore presente in authentication
	//UsernameNotExisting non viene mai sollevata perchè vi è l'eccezione IdNotFound che previene ciò
	}

}
