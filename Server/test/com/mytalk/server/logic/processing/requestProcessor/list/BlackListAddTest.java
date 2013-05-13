/**
* Filename: BlackListAddTest.java
* Package: com.mytalk.server.logic.processing.requestProcessor.list
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


package com.mytalk.server.logic.processing.requestProcessor.list;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.mytalk.server.EnvironmentSetter;
import com.mytalk.server.logic.processing.Convert;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.ListPack;
import com.mytalk.server.logic.shared.modelClient.UserList;
import com.mytalk.server.logic.shared.modelClient.WrapperUserList;
import com.mytalk.server.logic.shared.modelClient.User;

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
		User user2=new User("user2",true,"user2","user2","123.123.123.2");
		List<User> listUser=new ArrayList<User>();
		listUser.add(user2);
		UserList userList=new UserList(0,"Blacklist",listUser);
		List<UserList> listUserList=new ArrayList<UserList>();
		listUserList.add(userList);
		WrapperUserList wul=new WrapperUserList(listUserList);
		ListPack pack=new ListPack(wul);
		String packString=conv.convertJavaToJson(pack);
		ARI ari= new ARI(auth,"BlackListAdd",packString);
		
		ARI ariResponse=blackListAdd.manage(ari);
		assertEquals("Dati corretti ma non viene processata la richiesta","SuccessfulBlacklistAdd",ariResponse.getReq());
		assertEquals("Ip a cui mandare errato","123.123.123.1",ariResponse.getAuth().getIp());	
		
		ariResponse=blackListAdd.manage(ari);
		assertEquals("User già in blacklist ma viene aggiunto lo stesso","UserAlreadyBlacklisted",ariResponse.getReq());
		assertEquals("Ip a cui mandare errato","123.123.123.1",ariResponse.getAuth().getIp());
	
		Authentication new_auth=new Authentication("user1","user2","123.123.123.1");
		ari=new ARI(new_auth,"BlackListAdd",packString);
		ariResponse=blackListAdd.manage(ari);
		assertEquals("Autenticazione assente ma viene aggiunto lo stesso","AuthenticationFail",ariResponse.getReq());
		assertEquals("Ip a cui mandare errato","123.123.123.1",ariResponse.getAuth().getIp());
		
		User user12=new User("user12",false,"user12","user12","123.123.123.12");
		listUser.remove(0);
		listUser.add(user12);
		String new_packString=conv.convertJavaToJson(pack);
		ari=new ARI(auth,"BlackListAdd",new_packString);
		ariResponse=blackListAdd.manage(ari);
		assertEquals("Username non esiste ma viene aggiunto lo stesso","UserNotExisting",ariResponse.getReq());
		assertEquals("Ip a cui mandare errato","123.123.123.1",ariResponse.getAuth().getIp());
		
		listUserList.get(0).setList(null);
		new_packString=conv.convertJavaToJson(pack);
		ari=new ARI(auth,"BlackListAdd",new_packString);
		ariResponse=blackListAdd.manage(ari);
		assertEquals("Nesssun user da aggiungere in blacklist ma processata","CorruptedPack",ariResponse.getReq());
	}

}
