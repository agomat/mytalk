/**
* Filename: BlackListRemoveTest.java
* Package: com.mytalk.server.logic.processing.requestProcessor.list
* Author: 
* Date:
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	           |           | [+] Inserimento classe, oggetti e costruttore     
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
import com.mytalk.server.logic.shared.modelClient.User;
import com.mytalk.server.logic.shared.modelClient.UserList;
import com.mytalk.server.logic.shared.modelClient.WrapperUserList;

public class BlackListRemoveTest {

	EnvironmentSetter envSetter=new EnvironmentSetter();
	private Convert conv=new Convert();

	@Before
	public void setTestEnvironment(){
		envSetter.cleanDB();
		envSetter.initDB();
	}
	
	@Test
	public void testManage() {
		BlackListRemove blackListRemove=new BlackListRemove();
		Authentication auth=new Authentication("user1","user1","123.123.123.1");
		User user8=new User("user8",true,"user8","user8",null);
		List<User> listUser=new ArrayList<User>();
		listUser.add(user8);
		UserList userList=new UserList(0,"Blacklist",listUser);
		List<UserList> listUserList=new ArrayList<UserList>();
		listUserList.add(userList);
		WrapperUserList wul=new WrapperUserList(listUserList);
		ListPack pack=new ListPack(wul);
		String packString=conv.convertJavaToJson(pack);
		ARI ari= new ARI(auth,"BlackListRemove",packString);
		
		ARI ariResponse=blackListRemove.manage(ari);
		assertEquals("Dati corretti ma non viene processata la richiesta","SuccessfulBlacklistRemove",ariResponse.getReq());
		
		ariResponse=blackListRemove.manage(ari);
		assertEquals("User non presente in blacklist ma la richiesta viene processata lo stesso","UserNotBlacklisted",ariResponse.getReq());
	
		Authentication new_auth=new Authentication("user1","user2","123.123.123.1");
		ari=new ARI(new_auth,"BlackListRemove",packString);
		ariResponse=blackListRemove.manage(ari);
		assertEquals("Autenticazione errata ma viene aggiunto lo stesso","AuthenticationFail",ariResponse.getReq());
		
		listUserList.get(0).setList(null);
		String new_packString=conv.convertJavaToJson(pack);
		ari=new ARI(auth,"BlackListAdd",new_packString);
		ariResponse=blackListRemove.manage(ari);
		assertEquals("Nesssun user da togliere dalla blacklist ma processata","CorruptedPack",ariResponse.getReq());
	
	}

}
