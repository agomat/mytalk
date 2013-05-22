/**
* Filename: GenericRequestTest.java
* Package: com.mytalk.server.logic.processing.request_processor
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

package com.mytalk.server.logic.processing.request_processor;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.mytalk.server.EnvironmentSetter;
import com.mytalk.server.logic.processing.request_processor.GenericRequest;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.ConnectionPack;
import com.mytalk.server.logic.shared.GiveCallPack;
import com.mytalk.server.logic.shared.ListPack;
import com.mytalk.server.logic.shared.UpdateListPack;
import com.mytalk.server.logic.shared.WorldPack;
import com.mytalk.server.logic.shared.model_client.Call;
import com.mytalk.server.logic.shared.model_client.PersonalData;
import com.mytalk.server.logic.shared.model_client.UserList;
import com.mytalk.server.logic.shared.model_client.WorldPersonalData;
import com.mytalk.server.logic.shared.model_client.WrapperCall;

public class GenericRequestTest {

	EnvironmentSetter envSetter=new EnvironmentSetter();
	
	@Before
	public void setTestEnvironment(){
		envSetter.cleanDB();
		envSetter.initDB();
	}
	
	@Test
	public void testCheckAuthenticationWellFormed() {
		Authentication auth=new Authentication("user0","user0","123.123.123.0");
		boolean esito=GenericRequest.checkAuthenticationWellFormed(auth);
		assertTrue("esito positivo",esito);
		
		auth.setUser(null);
		esito=GenericRequest.checkAuthenticationWellFormed(auth);
		assertFalse("esito negativo",esito);
		
		auth.setUser("user0");
		auth.setPwd(null);
		esito=GenericRequest.checkAuthenticationWellFormed(auth);
		assertFalse("esito negativo",esito);
		
		auth=null;
		esito=GenericRequest.checkAuthenticationWellFormed(auth);
		assertFalse("esito negativo",esito);
	}

	@Test
	public void testCheckConnectionPackWellFormed() {
		ConnectionPack pack= new ConnectionPack("myIp",0,"speakerIp",1,"sdp");
		boolean esito=GenericRequest.checkConnectionPackWellFormed(pack);
		assertTrue("esito positivo",esito);
		
		pack.setMyIp(null);
		esito=GenericRequest.checkConnectionPackWellFormed(pack);
		assertFalse("esito negativo",esito);
		
		pack.setMyIp("myIp");
		pack.setSpeakerIp(null);
		esito=GenericRequest.checkConnectionPackWellFormed(pack);
		assertFalse("esito negativo",esito);
		
		pack.setSpeakerIp("speakerIp");
		pack.setSdpCall(null);
		esito=GenericRequest.checkConnectionPackWellFormed(pack);
		assertFalse("esito negativo",esito);
		
		pack.setMyUserId(null);
		pack.setSdpCall("sdp");
		esito=GenericRequest.checkConnectionPackWellFormed(pack);
		assertFalse("esito negativo",esito);
		
		pack.setMyUserId(0);
		pack.setSpeakerUserId(null);
		esito=GenericRequest.checkConnectionPackWellFormed(pack);
		assertFalse("esito negativo",esito);
		
		pack=null;
		esito=GenericRequest.checkConnectionPackWellFormed(pack);
		assertFalse("esito negativo",esito);
	}
	
	@Test
	public void testCheckAnonymousConnectionPackWellFormed() {
		ConnectionPack pack= new ConnectionPack("myIp",null,"speakerIp",null,"sdp");
		boolean esito=GenericRequest.checkAnonymousConnectionPackWellFormed(pack);
		assertTrue("esito positivo",esito);
		
		pack.setMyIp(null);
		esito=GenericRequest.checkAnonymousConnectionPackWellFormed(pack);
		assertFalse("esito negativo",esito);
		
		pack.setMyIp("myIp");
		pack.setSpeakerIp(null);
		esito=GenericRequest.checkAnonymousConnectionPackWellFormed(pack);
		assertFalse("esito negativo",esito);
		
		pack.setSpeakerIp("speakerIp");
		pack.setSdpCall(null);
		esito=GenericRequest.checkAnonymousConnectionPackWellFormed(pack);
		assertFalse("esito negativo",esito);
		
		pack=null;
		esito=GenericRequest.checkAnonymousConnectionPackWellFormed(pack);
		assertFalse("esito negativo",esito);
	}

	@Test
	public void testCheckListPackWellFormed() {
		UserList userList=new UserList(0,"nome",new ArrayList<Integer>());
		List<UserList> listUserList=new ArrayList<UserList>();
		listUserList.add(userList);
		ListPack pack=new ListPack(listUserList);
		boolean esito=GenericRequest.checkListPackWellFormed(pack);
		assertTrue("esito positivo",esito);
		
		ListPack pack2=null;
		esito=GenericRequest.checkListPackWellFormed(pack2);
		assertFalse("esito negativo",esito);
		
		pack.setList(null);
		esito=GenericRequest.checkListPackWellFormed(pack);
		assertFalse("esito negativo",esito);
		
		userList.setList(null);
		pack.setList(listUserList);
		esito=GenericRequest.checkListPackWellFormed(pack);
		assertFalse("esito negativo",esito);
	}

	@Test
	public void testCheckPartialListPackWellFormed() {
		UserList userList=new UserList(0,"nome",null);
		List<UserList> listUserList=new ArrayList<UserList>();
		listUserList.add(userList);
		ListPack pack=new ListPack(listUserList);
		boolean esito=GenericRequest.checkPartialListPackWellFormed(pack);
		assertTrue("esito positivo",esito);
		
		ListPack pack2=null;
		esito=GenericRequest.checkListPackWellFormed(pack2);
		assertFalse("esito negativo",esito);
		
		pack.setList(null);
		esito=GenericRequest.checkListPackWellFormed(pack);
		assertFalse("esito negativo",esito);
	}

	@Test
	public void testCheckGiveCallPackWellFormed() {
		List<Call> listCall=new ArrayList<Call>();
		Call c=new Call(1,true,"startDate",0,0,0);
		listCall.add(c);
		WrapperCall wc=new WrapperCall(listCall);
		GiveCallPack pack=new GiveCallPack(wc);
		boolean esito=GenericRequest.checkGiveCallPackWellFormed(pack);
		assertTrue("esito positivo",esito);
		
		GiveCallPack pack2=null;
		esito=GenericRequest.checkGiveCallPackWellFormed(pack2);
		assertFalse("esito negativo",esito);
		
		pack.setWrapperCall(null);
		esito=GenericRequest.checkGiveCallPackWellFormed(pack);
		assertFalse("esito negativo",esito);
		
		wc.setList(null);
		pack.setWrapperCall(wc);
		esito=GenericRequest.checkGiveCallPackWellFormed(pack);
		assertFalse("esito negativo",esito);
		
		listCall.remove(0);
		wc.setList(listCall);
		esito=GenericRequest.checkGiveCallPackWellFormed(pack);
		assertFalse("esito negativo",esito);
	}

	@Test
	public void testCheckWorldPackWellFormed() {
		PersonalData pd=new PersonalData(0,"us","pwd","n","s","mail","md5","ip");
		WorldPersonalData wpd=new WorldPersonalData(pd);
		WorldPack pack= new WorldPack(null,wpd);
		boolean esito=GenericRequest.checkWorldPackWellFormed(pack);
		assertTrue("esito positivo",esito);
		
		wpd.setPersonalData(null);
		esito=GenericRequest.checkWorldPackWellFormed(pack);
		assertFalse("esito negativo",esito);
		
		pack.setWorldPersonalData(null);
		esito=GenericRequest.checkWorldPackWellFormed(pack);
		assertFalse("esito negativo",esito);
		
		pack=null;
		esito=GenericRequest.checkWorldPackWellFormed(pack);
		assertFalse("esito negativo",esito);
	}

	@Test
	public void testCheckUpdateListPackWellFormed() {
		UpdateListPack pack= new UpdateListPack("name","owner","newName");
		boolean esito=GenericRequest.checkUpdateListPackWellFormed(pack);
		assertTrue("esito positivo",esito);
		
		pack.setNewListName(null);
		esito=GenericRequest.checkUpdateListPackWellFormed(pack);
		assertFalse("esito negativo",esito);
		
		pack.setNewListName("newName");
		pack.setOwner(null);
		esito=GenericRequest.checkUpdateListPackWellFormed(pack);
		assertFalse("esito negativo",esito);
		
		pack.setOwner("owner");
		pack.setListName(null);
		esito=GenericRequest.checkUpdateListPackWellFormed(pack);
		assertFalse("esito negativo",esito);
		
		pack=null;
		esito=GenericRequest.checkUpdateListPackWellFormed(pack);
		assertFalse("esito negativo",esito);
	}

}
